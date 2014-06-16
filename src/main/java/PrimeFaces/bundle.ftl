<#if comment>

  TEMPLATE DESCRIPTION:

  This is Bundle.properties template for 'JSF Pages From Entity Beans' action. Templating
  is performed using FreeMaker (http://freemarker.org/) - see its documentation
  for full syntax. Variables available for templating are:

    comment - always (Boolean; always FALSE)
    projectName - display name of the project (type: String)
    entities - list of beans with following properites:
        entityClassName - controller class name (type: String)
        entityDescriptors - list of beans describing individual entities. Bean has following properties:
            label - part of bundle key name for label (type: String)
            title - part of bundle key name for title (type: String)
            name - field property name (type: String)
            dateTimeFormat - date/time/datetime formatting (type: String)
            blob - does field represents a large block of text? (type: boolean)
            relationshipOne - does field represent one to one or many to one relationship (type: boolean)
            relationshipMany - does field represent one to many relationship (type: boolean)
            id - field id name (type: String)
            required - is field optional and nullable or it is not? (type: boolean)
            valuesGetter - if item is of type 1:1 or 1:many relationship then use this
                getter to populate <h:selectOneMenu> or <h:selectManyMenu>

  This template is accessible via top level menu Tools->Templates and can
  be found in category JavaServer Faces->JSF from Entity.

</#if>
PersistenceErrorOccured=A persistence error occurred.
Search=Search
SearchAll = SearchAll
GetAll = ALL
SearchConsTitle = Search Conditions
Add = Add
AddNode = AddNode
AddSubNode = AddSubNode
Create=Create
View=View
Edit=Edit
Delete=Delete
Close=Close
Cancel=Cancel
Save=Save
SelectOneMessage=Select One...
Home=Home
Maintenance=Maintenance
AppName=${projectName}
AppNameLabel=${projectName}
ModuleName = Module
ModuleNameLabel = Module

<#list entities as entity>
#
#
#For ${entity.entityClassName}

${entity.entityClassName}Created=${entity.entityClassName} was successfully created.
${entity.entityClassName}Updated=${entity.entityClassName} was successfully updated.
${entity.entityClassName}Deleted=${entity.entityClassName} was successfully deleted.
${entity.entityClassName}EntityLabel=${entity.entityClassName}-
${entity.entityClassName}EntityTitle=${entity.entityClassName}

#For Menu
${entity.entityClassName}MenuLabel = ${entity.entityClassName}


#For Link
${entity.entityClassName}EditLink=Edit
${entity.entityClassName}ViewLink=View
${entity.entityClassName}CreateLink=Create New ${entity.entityClassName}
${entity.entityClassName}IndexLink=Index
${entity.entityClassName}DestroyLink=Destroy
${entity.entityClassName}SaveLink=Save
${entity.entityClassName}ShowAllLink=Show All ${entity.entityClassName} Items


#For List ${entity.entityClassName}
    <#list entity.entityDescriptors as entityDescriptor>
${entity.entityClassName}Title_${entityDescriptor.id?replace(".","_")}=${entityDescriptor.label}
${entity.entityClassName}Label_${entityDescriptor.id?replace(".","_")}=${entityDescriptor.label}:
    </#list>
#
#For Create ${entity.entityClassName}

Create${entity.entityClassName}Title=Create New ${entity.entityClassName}

    <#list entity.entityDescriptors as entityDescriptor>
Create${entity.entityClassName}Label_${entityDescriptor.id?replace(".","_")}=${entityDescriptor.label}:
<#if entityDescriptor.required>Create${entity.entityClassName}RequiredMessage_${entityDescriptor.id?replace(".","_")}=The ${entityDescriptor.label} field is required.
</#if>Create${entity.entityClassName}Title_${entityDescriptor.id?replace(".","_")}=${entityDescriptor.label}
    </#list>
#
#For Edit ${entity.entityClassName}
Edit${entity.entityClassName}Title=Edit ${entity.entityClassName}

    <#list entity.entityDescriptors as entityDescriptor>
Edit${entity.entityClassName}Label_${entityDescriptor.id?replace(".","_")}=${entityDescriptor.label}:
<#if entityDescriptor.required>Edit${entity.entityClassName}RequiredMessage_${entityDescriptor.id?replace(".","_")}=The ${entityDescriptor.label} field is required.
</#if>Edit${entity.entityClassName}Title_${entityDescriptor.id?replace(".","_")}=${entityDescriptor.label}
    </#list>
#
#For View ${entity.entityClassName}
View${entity.entityClassName}Title=View ${entity.entityClassName}


    <#list entity.entityDescriptors as entityDescriptor>
View${entity.entityClassName}Label_${entityDescriptor.id?replace(".","_")}=${entityDescriptor.label}:
View${entity.entityClassName}Title_${entityDescriptor.id?replace(".","_")}=${entityDescriptor.label}
    </#list>
List${entity.entityClassName}Title=List of ${entity.entityClassName}
List${entity.entityClassName}Empty=(No ${entity.entityClassName} Items Found)




</#list>
