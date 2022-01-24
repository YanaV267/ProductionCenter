$('input[name=card_number]').on('keydown', function () {
    $(this).data('val', $(this).val());
});

$('input[name=card_number]').bind('input', function () {
    let length = $(this).val().length;
    if ($(this).data('val').length < length) {
        if (length === 4 || length === 9 || length === 14)
            $(this).val($(this).val() + ' ');
    }
    if ($(this).data('val').length > length) {
        if ($(this).data('val').charAt(length) === ' ') {
            $(this).val($(this).val().substr(0, $(this).val().length - 1));
        }
    }
});