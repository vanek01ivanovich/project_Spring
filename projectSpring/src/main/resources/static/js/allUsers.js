$(document).ready(function () {

    $('#en').click(function () {

        $.ajax({
            method : 'GET',
            url : "/admin/allusers?lang=en",
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
            url : "/admin/allusers?lang=ukr",
            success : [
                function () {
                    window.location.reload();
                }
            ]
        })

    })
});

/*
$(document).ready(function () {
    $('#update').click(function () {

        $.ajax({
            method : 'GET',
            url : '/login/allusers/update',
            success : function () {
                    window.location.reload('/login/allusers/update');
                }

        })
    });

    $('#delete').click(function () {

        $.ajax({
            method : 'GET',
            url : '/login/allusers/delete',
            success : function () {
                window.location.replace('/login/allusers/delete');
            }
        })
    });
});
*/



