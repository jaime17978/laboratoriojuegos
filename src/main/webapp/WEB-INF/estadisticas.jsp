<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Alumnos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/alumnos.js"></script>
    <link rel="stylesheet" href="css/styles_home.css">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
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
				<a href="./perfiles_menus">Perfiles Menus</a>
				<br>
				<a href="./logout">Salir</a> 
				<br>
				<img style="width:80%; margin-left: 10%; position:absolute; bottom:2%;" src="${pageContext.servletContext.contextPath}/static/FED logo horizontal color 150pp.jpg" alt="">
			</div>
	    </div>
	    <div class="col-10">
	    	<div style="text-align: center">
	    		<h1>${ titulo }</h1>
	    		<br><br>
	    		<div class="row">
	    			<div class="col-3">
	    				<h6>Numero de niños y niñas</h6>
		    			<table class="table">
						  <tr class="bg-primary text-light">
						    <th scope="col">Genero</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${numAlumnosGenero}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
	    				<h6>Numero de juegos mencionados por alumno</h6>
		    			<table class="table">
						  <tr class="bg-primary text-light">
						    <th scope="col">Alumnos</th>
						    <th scope="col">Nº de juegos</th>
						  </tr>
						  <c:forEach items="${juegosPorAlumno}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
	    				<h6>Numero de juegos mencionados por niños</h6>
		    			<table class="table">
						  <tr class="bg-primary text-light">
						    <th scope="col">Alumnos</th>
						    <th scope="col">Nº de juegos</th>
						  </tr>
						  <c:forEach items="${juegosPorNinhos}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
	    				<h6>Numero de juegos mencionados por niñas</h6>
		    			<table class="table">
						  <tr class="bg-primary text-light">
						    <th scope="col">Alumnos</th>
						    <th scope="col">Nº de juegos</th>
						  </tr>
						  <c:forEach items="${juegosPorNinhas}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
	    				<h6>Numero de juegos mencionados en el colegio o favoritos</h6>
		    			<table class="table">
						  <tr class="bg-primary text-light">
						    <th scope="col">Juegos</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${juegosColFav}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
	    				<h6>Numero de juegos mencionados en el barrio o favoritos</h6>
		    			<table class="table">
						  <tr class="bg-primary text-light">
						    <th scope="col">Juegos</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${juegosBarFav}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
	    				<h6>Numero de juegos mencionados como favoritos</h6>
		    			<table class="table">
						  <tr class="bg-primary text-light">
						    <th scope="col">Juegos</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${juegosFav}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    		</div>
			</div>
	    </div>
	  </div>
	</div>
</body>
</html>