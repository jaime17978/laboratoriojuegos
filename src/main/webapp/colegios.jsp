<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Colegios</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/colegios.js"></script>
    <link rel="stylesheet" href="css/styles_home.css">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
</head>
<body>
	<div class="container">
	  <div class="row">
	    <div class="col-1">
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
	    <div class="col-2">
	    	<button type="button" class="mt-5 add_col btn btn-primary">Añadir colegio</button>
	    </div>
	    <div class="col-9">
	    	<div style="text-align: center">
			    <table class="table">
				  <tr class="bg-primary text-light">
				  	<th class="d-none">ID</th>
				    <th scope="col">Nombre</th>
				    <th scope="col">Direccion</th>
				    <th scope="col">Localidad</th>
				    <th scope="col">Region</th>
				    <th scope="col">Tipo</th>
				    <th scope="col">Codigo Postal</th>
				    <th scope="col">Borrar</th>
				  </tr>
				  
				  <tr class="d-none copia">
				  		<td class="d-none colId"></td>
				  		<td><input type="text" class="iNombre form-control form-input" maxlength="255" value=""></td>
				  		<td><input type="text" class="iDireccion form-control form-input" maxlength="255" value=""></td>
				  		<td><input type="text" class="iLocalidad form-control form-input" maxlength="255" value=""></td>
				  		<td>
					  		<select class="ddRegion form-control" name="colegio_region">
					  			<c:forEach items="${listaRegiones}" var="region">
					  				<option value="${region.id}" ${region.id == '??-??' ? 'selected="selected"' : ''}>${region.nombre}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddTipo form-control" name="colegio_tipo">
					  			<option value="publico" selected>Publico</option>
					  			<option value="privado">Privado</option>
					  			<option value="concertado">Concertado</option>
							</select>
						</td>
						<td><input type="text" class="iCP form-control form-input" maxlength="255" value=""></td>
						<td>
							<button type="button" class="del_col btn btn-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
							  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
							  <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
							</svg> </button>
						</td>	
				  	</tr>  
				  <c:forEach items="${listaColegios}" var="colegio">
				  	<tr>
				  		<td class="d-none colId">${colegio.id}</td>
				  		<td><input type="text" class="iNombre form-control form-input" maxlength="255" value="${colegio.nombre}"></td>
				  		<td><input type="text" class="iDireccion form-control form-input" maxlength="255" value="${colegio.direccion}"></td>
				  		<td><input type="text" class="iLocalidad form-control form-input" maxlength="255" value="${colegio.localidad}"></td>
				  		<td>
					  		<select class="ddRegion form-control" name="colegio_region">
					  			<c:forEach items="${listaRegiones}" var="region">
					  				<option value="${region.id}" ${region.id == colegio.region ? 'selected="selected"' : ''}>${region.nombre}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddTipo form-control" name="colegio_tipo">
					  			<option value="publico" ${"publico" == colegio.tipo ? 'selected="selected"' : ''}>Publico</option>
					  			<option value="privado" ${"privado" == colegio.tipo ? 'selected="selected"' : ''}>Privado</option>
					  			<option value="concertado" ${"concertado" == colegio.tipo ? 'selected="selected"' : ''}>Concertado</option>
							</select>
						</td>
						<td><input type="text" class="iCP form-control form-input" maxlength="255" value="${colegio.codigo}"></td>
						<td>
							<button type="button" class="del_col btn btn-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
							  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
							  <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
							</svg> </button>
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