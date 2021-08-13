<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Practicas</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/practicas.js"></script>
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
				<a href="./perfiles">Perfiles</a>
				<a href="./perfiles_menus">Perfiles Menus</a>
				<br>
				<a href="./logout">Salir</a> 
				<br>
				<img style="width:80%; margin-left: 10%; position:absolute; bottom:2%;" src="${pageContext.servletContext.contextPath}/static/FED logo horizontal color 150pp.jpg" alt="">
			</div>
	    </div>
	    <div class="col-2">
	    	<button type="button" class="mt-5 add_pra btn btn-primary">Añadir practica</button>
	    </div>
	    <div class="col-9">
	    	<div style="text-align: center">
			    <table class="table">
				  <tr class="bg-primary text-light">
				  	<th class="d-none">ID</th>
				    <th scope="col">Nombre</th>
				    <th scope="col" style="width: 13%">Tipo</th>
				    <th scope="col">Colegio</th>
				    <th scope="col" style="width: 13%">Anho</th>
				    <th scope="col">Alumno</th>
				    <th scope="col">Borrar</th>
				  </tr>
				  
				  <tr class="d-none copia">
				  		<td class="d-none colId"></td>
				  		<td><input type="text" class="iNombre form-control form-input" maxlength="255" value=""></td>
						<td>
					  		<select class="ddTipo form-control" name="practica_tipo">
					  			<c:forEach items="${listaTiposAct}" var="tipo">
					  				<option value="${tipo.id}" ${tipo.id == 6 ? 'selected="selected"' : ''}>${tipo.nombre}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddColegio form-control" name="practica_cole">
					  			<c:forEach items="${listaColegios}" var="colegio">
					  				<option value="${colegio.id}" ${colegio.id == 1 ? 'selected="selected"' : ''}>${colegio.nombre}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddAnho form-control" name="practica_anho">
					  			<c:forEach items="${listaAnhos}" var="anho">
					  				<option value="${anho.id}" ${anho.id == 1 ? 'selected="selected"' : ''}>${anho.nombre}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddAlumno form-control" name="practica_alumno">
					  			<c:forEach items="${listaUsuarios}" var="alumno">
					  				<option value="${alumno.id}" ${alumno.id == 1 ? 'selected="selected"' : ''}>${alumno.email}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
							<button type="button" class="del_pra btn btn-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
							  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
							  <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
							</svg> </button>
						</td>	
				  	</tr>
				  <c:forEach items="${listaPracticas}" var="practica">
				  	<tr>
				  		<td class="d-none colId">${practica.id}</td>
				  		<td><input type="text" class="iNombre form-control form-input" maxlength="255" value="${practica.nombre}"></td>
						<td>
					  		<select class="ddTipo form-control" name="practica_tipo">
					  			<c:forEach items="${listaTiposAct}" var="tipo">
					  				<option value="${tipo.id}" ${tipo.id == practica.tipo ? 'selected="selected"' : ''}>${tipo.nombre}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddColegio form-control" name="practica_cole">
					  			<c:forEach items="${listaColegios}" var="colegio">
					  				<option value="${colegio.id}" ${colegio.id == practica.colegio ? 'selected="selected"' : ''}>${colegio.nombre}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddAnho form-control" name="practica_anho">
					  			<c:forEach items="${listaAnhos}" var="anho">
					  				<option value="${anho.id}" ${anho.id == practica.anho ? 'selected="selected"' : ''}>${anho.nombre}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddAlumno form-control" name="practica_alumno">
					  			<c:forEach items="${listaUsuarios}" var="alumno">
					  				<option value="${alumno.id}" ${alumno.id == practica.alumno ? 'selected="selected"' : ''}>${alumno.email}</option>
					  			</c:forEach>
							</select>
						</td>
						<td>
							<button type="button" class="del_pra btn btn-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
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