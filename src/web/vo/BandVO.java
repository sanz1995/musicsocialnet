package web.vo;

import java.util.ArrayList;
/**
 * Clase de tipo Value Object que representa una entidad simple en la BBDD,
 * en este caso, la entidad representada en la tabla "banda" de la BBDD.
 * Hereda mÃ©todos y atributos de la clase UserVO.
 */
public class BandVO extends UserVO{

	protected ArrayList<String> generos;
	protected String descripcion;
	protected String canal;
	/**
	 * Constructor por defecto
	 */
	public BandVO() {
	}

	/**
	 * Constructor a partir de los atributos que se permiten almacenar en la 
	 * tabla que representa la entidad banda en la BBDD.
	 */
	public BandVO(String nombre, String password, String fotoPerfil, String canal, String email, String descripcion) {
		super.nombre = nombre;
		super.password = password;
		super.fotoPerfil = fotoPerfil;
		this.canal = canal;
		super.email = email;
		this.descripcion = descripcion;
	}
	
	/**
	 * Constructor a partir de los atributos que se permiten almacenar en la 
	 * tabla que representa la entidad banda en la BBDD. Incluyendo los generos 
	 * mÃºsicales que la representan y se encuentran a partir de las tablas "pertenece"
	 * y "generos" en la BBDD.
	 */
	public BandVO(String nombre, String password, String fotoPerfil, String canal, String email, ArrayList<String> generos, String descripcion) {
		super.nombre = nombre;
		super.password = password;
		super.fotoPerfil = fotoPerfil;
		this.canal = canal;
		super.email = email;
		this.generos = generos;
		this.descripcion = descripcion;
	}

	
	/**
	 * Devuleve una lista con los gÃ©neros mÃºsicales almacenados en el
	 *  objeto BandaVO.
	 * 
	 * @return Lista de cadenas de carÃ¡cteres con los gÃ©neros musicales que
	 * se relacionan con este objeto banda.
	 */
	public ArrayList<String> getGeneros() {
		return generos;
	}

	
	/**
	 * Modifica la lista de gÃ©neros mÃºsicales almacenados en este objeto BandaVO.
	 * 
	 * @param generos Lista de cadenas de carácteres con géneros musicales.
	 */
	public void setGeneros(ArrayList<String> generos) {
		this.generos = generos;
	}
		
	/**
	 * Devuleve una cadena de carácteres con el atributo descripción del objeto BandaVO.
	 * 
	 * @return Cadena de carácteres que representa la descripción del objeto
	 * BandaVO.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Modifica la cadena de carácteres del atributo descripción del objeto BandaVO.
	 * 
	 * @param descripcion Cadena de carácteres que representa la nueva descripción del objeto
	 * BandaVO.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * Devuleve una cadena de carácteres con el atributo canal del objeto BandaVO.
	 * 
	 * @return Cadena de carácteres que representa la canal del objeto
	 * BandaVO.
	 */
	public String getCanal() {
		return canal;
	}

	/**
	 * Modifica la cadena de carácteres del atributo canal del objeto BandaVO.
	 * 
	 * @param descripcion Cadena de carácteres que representa el nuevo canal del objeto
	 * BandaVO.
	 */
	public void setCanal(String canal) {
		this.canal = canal;
	}
}
