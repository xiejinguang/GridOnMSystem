/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.basic.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author 谢金光
 */
@Entity
@DiscriminatorValue(value = "BTS")
@Table(name = "basic_BTSnode")
public class BTSnode extends Netnode {

    
    @Column
    private int bandWidth=2048;//kb为衡量单位

    /**
     * Get the value of bandWidth
     *
     * @return the value of bandWidth
     */
    public int getBandWidth() {
        return bandWidth;
    }

    /**
     * Set the value of bandWidth
     *
     * @param bandWidth new value of bandWidth
     */
    public void setBandWidth(int bandWidth) {
        this.bandWidth = bandWidth;
    }

    public BTSnode() {
    }

    public BTSnode(String id, String ossCode) {
        super(id, ossCode);
    }

}
