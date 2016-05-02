package web.vo;

public class EventVO {

	private String id;
	private String nombre;
	private String banda;
	private String fecha;
	private String lugar;
	private String nasistentes;
	private String hora;
	
	public EventVO(String id,String nombre,String banda, String fecha, String lugar, String nasistentes,String hora) {
		this.id=id;
		this.nombre=nombre;
		this.banda=banda;
		this.fecha=formatearFecha(fecha);
		this.lugar=lugar;
		this.nasistentes=nasistentes;
		this.hora=formatearHora(hora);
	}
	
	public EventVO(String nombre,String banda,String fecha, String lugar, String nasistentes,String hora) {
		this.nombre=nombre;
		this.banda=banda;
		this.fecha=formatearFecha(fecha);
		this.lugar=lugar;
		this.nasistentes=nasistentes;
		this.hora=formatearHora(hora);
	}
	
	private String formatearFecha(String fecha){
		String res = "";
		
		for (int i = 0; i<fecha.length();i++){
			if(fecha.charAt(i)!='-'){
				res += fecha.charAt(i);
			}
		}
		return res;
		
	} 
	private String formatearHora(String hora){
		String res = "";
		
		for (int i = 0; i<hora.length();i++){
			if(hora.charAt(i)!=':'){
				res += hora.charAt(i);
			}
		}
		return res;
	}
	
	/**
	 * Devuleve una cadena de carácteres con el id de la evento almacenado
	 * en el atributo "id" del objeto eventoVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  id de este objeto evento.
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Devuleve una cadena de carácteres con el nombre del evento almacenado
	 * en el atributo "nombre" del objeto EventoVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  nombre de este objeto evento.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuleve una cadena de carácteres con la la banda almacenado
	 * en el atributo "banda" del objeto EventVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  banda de este objeto evento.
	 */
	public String getBanda() {
		return banda;
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
	 * Devuleve una cadena de carácteres con el lugar de la evento almacenado
	 * en el atributo "lugar" del objeto eventoVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  lugar de este objeto evento.
	 */
	public String getLugar() {
		return lugar;
	}

	/**
	 * Devuleve una cadena de carácteres con el numero de asistentes de la evento almacenado
	 * en el atributo "nasistentes" del objeto eventoVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  nasistentes de este objeto evento.
	 */
	public String getNumAsistentes() {
		return nasistentes;
	}
	/**
	 * Devuleve una cadena de carácteres con la hora de la evento almacenado
	 * en el atributo "hora" del objeto eventoVO.
	 * 
	 * @return Cadenas de carácteres que representa el contenido del atributo
	 *  hora de este objeto evento.
	 */
	public String getHora() {
		return hora;
	}

	public String getMonth(){
		int mes=Integer.parseInt(fecha.substring(4, 6));
		switch(mes){
		case 1: return "ENERO";
		case 2: return "FEBRERO";
		case 3: return "MARZO";
		case 4: return "ABRIL";
		case 5: return "MAYO";
		case 6: return "JUNIO";
		case 7: return "JULIO";
		case 8: return "AGOSTO";
		case 9: return "SEPTIEMBRE";
		case 10: return "OCTUBRE";
		case 11: return "NOVIEMBRE";
		case 12: return "DICIEMBRE";
		default: return "ERROR";
		}
	}
	public String getDay(){
		return fecha.substring(6);
	}

}
