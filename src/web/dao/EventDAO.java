package web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import web.Conexion;
import web.vo.BandVO;
import web.vo.EventVO;

/**
 * Clase que implementa un patr�n de acceso a BBDD de tipo Table Data Gateway,
 * en este caso, para la tabla de la BBDD que almacena los datos de un evento.
 * Tambi�n implementan un Singleton, permitiendose una sola instancia
 * de esta clase en ejecuci�n.
 */

public class EventDAO {

	private static EventDAO eventDAO;
	
	private Conexion c;
	
	public EventDAO(){
		c = Conexion.getConexion();
		eventDAO = this;
	}
	public static EventDAO getDAO(){
		if(eventDAO==null){
			return new EventDAO();
		}else{
			return eventDAO;
		}
	}
	
	/**
	 * Funci�n que se encarga de insertar los datos de un evento en la BBDD 
	 * en la tabla "evento". 
	 * Si no puede insertarlos lanza una excepci�n.
	 * 
	 * @param nombre Objeto de tipo EventVO que contiene la informaci�n de un evento
	 *  que se ha de almacenar en la BBDD.
	 */
	public void crearEvento(EventVO e) {
		try {
			Statement s = c.getConnection().createStatement();
			s.execute(
					"INSERT INTO evento (nombreevento,bandaemail,fecha,lugar,nasistentes,hora)"
					+ " VALUES ('" + e.getNombre() + "','" + e.getBanda() +
					"'," + e.getFecha()  + ",'" + e.getLugar() + "'," + e.getNumAsistentes() 
					+ "," + e.getHora()  + ");");
		} catch (SQLException ex) {
			System.out.println("Error al insertar Evento" + ex.getMessage());
		}
	}
	
	/**
	 * Funci�n que se encarga de indicar la asistencia de un fan a un evento en 
	 * la BBDD. 
	 * Si no puede insertarlo lanza una excepci�n.
	 * 
	 * @param email Cadena de caracteres que identifica al usuario de tipo 
	 * fan a comprobar que va a asistir al evento.
	 * @param id Cadena de caracteres que identifica al evento al que el fan quiere asistir.
	 */
	public void asistir(String email, String id) {
		try {
			Statement s = c.getConnection().createStatement();
			s.execute("INSERT INTO `asistir` VALUES ('" + email + "','" + id + "');");
		} catch (SQLException ex) {
			System.out.println("Error al insertar FAN" + ex.getMessage());
		}
	}
	
	/**
	 * Funci�n que se encarga de indicar la eliminaci�n de la asistencia de un fan
	 * a un evento en la BBDD. 
	 * Si no puede insertarlo lanza una excepci�n.
	 * 
	 * @param email Cadena de caracteres que identifica al usuario de tipo 
	 * fan a comprobar que va a asistir al evento.
	 * @param id Cadena de caracteres que identifica al evento al que el fan quiere
	 * dejar de asistir.
	 */
	public void dejarDeAsistir(String email, String id) {
		try {
			//Conexion c = new Conexion();
			//Connection conexion = c.getConnection();
			Statement s = c.getConnection().createStatement();
			s.execute(
					"DELETE FROM asistir WHERE fan_email='" + email + "' AND evento_idevento=" + id + ";");
		} catch (SQLException ex) {
			System.out.println("Error al insertar FAN" + ex.getMessage());
		}
	}
	
	/**
	 * Funci�n que se comprueba si el email del fan introducido como par�metro
	 * ha indicado anteriormente y se ha almacenado en la BBDD que va a asistir
	 * al evento indicado en id.
	 * 
	 * @param email Cadena de caracteres que identifica al usuario de tipo 
	 * fan que se quiere comprobar si ya asiste al evento o no.
	 * @param id Cadena de caracteres que identifica al evento al que el fan 
	 * esta asistiendo o no
	 * @return Booleano con valor true que indica que el fan habia indicado 
	 * su asistencia al evento o a false si no lo habia indicado. 
	 */
	public boolean asiste(String email,String id){
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs=s.executeQuery("select * from asistir "
					+ "where evento_idevento="+id+" AND fan_email=\""+email+"\";");
			
			return rs.next();
		} catch (SQLException ex) {
			System.out.println("SQLException " + ex.getMessage());
			return false;
		}
	}
	
	/**
	 * Funci�n que se busca los pr�ximos eventos que tiene planeado realizar la banda
	 * introducida como par�metros segun la tabla "evento" de la BBDD.
	 * 
	 * @param b Cadena de caracteres que identifica al usuario de tipo 
	 * banda del que se quieren buscar sus pr�ximos eventos.
	 * @return Lista de objetos EventVO en los que se almacenan los eventos de la banda
	 * introducida como par�metro. 
	 */
	public List<EventVO> proximosEventosBanda(String b) {
		try {
			Statement s = c.getConnection().createStatement();
			ResultSet rs=s.executeQuery("select * from evento "
					+ "where bandaemail=\""+b+"\" order by fecha;");
			List<EventVO> events=new ArrayList<EventVO>();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			while(rs.next()){
				if(rs.getString(4).compareTo(dateFormat.format(date))>=0){
					events.add(new EventVO(rs.getString(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
				}
			}
			return events;
		} catch (SQLException ex) {
			System.out.println("SQLException " + ex.getMessage());
			return null;
		}
	}
	
	/**
	 * Funci�n que se busca los pr�ximos eventos que tiene indicado su asistencia
	 * el fan introducido como par�metros segun la tabla "asistir" de la BBDD.
	 * 
	 * @param u Cadena de caracteres que identifica al usuario de tipo 
	 * fan del que se quieren buscar sus pr�ximos eventos.
	 * @return Lista de objetos EventVO en los que se almacenan los eventos del fan
	 * introducida como par�metro. 
	 */
	public List<EventVO> proximosEventosFan(String u) {
		try {
			
			Statement s = c.getConnection().createStatement();
			ResultSet rs=s.executeQuery("select E.* from asistir A, evento E "
					+ "where A.evento_idevento=E.idevento AND A.fan_email=\""+u+"\" order by E.fecha;");
			List<EventVO> events=new ArrayList<EventVO>();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			while(rs.next()){
				if(rs.getString(4).compareTo(dateFormat.format(date))>=0){
					events.add(new EventVO(rs.getString(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
				}
			}
			return events;
		} catch (SQLException ex) {
			System.out.println("SQLException " + ex.getMessage());
			return null;
		}
	}

	/**
	 * Función que se busca los próximos eventos de las bandas seguidas por
	 * el fan pasado como parámetro.
	 *
	 * @param user Cadena de caracteres que identifica al usuario de tipo
	 * fan del que se quieren buscar sus próximos eventos.
	 * @return Lista de objetos EventVO en los que se almacenan los eventos del fan
	 * introducida como par�metro.
	 */
	public List<EventVO> todosEventosFan (String user) {

		FanDAO fanDAO = FanDAO.getDAO();
		List<BandVO> bands = fanDAO.siguiendoA(user);
		List<EventVO> events = new ArrayList<EventVO>();

		for (BandVO band:bands) {
			List<EventVO> eventsBand = proximosEventosBanda(band.getEmail());
			events.addAll(eventsBand);
		}
		
		return events;
	}
		
}
