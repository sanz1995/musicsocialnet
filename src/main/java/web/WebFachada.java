package web;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import web.dao.*;
import web.vo.*;
import web.exception.*;

/**
 * Clase que se encarga de interaccionar con las clases java que interactuan
 * con los servlets y las clases de tipo Tabla Data Gateway(DAO).
 */
public class WebFachada{
	
	/**
	 * Función que se encarga de comprobar si un fan existe o no. Añadiendo
	 * al fan en la base de datos si este no se encuentra ya en esta.
	 * Si el usuario fan ya existe se lanza una excepción.
	 *  
	 * @param fan Objeto de tipo FanVO que contiene la información de un usuario
	 * de tipo fan que se ha de almacenar en la BBDD.
	 */
	private static WebFachada w = new WebFachada();;
	
	private FanDAO fanDAO;
	private BandDAO bandDAO;
	private EventDAO eventDAO;
	/**
	 * Si el fan no existe, lo a�ade a la base de datos
	 */
	public WebFachada(){
		try {
			Connection c=(new Conexion()).getConnection();
			eventDAO =new EventDAO(c);
			fanDAO =new FanDAO(c);
			bandDAO =new BandDAO(c);
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}
	
	public static WebFachada getWebFachada(){
		return w;
	}
	
	/**
	 * Función que se encarga de comprobar si un fan existe o no. Añadiendo
	 * al fan en la base de datos si este no se encuentra ya en esta.
	 * Si el usuario fan ya existe se lanza una excepción.
	 *  
	 * @param fan Objeto de tipo FanVO que contiene la información de un usuario
	 * de tipo fan que se ha de almacenar en la BBDD.
	 */
	public void registrarFan(FanVO fan) throws ErrorFanException {
		if (!fanDAO.existeFan(fan.getEmail())) {
			fanDAO.addFan(fan);
		} else {
			throw new ErrorFanException();
		}
	}
	/**
	 * Función que se encarga de comprobar si una banda existe o no. Añadiendo
	 * a la banda en la base de datos si este no se encuentra ya en esta.
	 * Si el usuario banda ya existe se lanza una excepción.
	 *  
	 * @param band Objeto de tipo BandVO que contiene la información de un usuario
	 * de tipo banda que se ha de almacenar en la BBDD.
	 */
	public void registrarBanda (BandVO band) throws ErrorBandException {
		if (!bandDAO.existeBanda(band.getEmail())) {
			bandDAO.addBand(band);
		} else {
			throw new ErrorBandException();
		}
	}
	/**
	 * Función que se encarga de comprobar si el usuario introducido a traves
	 * del email y su contraseña existe en la BBDD y si es así comprobar
	 * si la contrasela introducida es correcta y por lo tanto se podría realizar
	 * un "inicio de sesión".
	 *  
	 * @param email Cadena de caracteres que identifica al usuario de tipo fan o 
	 * banda a comprobar.
	 * @param password Cadena de caracteres que indica la contraseña correspondiente 
	 * al mail introducido en el parámetro email.
	 */
	public UserVO iniciarSesion(String email,String password) throws LoginException, ErrorFanException, ErrorBandException {
		UserVO user=fanDAO.buscarFan(email);
		if(user==null){
			user=bandDAO.buscarBanda(email);
		}
		if(user.getPassword().equals(password)){
			return user;
		}else{
			throw new LoginException();
		}
		
	}
	public BandVO buscarBanda(String email){
		BandVO u = null;
		try {
			u = bandDAO.buscarBanda(email);
		} catch (ErrorBandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return u;
	}
	

	/**
	 * Función que se encarga de comprobar que usuarios de tipo banda estan 
	 * relacionados con el usuario fan introducido.
	 *  
	 * @param fan Objeto de tipo FanVO que contiene la información de un usuario
	 * de tipo fan que se ha de almacenar en la BBDD.
	 * @return Lista con los usuarios de tipo banda encapsulados en el objeto 
	 * BandVO que se relacionan con el usuario introducido.
	 */
	public List<BandVO> siguiendoA(String fan){
		return fanDAO.showUserBands(fan);
	}
	public List<String> obtenerGeneros(){
		return bandDAO.totalGeneros();
	}
	
	public List<EventVO> proximosEventos(UserVO u){
		if(u instanceof BandVO){
			return eventDAO.showBandEvents(u.getEmail());
		}else{
			return eventDAO.showUserEvents(u.getEmail());
		}
		
	}
	public void asistir(String email, String id){
		eventDAO.asistir(email, id);
	}
	
	public void dejarDeAsistir(String email, String id){
		eventDAO.dejarDeAsistir(email, id);
	}
	
	public void crearEvento(EventVO e){
		eventDAO.crearEvento(e);
	}
	
	public boolean asiste(String email, String id){
		return eventDAO.asiste(email, id);
	}
	
	/**
	 * Función que se encarga de actualiza en la BBDD el atributo información de la banda
	 * introducida como segundo parámetro.
	 *  
	 * @param emailBanda Cadena de caracteres que representa el email que identifica a una banda
	 * @param info Cadena de caracteres con la información a actualizar en la BBDD sobre la banda
	 */
	public void updateInfoBand(String emailBanda, String info) throws ErrorBandException{
		bandDAO.updateInfo(emailBanda, info);
	}
	
	public void seguir(String fan,String band){
		fanDAO.seguir(fan, band);
	}
	
	public void dejarDeSeguir(String fan,String band){
		fanDAO.dejarDeSeguir(fan, band);
	}
	
	public List<BandVO> search(String keyWord){
		return bandDAO.search(keyWord);
	}

}
