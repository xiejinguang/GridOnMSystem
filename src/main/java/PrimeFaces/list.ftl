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
        returnType - fully qualified data type of the field (type: String)
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
      xmlns:pta="${nsLocation}/jsf/passthrough"
      xmlns:pt="${nsLocation}/jsf"
      xmlns:cc="${nsLocation}/jsf/composite"
      xmlns:h="${nsLocation}/jsf/html"
      xmlns:f="${nsLocation}/jsf/core"
      xmlns:p="http://primefaces.org/ui">

    <ui:composition template="../template.xhtml">


        <ui:define name="title">
            <h:outputText value="${r"#{"}${bundle}.List${entityName}Title${r"}"}"></h:outputText>
        </ui:define>

        <ui:define name="body">
                
                <h:form id="SearchConsForm">
                    
                        <p:fieldset id="searchField" legend="${r"${"}${bundle}.SearchConsTitle${r"}"}" toggleable="true" toggleSpeed="500" >

                            <p:panelGrid id="searchConsGrid" columns="8">

<#list entityDescriptors as entityDescriptor>

                                    <p:outputLabel value="${r"#{"}${bundle}.${entityName}Label_${entityDescriptor.id?replace(".","_")}${r"}"}" for="${entityDescriptor.id?replace(".","_")}" />
<#if entityDescriptor.dateTimeFormat?? && entityDescriptor.dateTimeFormat != "">
                                    <p:calendar id="${entityDescriptor.id?replace(".","_")}" pattern="yyyy-MM-dd  HH:mm:ss" mask="true"  locale="zh_CN" showButtonPanel="true" value="${r"#{"}${managedBean}.searchCons['${entityDescriptor.id}']${r"}"}" title="${r"#{"}${bundle}.${entityName}Title_${entityDescriptor.id?replace(".","_")}${r"}"}" showOn="both"/>
<#elseif entityDescriptor.returnType?matches(".*[Bb]+oolean")>
                                    <p:selectBooleanCheckbox id="${entityDescriptor.id?replace(".","_")}" value="${r"#{"}${managedBean}.searchCons['${entityDescriptor.id}']${r"}"}" converter="javax.faces.Boolean" />
<#elseif entityDescriptor.blob>
                                     <p:inputTextarea rows="4" cols="30" id="${entityDescriptor.id?replace(".","_")}" value="${r"#{"}${managedBean}.searchCons['${entityDescriptor.id}']${r"}"}" title="${r"#{"}${bundle}.${entityName}Title_${entityDescriptor.id?replace(".","_")}${r"}"}" />
<#elseif entityDescriptor.relationshipOne>
                                     <p:selectOneMenu id="${entityDescriptor.id?replace(".","_")}" value="${r"#{"}${managedBean}.searchCons['${entityDescriptor.id}']${r"}"}" filter="true"  filterMatchMode="contains" <#if !(entityDescriptor.returnType?matches(".*[Ss]+tring"))> <#if (entityDescriptor.returnType?matches("java\\.lang\\..*"))> converter="javax.faces.${entityDescriptor.returnType?substring((entityDescriptor.returnType?last_index_of('.'))+1)}" <#else> converter="${entityDescriptor.returnType}"</#if> </#if> >
                                        <f:selectItem itemLabel="${r"#{"}bundle.SelectOneMessage${r"}"}" />
                                        <f:selectItems value="${r"#{"}${entityDescriptor.valuesGetter}${r"}"}"
                                                 var="${entityDescriptor.id?replace(".","_")}Item"
                                                itemValue="${r"#{"}${entityDescriptor.id?replace(".","_")}Item${r"}"}"/>
                                    </p:selectOneMenu>
<#elseif entityDescriptor.relationshipMany>
                                    <p:selectManyMenu id="${entityDescriptor.id?replace(".","_")}" value="${r"#{"}${managedBean}.searchCons['${entityDescriptor.id}']${r"}"}" filter="true"  filterMatchMode="contains" showCheckbox="true" >
                                        <f:selectItem itemLabel="${r"#{"}bundle.SelectManyMessage${r"}"}" />
                                        <f:selectItems value="${r"#{"}${entityDescriptor.valuesGetter}${r"}"}"
                                            var="${entityDescriptor.id?replace(".","_")}Item"
                                            itemValue="${r"#{"}${entityDescriptor.id?replace(".","_")}Item${r"}"}"/>
<#else>
                                    <p:inputText id="${entityDescriptor.id?replace(".","_")}" value="${r"#{"}${managedBean}.searchCons['${entityDescriptor.id}']${r"}"}" title="${r"#{"}${bundle}.${entityName}Title_${entityDescriptor.id?replace(".","_")}${r"}"}" <#if !(entityDescriptor.returnType?matches(".*[Ss]+tring"))> <#if (entityDescriptor.returnType?matches("java\\.lang\\..*"))> converter="javax.faces.${entityDescriptor.returnType?substring((entityDescriptor.returnType?last_index_of('.'))+1)}" <#else> converter="${entityDescriptor.returnType}"</#if> </#if> />
</#if>

