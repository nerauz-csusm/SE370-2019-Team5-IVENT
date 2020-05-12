import {formArrayToObject, makeAlert} from "/js/form.js";

$(() => {
    $('#login-form').submit(function(e) {
        e.preventDefault();

        const formData = formArrayToObject($(this));

        axios.post('/user/auth', formData)
            .then(function (response) {
                makeAlert('sign-button', 'alert-success', response.data, () => {
                    localStorage.setItem('logged', "true");
                    window.location.href = "/"
                });
            })
            .catch(function (error) {
                if (error.response.status === 400)
                    makeAlert('sign-button', 'alert-danger', error.response.data);
                else
                    console.log({error });
            });
    });
});