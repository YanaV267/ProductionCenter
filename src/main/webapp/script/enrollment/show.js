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


if ($('form').css('height') === $('form').css('min-height')) {
    $('input[type=submit]').css({
        'position': 'absolute',
        'left': '440px',
        'top': '395px'
    });
    $('#pages').css({
        'position': 'absolute',
        'left': '440px',
        'top': '335px'
    });
} else {
    $('input[type=submit]').css('position', 'relative');
    $('#pages').css('position', 'relative');
}