</#list>
                                </p:panelGrid>
                                <h:panelGroup> 
                                    <p:commandButton id="searchButton" icon="ui-icon-search"   value="${r"${"}${bundle}.Search${r"}"}" actionListener="${r"#{"}${managedBean}.searchItems${r"}"}" update=":growl,@form:@parent:${entityName}ListForm:datalist"/>
                                  
                                    <p:commandButton id="searchAllButton" icon="ui-icon-search"   value="${r"${"}${bundle}.GetAll${r"}"}" actionListener="${r"#{"}${managedBean}.allItems${r"}"}" update=":growl,@form:@parent:${entityName}ListForm:datalist"/>
                                </h:panelGroup> 
                        </p:fieldset>
        </h:form>

   <h:form id="${entityName}ListForm">
                        <p:dataTable id="datalist" value="${r"#{"}${managedBeanProperty}${r"}"}" var="${item}"
                            selection="${r"#{"}${managedBean}${r".selectedItems}"}"
                            rowKey="${r"#{"}${item}.${entityIdField}${r"}"}" rowIndexVar="index"
                            
                             
                             draggableColumns="true" resizableColumns="true" liveResize="true"
                             scrollable="true"    liveScroll="false" scrollHeight="420"
                             sortMode="multiple" 
                             editable="true" 
                             filteredValue="${r"#{"}${managedBean}${r".filteredValue}"}"
                             stickyHeader="false" 


                             rows="20"    
                            paginatorTemplate="{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}"
                            rowsPerPageTemplate="10,20,30,40,50,100,200,500,1000"  paginator="true" paginatorPosition="bottom"                            
                            currentPageReportTemplate="第{currentPage}页,共{totalPages}页；第{startRecord}条至第{endRecord}条,共{totalRecords}条记录"
                            >

                            <p:ajax event="rowSelect"   update="createButton,viewButton,editButton,deleteButton"/>
                            <p:ajax event="rowUnselect" update="createButton,viewButton editButton,deleteButton"/>
                            <p:ajax event="toggleSelect" update="createButton,viewButton editButton,deleteButton"/>

                            <p:ajax event="rowUnselectCheckbox" update="createButton,viewButton editButton,deleteButton"/>
                            <p:ajax event="rowSelectCheckbox" update="createButton,viewButton editButton,deleteButton"/>

                            <p:ajax event="rowDblselect" update="createButton,viewButton editButton,deleteButton"/>
                            <p:ajax event="rowSelectRadio" update="createButton,viewButton editButton,deleteButton"/>

                            <f:facet name="header"><p:outputLabel value="${r"#{"}${bundle}.List${entityName}Title${r"}"}" /></f:facet>

                            <p:column exportable="false" style="width:25px;text-align:center" disabledSelection="true" toggleable="false" resizable="false"  ><h:outputText value="${index+1}"/></p:column>

                            <p:column selectionMode="multiple" style="width:15px;text-align:center" toggleable="false" exportable="false"/>  

<#list entityDescriptors as entityDescriptor>

                            <p:column sortBy="${r"#{"}${entityDescriptor.name}${r"}"}" filterBy="${r"#{"}${entityDescriptor.name}${r"}"}" filterMatchMode="contains">
                                <f:facet name="header">
                                    <h:outputText value="${r"#{"}${bundle}.${entityName}Title_${entityDescriptor.id?replace(".","_")}${r"}"}"/>
                                </f:facet>
    <#if entityDescriptor.dateTimeFormat?? && entityDescriptor.dateTimeFormat != "">
                                <h:outputText value="${r"#{"}${entityDescriptor.name}${r"}"}">
                                    <f:convertDateTime pattern="yyyy-MM-dd  HH:mm:ss" />
                                </h:outputText>
    <#elseif entityDescriptor.returnType?matches(".*[Bb]+oolean")>
                                <p:selectBooleanCheckbox value="${r"#{"}${entityDescriptor.name}${r"}"}" disabled="true"/>
    <#else>
                                <h:outputText value="${r"#{"}${entityDescriptor.name}${r"}"}"/>
    </#if>
                            </p:column>
</#list>
                            <f:facet name="footer">
                          
                                <p:commandButton id="createButton" icon="ui-icon-plus"   value="${r"#{"}${bundle}.Create${r"}"}" actionListener="${r"#{"}${managedBean}.prepareCreate${r"}"}" process="@this" update="@form:@parent:${entityName}CreateForm" oncomplete="PF('${entityName}CreateDialog').show()"/>
                                <p:commandButton id="viewButton"   icon="ui-icon-search" value="${r"#{"}${bundle}.View${r"}"}" process="@this" update="@form:@parent:${entityName}ViewForm" oncomplete="PF('${entityName}ViewDialog').show()" disabled="${r"#{"}empty ${managedBean}.selectedItems${r"}"}"/>
                                <p:commandButton id="editButton"   icon="ui-icon-pencil" value="${r"#{"}${bundle}.Edit${r"}"}" process="@this" update="@form:@parent:${entityName}EditForm" oncomplete="PF('${entityName}EditDialog').show()" disabled="${r"#{"}empty ${managedBean}.selectedItems${r"}"}"/>
                                <p:commandButton id="deleteButton" icon="ui-icon-trash"  value="${r"#{"}${bundle}.Delete${r"}"}" actionListener="${r"#{"}${managedBean}${r".destroy}"}" process="@this" update=":growl,datalist" disabled="${r"#{"}empty ${managedBean}.selectedItems${r"}"}"/>
                                <p:commandButton id="toggler" type="button" value="Columns" style="float:right" icon="ui-icon-calculator" />
                                <p:columnToggler datasource="datalist" trigger="toggler" />
                          
                            </f:facet>
                        </p:dataTable>
                
                </h:form>
            <ui:include src="Create.xhtml"/>
            <ui:include src="Edit.xhtml"/>
            <ui:include src="View.xhtml"/>
        </ui:define>
    </ui:composition>
</html>
