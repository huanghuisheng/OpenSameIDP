package com.smal.sso.idpService.impl;

import com.smal.sso.idpDao.IdpResourceDAO;
import com.smal.sso.idpDao.IdpSsoDAO;
import com.smal.sso.idpDao.IdpUserDAO;
import com.smal.sso.idpDto.ArtifactDTO;
import com.smal.sso.idpDto.ObjectRsDTO;
import com.smal.sso.idpDto.PermissionDTO;
import com.smal.sso.idpPojo.DsAssociationPermission;
import com.smal.sso.idpPojo.DsServiceProvider;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpPojo.DsUserAssociation;
import com.smal.sso.idpService.IdpResourceService;
import com.smal.sso.idpService.IdpSsoService;
import com.smal.sso.idpWebAction.IdpSsoController;
import com.smal.sso.util.Base64;
import com.smal.sso.util.*;
import com.smal.sso.util.generator.CustomGenerator;
import com.tone.waf.dao.DaoFactory;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xml.security.utils.EncryptionConstants;
import org.joda.time.DateTime;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSStringBuilder;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPRedirectDeflateDecoder;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPSOAP11Decoder;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPSOAP11Encoder;
import org.opensaml.saml.saml2.core.*;
import org.opensaml.saml.saml2.encryption.Encrypter;
import org.opensaml.xmlsec.encryption.support.DataEncryptionParameters;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.opensaml.xmlsec.encryption.support.KeyEncryptionParameters;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.Signer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class IdpResourceServiceImpl implements IdpResourceService{
    private static Logger logger = (Logger) LoggerFactory.getLogger(IdpSsoController.class);
    private static Map<String, ArtifactDTO> artifactList = new HashMap<String, ArtifactDTO>();
    private IdpResourceDAO idpResourceDAO = (IdpResourceDAO) DaoFactory.getDao(IdpResourceDAO.class);
    private IdpUserDAO idpUserDAO = (IdpUserDAO) DaoFactory.getDao(IdpUserDAO.class);

    public List<Map<String,List>> getAllResource(DsUser user) {
        //获取所有注册的系统
        List<DsServiceProvider> list =idpUserDAO.getServiceProvider(null);
        List<PermissionDTO>  listDTO = idpResourceDAO.getAllResource(user);
        Map<String,List> map = new HashMap<String, List>();
         for(PermissionDTO dto:listDTO)
         {
             List<String> innerList = map.get(dto.getServiceProviderId()+"|"+dto.getServiceProviderName());
             if(innerList != null){
                innerList.add(dto.getUrl());
            }else {
                 List<String> newList = new ArrayList<String>();
                 newList.add(dto.getUrl());
                 map.put(dto.getServiceProviderId()+"|"+dto.getServiceProviderName(), newList);
             }
         }
        for(DsServiceProvider dto:list)
        {
            List<String> innerList = map.get(dto.getId()+"|"+dto.getSeviceName());
            if(innerList != null){
                innerList.add(dto.getServiceUrl());
            }else {
                List<String> newList = new ArrayList<String>();
                newList.add(dto.getServiceUrl());
                map.put(dto.getId()+"|"+dto.getSeviceName(), newList);
            }
        }
        List lista=new ArrayList<Map<String, List>>();
        lista.add(map);
        return lista;
    }



}