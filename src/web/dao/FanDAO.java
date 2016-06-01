package web.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import web.Conexion;
import web.exception.ErrorFanException;
import web.vo.BandVO;
import web.vo.FanVO;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que implementa un patrón de acceso a BBDD de tipo Table Data Gateway,
 * en este caso, para la tabla de la BBDD que almacena los datos de un usuario
 * de tipo fan. También implementan un Singleton, permitiendose una sola instancia
 * de esta clase en ejecución.
 */
public class FanDAO {

	private static FanDAO fanDAO;
	
	private Conexion c;
	
	public FanDAO(){
		c = Conexion.getConexion();
		fanDAO = this;
	}
	public static FanDAO getDAO(){
		if(fanDAO==null){
			return new FanDAO();
		}else{
			return fanDAO;
		}
	}
	
	/**
	 * Función que se encarga de insertar los datos de un fan en la BBDD 
	 * en la tabla "fan". 
	 * Si no puede insertarlos lanza una excepción.
	 * 
	 * @param nombre Cadena de carácteres con el nombre del fan
	 * @param password Cadena de carácteres con la contraseña del fan
	 * @param fotoPerfil Cadena de carácteres con la URL de la imágen del fan
	 * @param email Cadena de carácteres con el email que identifica al fan
	 */
	public void addFan(String nombre, String password, String fotoPerfil, String email) {
		try {
			Statement s = c.getConnection().createStatement();
			if(fotoPerfil == null)
			s.execute(
					"INSERT INTO `fan` (`nombre`,`password`,`email`)"
							+ " VALUES ('" + nombre + "','" + password
							 + "','" + email + "');");
			else
				s.execute(
						"INSERT INTO `fan` (`nombre`,`password`,`fotoperfil`,`email`)"
								+ " VALUES ('" + nombre + "','" + password + "','"
								+ fotoPerfil + "','" + email + "');");
		} catch (SQLException ex) {
			System.out.println("Error al insertar FAN" + ex.getMessage());
		}
	}
	
	
	/**
	 * Función que se encarga de insertar en la BBDD la relación de follow sobre 
	 * la banda y el fansobre introducidos como parámetro.
	 * 
	 * @param fan Cadena de carácteres que representa el nombre del fan seguidor
	 * @param band Cadena de carácteres que representa el nombre de la banda a seguir
	 */
	public void seguir(String fan,String band) {
		try {
			Statement s = c.getConnection().createStatement();
			s.execute("INSERT INTO seguir VALUES ('" + fan + "','" + band + "');");
		} catch (SQLException ex) {
			System.out.println("Error al seguir a banda" + ex.getMessage());
		}
	}
	
	/**
	 * Función que se encarga de insertar en la BBDD la relación de unfollow sobre 
	 * la banda y el fansobre introducidos como parámetro.
	 *  
	 * @param fan Cadena de carácteres que representa el nombre del fan seguidor
	 * @param band Cadena de carácteres que representa el nombre de la banda a seguir
	 */
	public void dejarDeSeguir(String fan,String band) {
		try {
			Statement s = c.getConnection().createStatement();
			s.execute("DELETE FROM seguir where fan_email='" + fan + "' AND banda_email='" + band + "';");
		} catch (SQLException ex) {
			System.out.println("Error al dejar de seguir a banda" + ex.getMessage());
		}
	}
	
	/**
	 * Función que se encarga de verificar si el fan introducido como parámetro sigue
	 * a la banda introducida o no en la BBDD.
	 *  
	 * @param fan Cadena de carácteres que representa el nombre del fan seguidor
	 * @param band Cadena de carácteres que representa el nombre de la banda seguida o no
	 */
	public boolean sigue(String fan,String band){
		try {
			System.out.println("En SIGUE");
			Statement s = c.getConnection().createStatement();
			ResultSet rs=s.executeQuery("SELECT fan_email from seguir WHERE fan_email='" + fan + "' AND banda_email='" + band + "';");
			System.out.println(rs);
			while (rs.next()) {
				System.out.println("FAN: "+rs.getString("fan_email"));
				if (rs.getString("fan_email") != null){
					rs.close();
					return true;
				}
			}
			rs.close();
			return false;
		} catch (SQLException ex) {
			System.out.println("SQLException " + ex.getMessage());
			return false;
		}
	}	
	
