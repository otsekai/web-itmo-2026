<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = "">

    <#if user_id??>
        <#assign u = c.findById(users, user_id)!/>
    </#if>

    <#if u?has_content>
        <#assign userPosts = c.filterPostsByUserId(posts, user_id)/>

        <h1>Posts by <@c.userlink u/></h1>

        <#if userPosts?size gt 0>
            <#list userPosts?reverse as p>
                <@c.renderPost p users/>
            </#list>
        <#else>
            No posts
        </#if>
    <#else>
        No such user
    </#if>
</@c.page>