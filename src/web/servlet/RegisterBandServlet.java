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
import web.exception.ErrorBandException;

/**
 * Clase servlet que se encarga de gestionar la interacción entre la interfaz
 * web y la base de datos cuando se produce una petición de tipo get o post
 * en el formulario de acceso a la red social a traves de la interfaz.
 */
@WebServlet("registroBanda")
public class RegisterBandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
	 * Función que se encarga de comprobar si los datos enviados en la petici�n
	 * se han rellenado correctamente. Y tras esto, si los datos son correctos 
	 * comprobar si el mail usado en un login esta registrado en la base de 
	 * datos o no. Devolviendo como respuesta una interfaz web o otra.
	 * 
	 * @param  request Objeto que provee información sobre la petición del cliente al servlet.
	 * @param response Objeto que permite al servlet enviar una respuesta al cliente.
     */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ArrayList<String> errores = new ArrayList<String>();

		String nombre = request.getParameter("band");
		String email = request.getParameter("email");
		String password = request.getParameter("paswd");
		String repassword = request.getParameter("rpaswd");
		String [] generos = request.getParameterValues("genero");

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
		if (generos == null || generos.length == 0 ) {
			errores.add("genero1");
		}
		if (generos != null && generos.length > 3) {
			errores.add("genero2");
		}

		if (errores.isEmpty()) {
			ArrayList<String> generosArray = new ArrayList<String>();
			for (int i = 0; i < generos.length; i++) {
				generosArray.add(generos[i]);
			}
			try {
				BandDAO bandDAO = BandDAO.getDAO();
				
				if (!bandDAO.existeBanda(email)) {
					//Pongo a null foto de perfil y descripci�n, no se pide en registro
					bandDAO.addBand(nombre, password, null, null, email, null, generosArray);
				} else {
					throw new ErrorBandException();
				}
				
				
				request.setAttribute("errores", null);
				HttpSession session = request.getSession();
				session.setAttribute("email", email);
				session.setAttribute("nombre", nombre);
				session.setAttribute("generos", generosArray);
				session.setAttribute("home","home_band_info.jsp");
				session.setAttribute("fotoPerfil", null);
				response.sendRedirect("home_band_info.jsp");
				session.setAttribute("esFan","false");
			} catch (ErrorBandException e) {
				request.setAttribute("error", "Lo sentimos, no se ha podido registrar a la banda con"
						+ " el mail: "+email+".\n Esa banda ya se encuentra en el sistema.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
				dispatcher.forward(request, response);			
			}
		} else {
			request.setAttribute("nombre", nombre);
			request.setAttribute("email", email);
			request.setAttribute("errores", errores);
			RequestDispatcher dispatcher = request.getRequestDispatcher("register_band.jsp");
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
