/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.gmsys.model;

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
import org.eman.basic.model.BasicStation;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "gmsys_fix_demand", catalog = "jobpromotion", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"demandCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GmsysFixDemand.findAll", query = "SELECT g FROM GmsysFixDemand g"),
    @NamedQuery(name = "GmsysFixDemand.findByDemandID", query = "SELECT g FROM GmsysFixDemand g WHERE g.demandID = :demandID"),
    @NamedQuery(name = "GmsysFixDemand.findByDemandCode", query = "SELECT g FROM GmsysFixDemand g WHERE g.demandCode = :demandCode"),
    @NamedQuery(name = "GmsysFixDemand.findByProblemKind", query = "SELECT g FROM GmsysFixDemand g WHERE g.problemKind = :problemKind"),
    @NamedQuery(name = "GmsysFixDemand.findByProblemSubKind", query = "SELECT g FROM GmsysFixDemand g WHERE g.problemSubKind = :problemSubKind"),
    @NamedQuery(name = "GmsysFixDemand.findByProblemDetails", query = "SELECT g FROM GmsysFixDemand g WHERE g.problemDetails = :problemDetails"),
    @NamedQuery(name = "GmsysFixDemand.findBySolution", query = "SELECT g FROM GmsysFixDemand g WHERE g.solution = :solution"),
    @NamedQuery(name = "GmsysFixDemand.findByFixKind", query = "SELECT g FROM GmsysFixDemand g WHERE g.fixKind = :fixKind"),
    @NamedQuery(name = "GmsysFixDemand.findByBudgetList", query = "SELECT g FROM GmsysFixDemand g WHERE g.budgetList = :budgetList"),
    @NamedQuery(name = "GmsysFixDemand.findByBudget", query = "SELECT g FROM GmsysFixDemand g WHERE g.budget = :budget"),
    @NamedQuery(name = "GmsysFixDemand.findByDiscoverDate", query = "SELECT g FROM GmsysFixDemand g WHERE g.discoverDate = :discoverDate"),
    @NamedQuery(name = "GmsysFixDemand.findBySource", query = "SELECT g FROM GmsysFixDemand g WHERE g.source = :source"),
    @NamedQuery(name = "GmsysFixDemand.findByStatus", query = "SELECT g FROM GmsysFixDemand g WHERE g.status = :status")})
public class GmsysFixDemand implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String demandID;
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
    private String problemDetails;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String solution;
    @Size(max = 25)
    @Column(length = 25)
    private String fixKind;
    @Size(max = 45)
    @Column(length = 45)
    private String budgetList;
    private Long budget;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date discoverDate;
    @Size(max = 45)
    @Column(length = 45)
    private String source;
    @Size(max = 45)
    @Column(length = 45)
    private String status;
    @JoinColumn(name = "statID", referencedColumnName = "statID", nullable = false)
    @ManyToOne(optional = false)
    private BasicStation statID;

    public GmsysFixDemand() {
    }


    public GmsysFixDemand(String demandID) {
        this.demandID = demandID;
    }

    public GmsysFixDemand(String demandID, String demandCode, String problemKind, String problemSubKind, String problemDetails, String solution, Date discoverDate) {
        this.demandID = demandID;
        this.demandCode = demandCode;
        this.problemKind = problemKind;
        this.problemSubKind = problemSubKind;
        this.problemDetails = problemDetails;
        this.solution = solution;
        this.discoverDate = discoverDate;
    }

    public String getDemandID() {
        return demandID;
    }

    public void setDemandID(String demandID) {
        this.demandID = demandID;
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

    public String getProblemDetails() {
        return problemDetails;
    }

    public void setProblemDetails(String problemDetails) {
        this.problemDetails = problemDetails;
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

    public String getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(String budgetList) {
        this.budgetList = budgetList;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Date getDiscoverDate() {
        return discoverDate;
    }

    public void setDiscoverDate(Date discoverDate) {
        this.discoverDate = discoverDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (demandID != null ? demandID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GmsysFixDemand)) {
            return false;
        }
        GmsysFixDemand other = (GmsysFixDemand) object;
        if ((this.demandID == null && other.demandID != null) || (this.demandID != null && !this.demandID.equals(other.demandID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.gmsys.model.GmsysFixDemand[ demandID=" + demandID + " ]";
    }
    
}
