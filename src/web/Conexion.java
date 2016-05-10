package web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que se encarga de realizar la conexión de las clases java con el
 * servior de base de datos mysql. 
 */
public class Conexion {

	private static String servidor = "jdbc:mysql://192.168.1.100:3306/webmusica";
	private static String user = "root";
	private static String pass = "root";
	private static String driver = "com.mysql.jdbc.Driver";
	
	private static Connection conexion;
	private static Conexion c;
	
	public Conexion(){
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(servidor, user, pass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Conexion getConexion(){
		if(c==null){
			return new Conexion();
		}else{
			return c;
		}
	}
	
	public Connection getConnection(){
		return conexion;
	}
}
