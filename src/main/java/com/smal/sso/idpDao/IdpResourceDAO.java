package com.smal.sso.idpDao;

import com.smal.sso.idpDto.ArtifactDTO;
import com.smal.sso.idpDto.PermissionDTO;
import com.smal.sso.idpPojo.DsAssociationPermission;
import com.smal.sso.idpPojo.DsServiceProvider;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpPojo.DsUserAssociation;

import java.util.List;

public interface IdpResourceDAO {



	public List<DsAssociationPermission> getDsAssociationPermission(ArtifactDTO artifactDTO);


	public List<PermissionDTO> getAllResource(DsUser user);

	public List<DsUserAssociation> getUserAssociation(String username, String password,String systemCodeFrom,String systemCodeTo);


}
