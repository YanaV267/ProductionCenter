$('#upload_file_input').change(function () {
    if ($(this).val())
        $('input[type=submit]').prop('disabled', false);
});