<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Regiones</title>
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
	    	</div>
	    </div>
	    <div class="col-10">
	    	<div style="text-align: center" class="mt-4">
	    		
	    		<form action="regiones_pais" method="post">
	    			<label for="regiones_pais">Pais</label>
	    			<br>
				    <select class="paises" name="regiones_pais">
						<c:forEach var="pais" items="${listaPaises}">
							<option value="${pais.id}" ${pais.id == "ad" ? 'selected="selected"' : ''}>${pais.nombre}</option>
						</c:forEach>
					</select>
				    <br><br>
				    <button type="submit">Seleccionar pais</button>
				 </form>
				 
	    		
			    
			</div>
	    </div>
	  </div>
	</div>
</body>
</html>