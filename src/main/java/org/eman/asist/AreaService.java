/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.asist;

import java.io.Serializable;
import java.util.List;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author 谢金光
 */
public interface AreaService extends Serializable{

    List<String> getCitys();

    List<String> getCountys();

    List<String> getProvinces();

    void handleCityChange(ValueChangeEvent event);

    void handleProvinceChange(ValueChangeEvent event);
    
}
