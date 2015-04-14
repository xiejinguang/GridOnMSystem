/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.cc;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.peasant.util.Converters;
import org.peasant.excel2entity.ExcelPOJOUtil;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author 谢金光
 */
@Named(value = "excelImporter")
@ViewScoped
public class ExcelImporter implements Serializable {

    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Inject 
    ServletContext svc;
    
    @Inject
    UserTransaction utr;

    @Inject 
    Converters converters;


    private String configPath;
    private StringBuilder result = new StringBuilder();
    private Class targetClazz;
    private String target;
    private String sheet;

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public StringBuilder getResult() {
        return result;
    }

    public void setResult(StringBuilder result) {
        this.result = result;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        try {
            this.target = target;
            this.targetClazz = Class.forName(target);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExcelImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
@Transactional
    public void handleExcelImport(FileUploadEvent fue) {

        feedbackInfo("文件上传成功，正在执行导入,请稍候……\n", null);
        try (InputStream fis = fue.getFile().getInputstream()) {

            Properties props = new Properties();
            InputStream cis = this.getClass().getResourceAsStream(configPath);
            if (cis == null) {
                cis = svc.getResourceAsStream(configPath);
            }
            if (cis == null) {
                throw new IllegalArgumentException("找不到待导入的Excel的配置文件！！");
            }

            props.load(cis);

            Collection<Object> pojos = ExcelPOJOUtil.worksheetToPOJOs(fis, sheet, targetClazz, props, converters);
            // this.em.getTransaction().begin();JTA模式下不能使用该方法，而应该使用UserTransaction
            utr.begin();

            for (Object pojo : pojos) {
                this.em.merge(pojo);
            }
            // this.em.getTransaction().commit();
            utr.commit();
            feedbackInfo("导入成功，共导入" + pojos.size() + "条记录\n", null);

        } catch (ConstraintViolationException | IOException | ExcelPOJOUtil.ExcelException ex) {
            Logger.getLogger(ExcelImporter.class.getName()).log(Level.SEVERE, null, ex);
            feedbackError("导入失败：" + ex.getMessage(), null);
            if (ex instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) ex;
                StringBuilder r = new StringBuilder();
                for (ConstraintViolation cv : cve.getConstraintViolations()) {
                    r.append(cv.getMessage()).append("\n");
                }
                feedbackWarn("导入的数据不符合约束：", r.toString());
            }
        } catch (Exception ex) {
            feedbackError("导入失败：" + ex.getMessage(), null);
            Logger.getLogger(ExcelImporter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void feedbackInfo(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public void feedbackError(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public void feedbackWarn(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

}
