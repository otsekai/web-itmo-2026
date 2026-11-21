<#import "commons.ftlh" as c>

<@c.page>
    <h1>Your posts:</h1>
<#list user.posts as post>
    <@c.post post=post/>
</#list>
</@c.page>
