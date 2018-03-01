package no.steras.opensamlbook.idpDao;

import com.tone.dao.BaseDAO;
import com.tone.util.BaseProcessor;
import no.steras.opensamlbook.idpDto.ArtifactDTO;
import no.steras.opensamlbook.idpDto.PermissionDTO;
import no.steras.opensamlbook.idpPojo.DsAssociationPermission;
import no.steras.opensamlbook.idpPojo.DsServiceProvider;
import no.steras.opensamlbook.idpPojo.DsUser;
import no.steras.opensamlbook.idpPojo.DsUserAssociation;
import no.steras.opensamlbook.idpWebAction.IdpSsoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class IdpSsoMySQLDAOImpl extends BaseDAO implements IdpSsoDAO {
	private static Logger logger = (Logger) LoggerFactory.getLogger(IdpSsoMySQLDAOImpl.class);

	public List<DsUserAssociation> getUserAssociation(String username, String password,String systemCodeFrom,String systemCodeTo) {
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

	public List<DsUser> getUser(DsUser user) {

		StringBuffer sql =new StringBuffer();
		List param=new ArrayList();
		sql.append("SELECT  * from ds_user");
        sql.append(" where login_name like ? ");
		param.add("%"+user.getLoginName()+"%");
		List<DsUser> list=this.select(sql.toString(),null,param.toArray(), new BaseProcessor(DsUser.class));
		return list;
	}

	public List<DsServiceProvider> getServiceProvider(DsServiceProvider serviceProvider) {
		StringBuffer sql =new StringBuffer();
		List param=new ArrayList();
		sql.append("SELECT  * from ds_service_provider where status = 'A' ");

//		if(serviceProvider.getId()!=null)
//		{
//			sql.append(" and id = ? ");
//			param.add(serviceProvider.getId());
//		}
		List<DsServiceProvider> list=this.select(sql.toString(),null,param.toArray(), new BaseProcessor(DsServiceProvider.class));
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

	public int saveServiceProvider(DsServiceProvider serviceProvider) {

		Object object = 	this.create(serviceProvider);
		if(object!=null)
		{
			return Integer.valueOf(String.valueOf(object));
		}else {
			return -1;
		}

	}

    public Object saveUser(DsUser user) {
        return this.create(user);
    }

    public Object saveUserByBatch(List<DsUser> user) {
        return this.createByBatch(user);
    }

    public List<DsUser> queryUser(DsUser user) {

        StringBuffer sql =new StringBuffer();
        List param=new ArrayList();
        sql.append("SELECT  * from ds_user where 1=1");
        if(user.getLoginName()!=null)
        {
            sql.append(" and login_name like ? ");
            param.add("%"+user.getLoginName()+"%");
        }
        if(user.getPassword()!=null)
        {
            sql.append(" and password = ? ");
            param.add(user.getPassword());
        }
        List<DsUser> list=this.select(sql.toString(),null,param.toArray(), new BaseProcessor(DsUser.class));
        return list;
    }

    public DsUser queryUserById(DsUser user) {
        return (DsUser) this.selectOne(user);
    }

    public List<DsServiceProvider> queryServiceProvider(DsServiceProvider serviceProvider) {
        StringBuffer sql =new StringBuffer();
        List param=new ArrayList();
        sql.append("SELECT  * from ds_service_provider where 1=1 and status ='A' ");
//        if(serviceProvider.getSeviceName()!=null)
//        {
            sql.append(" and sevice_name like ? ");
            param.add("%"+serviceProvider.getSeviceName()+"%");
//        }
        List<DsServiceProvider> list=this.select(sql.toString(),null,param.toArray(), new BaseProcessor(DsServiceProvider.class));
        return list;
    }


}
