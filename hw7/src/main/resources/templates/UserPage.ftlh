<#import "commons.ftlh" as c>

<@c.page>
    <h1>User Profile</h1>
    <#if profileUser??>
        <ul>
            <li>id: ${profileUser.id}</li>
            <li>login: ${profileUser.login}</li>
            <li>creationTime: ${profileUser.creationTime}</li>
        </ul>
    <#else>
        No such user
    </#if>
</@c.page>
