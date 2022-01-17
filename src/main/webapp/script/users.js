$('#all>div:nth-child(7n):not(:nth-child(-n+7))').on('click', function () {
    let row = Math.trunc($(this).index() / 7);
    if ($(`input[type=checkbox]:nth-of-type(${row})`).prop('checked')) {
        alert('hi');
        $(`input:nth-of-type(${row * 3 + 1})`).val('blocked');
    } else {
        $(`input:nth-of-type(${row * 3 + 1})`).val('active');
    }
});

if ($('form').css('height') === $('form').css('min-height')) {
    $('input[type=submit]').css({
        'position': 'absolute',
        'left': '440px',
        'top': '375px'
    });
} else {
    $('input[type=submit]').css({
        'position': 'relative'
    });
}