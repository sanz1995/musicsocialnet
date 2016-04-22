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

	public Conexion() throws SQLException, ClassNotFoundException {
		Class.forName(driver); // Levanto el Driver
		conexion = DriverManager.getConnection(servidor, user, pass); // Establezco
																		// conexion
	}

	public Connection getConnection() {
		return conexion;
	}

}
