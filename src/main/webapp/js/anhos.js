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
			$.ajax('anhos', {
			    type: 'POST',  
			    data: { option: "c_nombre", nombre: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
		}
		else{
			$(this).val($(this).attr("oldvalue"));
			window.alert("El nombre de año introducido esta repetido. Intentalo con un nombre diferente.");
			
		}
	});
});

$(document).ready(function() {
	$('.iNombre').click(function(){
	  $(this).attr("oldvalue", $(this).val());
	});
});

$(document).ready(function() {
   $('.del_pa').click( function(){
		if (confirm("Seguro que quieres borrar el año seleccionado?")) {
	        $(this).parent().parent().remove();
			$.ajax('anhos', {
			    type: 'POST',  
			    data: { option: "delete", nombre: $(this).parent().siblings().find(".iNombre").eq(0).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
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
			$.ajax('anhos', {
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
		}
		else{
			$(this).val($(this).attr("oldvalue"));
			window.alert("Para crear otro año tienes que completar los datos del anterior.");
			
		}
		
		
		
		
		
	
		
		
	});
});
