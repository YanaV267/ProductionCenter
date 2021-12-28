$('input[type=submit]').prop('disabled', true);

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
        $('input[type=submit]').prop('disabled', false);
    } else {
        $('input[type=submit]').prop('disabled', true);
    }
});

let heightValue = $('form').height() + 40;
$('#rect').css('height', heightValue - 15);
$('form').css('margin-top', heightValue - heightValue * 2);
