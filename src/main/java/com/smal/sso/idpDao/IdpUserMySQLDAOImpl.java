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


public class IdpUserMySQLDAOImpl extends BaseDAO implements IdpUserDAO {
	private static Logger logger = (Logger) LoggerFactory.getLogger(IdpUserMySQLDAOImpl.class);


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

    public void saveUserByBatch(List<DsUser> user) {
//        return this.createByBatch(user);
		for (DsUser dsUser:user)
		{
			this.create(dsUser);
		}
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
//        return (DsUser) this.selectOne(user);
        return null;
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
