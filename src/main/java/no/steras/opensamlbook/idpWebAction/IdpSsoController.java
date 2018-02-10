package no.steras.opensamlbook.idpWebAction;

import com.tone.dao.ServiceHelper;
import no.steras.opensamlbook.idpDto.ObjectRsDTO;
import no.steras.opensamlbook.idpDto.PermissionDTO;
import no.steras.opensamlbook.idpPojo.DsUser;
import no.steras.opensamlbook.idpService.IdpSsoService;
import no.steras.opensamlbook.util.Base64;
import no.steras.opensamlbook.util.HttpRedirectClient;
import no.steras.opensamlbook.util.IDPConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/idpSso")
public class IdpSsoController  {
    private static Logger logger = (Logger) LoggerFactory.getLogger(IdpSsoController.class);


    @RequestMapping(value="/login.do")
    @ResponseBody
    protected ObjectRsDTO login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name") == null || ("").equals(req.getParameter("name")) ? null : req.getParameter("name");
        String password = req.getParameter("password") == null || ("").equals(req.getParameter("password")) ? null : req.getParameter("password");
        ObjectRsDTO dto = new ObjectRsDTO();
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);
        DsUser user = new DsUser();
        user.setLoginName(name);
        user.setPassword(password);

        int flag = service.getUser(user,req);
        if (flag == 1) {
            //登录成功
            dto.setCode(1);
            dto.setMessage("登录成功");
        } else if (flag == -1) {
            //没有此用户
            dto.setCode(-1);
            dto.setMessage("登录成功");
        } else if (flag == -2) {
            //密码错误
            dto.setCode(-2);
            dto.setMessage("登录成功");
        }
        return dto;
    }

    /***
     * 获取用户统一资源
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value="/getAllResource.do")
    @ResponseBody
    protected ObjectRsDTO getAllResource(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);
        ObjectRsDTO dto =new ObjectRsDTO();
        DsUser user= (DsUser)req.getSession().getAttribute(IDPConstants.SESSION_USER_OBJ);
        if(user!=null)
        {
            dto.setCode(1);
            List<PermissionDTO> list= service.getAllResource(user);
            dto.setContent(list);
            dto.setMessage("查询成功");
        }else{
            dto.setCode(-1);
            dto.setMessage("找不到用户，请重新登录idp");
        }
        return dto;
    }





    @RequestMapping(value="/authnRequestResolve.do")
    protected void authnRequestResolve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);
        service.authnRequestResolve(req,resp);
    }


    @RequestMapping(value="/artifactResolve.do")
    protected void artifactResolve(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);
        service.artifactResolve(req,resp);
    }



    @RequestMapping(value="/getAssertionsResolve.do")
    protected void getAssertionsResolve(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);
        DsUser dsUser=(DsUser)req.getSession().getAttribute(IDPConstants.SESSION_USER_OBJ);
        PermissionDTO permissionDTO=new PermissionDTO();
        String url=req.getParameter("url");
        permissionDTO.setUrl(url);
        String SAMLResponse= service.getAssertionsResolve(dsUser,permissionDTO);
        HttpRedirectClient client =new HttpRedirectClient(resp);

        System.out.println("---"+SAMLResponse);


        client.setParameter("SAMLResponse",Base64.encode(SAMLResponse));
        try {
            client.sendByPost("http://localhost:8090/hoffice/smsdoctor/referredreservations.do?method=enter&tagrandom=0.5669605692950603");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }









}
