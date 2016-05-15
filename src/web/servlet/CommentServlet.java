package web.servlet;

import web.dao.CommentDAO;
import web.vo.CommentVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

@WebServlet("comment")
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ArrayList<String> errores = new ArrayList<>();
        HttpSession session = request.getSession();
        String emailL = (String) session.getAttribute("email");

        String texto = request.getParameter("texto");
        String emailC = request.getParameter("emailC");

        if (texto == null || texto.trim().equals("")) {
            errores.add("texto");
        }

        if (errores.isEmpty()) {
            String fecha ="";
            Calendar hoy = Calendar.getInstance();

            fecha = hoy.YEAR + "-" + hoy.MONTH + "-" + hoy.DATE + " " + hoy.HOUR_OF_DAY + ":" +
                    hoy.MINUTE + ":" + hoy.SECOND;
            CommentVO comentario = new CommentVO(texto, fecha, emailL, emailC);
            CommentDAO commentDAO = CommentDAO.getDAO();
            commentDAO.comentar(comentario);

            request.setAttribute("errores", null);

            session.setAttribute("email", emailL);
            session.setAttribute("fotoPerfil", session.getAttribute("fotoPerfil"));
            session.setAttribute("nombre", session.getAttribute("nombre"));
            session.setAttribute("generos", session.getAttribute("generos"));
            session.setAttribute("home","home_band_info.jsp");
            response.sendRedirect("home_band_wall.jsp");
        } else {
            request.setAttribute("errores", errores);
            RequestDispatcher dispatcher = request.getRequestDispatcher("home_band_wall.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
