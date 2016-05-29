package web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.Conexion;
import web.vo.CommentVO;

public class CommentDAO {

	private static CommentDAO commentDAO;
	
	private Conexion c;
	
	public CommentDAO(){
		c = Conexion.getConexion();
		commentDAO = this;
	}
	public static CommentDAO getDAO(){
		if(commentDAO==null){
			return new CommentDAO();
		}else{
			return commentDAO;
		}
	}
	
	public void comentar(String texto, String fecha, String fanEmail, String bandEmail) {
		try {
			Statement s = c.getConnection().createStatement();
			s.execute("INSERT INTO comentario "
					+ "(texto,fecha,fan_email,banda_email) VALUES "
					+ "('" + texto +"','" + fecha + 
					"','" + fanEmail +"','" + bandEmail + "')");
		} catch (SQLException ex) {
			System.out.println("Error al comentar" + ex.getMessage());
		}
	}
	
	public List<CommentVO> mostrarComentarios(String band){
		
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM comentario "
					+ "WHERE banda_email='" + band + "' ORDER BY fecha DESC");
			List<CommentVO> l = new ArrayList<CommentVO>();
			
			while(rs.next()){
					l.add(new CommentVO(rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getString(5)));
			}
			return l;
		} catch (SQLException ex) {
			System.out.println("Error al mostrar comentarios" + ex.getMessage());
			return null;
		}
	}
	
	
}
