<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cuestionarios</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/alumnos.js"></script>
    <link rel="stylesheet" href="css/styles_home.css">
    <link rel="stylesheet" href="css/styles_juegos.css">
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
	    <div class="mx-auto cont-juegos">
	    	<h5 class="texto-form-juegos mb-4 text-center">Seleccionar curso para cuestionarios</h5> 
	    	<form action="cuest_curso" method="post" class="form-group">
	    		<label for="cuest_curso" class="texto-form-juegos">Curso</label>
	    		<br>
				  <select class="cursos form-control" name="cuest_curso">
					<c:forEach var="curso" items="${listaCursos}">
						<option value="${curso.id}" ${curso.id == 1 ? 'selected="selected"' : ''}>${curso.nombre}</option>
					</c:forEach>
				</select>
				<button type="submit" class="btn btn-outline-light btn-juegos mt-4">Siguiente</button>
			</form>
		</div>
	  </div>
	</div>
</body>
</html>