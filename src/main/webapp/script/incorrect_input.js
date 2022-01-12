$('input:not([type=submit])').each(function () {
    let borderColor;
    if ($(this).val() === 'incorrect') {
        borderColor = 'red';
        $(this).val('');
    } else borderColor = 'rgb(95, 108, 167)';
    $(this).css('border-color', borderColor);
});

$('input').mouseup(function () {
    $(this).css('border-color', 'rgb(95, 108, 167)');
});