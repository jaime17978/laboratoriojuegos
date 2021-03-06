<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Perfiles Menus</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/styles_home.css">
    <link rel="stylesheet" href="css/styles_juegos.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/perfiles_menus.js"></script>
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
				<a href="./anhos">A?os</a>
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
	    <div class="col-1">
	    	
	    </div>
	    <div class="col-5">
	    	<div class="cont-perfiles-menus">
	    	<h5 class="texto-form-juegos mb-4 text-center">A?adir permisos nuevos</h5>  
			    <form action="crear_perfil_menu" method="post" class="form-group">
				     <label for="nombre" class="texto-form-juegos">Nombre</label>
				     <input name="nombre" size="30" class="form-control mx-auto"/>
				     <br><br>
				     <label for=tipos class="texto-form-juegos">Perfil</label>
				   	 <select class="form-control" name="perfiles">
				       <c:forEach items="${listaPerfiles}" var="perfil">
				           <option value="${perfil.id}">${perfil.nombre}</option>
				       </c:forEach>
				     </select>
				     <br/><br/>
				     <label for=idiomas class="texto-form-juegos">Menu</label>
				     <select class="form-control" name="menus">
				        <c:forEach items="${listaMenus}" var="menu">
				            <option value="${menu.id}">${menu.nombre}</option>
				        </c:forEach>
				      </select>
				     <c:if test="${not empty message}">       
					 	<p class="mt-4 mb-2 ml-2 texto-form-juegos text-center">${message}</p>  
					 </c:if>
					 <c:if test="${not empty error}">       
					 	<p class="mt-2 mb-2 error-msg">${error}</p>  
					 </c:if>
				     <button type="submit" class="btn btn-outline-light btn-juegos mt-4">A?adir Permisos</button>
				 </form>  
	    	</div>
	    </div>
	    <div class="col-6">
	    	<div style="text-align: center">
			    <table class="table">
				  <tr class="bg-primary text-light">
				  	<th class="d-none">idPerfil</th>
				  	<th class="d-none">idMenu</th>
				  	<th class="col">Perfil</th>
				    <th scope="col">Menu</th>
				    <th scope="col">Nombre</th>
				    <th scope="col">Borrar</th>
				  </tr>
				  <c:forEach items="${listaPerfilesMenus}" var="perfilMenu">
				  	<tr>
				  		<td class="d-none colPerfil">${perfilMenu.perfil}</td> 
				  		<td class="d-none colMenu">${perfilMenu.menu}</td> 
					  	<c:forEach items="${listaPerfiles}" var="perfil">
					  		<c:if test="${perfil.id == perfilMenu.perfil}">       
								<td>${perfil.nombre}</td> 
							</c:if>
					  	</c:forEach>
					  	
					  	<c:forEach items="${listaMenus}" var="menu">
					  		<c:if test="${menu.id == perfilMenu.menu}">       
								<td>${menu.nombre}</td> 
							</c:if>
					  	</c:forEach>
				  		<td><input type="text" class="iNombre form-control form-input" maxlength="255" value="${perfilMenu.nombre}"></td>
						<td>
							<button type="button" class="del_pm btn btn-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
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