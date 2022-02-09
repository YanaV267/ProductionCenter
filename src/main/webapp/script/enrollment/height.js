let heightValue = $('form:last-of-type').height() + $('form:first-of-type').height() + 55;
$('#rect').css('height', heightValue);
$('form:last-of-type').css('margin-top', -50);
$('form:first-of-type').css('margin-top', heightValue - heightValue * 2 - 10);