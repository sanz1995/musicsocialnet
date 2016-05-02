package web.vo;

/**
 * Clase de tipo Value Object que representa una entidad simple en la BBDD,
 * en este caso, la entidad representada en la tabla "fan" de la BBDD.
 * Hereda métodos y atributos de la clase UserVO.
 */
public class FanVO extends UserVO {
	
	/**
	 * Constructor por defecto
	 */
	public FanVO() {
	}

	/**
	 * Constructor a partir de los atributos que se permiten almacenar en la 
	 * tabla que representa la entidad fan en la BBDD.
	 */
	public FanVO(String nombre, String password, String fotoPerfil, String email) {
		super.nombre = nombre;
		super.password = password;
		super.fotoPerfil = fotoPerfil;
		super.email = email;
	}
}
