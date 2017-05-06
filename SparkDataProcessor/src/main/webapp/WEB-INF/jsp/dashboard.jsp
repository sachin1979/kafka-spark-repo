<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>IBM Insight</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<c:forEach items="${result}" var="element"> 
  <tr>
    <td>${element.productbrand}</td>
    <td>${element.numberoforders}</td>
  </tr>
</c:forEach>	
</body>
</html>