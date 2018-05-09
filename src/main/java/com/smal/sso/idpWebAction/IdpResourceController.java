package com.smal.sso.idpWebAction;

import com.smal.sso.idpDto.ObjectRsDTO;
import com.smal.sso.idpPojo.DsServiceProvider;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpService.IdpResourceService;
import com.smal.sso.idpService.IdpSsoService;
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
@RequestMapping("/idpResource")
public class IdpResourceController {
    private static Logger logger = (Logger) LoggerFactory.getLogger(IdpResourceController.class);
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
        IdpResourceService service = (IdpResourceService) ServiceHelper.getService(IdpResourceService.class);
        ObjectRsDTO dto =new ObjectRsDTO();
        DsUser user= (DsUser)req.getSession().getAttribute(IDPConstants.SESSION_USER_OBJ);
        if(user!=null)
        {
            dto.setCode(1);
            List list= service.getAllResource(user);
            dto.setContent(list);
            dto.setMessage("查询成功");
        }else{
            dto.setCode(-1);
            dto.setMessage("找不到用户，请重新登录idp");
        }
        return dto;
    }





}
