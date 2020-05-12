export function toggleNavBtn() {
    if (localStorage.getItem('logged') === 'true') {
        $('#nav-sign').text('Sign out')
            .removeClass('btn-outline-primary')
            .addClass('btn-outline-danger')
            .click(() => {
            localStorage.setItem('logged', "false");
        })
    }
}