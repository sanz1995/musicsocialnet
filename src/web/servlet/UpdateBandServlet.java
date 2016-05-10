package web.servlet;

import web.WebFachada;
import web.exception.ErrorBandException;
import web.vo.BandVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("updateBanda")
public class UpdateBandServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<String> errores = new ArrayList<String>();

        String nombre = request.getParameter("band");

        String password = request.getParameter("paswd");
        String repassword = request.getParameter("rpaswd");
        String [] generos = request.getParameterValues("genero");

        if ((nombre == null) || (nombre.trim().equals(""))) {
            errores.add("nombre");
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
        if (generos == null || generos.length == 0 ) {
            errores.add("genero1");
        }
        if (generos != null && generos.length > 3) {
            errores.add("genero2");
        }

        if (errores.isEmpty()) {
            ArrayList<String> generosArray = new ArrayList<String>();
            for (int i = 0; i < generos.length; i++) {
                generosArray.add(generos[i]);
            }
            try {
                WebFachada model = WebFachada.getWebFachada();
                HttpSession session = request.getSession();
                String email = (String) session.getAttribute("email");
                //Pongo a null foto de perfil y descripción, no se pide en registro
                BandVO banda = new BandVO(nombre, password, null, email, generosArray, null);
                model.updateBand(banda);
                request.setAttribute("errores", null);

                session.setAttribute("nombre", nombre);
                session.setAttribute("generos", generosArray);
                session.setAttribute("home","home_band_info.jsp");
                response.sendRedirect("home_band_info.jsp");
            } catch (ErrorBandException e) {
                response.sendRedirect("index.html");
            }
        } else {
            request.setAttribute("nombre", nombre);
            request.setAttribute("errores", errores);
            RequestDispatcher dispatcher = request.getRequestDispatcher("update_band.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}