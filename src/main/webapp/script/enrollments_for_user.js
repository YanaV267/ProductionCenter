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
        $('input[type=submit]').prop('disabled', false);
    } else {
        $('input[type=button]').prop('disabled', true);
        $('input[type=submit]').prop('disabled', true);
    }
});

if ($('form').css('height') === $('form').css('min-height')) {
    $('#buttons').css({
        'position': 'absolute',
        'left': '375px',
        'top': '375px'
    });
} else {
    $('#buttons').css('position', 'relative');
}