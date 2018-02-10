package no.steras.opensamlbook.idpDao;

import com.tone.dao.BaseDAO;
import com.tone.util.BaseProcessor;
import no.steras.opensamlbook.idpDto.ArtifactDTO;
import no.steras.opensamlbook.idpDto.PermissionDTO;
import no.steras.opensamlbook.idpPojo.DsAssociationPermission;
import no.steras.opensamlbook.idpPojo.DsUser;
import no.steras.opensamlbook.idpPojo.DsUserAssociation;

import java.util.ArrayList;
import java.util.List;



public class IdpSsoOracDAOImpl extends BaseDAO implements IdpSsoDAO {


	public List<DsUserAssociation> getUserAssociation(String username, String password, String systemCodeFrom, String systemCodeTo) {
		StringBuffer sql=new StringBuffer();
		List param=new ArrayList();
		sql.append("SELECT  * from ds_user_association  duaa " +
				"where duaa.system_code= ? " +
				"and duaa.employee_info_id in (SELECT  dua.employee_info_id from ds_user_association dua " +
				"where  dua.system_code = ? " +
				"and dua.name = ? " +
				"and dua.password= ? )");

		param.add(systemCodeTo);
		param.add(systemCodeFrom);
		param.add(username);
		param.add(password);
		List<DsUserAssociation> list=this.select(sql.toString(),null,param.toArray(), new BaseProcessor(DsUserAssociation.class));
		return list;
	}

	public List<DsUser> getUser(DsUser user) {
		StringBuffer sql =new StringBuffer();
		List param=new ArrayList();
		sql.append("SELECT  * from ds_user");





		return null;
	}

	public List<DsAssociationPermission> getDsAssociationPermission(ArtifactDTO artifactDTO) {
		return null;
	}

	public List<PermissionDTO> getAllResource(DsUser user) {
		return null;
	}
}
