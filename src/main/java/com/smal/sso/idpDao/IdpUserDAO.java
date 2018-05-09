package com.smal.sso.idpDao;

import com.smal.sso.idpDto.ArtifactDTO;
import com.smal.sso.idpDto.PermissionDTO;
import com.smal.sso.idpPojo.DsAssociationPermission;
import com.smal.sso.idpPojo.DsServiceProvider;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpPojo.DsUserAssociation;

import java.util.List;

public interface IdpUserDAO {

	public List<DsUser> getUser(DsUser user);
	public Object saveUser(DsUser user);
	public List<DsUser> queryUser(DsUser user);
	public DsUser queryUserById(DsUser user);


	public int saveServiceProvider(DsServiceProvider serviceProvider);
	public List<DsServiceProvider> getServiceProvider(DsServiceProvider serviceProvider);
    public List<DsServiceProvider> queryServiceProvider(DsServiceProvider serviceProvider);

	public void saveUserByBatch(List<DsUser> user);
}
