package web.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase de tipo Value Object que representa una entidad simple en la BBDD,
 * en este caso, la entidad representada en la tabla "comentario" de la BBDD.
 */
public class CommentVO {

	private String texto;
	private String time;
	private String userEmail;
	private String bandEmail;
	
	/**
	 * Constructor a partir de los atributos que se permiten almacenar en la 
	 * tabla que representa la entidad comentario en la BBDD.
	 */
	public CommentVO(String tex,String tim,String u,String b){
		texto = tex;
		time = formatearFecha(tim);
		userEmail = u;
		bandEmail = b;
	}
	
	/**
	 * Método que da al atributo fecha de la base de datos un formato distinto al almacenado
	 * en la BBDD.
	 */
	private String formatearFecha(String fecha){
		String res = "";
		for (int i = 0; i<fecha.length();i++){
			if(fecha.charAt(i)!='-' && fecha.charAt(i)!=':' 
					&& fecha.charAt(i)!=' '){
				res += fecha.charAt(i);
			}
		}
		return res;
		
	} 
	
	/**
	 * Método que obtiene el valor del atributo texto de la base de datos
	 */
	public String getTexto(){
		return texto;
	}
	
	/**
	 * Método que obtiene el valor del atributo fecha de la base de datos
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * Método que calcula el tiempo que hace que se realizo un comentario en base a la hora actual
	 */
	public String getDifference(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		int year = Integer.parseInt(dateFormat.format(date).substring(0, 4)) -
				Integer.parseInt(time.substring(0,4));
		int month = Integer.parseInt(dateFormat.format(date).substring(4, 6)) -
				Integer.parseInt(time.substring(4,6));
		int day = Integer.parseInt(dateFormat.format(date).substring(6, 8)) -
				Integer.parseInt(time.substring(6,8));
		int hour = Integer.parseInt(dateFormat.format(date).substring(8, 10)) -
				Integer.parseInt(time.substring(8,10));
		System.out.println(day);
		int min = Integer.parseInt(dateFormat.format(date).substring(10, 12)) -
				Integer.parseInt(time.substring(10,12));
		int sec = Integer.parseInt(dateFormat.format(date).substring(12, 14)) -
				Integer.parseInt(time.substring(12,14));
		
		
		if(year==0 | (year==1 && month<0)){
			if(month==0 | (month==1 && day<0)){
				if(day==0 | (day==1 && hour<0)){
					if(hour==0 | (hour==1 && min<0)){
						if(min==0 | (min==1 && sec<0)){
							if(sec<0){
								sec += 60;
							}
							return sec +" seconds ago";
						}else{
							if(min<0){
								min += 60;
							}
							return min +" minutes ago";
						}
					}else{
						if(hour<0){
							hour += 24;
						}
						return hour +" hours ago";
					}
				}else{
					if(day<0){
						day += 30;
					}
					return day +" days ago";
				}
			}else{
				if(month<0){
					month += 12;
				}
				return month +" months ago";
			}
		}else{
			return year +" years ago";
		}
	}
	
	/**
	 * Método que obtiene el valor del atributo fanEmail de la base de datos
	 */
	public String getUserEmail(){
		return userEmail;
	}
	
	/**
	 * Método que obtiene el valor del atributo bandEmail de la base de datos
	 */
	public String getBandEmail(){
		return bandEmail;
	}
}
