$('#all>div:nth-child(7n):not(:nth-child(-n+7))').on('click', function () {
    let row = Math.trunc($(this).index() / 7);
    if ($(`input[type=checkbox]:eq(${row})`).prop('checked')) {
        $(`input[name=role]:eq(${row - 1})`).val('teacher');
    } else {
        $(`input[name=role]:eq(${row - 1})`).val('user');
    }
});

if ($('form:last-of-type').css('height') === $('form:last-of-type').css('min-height')) {
    $('form:last-of-type input[type=submit]').css({
        'position': 'absolute',
        'left': '440px',
        'top': '345px'
    });
    $('#pages').css({
        'position': 'absolute',
        'left': '440px',
        'top': '285px'
    });
} else {
    $('form:last-of-type input[type=submit]').css('position', 'relative');
    $('#pages').css('position', 'relative');
}

$('input[name=surname]').change(function () {
    if ($(this).val().length !== 0)
        $('input[name=name]').prop('disabled', false);
    else $('input[name=name]').prop('disabled', true);
});

let heightValue = $('form:last-of-type').height() + $('form:first-of-type').height() + 55;
$('#rect').css('height', heightValue);
$('form:last-of-type').css('margin-top', -50);
$('form:first-of-type').css('margin-top', heightValue - heightValue * 2 - 10);