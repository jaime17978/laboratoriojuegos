<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>FED</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script type="text/javascript"
            src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles_login.css">  
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

</head>
<body>

<div style="text-align: center" class="container d-flex flex-column div_login">
	<h1>Cambio contraseña</h1>
	
	<form action="rest_contra" method="post" class="form-group">
	     <label for="email">Email</label>
	     <input name="email" size="30" class="form-control form-input mx-auto mb-4"/>
	     <label for="password1">Password</label>
	     <input type="password" name="password1" size="30" class="form-control form-input mx-auto mb-4"/>
	     <label for="password2">Password</label>
	     <input type="password" name="password2" size="30" class="form-control form-input mx-auto mb-4"/>
	     <c:if test="${not empty message}">       
		 	<p class="mt-3 mb-2 error-msg">${message}</p>  
		 </c:if>
	     <button type="submit" class="btn btn-primary mt-2 boton-menu">Guardar cambios</button>
	 </form>    
	 <br>
	 <a href="./login" class="text-center">Volver a login</a>
</div>

</body>

</html>