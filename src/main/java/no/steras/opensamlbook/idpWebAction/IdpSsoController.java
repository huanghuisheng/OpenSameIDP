package no.steras.opensamlbook.idpWebAction;

import com.tone.dao.ServiceHelper;
import no.steras.opensamlbook.idpDto.ObjectRsDTO;
import no.steras.opensamlbook.idpDto.PermissionDTO;
import no.steras.opensamlbook.idpPojo.DsServiceProvider;
import no.steras.opensamlbook.idpPojo.DsUser;
import no.steras.opensamlbook.idpService.IdpSsoService;
import no.steras.opensamlbook.util.Base64;
import no.steras.opensamlbook.util.HttpRedirectClient;
import no.steras.opensamlbook.util.IDPConstants;
import no.steras.opensamlbook.util.generator.CustomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
            dto.setMessage("登录失败");
        } else if (flag == -2) {
            //密码错误
            dto.setCode(-2);
            dto.setMessage("登录失败");
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

    /***
     * 注册服务
     * @param req
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value="/registerService.do")
    @ResponseBody
    protected ObjectRsDTO registerService(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);
        DsUser dsUser=(DsUser)req.getSession().getAttribute(IDPConstants.SESSION_USER_OBJ);
        ObjectRsDTO dto=new ObjectRsDTO();
        DsServiceProvider serviceProvider=new DsServiceProvider();

        String seviceName = req.getParameter("seviceName") == null || ("").equals(req.getParameter("seviceName")) ? null : req.getParameter("seviceName");
        Integer uploadStrategy = req.getParameter("uploadStrategy") == null || ("").equals(req.getParameter("uploadStrategy")) ? null : Integer.valueOf(req.getParameter("uploadStrategy"));
        Integer checkStrategy = req.getParameter("checkStrategy") == null || ("").equals(req.getParameter("checkStrategy")) ? null : Integer.valueOf(req.getParameter("checkStrategy"));
        String serviceUrl = req.getParameter("serviceUrl") == null || ("").equals(req.getParameter("serviceUrl")) ? null : req.getParameter("serviceUrl");

        serviceProvider.setSeviceName(seviceName);
        serviceProvider.setUploadStrategy(uploadStrategy);
        serviceProvider.setCheckStrategy(checkStrategy);
        serviceProvider.setServiceUrl(serviceUrl);

        int flag=  service.saveServiceProvider(serviceProvider);
        if(flag>0)
        {
            dto.setCode(1);
            dto.setMessage("登记成功");
        }else{
            dto.setCode(-1);
            dto.setMessage("登记失败");
        }
        return dto;
    }


    /***
     * 上传用户，
     * @param file
     * @param request
     * @param groupid
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("UploadfileUser.do")
    public  ObjectRsDTO UploadfileUser(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request, String groupid) throws Exception {

        ObjectRsDTO dto =new ObjectRsDTO();
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);
        int flag= service.saveBatchUser(file);

        if(flag>0)
        {
            dto.setCode(1);
            dto.setMessage("上传用户成功");
        }else {
            dto.setCode(-1);
            dto.setMessage("上传用户失败,部分用户数据有问题");
        }
        return dto;
    }

    /***
     * 上传用户,保存用户，
     * @param req
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("UploadUser")
    public  ObjectRsDTO UploadUser( HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ObjectRsDTO dto =new ObjectRsDTO();
        IdpSsoService service = (IdpSsoService) ServiceHelper.getService(IdpSsoService.class);

        DsUser user =new DsUser();
        String loginName = req.getParameter("loginName") == null || ("").equals(req.getParameter("loginName")) ? null : req.getParameter("loginName");
        Long region = req.getParameter("region") == null || ("").equals(req.getParameter("region")) ? null : Long.valueOf(req.getParameter("region"));
        Long sex = req.getParameter("sex") == null || ("").equals(req.getParameter("sex")) ? null : Long.valueOf(req.getParameter("sex"));
        String phone = req.getParameter("phone") == null || ("").equals(req.getParameter("phone")) ? null : req.getParameter("phone");
        String email = req.getParameter("email") == null || ("").equals(req.getParameter("email")) ? null : req.getParameter("email");
        String password = req.getParameter("password") == null || ("").equals(req.getParameter("password")) ? null : req.getParameter("password");
        user.setId((Long) CustomGenerator.generate());
        user.setLoginName(loginName);
        user.setRegion(region);
        user.setSex(sex);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        int flag=  service.saveUser(user);
        if(flag>0)
        {
            dto.setCode(1);
            dto.setMessage("创建用户成功");
        }else {
            dto.setCode(-1);
            dto.setMessage("创建用户失败");
        }

        return dto;
    }


}
