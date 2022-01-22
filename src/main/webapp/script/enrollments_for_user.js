$('#all>div:not(:nth-child(-n+7))').on('click', function () {
    let row = Math.trunc($(this).index() / 7);
    let hasClass;
    $('#all>div').each(function () {
        if (Math.trunc($(this).index() / 7) !== row)
            $(this).removeClass('color');
    });
    for (let i = 1; i < 8; i++) {
        if ($(`#all > div:nth-child(${7 * row + i})`).hasClass('color'))
            hasClass = true;
        $(`#all > div:nth-child(${7 * row + i})`).removeClass('color');
    }
    if (!hasClass) {
        let counter = 1;
        $('input[name=enrollment_id]').each(function () {
            if (counter === row) {
                let id = $(this).val();
                $('input[name=chosen_enrollment_id]').val(id);
            }
            counter++;
        });
        for (let i = 1; i < 8; i++) {
            $(`#all > div:nth-child(${7 * row + i})`).toggleClass('color');
        }
        $('input[type=button]').prop('disabled', false);
    } else {
        $('input[type=button]').prop('disabled', true);
    }
});

$('input[type=number]').on('focusin', function () {
    if ($(this).data('val') === undefined)
        $(this).data('val', $(this).val());
});

$('input[type=number]').on('click', function () {
    $('input[type=submit]').prop('disabled', false);
    let clicked = this;
    $('input[type=number]').each(function (index) {
        if (this === clicked) {
            if ($(`#all > div:nth-child(${7 * (index + 1) + 5})`).addClass('color'))
                if ($(this).data('price') === undefined)
                    $(this).data('price', $(`#all > div:nth-child(${7 * (index + 1) + 5})`).text() / $(this).data('val'));
            $(`#all > div:nth-child(${7 * (index + 1) + 5})`).text($(this).data('price') * $(clicked).val());
        }
    });
});

if ($('form').css('height') === $('form').css('min-height')) {
    $('#buttons').css({
        'position': 'absolute',
        'left': '315px',
        'top': '375px'
    });
} else {
    $('#buttons').css('position', 'relative');
}