package no.steras.opensamlbook.idpService.impl;

import com.tone.dao.DaoFactory;
import net.sf.json.JSONObject;
import no.steras.opensamlbook.idpDao.IdpSsoDAO;
import no.steras.opensamlbook.idpService.SingleSignOnService;
import no.steras.opensamlbook.idpPojo.DsUserAssociation;

import java.util.List;

public class SingleSignOnServiceImpl implements SingleSignOnService {


    private IdpSsoDAO userDao = (IdpSsoDAO) DaoFactory.getDao(IdpSsoDAO.class);
    public List<DsUserAssociation> getUserAssociation(String consent) {
        JSONObject jsonObj = JSONObject.fromObject(consent);
        String username= jsonObj.has("ticket")==true?jsonObj.getString("ticket"):null;
        String password=jsonObj.has("password")==true?jsonObj.getString("password"):null;
        String systemCodeFrom=jsonObj.has("systemCodeFrom")==true?jsonObj.getString("systemCodeFrom"):null;
        String systemCodeTo=jsonObj.has("systemCodeTo")==true?jsonObj.getString("systemCodeTo"):null;
        return userDao.getUserAssociation(username,password,systemCodeFrom,systemCodeTo);
    }
}