$(document).ready(function() {
   $('.iNombre').change( function(){
	
		$.ajax('perfiles_menus', {
			    type: 'POST',  
			    data: { option: "c_nombre", nombre: $(this).val(), perfil: $(this).parent().siblings(".colPerfil").eq(0).html(), menu: $(this).parent().siblings(".colMenu").eq(0).html()},
			});
			
	});
});

$(document).ready(function() {
   $('.del_pm').click( function(){
		if (confirm("Seguro que quieres borrar el permiso seleccionado?")) {
	        $(this).parent().parent().remove();
			$.ajax('perfiles_menus', {
			    type: 'POST',  
			    data: { option: "delete", perfil: $(this).parent().siblings(".colPerfil").eq(0).html(), menu: $(this).parent().siblings(".colMenu").eq(0).html()},
			});
	    }
	    return false;

	});
});
