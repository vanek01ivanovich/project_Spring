$(document).ready(function () {
    var currentUrl = window.location.href;
    console.log(currentUrl)
    $('#next').click(function () {

        //var ob = $('#page').attr('th:href');
        console.log(currentUrl)
        //var urlLang = "?lang=ukr";

       /* $.ajax({
            method : 'GET',
            url : currentUrl+urlLang,
            success : [
                function () {
                    window.location.reload();


                }
            ]
        })*/

    })
});