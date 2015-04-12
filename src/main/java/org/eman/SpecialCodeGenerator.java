/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.eman.asist.facade.KeyCodeFacade;
import org.eman.asist.model.KeyCode;
import org.peasant.util.Cn2Spell;

/**
 *
 * @author 谢金光
 */
@Named
@ApplicationScoped
public class SpecialCodeGenerator implements Serializable{ 

    public SpecialCodeGenerator() {
    }

    protected  final static String STATION_PREFIX = "BS";
    protected final static int STATION_NUMBER_WIDTH = 4;
    protected final static String FIXDEMAND_PREFIX = "FD";
    protected final static int FIXDEMAND_NUMBER_WIDTH = 6;
    @Inject
    KeyCodeFacade keyCodeFacade;

    public String genNextStationCode(String province, String city, String county) {
        String c = Cn2Spell.converterToFirstSpell(STATION_PREFIX + province + city + county).toUpperCase();
        return c + genKeyCode(c, STATION_NUMBER_WIDTH);
    }

    public String genFixDemandCode(String province, String city) {
        String c = Cn2Spell.converterToFirstSpell(FIXDEMAND_PREFIX + province + city).toUpperCase();
        return c + genKeyCode(c, FIXDEMAND_NUMBER_WIDTH);
    }
    @Transactional
    protected String genKeyCode(String key, int length) {
        KeyCode kc = keyCodeFacade.find(key);
        if (kc == null) {
            kc = new KeyCode(key, 1);
        }
        int cn = kc.getNumber();
        kc.setNumber(cn + 1);
        keyCodeFacade.edit(kc);
        String r = String.valueOf(cn);
        if (r.length() < length) {
            int n = length - r.length();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < n; i++) {
                sb.append('0');
            }
            sb.append(r);
            r = sb.toString();
        }
        return r;
    }
}
