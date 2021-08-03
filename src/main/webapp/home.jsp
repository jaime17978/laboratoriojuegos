<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/styles_home.css">
</head>
<body>


<div class="container">
  <div class="row">
    <div class="col-2">
    	<div class="sidenav">
			<a href="./juegos">Juegos</a>
			<a href="./alumnos">Alumnos</a>
			<a href="./cuest_curso">Cuestionarios</a>
			<a href="./paises">Paises</a>
			<a href="./regiones_pais">Regiones</a>
			<a href="./estadisticas">Estadisticas</a>
			<a href="./anhos">Años</a>
			<a href="./universidades">Universidades</a>
			<a href="./usuarios">Usuarios</a>
			<a href="./colegios">Colegios</a>
			<a href="./practicas">Practicas</a>
			<a href="./perfiles">Perfiles</a>
			<br>
			<a href="./logout">Salir</a> 
		</div>
    </div>
    
    <div class="col-10 main">
    	<div style="text-align: center">
		    <h1>Home</h1>
		    <b>Logeado como: ${user.email}</b>
		    <br><br>
		    <a href="./logout">Logout</a>    
		</div>
    </div>
    
  </div>
</div>

</body>
</html>