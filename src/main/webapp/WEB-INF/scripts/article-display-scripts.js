document.addEventListener("DOMContentLoaded", function(event) {
    let buttons = document.getElementsByClassName("reply-button");
    let button, i;
    for (i = 0; i < buttons.length; i++) {
        button = buttons[i];
        button.onclick = insertCommentEditField;
    }

    function insertCommentEditField() {
        prevId = this.id;
        articleId = this.dataset.article
        console.log("ID: " + prevId);
        document.getElementById("comment-edit-wrapper-" + prevId).innerHTML = '<div class="comment-edit-wrapper"><form id="comment-form" action="${pageContext.request.contextPath}/newComment?id=${prevId}"><textarea id="comment-body" form="comment-form" name="commentBody" placeholder="Введите текст комментария"></textarea><br><input type="hidden" name="articleId" value="${articleId}"><input type="hidden" name="parentCommentId" value="${prevId}"><button class="btn" type="submit" name="submit" value="create">Отправить комментарий</button></form></div>';
    }

    document.getElementById("like-icon-request").onclick = async function () {
        likeIcon = document.getElementById("like-icon-request");
        id = document.getElementById("article-id").value
        url = "${pageContext.request.contextPath}" + "/like?id=" + id;
        let response = await fetch(url);

        if (response.ok) {
            if (likeIcon.classList.contains("liked")) {
                likeIcon.src = '<c:url value="/resources/icons/like.png"/>';
                likeIcon.classList.remove("liked");
            } else {
                likeIcon.src = '<c:url value="/resources/icons/like-active.png"/>';
                likeIcon.classList.add("liked");
            }
        } else {
            alert("Ошибка при отправлении запроса на сохранение статьи" + response.status);
        }
    }

    document.getElementById("delete-icon-request").onclick = async function () {
        let isConfirmed = confirm(" Do you really want to delete article? This cannot be undone.");
        if (isConfirmed) {
            id = this.dataset.id;
            console.log(id);
            url = "${pageContext.request.contextPath}" + "/deleteArticle?id=" + id;
            console.log(url);
            let response = await fetch(url);
        }
    }
});
