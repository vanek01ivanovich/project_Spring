$(document).ready(function () {
    var currentUrl = window.location.href;
    var lastSymbol = currentUrl.slice(-1);

    console.log(currentUrl);
    $('#en').click(function () {
        var urlLang = "?lang=en";
        if(lastSymbol === "?"){
            urlLang = "lang=en"
        }
        $.ajax({
            method : 'GET',
            url : currentUrl + urlLang,
            success : [
                function () {
                    window.location.reload();

                }
            ]
        })
    });

    $('#ukr').click(function () {
        var urlLang = "?lang=ukr";
        if(lastSymbol === "?"){
            urlLang = "lang=ukr"
        }
        $.ajax({
            method : 'GET',
            url : currentUrl+urlLang,
            success : [
                function () {
                    window.location.reload();


                }
            ]
        })

    })
});