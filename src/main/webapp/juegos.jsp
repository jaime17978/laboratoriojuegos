<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Juegos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
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
				<br>
				<a href="./logout">Salir</a> 
			</div>
	    </div>
	    
	    <div class="cont-juegos mx-auto">
	    	<h5 class="texto-form-juegos mb-4 text-center">Crear un juego nuevo</h5>  
			    <form action="juegos" method="post" class="form-group">
				     <label for="nombre" class="texto-form-juegos">Nombre</label>
				     <input name="nombre" size="30" class="form-control mx-auto"/>
				     <br><br>
				     <label for=tipos class="texto-form-juegos">Tipo de actividad</label>
				   	 <select class="form-control" name="tipos">
				       <c:forEach items="${listaTipos}" var="tipo">
				           <option value="${tipo.id}">${tipo.nombre}</option>
				       </c:forEach>
				     </select>
				     <br/><br/>
				     <label for=idiomas class="texto-form-juegos">Idioma</label>
				     <select class="form-control" name="idiomas">
				        <c:forEach items="${listaIdiomas}" var="idioma">
				            <option value="${idioma.id}">${idioma.nombre}</option>
				        </c:forEach>
				      </select>
				     <c:if test="${not empty message}">       
					 	<p class="mt-4 mb-2 ml-2 texto-form-juegos text-center">${message}</p>  
					 </c:if>
					 <c:if test="${not empty error}">       
					 	<p class="mt-2 mb-2 error-msg">${error}</p>  
					 </c:if>
				     <button type="submit" class="btn btn-outline-light btn-juegos mt-4">Añadir juego</button>
				 </form>  
	    </div>
	    
	  </div>
	</div>
</body>
</html>