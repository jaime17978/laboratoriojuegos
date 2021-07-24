/**
 * 
 */
function debounce(callback, wait) {
  let timeout;
  return (...args) => {
      clearTimeout(timeout);
      timeout = setTimeout(function () { callback.apply(this, args); }, wait);
  };
}

$(document).ready(function() {
   $('.ddGenero').change( function() {

	$.ajax('m_alumno', {
	    type: 'POST',  
	    data: { genero: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html() }, 
	});
    });
});

$(document).ready(function() {
   $('.ddCurso').change( function() {
	
	$.ajax('m_alumno', {
	    type: 'POST',  
	    data: { curso: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html() }, 
	});
    });
});

$(document).ready(function() {
   $('.iNombre').change( function(){
		$.ajax('m_alumno', {
	    type: 'POST',  
	    data: { nombre: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html() }, 
	});
	});
});

$(document).ready(function() {
   $('.iEdad').change( function(){
   		console.log($(this).parent().siblings(".colId").eq(0).html())
		$.ajax('m_alumno', {
	    type: 'POST',  
	    data: { edad: $(this).val(), id: $(this).parent().siblings(".colId").eq(0).html() }, 
	});
	});
});

$(document).ready(function() {
   $('.add_al').click( function(){
		$.ajax('c_alumno', {
	    type: 'POST',  
	    data: {},
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
   $('.del_al').click( function(){
		
		if (confirm("Seguro que quieres borrar al alumno seleccionado?")) {
	        $(this).parent().parent().remove();
			$.ajax('b_alumno', {
			    type: 'POST',  
			    data: { id: $(this).parent().siblings(".colId").eq(0).html() },
			});
	    }
	    return false;

	});
});
