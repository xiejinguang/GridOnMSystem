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
PersistenceErrorOccured =  发生一个持久化错误。
Search =  查 找
SearchAll  =  查找所有
GetAll  =  全 部
SearchConsTitle  =  查找条件
Add  =  添加
AddNode  =  添加节点
AddSubNode  =  添加子节点
Create =  创 建
View =  查 看
Edit =  编 辑
Delete =  删 除
Close =  关 闭
Cancel =  取 消
Save =  保 存
SelectOneMessage =  选择...
Home =  首 页
Maintenance =  维 护
AppName = ${projectName}
AppNameLabel  =  ${projectName}
ModuleName  =  Module
ModuleNameLabel  =  Module

<#list entities as entity>
#
#
#For ${entity.entityClassName}

${entity.entityClassName}Created  =  ${entity.entityClassName},已成功创建！
${entity.entityClassName}Updated  =  ${entity.entityClassName},已成功更新！
${entity.entityClassName}Deleted  =  ${entity.entityClassName},已成功删除！
${entity.entityClassName}EntityLabel  =  ${entity.entityClassName}：
${entity.entityClassName}EntityTitle  =  ${entity.entityClassName}

#For Menu
${entity.entityClassName}MenuLabel  =  ${entity.entityClassName}


#For Link
${entity.entityClassName}EditLink  =  编 辑
${entity.entityClassName}ViewLink  =  查 看
${entity.entityClassName}CreateLink  =  创建新${entity.entityClassName}
${entity.entityClassName}IndexLink  =  首 页
${entity.entityClassName}DestroyLink  =  销 毁
${entity.entityClassName}SaveLink  =  保 存
${entity.entityClassName}ShowAllLink  =  显示全部${entity.entityClassName}

#For ${entity.entityClassName} common 
    <#list entity.entityDescriptors as entityDescriptor>
${entity.entityClassName}Title_${entityDescriptor.id?replace(".","_")} = ${entityDescriptor.label}
${entity.entityClassName}Label_${entityDescriptor.id?replace(".","_")} = ${entityDescriptor.label}:
    </#list>


#For List ${entity.entityClassName}

List${entity.entityClassName}Title =  ${entity.entityClassName}列表
List${entity.entityClassName}Empty = (No ${entity.entityClassName} Items Found)

#For View ${entity.entityClassName}
View${entity.entityClassName}Title = 查看 ${entity.entityClassName}


#
#For Create ${entity.entityClassName}

Create${entity.entityClassName}Title = 创建 新${entity.entityClassName}

#For Edit ${entity.entityClassName}
Edit${entity.entityClassName}Title = 编辑 ${entity.entityClassName}

    <#list entity.entityDescriptors as entityDescriptor>
Edit${entity.entityClassName}Label_${entityDescriptor.id?replace(".","_")} = ${entityDescriptor.label}:
<#if entityDescriptor.required>Edit${entity.entityClassName}RequiredMessage_${entityDescriptor.id?replace(".","_")} = 字段“${entityDescriptor.label} ”不能为空！
</#if>Edit${entity.entityClassName}Title_${entityDescriptor.id?replace(".","_")} = ${entityDescriptor.label}
    </#list>
#


</#list>