	/**
	 * Función que se encarga de comprobar si el email del fan introducido
	 * por parametros se encuentra almacenado en la BBDD.
	 * 
	 * @param email Cadena de caracteres que identifica al usuario de tipo 
	 * fan a comprobar.
	 * @return Variable booleana con valor true si el fan indicado se encuentra
	 * en la BBDD y false si no est� almacenada en esta.
	 */
	public boolean existeFan(String email){
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT email FROM fan WHERE email='" + email + "'");
			while (rs.next()) {
				if (rs.getString("email") != null) {
					rs.close();
					return true;
				}
			}
				rs.close();
				return false;
		} catch (SQLException ex) {
			System.out.println("Error al comprobar fan");
			return false;
		}
	}	
	
	/**
	 * Función que se encarga de comprobar que usuarios de tipo banda son 
	 * seguidos por el usuario fan introducido, es decir, tienen una relaci�n
	 * a traves de la tabla "seguir" en la base de datos.
	 *  
	 * @param user Cadena de carácteres que indica el email del fan sobre el
	 * que buscar las bandas seguidas por este.
	 * @return Lista con los usuarios de tipo banda encapsulados en el objeto 
	 * BandVO que se relacionan con el usuario introducido.
	 */
	public List<BandVO> siguiendoA(String user) {
		try {
			Statement s = c.getConnection().createStatement();
			
			ResultSet rs=s.executeQuery("select B.* from banda B, seguir S "
					+ "where B.email=S.banda_email and S.fan_email=\""+user+"\";");
			List<BandVO> bands=new ArrayList<BandVO>();
			while(rs.next()){
				bands.add(new BandVO(rs.getString(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getString(5),rs.getString(6)));
			}
			return bands;
		} catch (SQLException ex) {
			System.out.println("SQLException " + ex.getMessage());
			return null;
		}
	}
	
	
	/**
	 * Función que se encarga de buscar en la base de datos los datos del usuario de
	 * tipo fan que se identifican a partir del email introducido como parámetro
	 * en la función.
	 * 
	 * @param email Cadena de caracteres que identifica al usuario de tipo 
	 * fan a buscar.
	 * @return Objeto de tipo FanVO con toda la información almacenada sobre
	 * un usuario de tipo fan en la tabla de la base de datos "fan" que se
	 * identifica a partir del email introducido como parámetro.
	 */
	public FanVO buscarFan(String email) throws ErrorFanException {
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM fan WHERE email=\"" + email + "\";");
			FanVO res = null;
			if (rs.next()) {
				res = new FanVO(rs.getString(1),rs.getString(2),rs.getString(3),email);
			}
			rs.close();
			return res;
		} catch (SQLException ex) {
			System.out.println("Error al comprobar fan");
			return null;
		}
	}

	/**
	 * Función que se encarga de hacer actualizar en la tabla de la BBDD que almacena
	 * los fans la información introducida en el parámetro "fan".
	 *
	 * @param nombre Cadena de carácteres con el nombre del fan
	 * @param password Cadena de carácteres con la contraseña del fan
	 * @param fotoPerfil Cadena de carácteres con la URL de la imágen del fan
	 * @param email Cadena de carácteres con el email que identifica al fan
	 */
	public void updateFan(String nombre, String password, String fotoPerfil, String email) {
		try {
			Statement s = c.getConnection().createStatement();
			s.execute(
					"UPDATE fan SET nombre='" + nombre + "', fotoperfil='" + fotoPerfil + "', password='" + password + "' " +
							"WHERE email='" + email + "';");
		} catch (SQLException ex) {
			System.out.println("Error al actualizar fan" + ex.getMessage());
		}
	}
		
}
