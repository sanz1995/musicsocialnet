package web;

import java.util.List;

import web.exception.ErrorBandException;
import web.exception.ErrorFanException;
import web.exception.LoginException;
import web.vo.BandVO;
import web.vo.EventVO;
import web.vo.UserVO;

public class Pruebas {

	public static void main(String[] args){
		WebFachada w = WebFachada.getWebFachada();
		//try {
			/**UserVO u = w.iniciarSesion("green.day@gmail.com", "green day");
			w.proximosEventos(u);
			for(EventVO e : w.proximosEventos(u)){
				System.out.println(e.getNombre());
			}
			
			EventVO e= new EventVO("Concierto Benefico","green.day@gmail.com",
							"2016-05-04","Moscu","1000000","18:00:00");
			
			System.out.println(e.getNombre());
			System.out.println(e.getFecha());
			System.out.println(e.getHora());
			//w.crearEvento(e);
			System.out.println(w.asiste("jorge.sanz.alcaine@gmail.com", "3"));
			//System.out.println(u);
			 * */
			//w.dejarDeSeguir("jorge.sanz.alcaine@gmail.com",  "foo.fighters@gmail.com");
			List<BandVO>  l = w.search("e");
			for(BandVO e : l){
				System.out.println(e.getNombre());
			}
			
			
		/**}catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorFanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorBandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
