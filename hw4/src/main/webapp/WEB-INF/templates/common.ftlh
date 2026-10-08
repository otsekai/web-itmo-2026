<#-- @ftlvariable name="nameOnly" type="boolean" -->
<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="stylesheet" href="/css/hw_added.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/users", "name": "Users"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"}
            ]/>
            <ul>
                <#list items as item>
                    <@menuItem item/>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as post>
            <#if post?index lt 3>
                <section>
                    <div class="header">Post #${post.id}</div>
                    <div class="body">
                        ${truncText(post.text)}
                    </div>
                    <div class="footer">
                        <a href="/post?post_id=${post.id}">View all</a>
                    </div>
                </section>
            </#if>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly=false>
    <a href="/user?handle=${user.handle}" class="userlink">
        <#if nameOnly>
            ${user.name}
        <#else>
            <span class="userlink-handle userlink-handle-${user.color?string?lower_case}">
                ${user.handle}
            </span>
            <span class="userlink-name">
                ${user.name}
            </span>
        </#if>
    </a>
</#macro>

<#macro menuItem item>
    <li>
        <a href="${item.href}"
            <#if current_url?? && current_url == item.href>class="active"</#if>
        >${item.name}</a>
    </li>
</#macro>

<#macro renderPost post users full=false>
    <#assign author = findById(users, post.userId)!/>
    <article>
        <div class="title">
            <a href="/post?post_id=${post.id}">
                ${post.title}
            </a>
        </div>
        <div class="information">
            By <#if author?has_content><@userlink author/><#else>unknown</#if>
        </div>
        <div class="body">
            <p>
                <#if full>
                    ${post.text}
                <#else>
                    ${truncText(post.text)}
                </#if>
            </p>
        </div>
    </article>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findById items id>
    <#list items as item>
        <#if item.id == id>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function truncText text>
    <#if (text?length) gt 350>
        <#return text?substring(0, 350) + "..."/>
    <#else>
        <#return text/>
    </#if>
</#function>

<#function countPosts posts userId>
    <#local count = 0>
    <#list posts as p>
        <#if p.userId == userId>
            <#local count = count + 1>
        </#if>
    </#list>
    <#return count/>
</#function>

<#function filterPostsByUserId posts userId>
    <#local result = []>
    <#list posts as p>
        <#if p.userId == userId>
            <#local result = result + [p]>
        </#if>
    </#list>
    <#return result/>
</#function>