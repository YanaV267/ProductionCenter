if ($('form').css('height') === $('form').css('min-height')) {
    $('input[type=button]').css({
        'position': 'absolute',
        'left': '300px',
        'top': '410px'
    });
    $('#pages').css({
        'position': 'absolute',
        'left': '300px',
        'top': '345px'
    });
} else {
    $('#buttons').css('position', 'relative');
    $('#pages').css('position', 'relative');
}