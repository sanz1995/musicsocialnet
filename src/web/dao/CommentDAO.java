package web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import web.Conexion;
import web.vo.CommentVO;

/**
 * Clase que implementa un patrón de acceso a BBDD de tipo Table Data Gateway,
 * en este caso, para la tabla de la BBDD que almacena los datos de un evento.
 * También implementan un Singleton, permitiendose una sola instancia
 * de esta clase en ejecución.
 */

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
	
	/**
	 * Función que se encarga de insertar un nuevo comentario en la BBDD en la tabla "comentario". 
	 * Si no puede insertarlos lanza una excepción.
	 * 
	 * @param texto Cadena de carácteres con el contenido del comentario del evento
	 * @param fecha Cadena de carácteres con la fecha en formato dd-mm-aaaa en la que se produjo el comentario
	 * @param fanEmail Cadena de carácteres con el email el usuario que ha comentado
	 * @param bandEmail Cadena de carácteres con el email del usuario banda sobre el que se ha comentado
	 */
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
	
	/**
	 * Función que se encarga de obtener todos los comentarios que se han realizado en el perfil de 
	 * la banda introducida como parámetros.
	 * 
	 * @param band Cadena de carácteres con el email del usuario banda sobre el que buscar sus comentarios
	 * @result Lista con objetos de tipo CommentVO en los que se almacenan los valores de cada comentario sobre el usuario banda
	 */
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
