document.addEventListener("DOMContentLoaded", function(event) {

    let buttons = document.getElementsByClassName("reply-button");
    let button, i;
    for (i = 0; i < buttons.length; i++) {
        button = buttons[i];
        button.onclick = function () {
           let id = button.id;
           document.getElementById("comment-edit-wrapper-" + id).innerHTML = "<div class=\"comment-edit-wrapper\"><form action=\"<c:url value=\"/newComment?id=${id}\"/>\" id=\"comment-form\"><textarea class=\"\" id=\"comment-body\" form=\"comment-form\" name=\"commentBody\" placeholder=\"Введите текст комментария\" required></textarea><br><button class=\"btn\" type=\"submit\" name=\"submit\" value=\"create\">Отправить комментарий</button></form></div>";
        };
    }

    document.getElementById("like-icon-request").onclick = function(event) {

    }

    document.getElementById("delete-icon-request").onclick = function () {

    }
});
