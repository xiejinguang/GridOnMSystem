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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "fix_needs", catalog = "jobpromotion", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"code"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FixNeeds.findAll", query = "SELECT f FROM FixNeeds f"),
    @NamedQuery(name = "FixNeeds.findById", query = "SELECT f FROM FixNeeds f WHERE f.id = :id"),
    @NamedQuery(name = "FixNeeds.findByCode", query = "SELECT f FROM FixNeeds f WHERE f.code = :code"),
    @NamedQuery(name = "FixNeeds.findByProblemKind", query = "SELECT f FROM FixNeeds f WHERE f.problemKind = :problemKind"),
    @NamedQuery(name = "FixNeeds.findByProblemSubkind", query = "SELECT f FROM FixNeeds f WHERE f.problemSubkind = :problemSubkind"),
    @NamedQuery(name = "FixNeeds.findByDetails", query = "SELECT f FROM FixNeeds f WHERE f.details = :details"),
    @NamedQuery(name = "FixNeeds.findBySolution", query = "SELECT f FROM FixNeeds f WHERE f.solution = :solution"),
    @NamedQuery(name = "FixNeeds.findByFixKind", query = "SELECT f FROM FixNeeds f WHERE f.fixKind = :fixKind"),
    @NamedQuery(name = "FixNeeds.findByBudget", query = "SELECT f FROM FixNeeds f WHERE f.budget = :budget"),
    @NamedQuery(name = "FixNeeds.findByBudgetList", query = "SELECT f FROM FixNeeds f WHERE f.budgetList = :budgetList"),
    @NamedQuery(name = "FixNeeds.findByDisDate", query = "SELECT f FROM FixNeeds f WHERE f.disDate = :disDate")})
public class FixNeeds implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "code", nullable = false, length = 45)
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "problem_kind", nullable = false, length = 45)
    private String problemKind;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "problem_subkind", nullable = false, length = 45)
    private String problemSubkind;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "details", nullable = false, length = 255)
    private String details;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "solution", nullable = false, length = 255)
    private String solution;
    @Size(max = 25)
    @Column(name = "fix_kind", length = 25)
    private String fixKind;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "budget", precision = 12)
    private Float budget;
    @Size(max = 45)
    @Column(name = "budget_list", length = 45)
    private String budgetList;
    @Column(name = "dis_date")
    @Temporal(TemporalType.DATE)
    private Date disDate;
    @JoinColumn(name = "station_name", referencedColumnName = "statname", nullable = false)
    @ManyToOne(optional = false)
    private StationInfo stationName;

    public FixNeeds() {
    }

    public FixNeeds(Integer id) {
        this.id = id;
    }

    public FixNeeds(Integer id, String code, String problemKind, String problemSubkind, String details, String solution) {
        this.id = id;
        this.code = code;
        this.problemKind = problemKind;
        this.problemSubkind = problemSubkind;
        this.details = details;
        this.solution = solution;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProblemKind() {
        return problemKind;
    }

    public void setProblemKind(String problemKind) {
        this.problemKind = problemKind;
    }

    public String getProblemSubkind() {
        return problemSubkind;
    }

    public void setProblemSubkind(String problemSubkind) {
        this.problemSubkind = problemSubkind;
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

    public Date getDisDate() {
        return disDate;
    }

    public void setDisDate(Date disDate) {
        this.disDate = disDate;
    }

    public StationInfo getStationName() {
        return stationName;
    }

    public void setStationName(StationInfo stationName) {
        this.stationName = stationName;
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
        if (!(object instanceof FixNeeds)) {
            return false;
        }
        FixNeeds other = (FixNeeds) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gmsys.model.entity.FixNeeds[ id=" + id + " ]";
    }
    
}
