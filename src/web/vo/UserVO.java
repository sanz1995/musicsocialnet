package web.vo;

/**
 * Clase de tipo Value Object que representa una los atributos comunes en la BBDD
 * a las entidades representadas en la tabla "fan" y "banda" de la BBDD.
 */
public class UserVO {
	protected String nombre;
	protected String password;
	protected String fotoPerfil;
	protected String email;
	
	/**
	 * Constructor a partir de los atributos comunes almacenados en la tabla
	 * que representa la entidad fan y la entidad banda en la BBDD.
	 */
	public UserVO(String nombre,String password,String fotoPerfil,String email) {
		this.nombre=nombre;
		this.password=password;
		this.fotoPerfil=fotoPerfil;
		this.email=email;
	}
	/**
	 * Constructor por defecto
	 */
	public UserVO() {
	}

	/**
	 * Devuleve una cadena de carácteres con el nombre de la banda almacenado
	 * en el atributo "nombre" del objeto BandaVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  nombre de este objeto banda.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica la cadena de carácteres "nombre" en este objeto BandaVO.
	 * 
	 * @param nombre Cadena de carácteres con el nuevo nombre de la banda.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Devuleve una cadena de carácteres con la contraseña de la banda, almacenada
	 * en el atributo "password" del objeto BandaVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  password de este objeto banda.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Modifica la cadena de carácteres "password" en este objeto BandaVO.
	 * 
	 * @param password Cadena de carácteres con la nueva contraseña de la banda.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Devuleve una cadena de carácteres que indica la ruta de almacenamiento de la 
	 * foto de perfil que reprensenta a este objeto BandaVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  fotoPerfil de este objeto banda.
	 */
	public String getFotoPerfil() {
		return fotoPerfil;
	}

	/**
	 * Modifica la cadena de carácteres "fotoPerfil" en este objeto BandaVO.
	 * 
	 * @param fotoPerfil Cadena de carácteres con la nueva ruta donde se almacena
	 * la foto de perfil de la banda.
	 */
	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	
	/**
	 * Devuleve una cadena de carácteres con el email de la banda almacenado
	 * en el atributo "email" del objeto BandaVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo 
	 * email de este objeto banda.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Modifica la cadena de carácteres "email" en este objeto BandaVO.
	 * 
	 * @param email Cadena de carácteres con el nuevo email de la banda.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
