$('#next').on('click', function (event) {
    event.preventDefault();
    $('#subm input').css({
        'margin-left': '55px',
        'width': '180px'
    });
    $('form').css({
        'height': '380px'
    });
    $('#reg').css({
        'transform': 'rotateY(180deg)'
    });
});