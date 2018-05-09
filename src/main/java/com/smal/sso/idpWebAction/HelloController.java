/**
  * 文件说明
  * @Description:扩展说明
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.smal.sso.idpWebAction;


import com.smal.sso.idpDto.ArtifactDTO;
import com.smal.sso.idpDto.ObjectRsDTO;
import com.smal.sso.util.json.Config;
import net.sf.json.JSONObject;
import com.smal.sso.util.HttpRedirectClient;
import com.smal.sso.util.OpenSAMLUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;


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

	// ----------------------------批量导入
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("basicController/fileUpload")
	public  void fileUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request, String groupid) throws Exception {
		String path1 = request.getContextPath();
		String cp11111 = request.getSession().getServletContext().getRealPath("/");
		String path = "/" + new Date().getTime();
		InputStream input = file.getInputStream();
		Workbook wb = null;
		// 根据文件格式(2003或者2007)来初始化
		wb = new XSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
		Iterator<Row> rows = sheet.rowIterator();
		List listImei = new ArrayList<Object>();
		List errorImei = new ArrayList<Object>();

		while (rows.hasNext()) {
			Row row = rows.next(); // 获得行数据
			Iterator<Cell> cells = row.cellIterator(); // 获得第一行的迭代器
			if (row.getRowNum() == 0) {
				continue;
			}
			while (cells.hasNext()) {
				Cell cell = cells.next();
				switch (cell.getCellType()) {
					// 根据cell中的类型来输出数据
					case HSSFCell.CELL_TYPE_NUMERIC:
						DecimalFormat df = new DecimalFormat("0");
						String whatYourWant = df.format(cell.getNumericCellValue());
						listImei.add(whatYourWant);
						break;

					default:
						errorImei.add(cell.getStringCellValue());
						break;
				}
			}
		}
		System.out.println("list is ---" + listImei);
		System.out.println("list size is ---" + listImei.size());
		System.out.println("error is ---" + errorImei);
		System.out.println("groupid is ---" + groupid);

		// --------------------------------------输出imei号；
	}
    @ResponseBody
	@RequestMapping(value="/user.do")
	private void getuser(ArtifactDTO dto, HttpServletRequest request) {
//		System.out.println(dto.getName());
//        ArtifactDTO dto1=new ArtifactDTO();
//        reflectParameter(dto1,request);
//        System.out.println(dto1.getName());
	}



}
