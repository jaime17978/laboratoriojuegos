<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Menu estadisticas</title>
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
				<a href="./anhos">A�os</a>
				<a href="./universidades">Universidades</a>
				<a href="./usuarios">Usuarios</a>
				<a href="./colegios">Colegios</a>
				<a href="./practicas">Practicas</a>
				<br>
				<a href="./logout">Salir</a> 
			</div>
	    </div>
	    <div class="col-10">
	    	<div class="mx-auto cont-juegos">
	    	<h5 class="texto-form-juegos mb-4 text-center">Selecciona un curso para ver sus estadisticas</h5> 
	    		<form action="estadisticas" method="post" class="form-group">
	    			<label for="cuest_curso" class="texto-form-juegos">Curso</label>
	    			<br>
				    <select class="cursos form-control" name="cuest_curso">  
						<option value="all" selected="selected">Todos los cursos</option> 
						<c:forEach var="curso" items="${listaCursos}">
							<option value="${curso.id}">${curso.nombre}</option>
						</c:forEach>
					</select>
				    <br><br>
				    <button type="submit" class="btn btn-outline-light btn-juegos">Seleccionar curso</button>
				 </form>		    
			</div>
	    </div>
	  </div>
	</div>
</body>
</html>