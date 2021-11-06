document.addEventListener("DOMContentLoaded", function(event) {
    let buttons = document.getElementsByClassName("reply-button");
    let button, i;
    for (i = 0; i < buttons.length; i++) {
        button = buttons[i];
        button.onclick = insertCommentEditField;
    }

    // function checkUserAuthorized() {
    //     console.log(${pageContext.request.authorized});
    // }

    function insertCommentEditField() {
        // checkUserAuthorized()
        id = this.id;
        console.log("ID: " + id);
        document.getElementById("comment-edit-wrapper-" + id).innerHTML = '<div class="comment-edit-wrapper"><form id="comment-form" action="<c:url value="/newComment?id=${id}"/>"><textarea id="comment-body" form="comment-form" name="commentBody" placeholder="Введите текст комментария"></textarea><br><button class="btn" type="submit" name="submit" value="create">Отправить комментарий</button></form></div>';
    }

    document.getElementById("like-icon-request").onclick = async function (event) {
        likeIcon = document.getElementById("like-icon-request");
        id = document.getElementById("article-id").value
        url = "${pageContext.request.contextPath}" + "/like?id=" + id;
        console.log(url);
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
        let isConfirmed = confirm("Вы действительно желаете удалить статью? Это действие нельзя отменить.");
        console.log(isConfirmed);
        if (isConfirmed) {
            id = document.getElementById("article-id").value
            url = "${pageContext.request.contextPath}" + "/deleteArticle?id=" + id;
            console.log(url);
            let response = await fetch(url);
        }
    }
});
