export function toggleNavBtn() {
    if (localStorage.getItem('id')) {
        $('#nav-sign').text('Sign out')
            .removeClass('btn-outline-primary')
            .addClass('btn-outline-danger')
            .click(() => {
            localStorage.removeItem('id');
        })
    }
}