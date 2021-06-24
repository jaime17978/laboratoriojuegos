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
    <link rel="stylesheet" href="css/bootstrap.min.css">   		
    <script src="js/bootstrap.min.js"></script> 

</head>
<body>

<div style="text-align: center">
	<h1>Cambio contraseña</h1>
	
	<form action="rest_contra" method="post">
	     <label for="email">Email</label>
	     <br>
	     <input name="email" size="30" />
	     <br><br>
	     <label for="password1">Password</label>
	     <br>
	     <input type="password" name="password1" size="30" />
	     <br><br>
	     <label for="password2">Password</label>
	     <br>
	     <input type="password" name="password2" size="30" />
	     <br>${message}
	     <br><br>
	     <button type="submit">Guardar cambios</button>
	 </form>    
	 <br>
</div>

</body>

</html>