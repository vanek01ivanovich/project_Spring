$(document).ready(function () {

    $('#en').click(function () {

        $.ajax({
            method : 'GET',
            url : "/admin/alltickets?lang=en",
            success : [
                function () {
                    window.location.reload();
                }
            ]
        })
    });

    $('#ukr').click(function () {

        $.ajax({
            method : 'GET',
            url : "/admin/alltickets?lang=ukr",
            success : [
                function () {
                    window.location.reload();
                }
            ]
        })

    })
});
