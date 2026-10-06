<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = c.findBy(users, "handle", handle)!/>

    <#if u?has_content>
        ${u.handle} / ${u.name} (${u.id})
    <#else>
        No such user
    </#if>
</@c.page>
