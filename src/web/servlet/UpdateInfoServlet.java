package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.*;
import web.exception.*;


/**
 * Clase servlet que se encarga de gestionar la interacción entre la interfaz
 * web y la base de datos cuando se produce una petición de tipo get o post
 * en el formulario de registro en la red social por parte de un usuario de tipo 
 * fan a traves de la interfaz.
 */
@WebServlet("infoBanda")
public class UpdateInfoServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Función que se encarga de modificar en la información de un usuario banda
	 * en la base de datos. Dependiendo de si el se consigue o no actualizar el valor
	 *  se mostrara la interfaz web con la info actualizada o un error.
	 * 
	 * @param  request Objeto que provee información sobre la petición del cliente al servlet.
	 * @param response Objeto que permite al servlet enviar una respuesta al cliente.
     */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String info = request.getParameter("info");

		//Actualizo información de la banda en la base de datos
		HttpSession session = request.getSession(); //Obtengo la sesion abierta del usuario
		try {
			WebFachada model = WebFachada.getWebFachada();
			model.updateInfoBand((String) session.getAttribute("email"), info);
			session.setAttribute("desc", info);
			response.sendRedirect("home_band_info.jsp");
		} catch (ErrorBandException e) {
			session.setAttribute("error", true);
			response.sendRedirect("home_band_info.jsp");
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
