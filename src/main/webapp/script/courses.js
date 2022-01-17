$('#all>div:not(:nth-child(-n+4))').on('click', function () {
    let row = Math.trunc($(this).index() / 4);
    let hasClass;
    $('#all>div').each(function () {
        if (Math.trunc($(this).index() / 4) !== row)
            $(this).removeClass('color');
    });
    for (let i = 1; i < 5; i++) {
        if ($(`#all > div:nth-child(${4 * row + i})`).hasClass('color'))
            hasClass = true;
        $(`#all > div:nth-child(${4 * row + i})`).removeClass('color');
    }
    if (!hasClass) {
        for (let i = 1; i < 5; i++) {
            $(`#all > div:nth-child(${4 * row + i})`).toggleClass('color');
            if (i !== 4) {
                let property = $(`#all > div:nth-child(${4 * row + i})`).text();
                $(`input:nth-of-type(${i + 3})`).val(property);
            }
        }
        $('input:nth-of-type(2)').prop('disabled', false);
        $('input[type=button]:last-of-type').prop('disabled', false);
    } else {
        $('input:nth-of-type(2)').prop('disabled', true);
        $('input[type=button]:last-of-type').prop('disabled', true);
    }
});

let heightValue = $('form').height() + 40;
$('#rect').css('height', heightValue - 15);
$('form').css('margin-top', heightValue - heightValue * 2);