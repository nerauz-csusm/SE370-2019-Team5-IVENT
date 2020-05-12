export function formArrayToObject(form) {
    let formArray = form.serializeArray();
    let obj = {};


    formArray.forEach((elem) => {
        obj[elem.name] = elem.value;
    });

    return obj;
}

export function makeAlert(parentId, className, text, handler = () => {}, timeout = 3000) {
    $(`#${parentId}`).after(`<div class="alert ${className}" role="alert">${text}</div>`);
    $('.alert').alert();
    setTimeout(() => {
        handler();
        $('.alert').remove();
    }, timeout);
}