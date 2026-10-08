<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = "">

    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#elseif user_id??>
        <#assign u = c.findById(users, user_id)!/>
    </#if>

    <#if u?has_content>
        <#assign idx = -1>
        <#list users as user>
            <#if user.id == u.id>
                <#assign idx = user?index>
            </#if>
        </#list>

        <#assign prev = "">
        <#assign next = "">

        <#if idx gt 0>
            <#assign prev = users[idx - 1]>
        </#if>

        <#if idx lt (users?size - 1)>
            <#assign next = users[idx + 1]>
        </#if>

        <#assign postCount = c.countPosts(posts, u.id)/>

        <div class="user-profile">
            <img class="user-avatar" src="/img/avatar.png" alt="User avatar">

            <h1>
                <@c.userlink u/>
            </h1>

            <ul>
                <li>id: ${u.id}</li>
                <li>handle: ${u.handle}</li>
                <li>name: ${u.name}</li>
                <li>posts: ${postCount}
                    <#if postCount gt 0>
                        - <a href="/posts?user_id=${u.id}">All posts</a>
                    </#if>
                </li>
            </ul>
        </div>

        <div class="user-nav">
            <#if prev?has_content>
                <a class="arrow" href="/user?handle=${prev.handle}">←</a>
            <#else>
                 <span class="arrow disabled">←</span>
            </#if>

            <#if next?has_content>
                <a class="arrow" href="/user?handle=${next.handle}">→</a>
            <#else>
                <span class="arrow disabled">→</span>
            </#if>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
