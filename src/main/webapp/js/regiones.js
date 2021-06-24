$(document).ready(function() {
   $('.iID').change( function(){
	
		var old = $(this).val();
		var isValid = true;
		$('.iID').not($(this)).each(function() {
		   var element = $(this);
		   if (element.val() == old) {
		       isValid = false;
		   }
		});
		if (isValid == true){
			$.ajax('regiones', {
			    type: 'POST',  
			    data: { option: "c_id", id: $(this).val(), old: $(this).attr("oldvalue") },
			});
		}
		else{
			$(this).val($(this).attr("oldvalue"));
			window.alert("La id de la region esta repetida. Intentalo con una id diferente.");
		}
	});
});

$(document).ready(function() {
	$('.iID').click(function(){
	  $(this).attr("oldvalue", $(this).val());
	});
});

$(document).ready(function() {
   $('.iNombre').change( function(){
		$.ajax('regiones', {
	    type: 'POST',  
	    data: { option:"c_nombre", nombre: $(this).val(), id: $(this).parent().siblings().find(".iID").eq(0).val() }, 
	});
	});
});

$(document).ready(function() {
   $('.del_reg').click( function(){
		if (confirm("Seguro que quieres borrar la region seleccionada?")) {
	        $(this).parent().parent().remove();
			$.ajax('regiones', {
			    type: 'POST',  
			    data: { option: "delete", id: $(this).parent().siblings().find(".iID").eq(0).val()},
			});
	    }
	    return false;

	});
});

$(document).ready(function() {
   $('.add_reg').click( function(){
		
		var isValid = true;
		$(".iID").not($(".copia").find(".iID")).each(function() {
		   var element = $(this);
		   if (element.val() == "") {
		       isValid = false;
		   }
		});
		if (isValid == true){
			$.ajax('regiones', {
			    type: 'POST',  
			    data: { option:"create" },
				success: function(){
				    
					$clone = $(".copia").clone(true);
					$clone.removeClass("d-none copia");
					$clone.find(".iID").val("");
					$clone.insertAfter(".copia");
					
				},
			});
		}
		else{
			$(this).val($(this).attr("oldvalue"));
			window.alert("Para crear otra region tienes que completar los datos de la anterior.");			
		}	
	});
});


$(document).ready(function() {
   $('.ddPais').change( function() {

	$.ajax('regiones', {
	    type: 'POST',  
	    data: { option: "c_pais", pais: $(this).val(), id: $(this).parent().siblings().find(".iID").eq(0).val() }, 
	});
    });
});
