package web;

import web.exception.ErrorBandException;
import web.exception.ErrorFanException;
import web.exception.LoginException;
import web.vo.EventVO;
import web.vo.UserVO;

public class Pruebas {

	public static void main(String[] args){
		WebFachada w = WebFachada.getWebFachada();
		try {
			UserVO u = w.iniciarSesion("metallica@gmail.com", "metallica");
			w.proximosEventos(u);
			for(EventVO e : w.proximosEventos(u)){
				System.out.println(e);
			}
			//System.out.println(u);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorFanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorBandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
