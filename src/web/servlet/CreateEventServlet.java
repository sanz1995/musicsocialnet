package web.servlet;

import web.dao.EventDAO;
import web.vo.EventVO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase servlet que se encarga de gestionar la interacción entre la interfaz
 * web y la base de datos cuando se produce una petición de tipo get o post
 * en el formulario de acceso a la red social a traves de la interfaz.
 */
@WebServlet
public class CreateEventServlet extends HttpServlet{
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
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<String> errores = new ArrayList<String>();

        String nombre = request.getParameter("nombreEvento");
        String banda = request.getParameter("banda");
        String lugar = request.getParameter("lugar");
        String fecha = request.getParameter("fecha");
        String tiempo = request.getParameter("time") + ":00";
        

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

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if(fecha.compareTo(dateFormat.format(date)) < 0) {
            errores.add("fecha");
        }
        if (errores.isEmpty()) {
            EventDAO eventDAO = EventDAO.getDAO();
            eventDAO.crearEvento(new EventVO(nombre, banda, fecha, lugar,"0", tiempo));
            request.setAttribute("errores", null);
            response.sendRedirect("home_band_event.jsp");
        } else {
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
