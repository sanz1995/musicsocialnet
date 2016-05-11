package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.dao.BandDAO;
import web.dao.FanDAO;
import web.vo.*;
import web.exception.*;

/**
 * Clase servlet que se encarga de gestionar la interacción entre la interfaz
 * web y la base de datos cuando se produce una petición de tipo get o post
 * en el formulario de acceso a la red social a traves de la interfaz.
 */
@WebServlet("loginFan")
public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Función que se encarga de comprobar si los datos enviados en la petición
	 * se han rellenado correctamente. Y tras esto, si los datos son correctos 
	 * comprobar si el mail usado en un login esta registrado en la base de 
	 * datos o no. Devolviendo como respuesta una interfaz web o otra.
	 * 
	 * @param  request Objeto que provee información sobre la petición del cliente al servlet.
	 * @param response Objeto que permite al servlet enviar una respuesta al cliente.
     */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ArrayList<String> errores = new ArrayList<String>();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if ((email == null) || (email.trim().equals(""))) {
			errores.add("email");
		}
		if ((password == null) || (password.trim().equals(""))) {
			errores.add("password");
		}
		
		
		if (errores.isEmpty()) {
			try {
				FanDAO fanDAO = FanDAO.getDAO();
				BandDAO bandDAO = BandDAO.getDAO();

				
				UserVO user=fanDAO.buscarFan(email);
				if(user==null){
					user=bandDAO.buscarBanda(email);
					if(user==null){
						throw new LoginException();
					}
				}
		
				if(!user.getPassword().equals(password)){
					throw new LoginException();
				}
				
				request.setAttribute("errores", null);
				HttpSession session = request.getSession();
				session.setAttribute("nombre", user.getNombre());
				session.setAttribute("email", email);
				session.setAttribute("fotoPerfil", user.getFotoPerfil());
				if(user instanceof FanVO){
					session.setAttribute("home","home_fan_concert.jsp");
					session.setAttribute("esFan","true");
					response.sendRedirect("home_fan_concert.jsp");
				}else{
					session.setAttribute("home","home_band_info.jsp");
					session.setAttribute("desc", ((BandVO)user).getDescripcion());
					session.setAttribute("generos", ((BandVO)user).getGeneros());
					session.setAttribute("esFan","false");
					response.sendRedirect("home_band_info.jsp");
				}
			} catch (LoginException e) {
				response.sendRedirect("index.jsp");
			}
			catch (ErrorBandException e) {
				response.sendRedirect("index.jsp");
			}
		} else {
			request.setAttribute("errores", errores);
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * Función que se encarga de llamar a la función doPost de esta clase
	 * y realizar las comprobaciones y pertinentes a los datos enviados en 
	 * la petición y devolver las respuestas, al igual que se hace con una
	 * petición de tipo post.
	 * 
	 * @param  request Objeto que provee información sobre la petición del cliente al servlet.
	 * @param response Objeto que permite al servlet enviar una respuesta al cliente.
     */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}
}
