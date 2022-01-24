$('#next').on('click', function (event) {
    event.preventDefault();
    $('form').css('height', '390px');
    $('#reg').css('transform', 'rotateY(180deg)');
    $('#rect').css('height', '410px');
});

$('#subm input').on('click', function () {
    $('#page1>input').each(function () {
        let pattern = new RegExp($(this).attr('pattern'));
        if (!pattern.test($(this).val())) {
            $('form').css('height', '330px');
            $('#reg').css('transform', 'rotateY(0deg)');
            $('#rect').css('height', '350px');
        }
    });
});

$('#subm img').on('click', function (event) {
    event.preventDefault();
    $('form').css('height', '330px');
    $('#reg').css('transform', 'rotateY(0deg)');
    $('#rect').css('height', '350px');
});

$('input[type=tel]').on('keydown', function () {
    $(this).data('val', $(this).val());
});

$('input[type=tel]').bind('input', function () {
    if ($(this).data('val').length < $(this).val().length) {
        if ($(this).val().length === 4)
            $(this).val($(this).val() + '(');
        if ($(this).val().length === 7)
            $(this).val($(this).val() + ')');
        if ($(this).val().length === 11)
            $(this).val($(this).val() + '-');
        if ($(this).val().length === 14)
            $(this).val($(this).val() + '-');
    }
    if ($(this).data('val').length > $(this).val().length) {
        let symbol = $(this).data('val').charAt($(this).val().length);
        if (symbol === '(' || symbol === ')' || symbol === '-') {
            $(this).val($(this).val().substr(0, $(this).val().length - 1));
        }
    }
});

$("input[type=tel]").bind("keydown click focus", function () {
    if ($(this).val().length === 0)
        $(this).val('+');
});