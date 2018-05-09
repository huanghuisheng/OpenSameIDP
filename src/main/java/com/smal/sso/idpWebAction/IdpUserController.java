package com.smal.sso.idpWebAction;

import com.smal.sso.idpDto.ObjectRsDTO;
import com.smal.sso.idpDto.PermissionDTO;
import com.smal.sso.idpPojo.DsServiceProvider;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpService.IdpSsoService;
import com.smal.sso.idpService.IdpUserService;
import com.smal.sso.util.Base64;
import com.smal.sso.util.HttpRedirectClient;
import com.smal.sso.util.IDPConstants;
import com.smal.sso.util.generator.CustomGenerator;
import com.tone.waf.service.ServiceHelper;
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
@RequestMapping("/idpUser")
public class IdpUserController {
    private static Logger logger = (Logger) LoggerFactory.getLogger(IdpUserController.class);


    @RequestMapping(value="/login.do")
    @ResponseBody
    protected ObjectRsDTO login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name") == null || ("").equals(req.getParameter("name")) ? null : req.getParameter("name");
        String password = req.getParameter("password") == null || ("").equals(req.getParameter("password")) ? null : req.getParameter("password");
        ObjectRsDTO dto = new ObjectRsDTO();
        IdpUserService service = (IdpUserService) ServiceHelper.getService(IdpUserService.class);
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
     * 注册服务
     * @param req
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value="/registerService.do")
    @ResponseBody
    protected ObjectRsDTO registerService(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        IdpUserService service = (IdpUserService) ServiceHelper.getService(IdpUserService.class);
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
        try{
            dto= service.saveServiceProvider(serviceProvider);
        }catch (Exception e)
        {
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

        ObjectRsDTO dto =null;
        IdpUserService service = (IdpUserService) ServiceHelper.getService(IdpUserService.class);
       try{
           dto= service.saveBatchUser(file);
       }catch (Exception e)
       {
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
    @RequestMapping("UploadUser.do")
    public  ObjectRsDTO UploadUser( HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ObjectRsDTO dto =new ObjectRsDTO();
        IdpUserService service = (IdpUserService) ServiceHelper.getService(IdpUserService.class);
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
        try{
            dto=service.saveUser(user);
        }catch (Exception e)
        {
            dto.setCode(-1);
            dto.setMessage("创建用户失败");
        }
        return dto;
    }


}
