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

import web.dao.FanDAO;
import web.exception.ErrorFanException;

/**
 * Clase servlet que se encarga de gestionar la interacción entre la interfaz
 * web y la base de datos cuando se produce una petición de tipo get o post
 * en el formulario de registro en la red social por parte de un usuario de tipo 
 * fan a traves de la interfaz.
 */
@WebServlet("registroFan")
public class RegisterFanServlet extends HttpServlet {
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

		String nombre = request.getParameter("fan");
		String email = request.getParameter("email");
		String password = request.getParameter("paswd");
		String repassword = request.getParameter("rpaswd");

		if ((nombre == null) || (nombre.trim().equals(""))) {
			errores.add("nombre");
		}
		if ((email == null) || (email.trim().equals(""))) {
			errores.add("email");
		}
		if ((password == null) || (password.trim().equals(""))) {
			errores.add("password");
		}
		if ((repassword == null) || (repassword.trim().equals(""))) {
			errores.add("rePassword");
		}
		if (!errores.contains("password") && (!repassword.equals(password))) {
			errores.add("reclave");
		}
		
		if (errores.isEmpty()) {
			try {
				FanDAO fanDAO = FanDAO.getDAO();
				//WebFachada model = WebFachada.getWebFachada();
				if (!fanDAO.existeFan(email)) {
					//Pongo a null foto de perfil, no se pide en registro
					fanDAO.addFan(nombre, password, null, email);
				} else {
					throw new ErrorFanException();
				}
				
				request.setAttribute("errores", null);
				HttpSession session = request.getSession();
				session.setAttribute("nombre", nombre);
				session.setAttribute("home","home_fan_news.jsp");
				session.setAttribute("email", email);
				response.sendRedirect("home_fan_news.jsp");
				session.setAttribute("fotoPerfil", null);
				session.setAttribute("esFan","true");
			} catch (ErrorFanException e) {
				request.setAttribute("error", "Lo sentimos, no se ha podido registrar al fan con"
						+ " el mail: "+email+".\n Ese fan ya se encuentra en el sistema.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			request.setAttribute("nombre", nombre);
			request.setAttribute("email", email);
			request.setAttribute("errores", errores);
			RequestDispatcher dispatcher = request.getRequestDispatcher("register_fan.jsp");
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
