package web.servlet;

import web.dao.BandDAO;
import web.dao.CommentDAO;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("comment")
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            ArrayList<String> errores = new ArrayList<>();
            HttpSession session = request.getSession();
            String emailL = (String) session.getAttribute("email");

            String texto = request.getParameter("texto");
            String emailC = request.getParameter("emailC");

            if (texto == null || texto.trim().equals("")) {
                errores.add("texto");
            }
            
            System.out.println("Comentario de "+emailL+" a "+emailC+" dice "+texto);

            if (errores.isEmpty()) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                Date date = new Date();
                String fecha = dateFormat.format(date);
                
                CommentDAO commentDAO = CommentDAO.getDAO();
                commentDAO.comentar(texto, fecha, emailL, emailC);

                BandDAO bandDAO = BandDAO.getDAO();
                BandVO bandVO = bandDAO.buscarBanda(emailC);

                request.setAttribute("errores", null);

                session.setAttribute("email", emailL);
                session.setAttribute("emailB", emailC);
                session.setAttribute("fotoPerfil", bandVO.getFotoPerfil());
                session.setAttribute("nombre", bandVO.getNombre());
                session.setAttribute("generos", bandVO.getGeneros());
                if (emailL.equals(emailC)) {
                    session.setAttribute("home", "home_band_info.jsp");
                } else {
                    session.setAttribute("home", "home_fan_concert.jsp");
                }
                response.sendRedirect("home_band_wall.jsp");
            } else {
                request.setAttribute("errores", errores);
                RequestDispatcher dispatcher = request.getRequestDispatcher("home_band_wall.jsp");
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
