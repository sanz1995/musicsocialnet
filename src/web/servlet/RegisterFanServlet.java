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

import web.WebFachada;
import web.exception.ErrorFanException;
import web.vo.FanVO;

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
	 * se han rellenado correctamente, y no son nulos aquellos campos obligatorios.
	 * Y despues, si los datos son correctos intenta introducir el usuario fan
	 * a partir de los datos anteriores en la base de datos. Dependiendo de si el
	 * nuevo usuario es almacenado o no se devuelve como respuesta una interfaz web o otra.
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
			//Pongo a null foto de perfil, no se pide en registro
			FanVO fan = new FanVO(nombre, password, null, email);
			try {
				WebFachada model = WebFachada.getWebFachada();
				model.registrarFan(fan);
				request.setAttribute("errores", null);
				HttpSession session = request.getSession();
				session.setAttribute("nombre", nombre);
				session.setAttribute("home","home_fan_concert.jsp");
				response.sendRedirect("home_fan_concert.jsp");
			} catch (ErrorFanException e) {
				response.sendRedirect("index.html");
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
