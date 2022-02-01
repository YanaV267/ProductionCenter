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

$('select[name=category]').change(function () {
    let activities = $('input[name=activities]').val().replace("}]", "").split("},");
    $('select[name=type]').children('option').each(function () {
        if (!$(this).is(':disabled'))
            $(this).remove();
    });
    for (let activity of activities) {
        if (activity.includes(`category='${$(this).val()}'`)) {
            let type = activity.substring(activity.indexOf("type='") + 6, activity.length - 1);
            $('select[name=type]').append(`<option>${type}</option>`);
        }
    }
})