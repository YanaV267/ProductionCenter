$('button').prop('disabled', true);

$('tr').on('click', function () {
    if ($(this).children('th').length !== 0)
        return;
    let hasClass;
    if ($(this).hasClass('color'))
        hasClass = true;
    $('tr').each(function () {
        $(this).removeClass('color');
    });
    if (!hasClass) {
        $(this).toggleClass('color');
        $('button').prop('disabled', false);
    } else {
        $('button').prop('disabled', true);
    }
});