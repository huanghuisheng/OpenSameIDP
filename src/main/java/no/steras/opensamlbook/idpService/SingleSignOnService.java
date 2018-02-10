package no.steras.opensamlbook.idpService;

import no.steras.opensamlbook.idpPojo.DsUserAssociation;

import java.util.List;

public interface SingleSignOnService {

    public List<DsUserAssociation> getUserAssociation(String consent);

}