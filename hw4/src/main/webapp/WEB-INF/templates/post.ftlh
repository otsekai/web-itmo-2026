<#import "./common.ftlh" as c>

<@c.page>
    <#if post_id??>
        <#assign p = c.findById(posts, post_id)!/>
    </#if>

    <#if p?has_content>
        <@c.renderPost p users true/>
    <#else>
        No such post
    </#if>
</@c.page>