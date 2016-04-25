package web.servlet;

import web.WebFachada;
import web.vo.EventVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet
public class CreateEventServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<String> errores = new ArrayList<String>();

        String nombre = request.getParameter("nombreEvento");
        String banda = request.getParameter("banda");
        String lugar = request.getParameter("lugar");
        String fecha = request.getParameter("fecha");
        String tiempo = request.getParameter("time");

        if ((nombre == null) || (nombre.trim().equals(""))) {
            errores.add("nombre");
        }
        if ((lugar == null) || (lugar.trim().equals(""))) {
            errores.add("lugar");
        }
        if ((fecha == null) || (fecha.trim().equals(""))) {
            errores.add("fecha");
        }
        if ((tiempo == null) || (tiempo.trim().equals(""))) {
            errores.add("tiempo");
        }

        if (errores.isEmpty()) {
            EventVO evento = new EventVO(nombre, banda, fecha, lugar, "0", tiempo);
            WebFachada model = WebFachada.getWebFachada();
            model.crearEvento(evento);
            HttpSession session = request.getSession();
            request.setAttribute("errores", null);
            response.sendRedirect("home_band_event.jsp");
        } else {
            request.setAttribute("nombre", nombre);
            request.setAttribute("lugar", lugar);
            request.setAttribute("errores", errores);
            RequestDispatcher dispatcher = request.getRequestDispatcher("home_band_event.jsp");
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
