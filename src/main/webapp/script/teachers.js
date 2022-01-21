$('#all>div:nth-child(7n):not(:nth-child(-n+7))').on('click', function () {
    let row = Math.trunc($(this).index() / 7);
    if ($(`input[type=checkbox]:eq(${row - 1})`).prop('checked')) {
        $(`input[name=role]:eq(${row - 1})`).val('teacher');
    } else {
        $(`input[name=role]:eq(${row - 1})`).val('user');
    }
});

if ($('form').css('height') === $('form').css('min-height')) {
    $('input[type=submit]').css({
        'position': 'absolute',
        'left': '440px',
        'top': '375px'
    });
} else {
    $('input[type=submit]').css('position', 'relative');
}