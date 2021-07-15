$(document).ready(function() {
   $('.iEmail').change( function(){
	
		$.ajax('usuarios', {
			    type: 'POST',  
			    data: { option: "c_email", email: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	
	});
});

$(document).ready(function() {
	
   $('.ddPerfil').change( function(){
		$.ajax('usuarios', {
			    type: 'POST',  
			    data: { option: "c_perfil", perfil: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	});
});

$(document).ready(function() {
	
   $('.ddIdioma').change( function(){
		$.ajax('usuarios', {
			    type: 'POST',  
			    data: { option: "c_idioma", idioma: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	});
});

$(document).ready(function() {
	
   $('.ddUni').change( function(){
		$.ajax('usuarios', {
			    type: 'POST',  
			    data: { option: "c_uni", universidad: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	});
});

$(document).ready(function() {
	
   $('.box_act').change( function(){
	console.log($(this).prop('checked'));
		$.ajax('usuarios', {
			    type: 'POST',  
			    data: { option: "c_activo", activo: $(this).prop('checked'), id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	});
});

$(document).ready(function() {
   $('.del_usu').click( function(){
		if (confirm("Seguro que quieres borrar el usuario seleccionado?")) {
	        $(this).parent().parent().remove();
			$.ajax('usuarios', {
			    type: 'POST',  
			    data: { option: "delete", id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	    }
	    return false;

	});
});

$(document).ready(function() {
   $('.add_usu').click( function(){
	
		$.ajax('usuarios', {
			    type: 'POST',  
			    data: { option:"create" },
				success: function(result){
				    
					$clone = $(".copia").clone(true);
					$clone.removeClass("d-none copia");
					$clone.find(".iEmail").val("");
					$clone.find(".colId").text(result);
					$clone.insertAfter(".copia");
					
				},
			});	
	});
});
