document.addEventListener("DOMContentLoaded", function() {
    function checkInputIsPresent() {
        const editorData = editor.getData();
        document.getElementById("article-body-input").value = editorData;
    }
    setInterval(checkInputIsPresent, 500)

    tagString = document.getElementsByClassName("tags-wrapper")[0].dataset.taglist;
    let regexToGetArray = /(\d+)/ig
    let tagList = tagString.match(regexToGetArray);
    if (tagList !== null) {
        for (let i = 0; i < tagList.length; i++) {
            document.getElementById("tag" + tagList[i]).checked = true;
        }
    }

    specialCbsId = "tag-1";
    document.getElementById(specialCbsId).onclick = disableOtherCheckBoxes;

    function disableOtherCheckBoxes() {
        check(false);
    }

    function check(checked) {
        const cbs = document.querySelectorAll('input[name="tag"]');
        cbs.forEach((cb) => {
            if (cb.id !== specialCbsId) {
                cb.checked = checked;
            }
        });
    }

    function setSpecialChsUnchecked() {
        const cbs = document.querySelectorAll('input[name="tag"]');
        cbs.forEach((cb) => {
            if (cb.id !== specialCbsId) {
                if (cb.checked) {
                    document.getElementById(specialCbsId).checked = false;
                }
            }
        });
    }

    setInterval(setSpecialChsUnchecked, 500)
});
