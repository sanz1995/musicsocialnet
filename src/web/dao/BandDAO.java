package web.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.vo.*;
import web.exception.*;

/**
 * Clase que implementa un patrón de acceso a BBDD de tipo Table Data Gateway,
 * en este caso, para la tabla de la BBDD que almacena los datos de un usuario
 * de tipo banda.
 */
public class BandDAO {
	/**
	 * Función que se encarga de insertar los datos de una banda en la BBDD, incluidos los
	 * datos que se almacenan en la tabla banda y los de la tabla pertenece que indica los generos
	 * musicales que interpreta este usuario banda.
	 * Si no puede insertarlos lanza una excepción.
	 * 
	 * @param b Objeto de tipo BandVO que contiene la información de un usuario
	 * de tipo banda que se ha de almacenar en la BBDD.
	 */
	private Connection c;
	
	public BandDAO(Connection c){
		this.c=c;
	}

	public void addBand(BandVO b) {
		try {
			/*Conexion c = new Conexion();
			Connection conexion = c.getConnection();*/
			Statement s = c.createStatement();
			s.execute(
					"INSERT INTO `banda` (`nombre`,`password`,`fotoperfil`,`email`,`descripcion`)"
							+ " VALUES ('" + b.getNombre() + "','" + b.getPassword() + "','"
							+ b.getFotoPerfil() + "','" + b.getEmail() + "','" + b.getDescripcion() + "');");
			
			ArrayList<String> generos = b.getGeneros();
			for (String genero : generos) {
				s.execute("INSERT INTO `pertenecer` (`banda_email`,`genero_nombre`)"
						+ " VALUES ('" + b.getEmail() + "','" + genero + "');");
			}
			//conexion.close();
		} catch (SQLException ex) {
			System.out.println("Error al insertar BANDA" + ex.getMessage());
		}/* catch (ClassNotFoundException ex) {
			System.out.println("Error al insertar BANDA" + ex.getMessage());
		}*/
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
	public boolean existeBanda(String email) throws ErrorBandException{
		try {
			/*Conexion c = new Conexion();
			Connection conexion = c.getConnection();*/
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT email FROM banda WHERE email='" + email + "'");
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
			System.out.println("Error al comprobar banda");
			return false;
		}/* catch (ClassNotFoundException ex) {
			System.out.println("Error al comprobar banda");
			return false;
		}*/
	}

	/**
	 * Función que se encarga de realizar la query a la base de datos que 
	 * permiete obtener todos los generos músicales almacenador en esta.
	 * 
	 * @return Lista con los generos musicales almacenado en la tabla de la
	 * base de datos "genero".
	 */
	public ArrayList<String> totalGeneros () {
		try {
			/*Conexion c = new Conexion();
			Connection conexion = c.getConnection();*/
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM genero");
			ArrayList<String> generos = new ArrayList<String>();
			while (rs.next()) {
				generos.add(rs.getString("nombre"));				
			}
			rs.close();
			//conexion.close();
			return generos; 
		} catch (SQLException ex) {
			System.out.println("Error al comprobar los generos");
			return null;
		}/* catch (ClassNotFoundException ex) {
			System.out.println("Error al comprobar los generos");
			return null;
		}*/
	}	

	/**
	 * Función que se encarga de realizar la query a la base de datos que 
	 * permiete obtener todos los generos músicales almacenador en esta.
	 * 
	 * @return Lista con los generos musicales almacenado en la tabla de la

	 * base de datos "genero".
	 */
	public ArrayList<String> getGeneros (String email) {
		try {
			/*Conexion c = new Conexion();
			Connection conexion = c.getConnection();*/
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT genero_nombre FROM banda, pertenecer WHERE email='"+email+"' AND email=banda_email");
			ArrayList<String> generos = new ArrayList<String>();
			while (rs.next()) {
				generos.add(rs.getString("genero_nombre"));				
			}
			rs.close();
			//conexion.close();
			return generos; 
		} catch (SQLException ex) {
			System.out.println("Error al comprobar los generos");
			return null;
		}/* catch (ClassNotFoundException ex) {
			System.out.println("Error al comprobar los generos");
			return null;
		}*/
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
			/*Conexion c = new Conexion();
			Connection conexion = c.getConnection();*/
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM banda WHERE email='" + email + "'");
			BandVO res;
			if (rs.next()) {
				res = new BandVO(rs.getString(1),rs.getString(2),rs.getString(3),email, getGeneros(email), rs.getString(5));
			}else{
				res=null;
			}
			rs.close();
			//conexion.close();
			return res;
		} catch (SQLException ex) {
			System.out.println("Error SQL al comprobar banda");
			return null;
		}/* catch (ClassNotFoundException ex) {
			System.out.println("Error ClassNotFound al comprobar banda");
			return null;
		}*/
	}

	/**
	 * Función que se encarga de actualiza en la BBDD el atributo información de la banda
	 * introducida como segundo parámetro.
	 *  
	 * @param email Cadena de caracteres que representa el email que identifica a una banda
	 * @param info Cadena de caracteres con la información a actualizar en la BBDD sobre la banda
	 */
	public void updateInfo(String email, String info) throws ErrorBandException{
		try {

			Statement s = c.createStatement();
			System.out.println("Email banda:  "+email);
			System.out.println("Info banda:  "+info);
			s.execute("UPDATE banda SET descripcion='"+info+"' WHERE email='" + email + "'");
		} catch (SQLException ex) {
			System.out.println("Error SQL al actualizar info banda");
			throw new ErrorBandException();
		}
	}
	
	public List<BandVO> search(String keyWord){
		try {
			/*Conexion c = new Conexion();
			Connection conexion = c.getConnection();*/
			Statement s = c.createStatement();
			ResultSet rs;
			if (keyWord == null)
				rs = s.executeQuery("SELECT * FROM banda");
			else
				rs = s.executeQuery("SELECT * FROM banda WHERE UPPER(nombre) LIKE UPPER('%"+keyWord+"%')");
			List<BandVO> bands=new ArrayList<BandVO>();
			while(rs.next()){
				bands.add(new BandVO(rs.getString(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),getGeneros(rs.getString(4)),rs.getString(5)));
			}
			rs.close();
			//conexion.close();
			return bands; 
		} catch (SQLException ex) {
			System.out.println("Error al comprobar los generos");
			return null;
		}
	}
}
