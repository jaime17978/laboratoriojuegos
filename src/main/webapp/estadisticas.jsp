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
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
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
				</ul>
				<button type="button" class="mt-5 add_al">Añadir alumno</button>
	    	</div>
	    </div>
	    <div class="col-10">
	    	<div style="text-align: center">
	    		<h1>${ titulo }</h1>
	    		<br><br>
	    		<div class="row">
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>Tipos de juego</h6>
						  <tr>
						    <th scope="col">Tipo</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecTotalTipos}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>5 Juegos mas mencionados por niños</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecNignos}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>5 Juegos mas mencionados por niñas</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecNignas}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>5 Juegos mas mencionados en colegio</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecColegio}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>5 Juegos mas mencionados en barrio</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecBarrio}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>Juegos favoritos</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecFavGen}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>Juegos favoritos para niños</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecFavNignos}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>Juegos favoritos para niñas</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecFavNignas}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>Juegos favoritos en el colegio</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecFavCol}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>Juegos favoritos en el barrio</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecFavBarrio}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>Juegos favoritos en el colegio y barrio</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecFavColBar}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    			<div class="col-3">
		    			<table class="table">
					    	<h6>Frecuencia total de juegos</h6>
						  <tr>
						    <th scope="col">Nombre</th>
						    <th scope="col">Numero</th>
						  </tr>
						  <c:forEach items="${frecTotalJuegos}" var="datos">
						  	<tr>
						  	<td>${ datos.nombre }</td>
						  	<td>${ datos.contador }</td>
						  	</tr>  
						  </c:forEach>
						</table>
	    			</div>
	    		</div>
			      <!-- 

        	//Juegos favoritos en función del lugar de juego (colegio, barrio, colegio y barrio o ninguno).
        	
        	//Favoritos y colegio (incluye a los que tienen colegio y barrio?)
        	List<ContadorEst> frecuenciaFavCol = dao.frecFavColCursoBD(user_id, curso);
        	request.setAttribute("frecFavCol", frecuenciaFavCol);
        	
        	//Favoritos y barrio (incluye a los que tienen colegio y barrio?)
        	List<ContadorEst> frecuenciaFavBarrio = dao.frecFavBarrioCursoBD(user_id, curso);
        	request.setAttribute("frecFavBarrio", frecuenciaFavBarrio);
        	
        	//Favoritos, colegio y barrio
        	List<ContadorEst> frecuenciaFavColBar = dao.frecFavColBarCursoBD(user_id, curso);
        	request.setAttribute("frecFavColBar", frecuenciaFavColBar);
			      
			       -->
			</div>
	    </div>
	  </div>
	</div>
</body>
</html>