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
import org.eman.basic.model.Station;
import org.peasant.model.Labeled;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "gmsys_fix_demand", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"demandCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FixDemand.findAll", query = "SELECT f FROM FixDemand f"),
    @NamedQuery(name = "FixDemand.findById", query = "SELECT f FROM FixDemand f WHERE f.id = :id"),
    @NamedQuery(name = "FixDemand.findByDemandCode", query = "SELECT f FROM FixDemand f WHERE f.demandCode = :demandCode"),
    @NamedQuery(name = "FixDemand.findByProblemKind", query = "SELECT f FROM FixDemand f WHERE f.problemKind = :problemKind"),
    @NamedQuery(name = "FixDemand.findByProblemSubKind", query = "SELECT f FROM FixDemand f WHERE f.problemSubKind = :problemSubKind"),
    @NamedQuery(name = "FixDemand.findByProblemDetails", query = "SELECT f FROM FixDemand f WHERE f.problemDetails = :problemDetails"),
    @NamedQuery(name = "FixDemand.findBySolution", query = "SELECT f FROM FixDemand f WHERE f.solution = :solution"),
    @NamedQuery(name = "FixDemand.findByFixKind", query = "SELECT f FROM FixDemand f WHERE f.fixKind = :fixKind"),
    @NamedQuery(name = "FixDemand.findByBudgetList", query = "SELECT f FROM FixDemand f WHERE f.budgetList = :budgetList"),
    @NamedQuery(name = "FixDemand.findByBudget", query = "SELECT f FROM FixDemand f WHERE f.budget = :budget"),
    @NamedQuery(name = "FixDemand.findByDiscoverDate", query = "SELECT f FROM FixDemand f WHERE f.discoverDate = :discoverDate"),
    @NamedQuery(name = "FixDemand.findBySource", query = "SELECT f FROM FixDemand f WHERE f.source = :source"),
    @NamedQuery(name = "FixDemand.findByStatus", query = "SELECT f FROM FixDemand f WHERE f.status = :status")})
public class FixDemand implements Serializable, Labeled {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String demandCode;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 255)
    private String demandName;

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
    @Column(nullable = false, length = 1024)
    private String problemDetails;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 1024)
    private String solution;
    @Size(max = 25)
    @Column(length = 25)
    private String fixKind;
    @Size(max = 45)
    @Column(length = 1024)
    private String budgetList;
    private Long budget;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date discoverDate;

    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Size(max = 45)
    @Column(length = 45)
    private String source;
    @Size(max = 45)
    @Column(length = 45)
    private String status;
    @JoinColumn(name = "stationId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Station stationId;

    public FixDemand() {
    }

    public FixDemand(String id) {
        this.id = id;
    }

    public FixDemand(String id, String demandCode, String problemKind, String problemSubKind, String problemDetails, String solution, Date discoverDate) {
        this.id = id;
        this.demandCode = demandCode;
        this.problemKind = problemKind;
        this.problemSubKind = problemSubKind;
        this.problemDetails = problemDetails;
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

    public Station getStationId() {
        return stationId;
    }

    public void setStationId(Station stationId) {
        this.stationId = stationId;
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
        return "org.eman.basic.model.FixDemand[ id=" + id + " ]";
    }

    @Override
    public String getLabel() {
        return demandCode;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
