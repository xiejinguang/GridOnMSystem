<#if comment>

  TEMPLATE DESCRIPTION:

  This is XHTML template for 'JSF Pages From Entity Beans' action. Templating
  is performed using FreeMaker (http://freemarker.org/) - see its documentation
  for full syntax. Variables available for templating are:

    entityName - name of entity being modified (type: String)
    entityIdField - name of enity ID field 
    managedBean - name of managed choosen in UI (type: String)
    managedBeanProperty - name of managed bean property choosen in UI (type: String)
    item - name of property used for dataTable iteration (type: String)
    comment - always set to "false" (type: Boolean)
    nsLocation - which namespace location to use (http://xmlns.jcp.org in case of JSF2.2, http://java.sun.com otherwise)
    entityDescriptors - list of beans describing individual entities. Bean has following properties:
        label - field label (type: String)
        name - field property name (type: String)
        dateTimeFormat - date/time/datetime formatting (type: String)
        blob - does field represents a large block of text? (type: boolean)
        relationshipOne - does field represent one to one or many to one relationship (type: boolean)
        relationshipMany - does field represent one to many relationship (type: boolean)
        id - field id name (type: String)
        required - is field optional and nullable or it is not? (type: boolean)
        valuesGetter - if item is of type 1:1 or 1:many relationship then use this
            getter to populate <h:selectOneMenu> or <h:selectManyMenu>
    bundle - name of the variable defined in the JSF config file for the resource bundle (type: String)

  This template is accessible via top level menu Tools->Templates and can
  be found in category JavaServer Faces->JSF from Entity.

</#if>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="${nsLocation}/jsf/facelets"
      xmlns:h="${nsLocation}/jsf/html"
      xmlns:f="${nsLocation}/jsf/core"
      xmlns:p="http://primefaces.org/ui">

    <ui:composition>
<#assign managedBeanProperty=managedBean+".selectedItems">
        <p:dialog id="${entityName}ViewDlg" widgetVar="${entityName}ViewDialog" modal="true" dynamic="true" fitViewport="true" minHeight="450" minWidth="600"   position="center"
                  maximizable="true" minimizable="false" draggable="true" closable="true" resizable="true" appendTo="@(body)" closeOnEscape="true" showEffect="explode"
                  onShow="fitViewport(this)" 
                header="${r"#{"}${bundle}.View${entityName}Title${r"}"}">
            <h:form id="${entityName}ViewForm">
                <h:panelGroup id="display" rendered="${r"#{"}not empty ${managedBeanProperty}${r"}"}">
                    <ui:repeat  value="${r"#{"}${managedBean}.selectedItems${r"}"}" var="item">
                        <p:panel toggleable="true" >
                            <f:facet name="header">
                                <h:outputText value="${r"#{"}${bundle}.${entityName}EntityLabel}"/>
                                <h:outputText value="${r"#{"}item.${entityIdField}${r"}"}" title="${r"#{"}${bundle}.${entityName}EntityTitle}"/>
                            </f:facet>
                    <p:panelGrid columns="<#if entityDescriptors?size<=6>2<#elseif entityDescriptors?size<=12>4<#else>6</#if>" >
<#list entityDescriptors as entityDescriptor>
<#assign field = "item."+ entityDescriptor.id>

                        <!-- for ${field} -->
                        <h:outputText value="${r"#{"}${bundle}.${entityName}Label_${entityDescriptor.id?replace(".","_")}${r"}"}"/>
    <#if entityDescriptor.dateTimeFormat?? && entityDescriptor.dateTimeFormat != "">
                        <h:outputText value="${r"#{"}${field}${r"}"}" title="${r"#{"}${bundle}.${entityName}Title_${entityDescriptor.id?replace(".","_")}${r"}"}">
                            <f:convertDateTime pattern="yyyy-MM-dd HH:mm:ss"/>
                        </h:outputText>
    <#elseif entityDescriptor.returnType?matches(".*[Bb]+oolean")>
                        <p:selectBooleanCheckbox value="${r"#{"}${field}${r"}"}" disabled="true"/>
    <#else>
                        <h:outputText value="${r"#{"}${field}${r"}"}" title="${r"#{"}${bundle}.${entityName}Title_${entityDescriptor.id?replace(".","_")}${r"}"}"/>
    </#if>
</#list>
                    </p:panelGrid>
                    </p:panel>
                    </ui:repeat>
                    <p:commandButton type="button" value="${r"#{"}${bundle}.Close${r"}"}" onclick="PF('${entityName}ViewDialog').hide()"/>
                </h:panelGroup>
            </h:form>
        </p:dialog>
    </ui:composition>
  
</html>
