document.addEventListener("DOMContentLoaded", function(event) {
    function removeAlert() {
        return function () {
            if (this.classList.contains('invalid-data')) {
                this.classList.remove('invalid-data');
                document.getElementById("error-" + this.id).remove();
            }
        }
    }

    checkNameInput = function(prevId) {
        return function() {
            if (this.value.length < 5) {
                this.classList.add('invalid-data');
                createErrorDiv(prevId, 'Имя не должно быть пустым и должно содержать минимум 5 символов.', "error-" + prevId)
            }
        }
    }

    checkEmailInput = function(prevId) {
        return function () {
            regexp = /[-.\w]+@([\w-]+\.)+[\w-]+/iu;
            if (this.value.match(regexp) == null) {
                this.classList.add('invalid-data');
                createErrorDiv(prevId, 'Введите правильный email.', "error-" + prevId)
            }
        }
    }

    checkLoginInput = function(prevId) {
        return function () {
            if (this.value.length < 5) {
                this.classList.add('invalid-data');
                createErrorDiv(prevId, 'Логин не должен быть пустым и должно содержать минимум 5 символов.', "error-" + prevId)
            }
        }
    }

    checkPasswordInput = function(prevId) {
        return function () {
            if (this.value.length < 5) {
                this.classList.add('invalid-data');
                createErrorDiv(prevId, 'Пароль должен содержать как минимум 5 символов.', "error-" + prevId)
            }
        }
    }

    checkRepeatedPasswordInput = function(prevId) {
        return function () {
            pass = document.getElementById("passwordInput").value;
            repeatedPass = document.getElementById("repeatedPasswordInput").value;

            if ((pass.length != repeatedPass.length) || (pass != repeatedPass)) {
                this.classList.add('invalid-data');
                createErrorDiv(prevId, 'Пароли не совпадают.', "error-" + prevId)
            }
        }
    }

    function createErrorDiv(idOfPreviousElement, messageToDisplay, id) {
        if (document.getElementById(id) != null) {
            previous = document.getElementById(idOfPreviousElement);
            console.log("field's sibling: " + previous.nextSibling);
            console.log("field's parent: " + previous.parentNode);
            previous.nextSibling.remove();
            return;
        }
        previous = document.getElementById(idOfPreviousElement);
        errorDiv = document.createElement("div");
        errorDiv.classList.add("message-wrapper");
        errorDiv.id = id;
        errorDiv.innerHTML = "<h6 class=\"mx-3\">" + messageToDisplay + "</h6>";
        // https://stackoverflow.com/questions/23401641/uncaught-notfounderror-failed-to-execute-insertbefore-on-node-the-node-bef
        previous.parentNode.insertBefore(errorDiv, previous.nextSibling)
    }

    document.getElementById("nameInput").onblur = checkNameInput("nameInput");
    document.getElementById("emailInput").onblur = checkEmailInput("emailInput");
    document.getElementById("loginInput").onblur = checkLoginInput("loginInput");
    document.getElementById("passwordInput").onblur = checkPasswordInput("passwordInput");
    document.getElementById("repeatedPasswordInput").onblur = checkRepeatedPasswordInput("repeatedPasswordInput");

    for (const elem of document.getElementsByClassName("input-field")) {
        elem.onfocus = removeAlert(elem.id);
    }
});
