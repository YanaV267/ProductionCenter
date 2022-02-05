$('input[type=number]').bind('input', function () {
    let totalCost = $('#price').text().replace(/р/, '') * $(this).val();
    if (totalCost !== 0)
        $('#total').text(totalCost + 'р');
    else $('#total').text('');
});

$('#all>div:nth-child(8n):not(:nth-child(-n+8))').on('click', function () {
    let row = Math.trunc($(this).index() / 8);
    let status = $(`input[name=status]:eq(${row - 1})`).val();
    if (status === 'paid' || status === 'approved') {
        if ($(`input[type=checkbox]:eq(${row - 1})`).prop('checked')) {
            $(`input[name=status]:eq(${row - 1})`).val('approved');
        } else {
            $(`input[name=status]:eq(${row - 1})`).val('paid');
        }
        $('#status').text($(`input[name=status]:eq(${row - 1})`).val());
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

let heightValue = $('form:last-of-type').height() + $('form:first-of-type').height() + 55;
$('#rect').css('height', heightValue);
$('form:last-of-type').css('margin-top', -50);
$('form:first-of-type').css('margin-top', heightValue - heightValue * 2 - 10);