$('#nav p:first-child').toggleClass('chosen');
$('#nav p:first-child').on('click', function (event) {
    event.preventDefault();
    $('#personal').css('display', 'none');
    $('#authent').css('display', 'flex');
    $('#nav p:last-child').toggleClass('chosen');
    $('#nav p:first-child').toggleClass('chosen');
});

$('#nav p:last-child').on('click', function (event) {
    event.preventDefault();
    $('#authent').css('display', 'none');
    $('#personal').css('display', 'flex');
    $('#nav p:first-child').toggleClass('chosen');
    $('#nav p:last-child').toggleClass('chosen');
});