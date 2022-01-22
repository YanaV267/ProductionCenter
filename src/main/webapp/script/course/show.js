$('#all>div:not(:nth-child(-n+5))').on('click', function () {
    let row = Math.trunc($(this).index() / 5);
    let hasClass;
    $('#all>div').each(function () {
        if (Math.trunc($(this).index() / 5) !== row)
            $(this).removeClass('color');
    });
    for (let i = 1; i < 6; i++) {
        if ($(`#all > div:nth-child(${5 * row + i})`).hasClass('color'))
            hasClass = true;
        $(`#all > div:nth-child(${5 * row + i})`).removeClass('color');
    }
    if (!hasClass) {
        let counter = 1;
        $('input[name=course_id]').each(function () {
            if (counter === row) {
                let id = $(this).val();
                $('input[name=chosen_course_id]').val(id);
            }
            counter++;
        });
        for (let i = 1; i < 6; i++) {
            $(`#all > div:nth-child(${5 * row + i})`).toggleClass('color');
        }
        $('input:nth-of-type(3)').prop('disabled', false);
        $('input[type=button]:last-of-type').prop('disabled', false);
    } else {
        $('input:nth-of-type(3)').prop('disabled', true);
        $('input[type=button]:last-of-type').prop('disabled', true);
    }
});

$('input[type=checkbox]').change(function () {
    if ($('input[type=checkbox]:checked').length === 3) {
        $('input[type=checkbox]').each(function () {
            if (!this.checked)
                $(this).prop('disabled', true);
        });
    }
    if ($('input[type=checkbox]:checked').length < 3) {
        $('input[type=checkbox]').each(function () {
            if (!this.checked)
                $(this).prop('disabled', false);
        });
    }
});

let heightValue = $('form').height() + 40;
$('#rect').css('height', heightValue - 15);
$('form').css('margin-top', heightValue - heightValue * 2);