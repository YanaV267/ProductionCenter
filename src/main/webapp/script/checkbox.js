function checkboxChange() {
    if ($('input[type=checkbox]:checked').length === 3) {
        $('input[type=checkbox]').each(function () {
            if (!this.checked)
                $(this).prop('disabled', true);
        });
    }
    if ($('input[type=checkbox]:checked').length < 3) {
        $('input[type=checkbox]').each(function () {
            if (!this.checked)
                $(this).prop('disabled', false);
        });
    }
}