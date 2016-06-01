package web.servlet;

import web.dao.BandDAO;
import web.exception.ErrorBandException;

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
@WebServlet("updateBanda")
public class UpdateBandServlet extends HttpServlet {
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

            String nombre = request.getParameter("band");
            String fotoPerfil = request.getParameter("fotoPerfil");
            String canal = request.getParameter("canal");
            String passwordA = request.getParameter("paswdA");
            String password = request.getParameter("paswd");
            String repassword = request.getParameter("rpaswd");
            String [] generos = request.getParameterValues("genero");

            BandDAO bandDAO = BandDAO.getDAO();

            if ((nombre == null) || (nombre.trim().equals(""))) {
                errores.add("nombre");
            }
            if (passwordA != null && !passwordA.trim().equals("") && !bandDAO.buscarBanda(email).getPassword().equals(passwordA)){
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
            if (generos != null && generos.length > 3) {
                errores.add("genero2");
            }

            if (errores.isEmpty()) {
                ArrayList<String> generosArray = new ArrayList<String>();
                if (generos == null || generos.length == 0 ) {
                    generosArray = null;
                } else {
                    for (int i = 0; i < generos.length; i++) {
                        generosArray.add(generos[i]);
                    }
                }
            
                if ((fotoPerfil == null) || (fotoPerfil.trim().equals(""))) {
                	fotoPerfil=null;
                }
                if ((canal == null) || (canal.trim().equals(""))) {
                	canal=null;
                }
                
                if (passwordA == null || passwordA.trim().equals("")) {
                    bandDAO.updateBand(nombre, bandDAO.buscarBanda(email).getPassword(), fotoPerfil,canal, email, generosArray);

                } else {
                    bandDAO.updateBand(nombre, password, fotoPerfil,canal, email, generosArray);

                }
                if (generosArray == null) {
                    generosArray = bandDAO.buscarBanda(email).getGeneros();
                }
                request.setAttribute("errores", null);

                session.setAttribute("email", email);
                session.setAttribute("fotoPerfil", fotoPerfil);
                session.setAttribute("canal", canal);
                session.setAttribute("nombre", nombre);
                session.setAttribute("generos", generosArray);
                session.setAttribute("home","home_band_info.jsp");
                response.sendRedirect("home_band_info.jsp");

            } else {
                request.setAttribute("nombre", nombre);
                request.setAttribute("errores", errores);
                RequestDispatcher dispatcher = request.getRequestDispatcher("update_band.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ErrorBandException e) {
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