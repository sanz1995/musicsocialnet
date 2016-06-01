package web.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.dao.BandDAO;

/**
 * Clase servlet que se encarga de gestionar la interacción entre la interfaz
 * web y la base de datos cuando se produce una petición de tipo get o post
 * en el formulario de registro en la red social por parte de un usuario de tipo 
 * fan a traves de la interfaz.
 */
@WebServlet
public class SearchServlet  extends HttpServlet{
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
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
 		String banda = request.getParameter("banda");
 		String[] generos = request.getParameter("generos").split(",");
 		String[] gSelected = request.getParameter("gSelected").split(",");	
 		
 		ArrayList<String> generosBuscar = new ArrayList<String>();

 		for(int i=0;i<gSelected.length;i++){
 			if(gSelected[i].equals("true")){
 				generosBuscar.add(generos[i]);
 			}
 		}

 		BandDAO bandDAO = BandDAO.getDAO();
        HttpSession session = request.getSession();
        session.setAttribute("bandas", bandDAO.search(banda, generosBuscar));
        response.sendRedirect("search.jsp");
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
