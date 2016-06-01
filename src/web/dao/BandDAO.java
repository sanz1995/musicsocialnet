package web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import web.Conexion;
import web.exception.ErrorBandException;
import web.vo.BandVO;

/**
 * Clase que implementa un patrón de acceso a BBDD de tipo Table Data Gateway,
 * en este caso, para la tabla de la BBDD que almacena los datos de un usuario
 * de tipo banda. También implementan un Singleton, permitiendose una sola instancia
 * de esta clase en ejecución.
 */
public class BandDAO{

	private static BandDAO bandDAO;
	
	private Conexion c;
	
	public BandDAO(){
		c = Conexion.getConexion();
		bandDAO = this;
	}
	public static BandDAO getDAO(){
		if(bandDAO==null){
			return new BandDAO();
		}else{
			return bandDAO;
		}
	}

	/**
	 * Función que se encarga de insertar los datos de una banda en la BBDD, incluidos los
	 * datos que se almacenan en la tabla banda y los de la tabla pertenece que indica los generos
	 * musicales que interpreta este usuario banda.
	 * Si no puede insertarlos lanza una excepción.
	 * 
	 * @param nombre Cadena de carácteres con el nombre de la banda
	 * @param password Cadena de carácteres con la contraseña de la banda
	 * @param fotoPerfil Cadena de carácteres con la URL de la im�gen de la banda
	 * @param canal Cadena de carácteres con la URL del canal de youtube de la banda
	 * @param email Cadena de carácteres con el email que identifica a la banda
	 * @param descripcion Cadena de carácteres con la descripción de la banda
	 * @param generos Lista dinámica de cadenas de caracteres con los generos de la banda
	 */
	
	public void addBand(String nombre, String password, String fotoPerfil, String canal, String email, String descripcion, ArrayList<String> generos){
		try {
			Statement s = c.getConnection().createStatement();
			if(fotoPerfil == null)
			s.execute(
					"INSERT INTO `banda` (`nombre`,`password`,`canal`,`email`,`descripcion`)"
							+ " VALUES ('" + nombre + "','" + password + "','"
							+ canal + "','" + email + "','" + descripcion + "');");
			else
				s.execute(
						"INSERT INTO `banda` (`nombre`,`password`,`fotoperfil`,`canal`,`email`,`descripcion`)"
								+ " VALUES ('" + nombre + "','" + password + "','"
								+ fotoPerfil + "','" + canal + "','" + email + "','" + descripcion + "');");

			for (String genero : generos) {
				s.execute("INSERT INTO `pertenecer` (`banda_email`,`genero_nombre`)"
						+ " VALUES ('" + email + "','" + genero + "');");
			}
		} catch (SQLException ex) {
			System.out.println("Error al insertar BANDA");
		}
	}

