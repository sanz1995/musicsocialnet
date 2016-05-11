package web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.dao.FanDAO;

import java.io.IOException;

@WebServlet
public class FollowServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	HttpSession session = request.getSession();

        String fan = (String) session.getAttribute("email");    
        String banda = request.getParameter("banda");
        boolean seguir = Boolean.parseBoolean(request.getParameter("follow"));

        FanDAO fanDAO = FanDAO.getDAO();
        if(seguir){
        	fanDAO.seguir(fan, banda);
        }else{
        	fanDAO.dejarDeSeguir(fan, banda);
        }
        
        response.sendRedirect("home_fan_groups.jsp");
    }

    /**
     * Funci�n que se encarga de llamar a la funci�n doPost de esta clase
     * y realizar las comprobaciones y pertinentes a los datos enviados en
     * la petici�n y devolver las respuestas, al igual que se hace con una
     * petici�n de tipo post.
     *
     * @param  request Objeto que provee informaci�n sobre la petici�n del cliente al servlet.
     * @param response Objeto que permite al servlet enviar una respuesta al cliente.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
