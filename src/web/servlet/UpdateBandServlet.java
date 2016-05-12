package web.servlet;

import web.dao.BandDAO;
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

        try {
            ArrayList<String> errores = new ArrayList<>();
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");

            String nombre = request.getParameter("band");
            String fotoPerfil = request.getParameter("fotoPerfil");
            String passwordA = request.getParameter("paswdA");
            String password = request.getParameter("paswd");
            String repassword = request.getParameter("rpaswd");
            String [] generos = request.getParameterValues("genero");

            BandDAO bandDAO = BandDAO.getDAO();
            BandVO user = bandDAO.buscarBanda(email);

            if ((nombre == null) || (nombre.trim().equals(""))) {
                errores.add("nombre");
            }
            if (passwordA != null && !passwordA.trim().equals("") && !user.getPassword().equals(passwordA)){
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
                ArrayList<String> generosArray = new ArrayList<>();
                if (generos == null || generos.length == 0 ) {
                    generosArray = null;
                } else {
                    for (int i = 0; i < generos.length; i++) {
                        generosArray.add(generos[i]);
                    }
                }
                BandVO banda;
            
                if ((fotoPerfil == null) || (fotoPerfil.trim().equals(""))) {
                	fotoPerfil=null;
                }
                
                
                if (passwordA == null || passwordA.trim().equals("")) {
                    banda = new BandVO(nombre, user.getPassword(), fotoPerfil, email, generosArray, null);
                } else {
                    banda = new BandVO(nombre, password, fotoPerfil, email, generosArray, null);
                }

                bandDAO.updateBand(banda);
                if (generosArray == null) {
                    generosArray = user.getGeneros();
                }
                request.setAttribute("errores", null);

                session.setAttribute("email", email);
                session.setAttribute("fotoPerfil", fotoPerfil);
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}