<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<!-- action is QRCodeServlet send this request to QRCodeServlet.java page 
	 this Servlet is present in src folder com.qr.controller package
  -->
<form method="POST" action="QRCodeServlet">
		<br>
		<br>
		<br>	
		<h1>Generate QR Code<br></h1>
		<!-- Get the data for generate the qr image -->		
		
		Enter Name :<input type="text" name="Uname"><br>
   		Phone:<input type="text" name="PhoneNumber"><br>
 		<br>
 		
 		<input type="submit" value="Generate QR Code"><br>
 		
 		
 		<!-- Get the Uname from QRCodeServlet class from com.qr.controller package  -->
 		
 		<%
  		   	  String imageName = (String) request.getAttribute("imageName");
  			  out.println(imageName);
		%>
		<!-- Display QR image GIT -->
  				  Your QR  Code is <br>
		<!--  This QR image get Run time image name and match to image folder <br>-->
		<img src="image\\<%= request.getAttribute("imageName") %>.png" style="width:48px;height:48px" /><br>
 		
 		
 		
  </form>
</center>
</body>
</html>