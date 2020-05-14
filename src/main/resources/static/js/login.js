import {formArrayToObject, makeAlert} from "/js/form.js";

$(() => {
    $('#login-form').submit(function(e) {
        e.preventDefault();

        const formData = formArrayToObject($(this));

        axios.post('/user/auth', formData)
            .then(function (response) {
                makeAlert('sign-button', 'alert-success', response.data.message, () => {
                    localStorage.setItem("id", response.data.data.id);
                    window.location.href = "/"
                });
            })
            .catch(function (error) {
                if (error.response.status === 400)
                    makeAlert('sign-button', 'alert-danger', error.response.data.message);
                else
                    console.log({ error });
            });
    });
});