package web.servlet;

import web.dao.FanDAO;
import web.exception.ErrorFanException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase servlet que se encarga de gestionar la interacción entre la interfaz
 * web y la base de datos cuando se produce una petición de tipo get o post
 * en el formulario de registro en la red social por parte de un usuario de tipo 
 * fan a traves de la interfaz.
 */
@WebServlet("updateFan")
public class UpdateFanServlet extends HttpServlet {
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

        try {
            ArrayList<String> errores = new ArrayList<String>();
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");

            String nombre = request.getParameter("fan");
            String fotoPerfil = request.getParameter("fotoPerfil");
            String passwordA = request.getParameter("paswdA");
            String password = request.getParameter("paswd");
            String repassword = request.getParameter("rpaswd");

            FanDAO fanDAO = FanDAO.getDAO();

            if ((nombre == null) || (nombre.trim().equals(""))) {
                errores.add("nombre");
            }
            if (passwordA != null && !passwordA.trim().equals("") && !fanDAO.buscarFan(email).getPassword().equals(passwordA)){
                errores.add("clave");
            }
            if ((passwordA == null || passwordA.trim().equals("")) && password != null && !password.trim().equals("")) {
                errores.add("noclave");
            }
            if (passwordA != null && !passwordA.trim().equals("") && (password == null || password.trim().equals(""))) {
                errores.add("password");
            }
            if (!errores.contains("password") && (!repassword.equals(password))) {
                errores.add("reclave");
            }

            if (errores.isEmpty()) {               
                if ((fotoPerfil == null) || (fotoPerfil.trim().equals(""))) {
                	fotoPerfil=null;
                }
                
                if (passwordA == null || passwordA.trim().equals("")) {
                    fanDAO.updateFan(nombre, fanDAO.buscarFan(email).getPassword(), fotoPerfil, email);

                } else {
                    fanDAO.updateFan(nombre, password, fotoPerfil, email);

                }

                request.setAttribute("errores", null);
                session.setAttribute("email", email);
                session.setAttribute("fotoPerfil", fotoPerfil);
                session.setAttribute("nombre", nombre);
                session.setAttribute("home","home_fan_concert.jsp");
                response.sendRedirect("home_fan_concert.jsp");
            } else {
                request.setAttribute("nombre", nombre);
                request.setAttribute("errores", errores);
                RequestDispatcher dispatcher = request.getRequestDispatcher("update_fan.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ErrorFanException e) {
            e.printStackTrace();
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
