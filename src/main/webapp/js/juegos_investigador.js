$(document).ready(function() {
   $('.btn-sus').click( function(){
	
		var seleccionados = [];
		
		if($('.cb_inv input:checked').length > 0 && $(".juegoNombre").val() != ""){
			$('.cb_inv input:checked').each(function() {
		    	seleccionados.push($(this).attr('id_juego'));
			});
			
			$.ajax('juegos', {
				    type: 'POST',  
				    data: { option: "sus", nombre: $(".juegoNombre").val(), juegos: JSON.stringify(seleccionados) },
				    success: function(){
				      console.log("eo");
	                  location.reload(true);
	                } 
			});
		}
		else{
			window.alert("Para realizar la sustitucion de nombres es necesario marcar al menos un juego e indicar un nombre nuevo.");
		}
		
		
	});
});