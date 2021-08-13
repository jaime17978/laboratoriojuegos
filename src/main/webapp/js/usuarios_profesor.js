$(document).ready(function() {
   $('.sim_usu').click( function(){
   			$(".simul").html("Simulando al usuario: "+$(this).parent().siblings(".email").eq(0).html());
			$.ajax('usuarios', {
			    type: 'POST',  
			    data: { option: "simulate", id: $(this).parent().siblings(".colId").eq(0).html()},
			});
	    return false;

	});
});
