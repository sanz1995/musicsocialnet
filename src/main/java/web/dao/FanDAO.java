package web.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.exception.ErrorFanException;
import web.vo.BandVO;
import web.vo.FanVO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que implementa un patrón de acceso a BBDD de tipo Table Data Gateway,
 * en este caso, para la tabla de la BBDD que almacena los datos de un usuario
 * de tipo fan.
 */
public class FanDAO {

	private Connection c;
	/**
	 * constructor por defecto
	 * @param c conexion a la base de datos
	 */
	public FanDAO(Connection c){
		this.c=c;
	}
	/**
	 * Función que se encarga de insertar los datos de un fan en la BBDD 
	 * en la tabla "fan". 
	 * Si no puede insertarlos lanza una excepción.
	 * 
	 * @param f Objeto de tipo FanVO que contiene la información de un usuario
	 * de tipo fan que se ha de almacenar en la BBDD.
	 */
	public void addFan(FanVO f) {
		try {
			//Conexion c = new Conexion();
			//Connection conexion = c.getConnection();
			Statement s = c.createStatement();
			s.execute(
					"INSERT INTO `fan` (`nombre`,`password`,`fotoperfil`,`email`)"
							+ " VALUES ('" + f.getNombre() + "','" + f.getPassword() + "','"
							+ f.getFotoPerfil() + "','" + f.getEmail() + "');");
		} catch (SQLException ex) {
			System.out.println("Error al insertar FAN" + ex.getMessage());
		}
	}
	/**
	 * Función que se encarga de comprobar si el email del fan introducido
	 * por parametros se encuentra almacenado en la BBDD.
	 * 
	 * @param email Cadena de caracteres que identifica al usuario de tipo 
	 * fan a comprobar.
	 * @return Variable booleana con valor true si el fan indicado se encuentra
	 * en la BBDD y false si no está almacenada en esta.
	 */
	public boolean existeFan(String email) throws ErrorFanException{
		try {
			/*Conexion c = new Conexion();
			Connection conexion = c.getConnection();*/
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT email FROM fan WHERE email='" + email + "'");
			while (rs.next()) {
				if (rs.getString("email") != null) {
					rs.close();
					//conexion.close();
					return true;
				}
			}
				rs.close();
				//conexion.close();
				return false;
		} catch (SQLException ex) {
			System.out.println("Error al comprobar fan");
			return false;
		}/* catch (ClassNotFoundException ex) {
			System.out.println("Error al comprobar fan");
			return false;
		}*/
	}	
	
	/**
	 * Función que se encarga de comprobar que usuarios de tipo banda son 
	 * seguidos por el usuario fan introducido, es decir, tienen una relación
	 * a traves de la tabla "seguir" en la base de datos.
	 *  
	 * @param u Cadena de carácteres que indica el email del fan sobre el
	 * que buscar las bandas seguidas por este.
	 * @return Lista con los usuarios de tipo banda encapsulados en el objeto 
	 * BandVO que se relacionan con el usuario introducido.
	 */
	public List<BandVO> showUserBands(String u) {
		try {
			/*Conexion c = new Conexion();
			Connection conexion = c.getConnection();*/
			Statement s = c.createStatement();
			
			ResultSet rs=s.executeQuery("select B.* from banda B, seguir S "
					+ "where B.email=S.banda_email and S.fan_email=\""+u+"\";");
			List<BandVO> bands=new ArrayList<BandVO>();
			while(rs.next()){
				bands.add(new BandVO(rs.getString(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getString(5)));
			}
			//conexion.close();
			return bands;
		} catch (SQLException ex) {
			System.out.println("SQLException " + ex.getMessage());
			return null;
		}/* catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException" + ex.getMessage());
			return null;
		}*/
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
	public FanVO buscarFan(String email) throws ErrorFanException{
		try {
			/*Conexion c = new Conexion();
			Connection conexion = c.getConnection();
			*/Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM fan WHERE email=\"" + email + "\";");
			FanVO res;
			if (rs.next()) {
				res = new FanVO(rs.getString(1),rs.getString(2),rs.getString(3),email);
			}else{
				res=null;
			}
			rs.close();
			//conexion.close();
			return res;
		} catch (SQLException ex) {
			System.out.println("Error al comprobar fan");
			return null;
		}/* catch (ClassNotFoundException ex) {
			System.out.println("Error al comprobar fan");
			return null;
		}*/
	}
}
