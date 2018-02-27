package no.steras.opensamlbook.idpService;

import no.steras.opensamlbook.idpDto.PermissionDTO;
import no.steras.opensamlbook.idpPojo.DsAssociationPermission;
import no.steras.opensamlbook.idpPojo.DsServiceProvider;
import no.steras.opensamlbook.idpPojo.DsUser;
import no.steras.opensamlbook.idpPojo.DsUserAssociation;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IdpSsoService {

    public List<DsUserAssociation> getUserAssociation(String consent);



    public void  authnRequestResolve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    public int getUser(DsUser user,HttpServletRequest req);

    public void artifactResolve( HttpServletRequest req,  HttpServletResponse resp) throws Exception;

    public List<PermissionDTO> getAllResource(DsUser user);

    public String  getAssertionsResolve(DsUser user,PermissionDTO  permissionDTO);


    public int saveServiceProvider(DsServiceProvider serviceProvider);




    public int saveBatchUser(CommonsMultipartFile file);
    public int saveUser(DsUser user);


    public int saveBatchUserAssociationPermission(CommonsMultipartFile file);

}