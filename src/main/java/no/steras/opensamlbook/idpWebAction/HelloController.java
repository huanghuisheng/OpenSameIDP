/**
  * 文件说明
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package no.steras.opensamlbook.idpWebAction;

import net.sf.json.JSONObject;
import no.steras.opensamlbook.idpDto.ObjectRsDTO;
import no.steras.opensamlbook.util.HttpRedirectClient;
import no.steras.opensamlbook.util.OpenSAMLUtils;
import no.steras.opensamlbook.util.json.Config;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml.saml2.core.DecisionTypeEnumeration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//通过@Controller注解标识HelloController这个类是一个控制器
@Controller
@RequestMapping("/demo")
public class HelloController {
	
	@RequestMapping(value="/hello.do")
	public void sayHello(HttpServletRequest request,HttpServletResponse response){
		System.out.println("======================Hello World!!!================");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ObjectRsDTO dto =new ObjectRsDTO();
			dto.setMessage("======================Hello World!!!================");
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("1",new Date());
            map.put("2",null);
            map.put("3","");
            dto.setContent(map);

			JSONObject json = JSONObject.fromObject(dto, Config.jsonConfig);
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/jsonhello")
	@ResponseBody
	public ObjectRsDTO jsonhello(HttpServletRequest request,HttpServletResponse response){
		System.out.println("======================Hello World!11!!================");

			ObjectRsDTO dto =new ObjectRsDTO();
			dto.setMessage("======================Hello World!11!!================");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("1",new Date());
			map.put("2",null);
			map.put("3","");
			dto.setContent(map);
			return dto;
	}



	@RequestMapping(value="/jsonhello.do")
	private AuthzDecisionStatement buildAuthzDecisionStatement() {
		AuthzDecisionStatement authzDecisionStatement = OpenSAMLUtils.buildSAMLObject(AuthzDecisionStatement.class);
		Attribute attributeUserName = OpenSAMLUtils.buildSAMLObject(Attribute.class);
//		DecisionTypeEnumeration var1 =OpenSAMLUtils.buildSAMLObject(DecisionTypeEnumeration.class);

//		String a=  var1.toString();

		authzDecisionStatement.setResource("");
//		authzDecisionStatement.setDecision(var1);
		return null;
	}

	@RequestMapping(value="/sendRedirect.do")
	private void sendRedirect(HttpServletRequest request,HttpServletResponse response) {
		HttpRedirectClient client =new HttpRedirectClient(response);

		client.setParameter("SAMLResponse","11111111111111");
		try {
			client.sendByPost("http://localhost:8070/hoffice/smsdoctor/referredreservations.do?method=enter&tagrandom=0.5669605692950603");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}




}
