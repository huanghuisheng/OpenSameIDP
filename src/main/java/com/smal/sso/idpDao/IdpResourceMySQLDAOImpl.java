package com.smal.sso.idpDao;

import com.smal.sso.idpDto.ArtifactDTO;
import com.smal.sso.idpDto.PermissionDTO;
import com.smal.sso.idpPojo.DsAssociationPermission;
import com.smal.sso.idpPojo.DsServiceProvider;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpPojo.DsUserAssociation;
import com.tone.waf.dao.BaseDAO;
import com.tone.waf.util.BaseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class IdpResourceMySQLDAOImpl extends BaseDAO implements IdpResourceDAO {
	private static Logger logger = (Logger) LoggerFactory.getLogger(IdpResourceMySQLDAOImpl.class);

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
		System.out.println("----------"+sql.toString());
		List<DsUserAssociation> list=this.select(sql.toString(),null,param.toArray(), new BaseProcessor(DsUserAssociation.class));
		return list;
	}




	public List<DsAssociationPermission> getDsAssociationPermission(ArtifactDTO artifactDTO) {
		StringBuffer sql =new StringBuffer();
		List param=new ArrayList();
		sql.append("SELECT  dap.* from ds_user_association dua " +
				"LEFT JOIN  ds_association_permission dap " +
				" on dua.id=dap.association_id ");
		sql.append(" where 1=1  and dap.status='A' ");
        if(artifactDTO.getUserId()!=null)
		{
			sql.append(" and dua.ds_user_id = ? ");
			param.add(artifactDTO.getUserId());
		}
		if(artifactDTO.getSpIssuerId()!=null)
		{
			sql.append(" and dua.system_code = ? ");
			param.add(artifactDTO.getSpIssuerId());
		}
		logger.info("sql----------"+sql);
		List<DsAssociationPermission> list=this.select(sql.toString(),null,param.toArray(), new BaseProcessor(DsAssociationPermission.class));
		return list;
	}

	public List<PermissionDTO> getAllResource(DsUser user) {
		StringBuffer sql =new StringBuffer();
		List param=new ArrayList();
		sql.append("SELECT  dua.service_provider_id,dua.service_provider_name,dap.url from ds_user_association dua " +
				"LEFT JOIN  ds_association_permission dap " +
				" on dua.id=dap.association_id ");
		sql.append(" where 1=1  and dap.status='A' ");
		if(user.getId()!=null)
		{
			sql.append(" and dua.ds_user_id = ? ");
			param.add(user.getId());
		}

		logger.info("sql----------"+sql);
		List<PermissionDTO> list=this.select(sql.toString(),null,param.toArray(), new BaseProcessor(PermissionDTO.class));
		return list;
	}


}
