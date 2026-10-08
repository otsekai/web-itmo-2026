<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.renderPost post users false/>
    </#list>
</@c.page>
