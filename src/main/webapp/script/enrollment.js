$('input[type=number]').bind('input', function () {
    let totalCost = $('#price').text().replace(/р/, '') * $(this).val();
    if (totalCost !== 0)
        $('#total').text(totalCost + 'р');
    else $('#total').text('');
});

$('#all>div:nth-child(7n):not(:nth-child(-n+7))').on('click', function () {
});

$('#all>div:nth-child(8n):not(:nth-child(-n+8))').on('click', function () {
    let row = Math.trunc($(this).index() / 8);
    let status = $(`input:nth-of-type(${row * 3 + 1})`).val();
    if (status === 'paid' || status === 'approved') {
        if ($(`input[type=checkbox]:nth-of-type(${row})`).prop('checked')) {
            $(`input:nth-of-type(${row * 3 + 1})`).val('approved');
        } else {
            $(`input:nth-of-type(${row * 3 + 1})`).val('paid');
        }
        $('#status').text($(`input:nth-of-type(${row * 3 + 1})`).val());
    }
});