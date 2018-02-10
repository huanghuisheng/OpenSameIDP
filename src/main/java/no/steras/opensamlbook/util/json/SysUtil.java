package no.steras.opensamlbook.util.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysUtil {
	/**获得应用容器的真正路径*/
	public static String  getAppRealPath( HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	/**
	 * 设置Servlet响应参数
	 * @param response
	 * chenzk create the method at 2010-2-25 10:03:38
	 */ 
	public static void setResponse(HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("characterEncoding","UTF-8");
	}
}
