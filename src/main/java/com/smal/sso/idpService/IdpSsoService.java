package com.smal.sso.idpService;

import com.smal.sso.idpDto.ObjectRsDTO;
import com.smal.sso.idpDto.PermissionDTO;
import com.smal.sso.idpPojo.DsServiceProvider;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpPojo.DsUserAssociation;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IdpSsoService {


    public void  authnRequestResolve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    public void artifactResolve( HttpServletRequest req,  HttpServletResponse resp) throws Exception;


    public String  getAssertionsResolve(DsUser user,PermissionDTO  permissionDTO);


}