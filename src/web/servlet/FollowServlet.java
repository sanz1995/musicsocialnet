package web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class FollowServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
 
    	//HttpSession session = request.getSession();

       // String fan = (String) session.getAttribute("email");    
	//	 String banda = request.getParameter("banda");

        
        //WebFachada w = WebFachada.getWebFachada();
        //w.seguirBanda(banda, fan);
        response.sendRedirect("home_fan_concert.jsp");
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