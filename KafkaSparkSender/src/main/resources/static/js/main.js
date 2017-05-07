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
            $('#feedback').html("<div class='alert alert-success alert-dismissable'>" +
            		"<a href='#' class='close' data-dismiss='alert' aria-label='close'>x</a>" +
            		"<strong>Success!</strong>Order has been submitted successfully" +
            		"</div>");
            $("#btn-order").prop("disabled", false);
        },
        error: function (e) {
            $('#feedback').html("<div class='alert alert-success alert-dismissable'>" +
            		"<a href='#' class='close' data-dismiss='alert' aria-label='close'>x</a>" +
            		"<strong>Failure!</strong>There was some error while submitting the Order. Please try again at later time" +
            		"</div>");
            $("#btn-order").prop("disabled", false);
        }
    });
}