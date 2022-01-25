if ($('form').css('height') === $('form').css('min-height')) {
    $('input[type=button]').css({
        'position': 'absolute',
        'left': '300px',
        'top': '375px'
    });
    $('#pages').css({
        'position': 'absolute',
        'left': '300px',
        'top': '315px'
    });
} else {
    $('#buttons').css('position', 'relative');
    $('#pages').css('position', 'relative');
}