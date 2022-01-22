let heightValue = $('form').height() + 30;
$('#rect').css('height', heightValue);
$('form').attr('style', `margin-top: ${heightValue - heightValue * 2 + 5}px !important`);
