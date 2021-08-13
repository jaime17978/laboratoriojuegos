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
    <link rel="stylesheet" href="css/styles_home.css">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
</head>
<body>
	<div class="sidenav">
				<a href="./juegos">Juegos</a>
				<a href="./alumnos">Alumnos</a>
				<a href="./cuest_curso">Cuestionarios</a>
				<a href="./paises">Paises</a>
				<a href="./regiones_pais">Regiones</a>
				<a href="./estadisticas">Estadisticas</a>
				<a href="./anhos">A�os</a>
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
	<div class="container">
	  <div class="row">
	    <div class="col-2 mt-3">
	    </div>
	    <div class="col-8 mt-3">
	    	<div style="text-align: center">
			    <table class="table">
				  <tr class="bg-primary text-light">
				  	<th class="d-none" scope="col">ID</th>
				    <th scope="col">Nombre</th>
				    <th scope="col">Genero</th>
				    <th scope="col">Edad</th>
				    <th scope="col">Curso</th>
				  </tr>
				  <c:forEach items="${listaAlumnos}" var="alumno">
				  	<tr>
				  		<td class="d-none colId">${alumno.id}</td>
				  		<td>${alumno.nombre}</td>
				  		<td>${alumno.genero}</td>
				  		<td>${alumno.edad}</td>
				  		<td>
				  			<c:forEach var="curso" items="${listaCursos}">
						        <c:if test = "${curso.id == alumno.curso}">
						         	${curso.nombre}
						      	</c:if>
						    </c:forEach>
						</td>	
				  	</tr>  
				  </c:forEach>
				</table>  
			</div>
	    </div>
	  </div>
	</div>
</body>
</html>