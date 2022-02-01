$('#upload_file_input').change(function () {
    if ($(this).val())
        $('input[type=submit]').prop('disabled', false);
});
if ($('input[type=button]').length === 1) {
    $('input[type=button]').css('margin-left', '200px');
}