<#-- @ftlvariable name="commentForm" type="ru.itmo.wp.dto.CommentForm" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="icon" type="image/png" href="<@spring.url '/favicon.png'/>" />
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/normalize.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/form.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/datatable.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/article.css'/>">
        <script src="<@spring.url '/js/jquery-3.4.1.min.js'/>"></script>
        <script src="<@spring.url '/js/notify.js'/>"></script>
    </head>
    <body>
    <header>
        <a href="<@spring.url '/'/>"><img src="<@spring.url '/img/logo.png'/>" alt="Codeforces"
                    title="Codeforces" /></a>
        <div class="languages">
            <a href="#"><img src="<@spring.url '/img/gb.png'/>" alt="In English" title="In English" /></a>
            <a href="#"><img src="<@spring.url '/img/ru.png'/>" alt="In Russian" title="In Russian" /></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                ${user.login}
                |
                <a href="<@spring.url '/logout'/>">Logout</a>
            <#else>
                <a href="<@spring.url '/enter'/>">Enter</a>
                |
                <a href="<@spring.url '/register'/>">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <li><a href="<@spring.url '/'/>">Home</a></li>
                <li><a href="<@spring.url '/users/all'/>">Users</a></li>
                <#if user??>
                    <li><a href="<@spring.url '/writePost'/>">Write Post</a></li>
                    <li><a href="<@spring.url '/myPosts'/>">Posts</a></li>
                </#if>
            </ul>
        </nav>
    </header>
    <div class="middle">
        <aside>
            <section>
                <div class="header">
                    Pay attention
                </div>
                <div class="body">
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate ducimus enim facere impedit
                    nobis,
                    nulla placeat quam suscipit unde voluptatibus.
                </div>
                <div class="footer">
                    <a href="#">View all</a>
                </div>
            </section>
        </aside>
        <main>
            <#nested/>
        </main>
    </div>
    <footer>
        <a href="<@spring.url '/'/>">Codehorses</a> &copy; 2099 by Mike Mirzayanov
    </footer>
    </body>
    <script>
        <#--noinspection JSUnresolvedFunction-->
        $(function () {
            <#if message??>
            $.notify("${message?js_string}", {
                className: 'success',
                position: 'bottom right'
            });
            </#if>
        });
    </script>
    </html>
</#macro>

<#macro error field>
    <@spring.bind field/>
    <#if spring.status.errorMessage??>
        <div class="error">${spring.status.errorMessage}</div>
    </#if>
</#macro>

<#macro post post writeCommentForm=false showComments=false>
<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
    <article>
        <div class="title"><a href="/post/${post.id?c}">${post.title}</a></div>
        <div class="information">By ${post.user.login}, ${post.creationTime}</div>
        <div class="body">${post.text}</div>
        <#if post.tags?has_content>
            <div class="post-tags" style="margin: 10px 0">
                Tags: <#list post.tags as tag>${tag.name}<#sep>, </#list>
            </div>
        </#if>
        <ul class="attachment">
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="<@spring.url '/img/voteup.png'/>" title="Vote Up" alt="Vote Up" />
                <span class="positive-score">+173</span>
                <img src="<@spring.url '/img/votedown.png'/>" title="Vote Down" alt="Vote Down" />
            </div>
            <div class="right">
                <img src="<@spring.url '/img/date_16x16.png'/>" title="Publish Time" alt="Publish Time" />
                ${post.creationTime}
                <img src="<@spring.url '/img/comments_16x16.png'/>" title="Comments" alt="Comments" />
                <a href="#">68</a>
            </div>
        </div>
    </article>
    <#if writeCommentForm>
        <div class="form">
            <div class="header">Write Comment</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" />
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text">${commentForm.text!}</textarea>
                        </div>
                        <@error "commentForm.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </div>
    </#if>
    <#if showComments>
        <#list post.comments as comment>
            <div class="comment" style="border: 2px solid var(--border-color); margin-bottom: 20px; padding-left: 10px">
                <div class="comment-user-login">
                    <h3 style="color: cadetblue; font-weight: bold; margin-top: 4px">${comment.user.login}</h3>
                </div>
                <div class="comment-text"
                        style="padding-left: 10px; border-left: 6px solid var(--border-color); margin-bottom: 4px">
                    ${comment.text}
                </div>
                <div class="comment-date">
                    <img src="<@spring.url '/img/date_16x16.png'/>" title="Publish Time" alt="Publish Time" />
                    ${comment.creationTime}
                </div>
            </div>
        </#list>
    </#if>
</#macro>
