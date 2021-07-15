<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Usuarios</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/usuarios.js"></script>
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
				<button type="button" class="mt-5 add_usu">Añadir usuario</button>
	    	</div>
	    </div>
	    <div class="col-10">
	    	<div style="text-align: center">
			    <table>
				  <tr>
				  	<th class="d-none">ID</th>
				    <th>Email</th>
				    <th>Perfil</th>
				    <th>Universidad</th>
				    <th>Activo</th>
				    <th>Idioma</th>
				  </tr>
				  <tr class="d-none copia">
				  		<td class="d-none colId"></td>
				  		<td><input type="text" class="iEmail" maxlength="255" value=""></td>
				  		<td>
					  		<select class="ddPerfil" name="usuario_p">
							    <c:forEach var="perfil" items="${listaPerfiles}">
							        <option value="${perfil.id}" ${perfil.id == 3 ? 'selected="selected"' : ''}>${perfil.nombre}</option>
							    </c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddUni" name="usuario_u">
							    <c:forEach var="universidad" items="${listaUniversidades}">
							        <option value="${universidad.id}" ${universidad.id == 1 ? 'selected="selected"' : ''}>${universidad.nombre}</option>
							    </c:forEach>
							</select>
						</td>
						<td><input type="checkbox" class="box_act" name="activo" value="act"></td>
						<td>
					  		<select class="ddIdioma" name="usuario_i">
							    <c:forEach var="idioma" items="${listaIdiomas}">
							        <option value="${idioma.id}" ${idioma.id == 1 ? 'selected="selected"' : ''}>${idioma.nombre}</option>
							    </c:forEach>
							</select>
						</td>
						<td>
							<button type="button" class="del_usu btn btn-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
							  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
							  <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
							</svg> </button>
						</td>
				  	</tr>
				  <c:forEach items="${listaUsuarios}" var="usuario">
				  	<tr>
				  		<td class="d-none colId">${usuario.id}</td>
				  		<td><input type="text" class="iEmail" maxlength="255" value="${usuario.email}"></td>
				  		<td>
					  		<select class="ddPerfil" name="usuario_p">
							    <c:forEach var="perfil" items="${listaPerfiles}">
							        <option value="${perfil.id}" ${perfil.id == usuario.perfil ? 'selected="selected"' : ''}>${perfil.nombre}</option>
							    </c:forEach>
							</select>
						</td>
						<td>
					  		<select class="ddUni" name="usuario_u">
							    <c:forEach var="universidad" items="${listaUniversidades}">
							        <option value="${universidad.id}" ${universidad.id == usuario.universidad ? 'selected="selected"' : ''}>${universidad.nombre}</option>
							    </c:forEach>
							</select>
						</td>
						<td><input type="checkbox" class="box_act" name="activo" value="act" ${usuario.activo == true ? 'checked' : ''}></td>
						<td>
					  		<select class="ddIdioma" name="usuario_i">
							    <c:forEach var="idioma" items="${listaIdiomas}">
							        <option value="${idioma.id}" ${idioma.id == usuario.id ? 'selected="selected"' : ''}>${idioma.nombre}</option>
							    </c:forEach>
							</select>
						</td>
						<td>
							<button type="button" class="del_usu btn btn-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
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