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
	<img style="margin-left:25%;width: 50%" src="${pageContext.servletContext.contextPath}/static/FED logo ppal color 100pp.jpg" alt="">
	<h1>Login</h1>
	
	<form action="login" method="post" class="form-group">
	     <label for="email" class="mb-1">Email</label>
	     <br>
	     <input name="email" size="30" class="form-control form-input mx-auto"/>
	     <br>
	     <label for="password" class="mb-1">Contraseña</label>
	     <br>
	     <input type="password" name="password" class="form-control form-input mx-auto mb-2" size="30" />
	     <c:if test="${not empty message}">       
		 	<p class="mt-1 mb-2 error-msg">${message}</p>  
		 </c:if>
	     <button type="submit" class="btn btn-primary mt-2 boton-menu">Login</button>
	 </form>    
	 <br>
	 <a href="./rest_contra" class="text-center">Restablecer contraseña (temp)</a>
</div>

</body>

<script type="text/javascript">

    $(document).ready(function() {
        $("#loginForm").validate({
            rules: {
                email: {
                    required: true,
                    email: true
                },

                password: "required",
            },

            messages: {
                email: {
                    required: "Please enter email",
                    email: "Please enter a valid email address"
                },

                password: "Please enter password"
            }
        });

    });
</script>

</html>