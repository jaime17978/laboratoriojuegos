$(document).ready(function() {
   $('.iNombre').change( function(){
	
		$.ajax('perfiles', {
			    type: 'POST',  
			    data: { option: "c_nombre", nombre: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
			
	});
});

$(document).ready(function() {
   $('.del_pr').click( function(){
		if (confirm("Seguro que quieres borrar el perfil seleccionado?")) {
	        $(this).parent().parent().remove();
			$.ajax('perfiles', {
			    type: 'POST',  
			    data: { option: "delete", nombre: $(this).parent().siblings().find(".iNombre").eq(0).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	    }
	    return false;

	});
});

$(document).ready(function() {
	
   $('.box_act').change( function(){	
		$.ajax('perfiles', {
			    type: 'POST',  
			    data: { option: "c_activo", activo: $(this).prop('checked'), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	});
});

$(document).ready(function() {
   $('.add_pr').click( function(){
		
		var isValid = true;
		$(".iNombre").not($(".copia").find(".iNombre")).each(function() {
		   var element = $(this);
		   if (element.val() == "") {
		       isValid = false;
		   }
		});
		if (isValid == true){
			$.ajax('perfiles', {
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
