<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">NoticeForm</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="content" />
                <div class="field">
                    <div class="name">
                        <label for="content">Content</label>
                    </div>
                    <div class="value">
                        <textarea id="content" name="content">${noticeForm.content!}</textarea>
                    </div>
                    <@c.error "noticeForm.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>
