/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gmsys.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "fix_demand", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"demandCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FixDemand.findAll", query = "SELECT f FROM FixDemand f"),
    @NamedQuery(name = "FixDemand.findById", query = "SELECT f FROM FixDemand f WHERE f.id = :id"),
    @NamedQuery(name = "FixDemand.findByDemandCode", query = "SELECT f FROM FixDemand f WHERE f.demandCode = :demandCode"),
    @NamedQuery(name = "FixDemand.findByProblemKind", query = "SELECT f FROM FixDemand f WHERE f.problemKind = :problemKind"),
    @NamedQuery(name = "FixDemand.findByProblemSubKind", query = "SELECT f FROM FixDemand f WHERE f.problemSubKind = :problemSubKind"),
    @NamedQuery(name = "FixDemand.findByDetails", query = "SELECT f FROM FixDemand f WHERE f.details = :details"),
    @NamedQuery(name = "FixDemand.findBySolution", query = "SELECT f FROM FixDemand f WHERE f.solution = :solution"),
    @NamedQuery(name = "FixDemand.findByFixKind", query = "SELECT f FROM FixDemand f WHERE f.fixKind = :fixKind"),
    @NamedQuery(name = "FixDemand.findByBudget", query = "SELECT f FROM FixDemand f WHERE f.budget = :budget"),
    @NamedQuery(name = "FixDemand.findByBudgetList", query = "SELECT f FROM FixDemand f WHERE f.budgetList = :budgetList"),
    @NamedQuery(name = "FixDemand.findByDiscoverDate", query = "SELECT f FROM FixDemand f WHERE f.discoverDate = :discoverDate")})
public class FixDemand implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String demandCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String problemKind;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String problemSubKind;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String details;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String solution;
    @Size(max = 25)
    @Column(length = 25)
    private String fixKind;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 12)
    private Float budget;
    @Size(max = 45)
    @Column(length = 45)
    private String budgetList;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date discoverDate;
    @JoinColumn(name = "statName", referencedColumnName = "statName", nullable = false)
    @ManyToOne(optional = false)
    private StationInfo statName;

    public FixDemand() {
    }

    public FixDemand(String id) {
        this.id = id;
    }

    public FixDemand(String id, String demandCode, String problemKind, String problemSubKind, String details, String solution, Date discoverDate) {
        this.id = id;
        this.demandCode = demandCode;
        this.problemKind = problemKind;
        this.problemSubKind = problemSubKind;
        this.details = details;
        this.solution = solution;
        this.discoverDate = discoverDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemandCode() {
        return demandCode;
    }

    public void setDemandCode(String demandCode) {
        this.demandCode = demandCode;
    }

    public String getProblemKind() {
        return problemKind;
    }

    public void setProblemKind(String problemKind) {
        this.problemKind = problemKind;
    }

    public String getProblemSubKind() {
        return problemSubKind;
    }

    public void setProblemSubKind(String problemSubKind) {
        this.problemSubKind = problemSubKind;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getFixKind() {
        return fixKind;
    }

    public void setFixKind(String fixKind) {
        this.fixKind = fixKind;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public String getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(String budgetList) {
        this.budgetList = budgetList;
    }

    public Date getDiscoverDate() {
        return discoverDate;
    }

    public void setDiscoverDate(Date discoverDate) {
        this.discoverDate = discoverDate;
    }

    public StationInfo getStatName() {
        return statName;
    }

    public void setStatName(StationInfo statName) {
        this.statName = statName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FixDemand)) {
            return false;
        }
        FixDemand other = (FixDemand) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gmsys.model.entity.FixDemand[ id=" + id + " ]";
    }
    
}
