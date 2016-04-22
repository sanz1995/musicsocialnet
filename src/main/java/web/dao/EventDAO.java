package web.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import web.vo.EventVO;

public class EventDAO {

	private Connection c;
	/**
	 * constructor por defecto
	 * @param c conexion a la base de datos
	 */
	public EventDAO(Connection c){
		this.c=c;
	}
	
	
	public void asistir(String email, String id) {
		try {
			//Conexion c = new Conexion();
			//Connection conexion = c.getConnection();
			Statement s = c.createStatement();
			s.execute(
					"INSERT INTO `asistir` VALUES ('" + email + "','" + id + "');");
		} catch (SQLException ex) {
			System.out.println("Error al insertar FAN" + ex.getMessage());
		}
	}
	
	public void dejarDeAsistir(String email, String id) {
		try {
			//Conexion c = new Conexion();
			//Connection conexion = c.getConnection();
			Statement s = c.createStatement();
			s.execute(
					"DELETE FROM asistir WHERE fan_email='" + email + "' AND evento_idevento=" + id + ";");
		} catch (SQLException ex) {
			System.out.println("Error al insertar FAN" + ex.getMessage());
		}
	}
	
	
	
	public List<EventVO> showUserEvents(String u) {
		try {
			
			Statement s = c.createStatement();
			ResultSet rs=s.executeQuery("select E.* from asistir A, evento E "
					+ "where A.evento_idevento=E.idevento AND A.fan_email=\""+u+"\";");
			List<EventVO> events=new ArrayList<EventVO>();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			while(rs.next()){
				if(rs.getString(2).compareTo(dateFormat.format(date))>=0){
					events.add(new EventVO(rs.getString(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getString(5)));
				}
			}
			return events;
		} catch (SQLException ex) {
			System.out.println("SQLException " + ex.getMessage());
			return null;
		}
	}
	
	public List<EventVO> showBandEvents(String b) {
		try {
			
			Statement s = c.createStatement();
			ResultSet rs=s.executeQuery("select E.* from realizar R, evento E "
					+ "where R.Evento_idevento=E.idevento AND R.banda_email=\""+b+"\";");
			List<EventVO> events=new ArrayList<EventVO>();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			while(rs.next()){
//				if(rs.getString(2).compareTo(dateFormat.format(date))>=0){
					events.add(new EventVO(rs.getString(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getString(5)));
//				}
			}
			return events;
		} catch (SQLException ex) {
			System.out.println("SQLException " + ex.getMessage());
			return null;
		}
	}
}
