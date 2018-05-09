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

public interface IdpUserService {

    public int getUser(DsUser user, HttpServletRequest req);
    public ObjectRsDTO  saveServiceProvider(DsServiceProvider serviceProvider);

    public ObjectRsDTO saveBatchUser(CommonsMultipartFile file);
    public ObjectRsDTO  saveUser(DsUser user);




}