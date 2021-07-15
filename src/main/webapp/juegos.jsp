<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Juegos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
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
					<li><a href="./universidades">Universidades</a></li>
					<li><a href="./usuarios">Usuarios</a></li>
				</ul>
	    	</div>
	    </div>
	    
	    <div class="col-10">
	    	<div style="text-align: center">
			    <b>Pagina Juegos</b>  
			    <br><br>
			    <form action="juegos" method="post">
				     <label for="nombre">Nombre: </label>
				     <input name="nombre" size="30" />
				     <br><br>
				     <label for=tipos>Tipo de actividad:</label>
				   	 <select name="tipos">
				       <c:forEach items="${listaTipos}" var="tipo">
				           <option value="${tipo.id}">${tipo.nombre}</option>
				       </c:forEach>
				     </select>
				     <br/><br/>
				     <label for=idiomas>Idioma:</label>
				     <select name="idiomas">
				        <c:forEach items="${listaIdiomas}" var="idioma">
				            <option value="${idioma.id}">${idioma.nombre}</option>
				        </c:forEach>
				      </select>
				     <br/><br/> ${message}
				     <button type="submit">Añadir juego</button>
				 </form> 
			</div>
	    </div>
	    
	  </div>
	</div>
</body>
</html>