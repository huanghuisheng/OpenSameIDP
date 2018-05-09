package com.smal.sso.idpWebAction;

import com.smal.sso.idpDto.ObjectRsDTO;
import com.smal.sso.idpDto.PermissionDTO;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpService.IdpSsoService;
import com.smal.sso.util.Base64;
import com.smal.sso.util.HttpRedirectClient;
import com.smal.sso.util.IDPConstants;
import com.tone.waf.service.ServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/idpSso")
public class IdpSsoController  {
    private static Logger logger = (Logger) LoggerFactory.getLogger(IdpSsoController.class);


    /***
     * 处理authnRequest认证请求
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value="/authnRequestResolve.do")
    protected void authnRequestResolve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);
        service.authnRequestResolve(req,resp);
    }


    /***
     * artifact处理返回认证断言
     * @param req
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value="/artifactResolve.do")
    protected void artifactResolve(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);
        service.artifactResolve(req,resp);
    }


    /***
     * 统一资源下，返回认证断言
     * @param req
     * @param resp
     * @throws Exception
     */
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
