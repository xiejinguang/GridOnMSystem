/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.asist;

import java.util.Collection;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eman.asist.facade.AsistCandidateValueFacade;
import org.eman.asist.model.CandidateValue;

/**
 *
 * @author 谢金光
 */
@Singleton
public class CandidateValuesProvider {

    @Inject
    AsistCandidateValueFacade candidateValueFacade;

    @Produces
    @Dependent
    @Values(key = CandidateValueConstants.CompanyKey)
    public CandidateValue getCompanys() {
        return candidateValueFacade.findBy(CandidateValueConstants.CompanyKey, CandidateValueConstants.CompanyValue, true).get(0);
    }

    @Produces
    @Dependent
    @Values(key = CandidateValueConstants.GridKey)
    public CandidateValue getGrids() {
        return candidateValueFacade.findBy(CandidateValueConstants.GridKey, CandidateValueConstants.GridValue, true).get(0);
    }

    @Produces
    @Dependent
    @Values(key = CandidateValueConstants.StationStatusKey)
    public CandidateValue getStationStatus() {
        return candidateValueFacade.findBy(CandidateValueConstants.StationStatusKey, CandidateValueConstants.StationStatusValue, true).get(0);
    }

    @Produces
    @Dependent
    @Values(key = CandidateValueConstants.StationTypekey)
    public CandidateValue getStationTypes() {
        return candidateValueFacade.findBy(CandidateValueConstants.StationTypekey, CandidateValueConstants.StationTypeValue, true).get(0);
    }
}
