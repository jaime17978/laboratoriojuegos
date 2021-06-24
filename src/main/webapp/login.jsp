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
	<h1>Login</h1>
	
	<form action="login" method="post">
	     <label for="email">Email</label>
	     <br>
	     <input name="email" size="30" />
	     <br><br>
	     <label for="password">Password</label>
	     <br>
	     <input type="password" name="password" size="30" />
	     <br>${message}
	     <br><br>
	     <button type="submit">Login</button>
	 </form>    
	 <br>
	 <a href="./rest_contra">Restablecer contraseña (temp)</a>
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