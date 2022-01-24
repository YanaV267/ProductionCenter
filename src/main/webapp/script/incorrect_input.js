$('input:not([type=submit])').each(function () {
    let borderColor;
    let inputText = $(this).val();
    if (inputText.includes('incorrect')) {
        borderColor = 'red';
        $(this).val(inputText.replace(/incorrect/g, ""));
    } else borderColor = 'rgb(95, 108, 167)';
    $(this).css('border-color', borderColor);
});

$('input').mouseup(function () {
    $(this).css('border-color', 'rgb(95, 108, 167)');
});