$('input[name=card_number]').bind('input', function () {
    let length = $(this).val().length;
    if (length === 4 || length === 9 || length === 14)
        $(this).val($(this).val() + ' ');
});