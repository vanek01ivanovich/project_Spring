$(document).ready(function () {


    $('#en').click(function () {

        $.ajax({
            method : 'GET',
            url : '/login?lang=en',
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
            url :'/login?lang=ukr',
            success : [
                function () {
                    window.location.reload();


                }
            ]
        })

    })
});