package web.vo;

public class EventVO {

	private String id;
	private String fecha;
	private String lugar;
	private String nasistentes;
	private String hora;
	
	public EventVO(String id, String fecha, String lugar, String nasistentes,String hora) {
		this.id=id;
		this.fecha=fecha;
		this.lugar=lugar;
		this.nasistentes=nasistentes;
		this.hora=hora;
	}
	
	public EventVO(String fecha, String lugar, String nasistentes,String hora) {
		this.fecha=fecha;
		this.lugar=lugar;
		this.nasistentes=nasistentes;
		this.hora=hora;
	}
	
	/**
	 * Devuleve una cadena de carácteres con el id de la banda almacenado
	 * en el atributo "id" del objeto BandaVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  id de este objeto banda.
	 */
	public String getID() {
		return id;
	}

	/**
	 * Devuleve una cadena de carácteres con la fecha de la banda almacenado
	 * en el atributo "fecha" del objeto BandaVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  fecha de este objeto banda.
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Devuleve una cadena de carácteres con el lugar de la banda almacenado
	 * en el atributo "lugar" del objeto BandaVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  lugar de este objeto banda.
	 */
	public String getLugar() {
		return lugar;
	}

	/**
	 * Devuleve una cadena de carácteres con el numero de asistentes de la banda almacenado
	 * en el atributo "nasistentes" del objeto BandaVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  nasistentes de este objeto banda.
	 */
	public String getNumAsistentes() {
		return nasistentes;
	}
	/**
	 * Devuleve una cadena de carácteres con la hora de la banda almacenado
	 * en el atributo "hora" del objeto BandaVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  hora de este objeto banda.
	 */
	public String getHora() {
		return hora;
	}


}
