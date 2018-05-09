package com.smal.sso.idpDao;

import com.smal.sso.idpDto.ArtifactDTO;
import com.smal.sso.idpDto.PermissionDTO;
import com.smal.sso.idpPojo.DsAssociationPermission;
import com.smal.sso.idpPojo.DsServiceProvider;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpPojo.DsUserAssociation;
import com.tone.waf.dao.BaseDAO;
import com.tone.waf.util.BaseProcessor;

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

	public List<DsServiceProvider> getServiceProvider(DsServiceProvider serviceProvider) {
		return null;
	}

	public List<DsAssociationPermission> getDsAssociationPermission(ArtifactDTO artifactDTO) {
		return null;
	}

	public List<PermissionDTO> getAllResource(DsUser user) {
		return null;
	}

	public int saveServiceProvider(DsServiceProvider serviceProvider) {
		return 0;
	}

	public Object saveUser(DsUser user) {
		return null;
	}

	public Object saveUserByBatch(List<DsUser> user) {
		return null;
	}

	public List<DsUser> queryUser(DsUser user) {
		return null;
	}

	public DsUser queryUserById(DsUser user) {
		return null;
	}

	public List<DsServiceProvider> queryServiceProvider(DsServiceProvider serviceProvider) {
		return null;
	}
}
