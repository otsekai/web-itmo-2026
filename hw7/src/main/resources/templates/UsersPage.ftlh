<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if user?? && user.admin && !user.disabled>
                    <th>Actions</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as rowUser>
                    <tr>
                        <td class="id"><a href="/user/${rowUser.id?c}">${rowUser.id}</a></td>
                        <td class="login">${rowUser.login}</td>
                        <td class="creationTime">${rowUser.creationTime}</td>
                        <#if user?? && user.admin && !user.disabled>
                            <td class="actions">
                                <button type="button"
                                        data-id="${rowUser.id?c}"
                                        data-disabled="${(!rowUser.disabled)?c}">
                                    <#if rowUser.disabled>Enable<#else>Disable</#if>
                                </button>
                            </td>
                        </#if>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
        <form id="user-status-form" method="post"
                action="<@spring.url '/users/disabled'/>">
            <input type="hidden" name="id">
            <input type="hidden" name="disabled">
        </form>
    </div>
    <script>
        const form = document.getElementById('user-status-form');

        document.querySelectorAll('button[data-id][data-disabled]')
            .forEach(button => {
                button.addEventListener('click', () => {
                    form.elements.namedItem('id').value = button.dataset.id;
                    form.elements.namedItem('disabled').value = button.dataset.disabled;
                    form.requestSubmit();
                });
            });
    </script>
</@c.page>
