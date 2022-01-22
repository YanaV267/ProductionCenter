$(document).ready(function () {
    let heightValue = $('form').height() + 40;
    $('#rect').css('height', heightValue);
    $('form').attr('style', `margin-top: ${heightValue - heightValue * 2 - 40}px !important`);
});