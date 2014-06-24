/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.asist;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eman.asist.facade.AsistCandidateValueFacade;
import org.eman.asist.model.CandidateValue;

/**
 *
 * @author 谢金光
 */
@Named(value = "areaValueController")
@Dependent
public class AreaValueController implements AreaService {

    private CandidateValue countyCVs;
    private CandidateValue provinceCVs;
    private CandidateValue cityCVs;

    private List<String> provinces;
    private List<String> citys;
    private List<String> countys;

    @Inject
    AsistCandidateValueFacade candidateValueFacade;

    @PostConstruct
    public void init() {
        this.provinceCVs = candidateValueFacade.findBy(CandidateValueConstants.ProvinceKey, CandidateValueConstants.ProvinceValue, true).get(0);
    }

    public CandidateValue getCountyCVs() {
        return countyCVs;
    }

    public void setCountyCVs(CandidateValue countyCVs) {
        this.countyCVs = countyCVs;
    }

    public CandidateValue getProvinceCVs() {
        return provinceCVs;
    }

    public void setProvinceCVs(CandidateValue provinceCVs) {
        this.provinceCVs = provinceCVs;
    }

    public CandidateValue getCityCVs() {
        return cityCVs;
    }

    public void setCityCVs(CandidateValue cityCVs) {
        this.cityCVs = cityCVs;
    }

    @Override
    public void handleProvinceChange(ValueChangeEvent event) {
        this.cityCVs = getChildCandidateValueByValue(provinceCVs, (String) event.getNewValue());
        this.citys = getChildrenValues(cityCVs);
    }

    public CandidateValue getChildCandidateValueByValue(CandidateValue parent, String value) {
        for (CandidateValue v : parent.getChildren()) {
            if (value.equals(v.getValue())) {
                return v;
            }
        }
        return null;
    }

    public List<String> getChildrenValues(CandidateValue parent) {

        List<String> ls = new LinkedList<>();
        for (CandidateValue province : parent.getChildren()) {
            ls.add(province.getValue());
        }
        return ls;
    }

    @Override
    public void handleCityChange(ValueChangeEvent event) {
        this.countyCVs = getChildCandidateValueByValue(cityCVs, (String) event.getNewValue());
         this.countys = getChildrenValues(countyCVs);
    }

    @Override
    public List<String> getProvinces() {
        if (provinces == null) {
            provinces = new LinkedList<>();
            for (CandidateValue province : this.provinceCVs.getChildren()) {
                provinces.add(province.getValue());
            }
        }
        return provinces;
    }

    public void setProvinces(List<String> provinces) {
        this.provinces = provinces;
    }

    @Override
    public List<String> getCitys() {
        return citys;
    }

    public void setCitys(List<String> citys) {
        this.citys = citys;
    }

    @Override
    public List<String> getCountys() {
        return countys;
    }

    public void setCountys(List<String> countys) {
        this.countys = countys;
    }
}
