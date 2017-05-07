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
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#" style="color: white">IBM Insight ETL Output
					Indicator</a>
			</div>
		</div>
	</nav>

	<div class="container">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Brand Name</th>
					<th>Product Type</th>
					<th>Total Purchased Quantity</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${result}" var="element">
					<tr>
						<td>${element.productbrand}</td>
						<td>${element.productname}</td>
						<td>${element.numberoforders}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>