$(document).ready(function () {

    $("#order-form").submit(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });

});

function fire_ajax_submit() {

    var orderData = {};
    orderData["productname"] = $("#productname").val();
    orderData["productbrand"] = $("#productbrand").val();

    $.map($("#order-form").serializeArray(), function(n, i) {
    	orderData[n['name']] = n['value'];
    });

    console.log(JSON.stringify(orderData));
    $("#btn-order").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "placeOrder",
        data: JSON.stringify(orderData),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-order").prop("disabled", false);

        },
        error: function (e) {

            var json = "<pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-order").prop("disabled", false);

        }
    });
}