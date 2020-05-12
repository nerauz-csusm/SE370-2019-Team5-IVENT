import {formArrayToObject, makeAlert} from "/js/form.js";

$(() => {
    $('#register-form').submit(function(e) {
        e.preventDefault();

        const formData = formArrayToObject($(this));

        axios.post('/user', formData)
            .then(function (response) {
                makeAlert('register-button', 'alert-success', response.data, () => {window.location.href = "/login"});
            })
            .catch(function (error) {
                if (error.response.status === 400)
                    makeAlert('register-button', 'alert-danger', error.response.data);
                else
                    console.log({error });
            });
    });
})