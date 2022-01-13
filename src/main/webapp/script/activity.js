$('select').change(function () {
    if ($('option:selected').val() !== 'Выберите направление --') {
        $('form>input:first-of-type').val('');
        $('form>input:first-of-type').prop('disabled', true);
    }
});

$('input[type=button]').click(function () {
    $('input[type=text]').val('');
    $('form>input:first-of-type').prop('disabled', false);
    $('select').prop('selectedIndex', '0');
});
