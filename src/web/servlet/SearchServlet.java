package web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.WebFachada;

@WebServlet
public class SearchServlet  extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
 		 String banda = request.getParameter("banda");

        WebFachada w = WebFachada.getWebFachada();
        HttpSession session = request.getSession();
        session.setAttribute("bandas", w.search(banda));
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
