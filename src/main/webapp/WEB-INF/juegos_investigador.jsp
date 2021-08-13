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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/juegos_investigador.js"></script>
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
	<div class="container">
	  <div class="row">
	    <div class="col-4 mt-3">
	    	<div style="text-align: center">
	    		<form action="juegos_inv" method="post" class="">
	    			<input type="text" id="buscaJuegos" placeholder="Busca juegos.." name="busc_juegos">
				    <button type="submit" class="btn btn-primary">Buscar</button>
				 </form>
				 <br>
			    <table class="table">
				  <tr class="bg-primary text-light">
				  	<th scope="col">Seleccionar</th>
				  	<th scope="col">ID</th>
				    <th scope="col">Nombre</th>
				  </tr>
				  <c:forEach items="${listaJuegos}" var="juego">
				  	<tr> 
				  		<td class="cb_inv"><input type="checkbox" class="select-item checkbox" name="select-item" id_juego="${juego.id}"/></td>
				  		<td class="colId">${juego.id}</td>
				  		<td>${juego.name}</td>
				  	</tr>  
				  </c:forEach>
				</table>  
			</div>
	    </div>
	    <div class="col-5">
	    	<div class="mx-auto cont-juegos-inv">
	    		<div class="form-group">
	    			<h5 class="texto-form-juegos mb-4 text-center">Nombre nuevo para los juegos</h5> 
	    			<label for="nombre_juegos" class="texto-form-juegos">Nombre</label>
	    			<br>
	    			<input class="form-control juegoNombre" type="text" id="nombreJuego" name="nombre_juegos">
				    <br><br>
				    <button class="btn btn-outline-light btn-juegos btn-sus">Sustituir</button>
				 </div>
			</div>
	    </div>
	    <div class="col-3 mt-3">
	    	<div style="text-align: center">
			    <table class="table">
				  <tr class="bg-primary text-light">
				    <th scope="col">Nombre</th>
				    <th scope="col">ID juego</th>
				  </tr>
				  <c:forEach items="${listaJuegosInv}" var="juegoInv">
				  	<tr>
				  		<td>${juegoInv.nombre}</td> 
				  		<td class="colId">${juegoInv.id}</td>
				  	</tr>  
				  </c:forEach>
				</table>  
			</div>
	    </div>
	  </div>
	</div>
</body>
</html>