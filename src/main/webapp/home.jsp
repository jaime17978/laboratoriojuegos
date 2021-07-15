<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>


<div class="container">
  <div class="row">
    <div class="col-2">
    	<div id="navegador">
    		<ul class="pt-3 pe-4">
    			<li><a href="./logout">Logout</a> </li>
				<li><a href="./juegos">Juegos</a></li>
				<li><a href="./alumnos">Alumnos</a></li>
				<li><a href="./cuest_curso">Cuestionarios</a></li>
				<li><a href="./paises">Paises</a></li>
				<li><a href="./regiones_pais">Regiones</a></li>
				<li><a href="./estadisticas">Estadisticas</a></li>
				<li><a href="./anhos">Anhos</a></li>
				<li><a href="./universidades">Universidades</a></li>
				<li><a href="./usuarios">Usuarios</a></li>
			</ul>
    	</div>
    </div>
    
    <div class="col-10">
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