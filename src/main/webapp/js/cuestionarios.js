/**
 * 
 */
 
 function iJuegoChange(){
   		var aux= $(this).parent().siblings(".idJuego").eq(0);
   		var aux2= $(this).parent();
   		var nom= $(this).val();
   		
   		$.ajax('g_juego', {
		    type: 'POST',  
		    data: { nombre: nom },
		    success: function(data) {
		    	if(data != "0"){
		    		aux2.siblings('.cuestionario').children().removeClass('invisible');
		    		aux2.html(nom);
		    		aux2.addClass('nJuego');
		    		aux.html(data);
		    		console.log("Hola");
		    		$('.nuevoCuest').removeClass('nuevoCuest invisible');
        			$('.filaNueva').removeClass('filaNueva');
		    	}
		    	else{
		    		if (window.confirm("El nombre del juego introducido no existe. Quieres crearlo?")) {
					  	$('.juegoForm').removeClass('d-none');
			    		$('#tablaCuest').addClass('d-none');
			    		$('.formNombre').val(nom);
					}
		    	}
	        },
		});
	}
	
$(document).ready(function() {
   $('.iJuego').change(iJuegoChange);
});

function editCheckbox(){
   		var col = $(this).closest("td").index();
   		//var row = $(this).closest("tr").index();

		var idAlumno = $('#tablaCuest').children().eq(0).children().eq(0).children().eq(col).html();
		var idJuego = $(this).parent().parent().siblings(".idJuego").eq(0).html();
		var fav = $(this).parent().children('.fav').prop('checked');
		var bar = $(this).parent().children('.bar').prop('checked');
		var col = $(this).parent().children('.col').prop('checked');
		
		$.ajax('cuestionarios', {
	    type: 'POST',  
	    data: { idAlumno: idAlumno, idJuego: idJuego, fav: fav, bar: bar, col: col },
		});
	}
	
$(document).ready(function() {
   $('.box').change(editCheckbox);
});

function borrarCuest(){	
		if (confirm("Seguro que quieres borrar esta fila del cuestionario?")) {
			var idJuego = $(this).parent().siblings(".idJuego").eq(0).html();
	        $(this).parent().parent().remove();
	        if (idJuego != "0"){
	        	$.ajax('b_cuest', {
			    type: 'POST',  
			    data: { id: idJuego },
				});
	        }
	    }
	    else {
	    	return false;
	    }
	}

$(document).ready(function() {
   $('.del_cu').click(borrarCuest);
});

$(document).ready(function() {
   $('.fBoton').click( function(){
   		var nombre = $('.formNombre').val();
   		var tipo = $('.formTipo').val();
		//Hay que mandar nombre y tipo al servlet, que devuelva un juego en formato json y meter su id/nombre en los contenedores que corresponde.
		$.ajax('c_juegoCuest', {
	    type: 'POST',  
	    data: {	nombre: nombre, tipos: tipo },
	    success: function(data) {
         $('.filaNueva').children('.idJuego').html(data.id);
         $('.filaNueva').children('.nombreJuego').html(data.name);
         $('.nuevoCuest').removeClass('nuevoCuest invisible');
         $('.filaNueva').removeClass('filaNueva');
         
         $('.juegoForm').addClass('d-none');
		 $('#tablaCuest').removeClass('d-none');
      	}
		});
		
	});
});
