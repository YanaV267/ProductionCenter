$('#all>div:nth-child(7n):not(:nth-child(-n+7))').on('click', function () {
    let row = Math.trunc($(this).index() / 7);
    if ($(`input[type=checkbox]:eq(${row - 1})`).prop('checked')) {
        $(`input[name=status]:eq(${row - 1})`).val('blocked');
    } else {
        $(`input[name=status]:eq(${row - 1})`).val('active');
    }
});

if ($('form').css('height') === $('form').css('min-height')) {
    $('input[type=button]').css({
        'position': 'absolute',
        'left': '440px',
        'top': '375px'
    });
    $('#pages').css({
        'position': 'absolute',
        'left': '440px',
        'top': '315px'
    });
} else {
    $('input[type=submit]').css('position', 'relative');
    $('#pages').css('position', 'relative');
}