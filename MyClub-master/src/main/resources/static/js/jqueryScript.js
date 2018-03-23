//$(document).ready(function() {
//	
//	// DO GET
//	$.ajax({
//		type : "GET",
//		url : "/showteams3",
//		success: function(result){
//			$.each(result, function(i, team){
//				
//				var customerRow = '<tr>' +
//									'<td>' + team.id + '</td>' +
//									'<td>' + team.teamName.toUpperCase() + '</td>' +
//									'<td>' + team.teamAddress + '</td>' +
//									'<td>' + team.level + '</td>' +
//								
//								  '</tr>';
//				
//				$('#customerTable tbody').append(customerRow);
//			
//	        });
//			
//			$( "#customerTable tbody tr:odd" ).addClass("info");
//			$( "#customerTable tbody tr:even" ).addClass("success");
//		},
//		error : function(e) {
//			alert("ERROR: ", e);
//			console.log("ERROR: ", e);
//		}
//	});
//	
//	// do Filter on View
//	$("#inputFilter").on("keyup", function() {
//	    var inputValue = $(this).val().toLowerCase();
//	    $("#customerTable tr").filter(function() {
//	    	$(this).toggle($(this).text().toLowerCase().indexOf(inputValue) > -1)
//	    });
//	});
//})