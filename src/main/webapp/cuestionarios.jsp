<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cuestionarios</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/cuestionarios.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <script>
    var list = ${listaNombres}
        $(function(){
            $(".iJuego").autocomplete({
                source: list,
            });
        })
        
   $(document).ready(function() {
   		$('.add_cu').click( function(){
		    $clone = $(".copia").clone();
			$clone.removeClass("d-none copia");
			$clone.addClass("filaNueva");
			$clone.find("span").addClass('nuevoCuest');
			$clone.find('.box').change(editCheckbox);
			$clone.find('.del_cu').click(borrarCuest);
			$clone.find('.iJuego').change(iJuegoChange);
			$clone.find('.iJuego').autocomplete({
                source: list,
            });
			$clone.insertAfter(".copia");
		});
	});
	    
    $(document).ready(function() {
    	<c:forEach items="${listaCuestionarios}" var="cuest">
    		var col = $('.idAlumno:contains(${cuest.idAlumno})').eq(0).closest("td").index();
    		var row = $('.idJuego:contains(${cuest.idJuego})').eq(0).closest("tr").index();
    		
    		if(typeof col !== "undefined" && typeof row !== "undefined")
    		{
    			var cell = $("#tablaCuest").find('tr').eq(row).find('td').eq(col).children().eq(0);
    			cell.children('.fav').prop('checked', ${cuest.favorito});
    			cell.children('.bar').prop('checked', ${cuest.barrio});
    			cell.children('.col').prop('checked', ${cuest.colegio});
    		} 
    	</c:forEach>
    });
    </script>
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
				<button type="button" class="mt-5 add_cu">A�adir fila</button>
	    	</div>
	    </div>
	    
	    <div class="col-10">
	    	<div style="text-align: center">
	    		<div class="juegoForm d-none">
		    		<form class="py-4 fJuego">
					     <label for="nombre">Nombre: </label>
					     <input class="formNombre" name="nombre" size="30" />
					     <br><br>
					     <label for=tipos>Tipo de actividad:</label>
					   	 <select class="formTipo" name="tipos">
					       <c:forEach items="${listaTipos}" var="tipo">
					           <option value="${tipo.id}">${tipo.nombre}</option>
					       </c:forEach>
					     </select>
					 </form> 
				 <button class="fBoton">A�adir juego</button>
	    		</div>
			    <table id="tablaCuest">
				  <tr>
				  	<td class="invisible">-</td>
				  	<td class="invisible">-</td>
				  	<td class="invisible">-</td>
				  	<c:forEach items="${listaAlumnos}" var="alumno">
				  		<td class="idAlumno">${alumno.id}</td>
				  	</c:forEach>
				  </tr> 
				  <tr>
				  	<td class="invisible">-</td>
				  	<td class="invisible">-</td>
				  	<td class="">Nombre</td>
				  	<c:forEach items="${listaAlumnos}" var="alumno">
				  		<td>${alumno.nombre}</td>	
				  	</c:forEach>
				  </tr>
				  <tr>
				  	<td class="invisible">-</td>
				  	<td class="invisible">-</td>
				  	<td class="">Genero</td>
				  	<c:forEach items="${listaAlumnos}" var="alumno">
				  		<td>${alumno.genero}</td>	
				  	</c:forEach>
				  </tr>
				  <tr>
				  	<td class="invisible">-</td>
				  	<td class="invisible">-</td>
				  	<td class="">Edad</td>
				  	<c:forEach items="${listaAlumnos}" var="alumno">
				  		<td>${alumno.edad}</td>	
				  	</c:forEach>
				  </tr>
				  <tr class="copia d-none">	
				  	<td class="idJuego">0</td>
				  	<td>
						<button type="button" class="del_cu btn btn-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
							<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
							<path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
							</svg> 
						</button>					  	  
					</td>
				  	<td class="nombreJuego"><input type="text" class="iJuego" maxlength="255" value=""></td>
				  	<c:forEach items="${listaAlumnos}" var="alumno">
				  		<td class="cuestionario">
				  			<span class="invisible">
					  			<input type="checkbox" class="box fav" name="favorito" value="fav">
								<label for="favorito"> F.</label><br>
								<input type="checkbox" class="box bar" name="barrio" value="bar">
								<label for="barrio"> B.</label><br>
								<input type="checkbox" class="box col" name="colegio" value="col">
								<label for="colegio"> C.</label><br>
				  			</span>
				  		</td>	
				  	</c:forEach>
				  </tr>
				  <c:forEach items="${setJuegos}" var="juego">
					  <tr>
						 <td class="idJuego">${juego.id}</td>
						 <td>
							<button type="button" class="del_cu btn btn-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
								<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
								<path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
								</svg> 
							</button>					  	  
						</td>
						 <td class="nJuego">${juego.name}</td>
						  <c:forEach items="${listaAlumnos}" var="alumno">
						  	<td class="cuestionario">
						  		<span>
							  		<input type="checkbox" class="box fav" name="favorito" value="fav">
									<label for="favorito"> F.</label><br>
									<input type="checkbox" class="box bar" name="barrio" value="bar">
									<label for="barrio"> B.</label><br>
									<input type="checkbox" class="box col" name="colegio" value="col">
									<label for="colegio"> C.</label><br>
						  		</span>
						  	</td>	
						  </c:forEach>
					  </tr>
				  </c:forEach>
				</table> 
			</div>
	    </div>
	    
	  </div>
	</div>
</body>
</html>