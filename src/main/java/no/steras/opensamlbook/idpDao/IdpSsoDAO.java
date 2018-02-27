package no.steras.opensamlbook.idpDao;

import no.steras.opensamlbook.idpDto.ArtifactDTO;
import no.steras.opensamlbook.idpDto.PermissionDTO;
import no.steras.opensamlbook.idpPojo.DsAssociationPermission;
import no.steras.opensamlbook.idpPojo.DsServiceProvider;
import no.steras.opensamlbook.idpPojo.DsUser;
import no.steras.opensamlbook.idpPojo.DsUserAssociation;

import java.util.List;

public interface IdpSsoDAO {


	public List<DsUserAssociation> getUserAssociation(String username, String password,String systemCodeFrom,String systemCodeTo);

	public List<DsUser> getUser(DsUser user);

	public List<DsAssociationPermission> getDsAssociationPermission(ArtifactDTO artifactDTO);


	public List<PermissionDTO> getAllResource(DsUser user);

	public int saveServiceProvider(DsServiceProvider serviceProvider);

    public Object saveUser(DsUser user);

    public List<DsUser> queryUser(DsUser user);

    public List<DsServiceProvider> queryServiceProvider(DsServiceProvider serviceProvider);


}
