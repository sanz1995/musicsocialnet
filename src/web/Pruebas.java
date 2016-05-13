package web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import web.dao.CommentDAO;
import web.vo.CommentVO;

public class Pruebas {

	public static void main(String[] args){
		CommentVO c = new CommentVO("hola","20160513211000","","");
		System.out.println(c.getDifference());
		
	}
}
