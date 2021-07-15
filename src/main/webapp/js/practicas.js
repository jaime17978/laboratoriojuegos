$(document).ready(function() {
   $('.iNombre').change( function(){
	
		$.ajax('practicas', {
			    type: 'POST',  
			    data: { option: "c_nombre", nombre: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
   $('.ddTipo').change( function(){
	
		$.ajax('practicas', {
			    type: 'POST',  
			    data: { option: "c_tipo", tipo: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
   $('.ddColegio').change( function(){
	
		$.ajax('practicas', {
			    type: 'POST',  
			    data: { option: "c_colegio", colegio: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
   $('.ddAnho').change( function(){
	
		$.ajax('practicas', {
			    type: 'POST',  
			    data: { option: "c_anho", anho: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
   $('.ddAlumno').change( function(){
	
		$.ajax('practicas', {
			    type: 'POST',  
			    data: { option: "c_alumno", alumno: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
   $('.del_pra').click( function(){
		if (confirm("Seguro que quieres borrar la practica seleccionada?")) {
	        $(this).parent().parent().remove();
			$.ajax('practicas', {
			    type: 'POST',  
			    data: { option: "delete", id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	    }
	    return false;

	});
});

$(document).ready(function() {
   $('.add_pra').click( function(){
	
		$.ajax('practicas', {
			    type: 'POST',  
			    data: { option:"create" },
				success: function(result){
				    
					$clone = $(".copia").clone(true);
					$clone.removeClass("d-none copia");
					$clone.find(".iNombre").val("");
					$clone.find(".colId").text(result);
					$clone.insertAfter(".copia");
					
				},
			});	
	});
});
