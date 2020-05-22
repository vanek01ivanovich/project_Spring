$(document).ready(function () {

    $('#en').click(function () {

        $.ajax({
            method : 'GET',
            url : "/users/getroute?lang=en",
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
            url : "/users/getroute?lang=ukr",
            success : [
                function () {
                    window.location.reload();
                }
            ]
        })

    })
});
