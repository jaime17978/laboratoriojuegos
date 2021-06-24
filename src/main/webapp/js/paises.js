$(document).ready(function() {
   $('.iNombre').change( function(){
	
		var old = $(this).val();
		var isValid = true;
		$(".iNombre").not($(this)).each(function() {
		   var element = $(this);
		   if (element.val() == old) {
		       isValid = false;
		   }
		});
		if (isValid == true){
			$.ajax('paises', {
			    type: 'POST',  
			    data: { option: "c_nombre", nombre: $(this).val(), old: $(this).attr("oldvalue") },
			});
		}
		else{
			$(this).val($(this).attr("oldvalue"));
			window.alert("El nombre de pais introducido esta repetido. Intentalo con un nombre diferente.");
			
		}
	});
});

$(document).ready(function() {
	$('.iNombre').click(function(){
	  $(this).attr("oldvalue", $(this).val());
	});
});

$(document).ready(function() {
   $('.iID').change( function(){
		$.ajax('paises', {
	    type: 'POST',  
	    data: { option:"c_id", id: $(this).val(), nombre: $(this).parent().siblings().find(".iNombre").eq(0).val() }, 
	});
	});
});

$(document).ready(function() {
   $('.del_pa').click( function(){
		if (confirm("Seguro que quieres borrar el pais seleccionado?")) {
	        $(this).parent().parent().remove();
			$.ajax('paises', {
			    type: 'POST',  
			    data: { option: "delete", nombre: $(this).parent().siblings().find(".iNombre").eq(0).val()},
			});
	    }
	    return false;

	});
});

$(document).ready(function() {
   $('.add_pa').click( function(){
		
		var isValid = true;
		$(".iNombre").not($(".copia").find(".iNombre")).each(function() {
		   var element = $(this);
		   if (element.val() == "") {
		       isValid = false;
		   }
		});
		if (isValid == true){
			$.ajax('paises', {
			    type: 'POST',  
			    data: { option:"create" },
				success: function(){
				    
					$clone = $(".copia").clone(true);
					$clone.removeClass("d-none copia");
					$clone.find(".iNombre").val("");
					$clone.insertAfter(".copia");
					
				},
			});
		}
		else{
			$(this).val($(this).attr("oldvalue"));
			window.alert("Para crear otro pais tienes que completar los datos del anterior.");
			
		}
		
		
		
		
		
	
		
		
	});
});
