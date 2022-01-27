let heightValue = $('form').height() + 50;
$('#rect').css('height', heightValue);
$('form').attr('style', `margin-top: ${heightValue - heightValue * 2 + 5}px !important`);

$('input[type=checkbox]').change(function () {
    checkboxChange();
    let changed = this;
    $('input[type=checkbox]').each(function () {
        if (this === changed) {
            let value = $(this).val();
            let isSet = false;
            $('.time').each(function () {
                if (value === $(this).children('p').text().substring(0, $(this).children('p').text().length - 1)) {
                    if ($(changed).prop('checked')) {
                        $(this).css('display', 'flex');
                    } else {
                        $(this).css('display', 'none');
                    }
                    isSet = true;
                }
            });
            if (!isSet) {
                $('.time').each(function () {
                    if ($(this).children('p').text().length === 0) {
                        $(this).children('p').text(value + ":");
                        $(this).children('input[type=hidden]').val(value);
                        $(this).css('display', 'flex');
                        isSet = true;
                        return false;
                    }
                });
            }
            if (!isSet) {
                $('.time:last-of-type').children('p').text(value + ":");
                $('.time:last-of-type').children('input[type=hidden]').val(value);
                $('.time:last-of-type').css('display', 'flex');
            }
        }
    });
});

$('input[type=submit]').click(function (){
    $('.time').each(function () {
        if ($(this).css('display') === 'none') {
            $(this).children('p').text('');
            $(this).children('input[type=hidden]').val('');
            $(this).find('input[type=time]').val('');
            $(this).find('input[type=number]').val('');
        }
    });
});