	/**
	 * Función que se encarga de comprobar si el email de la banda introducido
	 * por parametros se encuentra almacenado en la BBDD.
	 * 
	 * @param email Cadena de caracteres que identifica al usuario de tipo 
	 * banda a comprobar.
	 * @return Variable booleana con valor true si la banda indicada se encuentra
	 * en la BBDD y false si no está almacenada en esta.
	 */
	public boolean existeBanda(String email){
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT email FROM banda WHERE email='" + email + "'");
			while (rs.next()) {
				if (rs.getString("email") != null) {
					rs.close();
					return true;
				}
			}
				rs.close();
				return false;
		} catch (SQLException ex) {
			System.out.println("Error al comprobar banda");
			return false;
		}
	}

	/**
	 * Función que se encarga de realizar la query a la base de datos que 
	 * permite obtener todos los generos musicales almacenados en esta.
	 * 
	 * @return Lista con los generos musicales almacenado en la tabla de la
	 * base de datos "genero".
	 */
	public ArrayList<String> totalGeneros () {
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM genero");
			ArrayList<String> generos = new ArrayList<String>();
			while (rs.next()) {
				generos.add(rs.getString("nombre"));				
			}
			rs.close();
			return generos; 
		} catch (SQLException ex) {
			System.out.println("Error al comprobar los generos");
			return null;
		}
	}	

	/**
	 * Función que se encarga de realizar la query a la base de datos que 
	 * permite obtener todos los generos musicales que pertenecen a la banda indicada
	 * como parámetro.
	 * 
	 * @param email Cadena de caracteres que identifica al usuario de tipo 
	 * banda a comprobar.
	 * @return Lista con los generos musicales almacenados en la tabla pertenecer con 
	 * relación a la banda introducida por parámetros.
	 */
	public ArrayList<String> getGeneros (String email) {
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT genero_nombre FROM banda, pertenecer WHERE email='"+email+"' AND email=banda_email");
			ArrayList<String> generos = new ArrayList<String>();
			while (rs.next()) {
				generos.add(rs.getString("genero_nombre"));				
			}
			rs.close();
			return generos; 
		} catch (SQLException ex) {
			System.out.println("Error al comprobar los generos");
			return null;
		}
	}


	/**
	 * Función que se encarga de buscar en la base de datos los datos del usuario de
	 * tipo banda que se identifican a partir del email introducido como parámetro
	 * en la función.
	 * 
	 * @param email Cadena de caracteres que identifica al usuario de tipo 
	 * banda a buscar.
	 * @return Objeto de tipo FanVO con toda la información almacenada sobre
	 * un usuario de tipo banda en la tabla de la base de datos "banda" que se
	 * identifica a partir del email introducido como parámetro.
	 */
	public BandVO buscarBanda(String email) throws ErrorBandException{
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM banda WHERE email='" + email + "'");
			BandVO res;
			if (rs.next()) {
				res = new BandVO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),email, getGeneros(email), rs.getString(6));
			}else{
				res=null;
			}
			rs.close();
			return res;
		} catch (SQLException ex) {
			System.out.println("Error SQL al comprobar banda");
			return null;
		}
	}

	/**
	 * Función que se encarga de actualiza en la BBDD el atributo información 
	 * de la banda introducida como segundo parámetro.
	 *  
	 * @param email Cadena de caracteres que representa el email que identifica a una banda
	 * @param info Cadena de caracteres con la información a actualizar en la BBDD sobre la banda
	 */
	public void updateInfo(String email, String info) throws ErrorBandException{
		try {

			Statement s = c.getConnection().createStatement();
			System.out.println("Email banda:  "+email);
			System.out.println("Info banda:  "+info);
			s.execute("UPDATE banda SET descripcion='"+info+"' WHERE email='" + email + "'");
		} catch (SQLException ex) {
			System.out.println("Error SQL al actualizar info banda");
			throw new ErrorBandException();
		}
	}
	
	/**
	 * Función que se encarga de hacer una búsqueda en la BBDD sobre las 
	 * bandas que cumplen con la keyWord introducida como parámetro.
	 *  
	 * @param keyWord Cadena de caracteres que representa el nombre de la banda introducido en la búsqueda
	 * @param generos Cadena de caracteres que representa el nombre de la banda introducido en la búsqueda
	 * @return Lista de objetos de tipo Banda con las bandas obtenidas como respuesta a la query
	 */
	public List<BandVO> search(String keyWord, ArrayList<String> generos){
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs;
			String query;
			if (keyWord == null && (generos == null || generos.size()==0))
				rs = s.executeQuery("SELECT * FROM banda");
			else if (keyWord != null && (generos == null || generos.size()==0))
				rs = s.executeQuery("SELECT * FROM banda WHERE UPPER(nombre) "
						+ "LIKE UPPER('%"+keyWord+"%')");
			else if (keyWord == null && generos.size()>0){
				query = "SELECT DISTINCT banda.* FROM banda, pertenecer WHERE email=banda_email AND (";
				for(int i=0; i<generos.size();i++){
					if(i!=generos.size()-1)
						query+=" UPPER(genero_nombre) LIKE UPPER('%"+generos.get(i)+"%') OR";
					else
						query+=" UPPER(genero_nombre) LIKE UPPER('%"+generos.get(i)+"%'))";
				}
				System.out.println(query);
				rs = s.executeQuery(query);
			}
			else{ //Se han introducido keyword y generos por los que filtrar
				query = "SELECT DISTINCT banda.* FROM banda, pertenecer "
						+ "WHERE email=banda_email AND UPPER(nombre) "
						+ "LIKE UPPER('%"+keyWord+"%') AND (";
				for(int i=0; i<generos.size();i++){
					if(i!=generos.size()-1)
						query+=" UPPER(genero_nombre) LIKE UPPER('%"+generos.get(i)+"%') OR";
					else
						query+=" UPPER(genero_nombre) LIKE UPPER('%"+generos.get(i)+"%'))";
				}
				System.out.println(query);
				rs = s.executeQuery(query);
			}

			List<BandVO> bands=new ArrayList<BandVO>();
			while(rs.next()){
				bands.add(new BandVO(rs.getString(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getString(5),getGeneros(rs.getString(5)),rs.getString(6)));
			}
			rs.close();
			return bands; 
		} catch (SQLException ex) {
			System.out.println("Error al buscar bandas");
			return null;
		}
	}
	
	/**
	 * Función que se encarga de hacer actualizar en la tabla de la BBDD que almacena
	 * las banda la información introducida en el parámetro "band" y de actualizar en
	 * la tabla "pertenecer" que géneros musicales se relacionan con la banda. 
	 *  
	 * @param nombre Cadena de carácteres con el nombre de la banda
	 * @param password Cadena de carácteres con la contraseña de la banda
	 * @param fotoPerfil Cadena de carácteres con la URL de la imágen de la banda
	 * @param canal Cadena de carácteres con la URL del canal de youtube de la banda
	 * @param email Cadena de carácteres con el email que identifica a la banda
	 * @param generos Lista dinámica de cadenas de caracteres con los generos de la banda
	 */	
	public void updateBand(String nombre, String password, String fotoPerfil, String canal, String email, ArrayList<String> generos){
		try {
			Statement s = c.getConnection().createStatement();
			s.execute(
					"UPDATE banda SET nombre='" + nombre + "', fotoperfil='" + 
			fotoPerfil + "', canal='" + canal + "', password='" +
							password + "' " +
							"WHERE email='" + email + "';");

			if (generos != null) {
				s.execute(
						"DELETE FROM pertenecer WHERE banda_email='" + email + "';");
				for (String genero : generos) {
					s.execute("INSERT INTO pertenecer (banda_email,genero_nombre)"
							+ " VALUES ('" + email + "','" + genero + "');");
				}
			}
		} catch (SQLException ex) {
			System.out.println("Error al insertar BANDA" + ex.getMessage());
		}
	}
	
}
