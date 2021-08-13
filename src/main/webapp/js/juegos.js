$(document).ready(function() {
   $('.iNombre').change( function(){
		$.ajax('juegos', {
	    type: 'POST',  
	    data: { option:"c_nombre", nombre: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html() }, 
	});
	});
});

$(document).ready(function() {
   $('.add_ju').click( function(){
		$.ajax('juegos', {
	    type: 'POST',  
	    data: { option: "create" },
		success: function(result){
			$clone = $(".copia").clone(true);
			$clone.removeClass("d-none copia");
			$clone.find(".colId").text(result);
			$clone.insertAfter(".copia");
		},
	});
	});
});

$(document).ready(function() {
   $('.del_ju').click( function(){
		
		if (confirm("Seguro que quieres borrar el juego seleccionado?")) {
	        $(this).parent().parent().remove();
			$.ajax('juegos', {
			    type: 'POST',  
			    data: { option:"delete", id: $(this).parent().siblings(".colId").eq(0).html() },
			});
	    }
	    return false;

	});
});


