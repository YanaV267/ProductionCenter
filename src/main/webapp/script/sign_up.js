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