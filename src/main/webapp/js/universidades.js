$(document).ready(function() {
   $('.iNombre').change( function(){
	
		$.ajax('universidades', {
			    type: 'POST',  
			    data: { option: "c_nombre", nombre: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
   $('.iDireccion').change( function(){
	
		$.ajax('universidades', {
			    type: 'POST',  
			    data: { option: "c_direccion", direccion: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
   $('.iLocalidad').change( function(){
	
		$.ajax('universidades', {
			    type: 'POST',  
			    data: { option: "c_localidad", localidad: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
   $('.ddRegion').change( function(){
	
		$.ajax('universidades', {
			    type: 'POST',  
			    data: { option: "c_region", region: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
   $('.del_uni').click( function(){
		if (confirm("Seguro que quieres borrar la universidad seleccionada?")) {
	        $(this).parent().parent().remove();
			$.ajax('universidades', {
			    type: 'POST',  
			    data: { option: "delete", id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	    }
	    return false;

	});
});

$(document).ready(function() {
   $('.add_uni').click( function(){
	
		$.ajax('universidades', {
			    type: 'POST',  
			    data: { option:"create" },
				success: function(result){
				    
					$clone = $(".copia").clone(true);
					$clone.removeClass("d-none copia");
					$clone.find(".iNombre").val("");
					$clone.find(".iDireccion").val("");
					$clone.find(".iLocalidad").val("");
					$clone.find(".colId").text(result);
					$clone.insertAfter(".copia");
					
				},
			});	
	});
});
