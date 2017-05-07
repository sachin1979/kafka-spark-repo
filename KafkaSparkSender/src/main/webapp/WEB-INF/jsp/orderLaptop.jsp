<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>IBM Insight</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<script>
		document.write('<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css" />');
	</script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">IBM Insight Kafka Sender</a>
			</div>
		</div>
	</nav>

	<div class="container">

		<div class="starter-template">
			<h2>IBMInsite Product Order Form</h2><br>

			<div id="feedback"></div><br>

			<form class="form-horizontal" id="order-form" action="#">
				<div class="form-group">
					<label class="col-sm-2 control-label">Customer Full Name</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="customerfullname" name="customerfullname" maxlength="60" required="required"/>
					</div>
					<div class='clearfix'></div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Customer Zip Code</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="customerzip" name="customerzip" maxlength="5" onkeypress='return event.charCode >= 48 && event.charCode <= 57' required="required"/>
					</div>
					<div class='clearfix'></div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">Product Name</label>
					<div class="col-sm-4">
						<select class="form-control" id="productname" name="productname">
							<option value="laptop">Laptop</option>
							<option value="smartphone">Smartphone</option>
							<option value="tablet">Tablet</option>
						</select>
					</div>
					<div class='clearfix'></div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Product Brand</label>
					<div class="col-sm-4">
						<select class="form-control" id="productbrand" name="productbrand">
							<option value="samsung">Samsung</option>
							<option value="apple">Apple</option>
							<option value="sony">Sony</option>
							<option value="dell">Dell</option>
							<option value="asus">Asus</option>
							<option value="hp">HP</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-2">
						<button type="submit" id="btn-order"
							class="btn btn-primary btn-lg btn-block">Place Order</button>
					</div>
				</div>
			</form>

		</div>

	</div>

	<!-- 	<div class="container">
		<footer>
			<p>
				© <a href="http://www.ibm.com">IBM Adopt Team</a> 2017
			</p>
		</footer>
	</div>

 -->
	<script>
		document.write('<script src="${contextPath}/js/main.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
	</script>

</body>
</html>