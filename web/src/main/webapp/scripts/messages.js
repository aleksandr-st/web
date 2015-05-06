$(document).ready(function(){
	$("#scrollTable").animate({ scrollTop: $('#scrollTable')[0].scrollHeight}, 100);
	
	function newIncomeCheck(){
    	var id = $('#contactId').val();
    	var recId = $('#recipientId').val();
    	var pathUrl = window.location.pathname;
    	var jsonUrl = window.location.pathname + "?checkNewMes";
    	var message = "";
    	var lastId = $('#lastId').val();

    	var json = {content:message,from:{id:id},to:{id:recId},id:lastId};
    	$.ajax({
    		url: jsonUrl,
    		data: JSON.stringify(json),
    		type: "POST",
    		
    		beforeSend: function(xhr){
    			xhr.setRequestHeader("Accept","application/json");
    			xhr.setRequestHeader("Content-Type","application/json");
    		},
    		success: function(messagesJson){
    	    	$.each(messagesJson, function(i, obj){
        	    	$('#messagesTable').append('<tr style="text-align: right"><td><b>'
        	    			+ obj.content + "</b><p />" 
        	    			+$.date(obj.date) + '</td></tr>');
        	    	$('#lastId').val(obj.id);
        	    	$("#scrollTable").animate({ scrollTop: $('#scrollTable')[0].scrollHeight}, 1000);
    	    	});
     		}
    	});
	}
	
	window.setInterval(newIncomeCheck, 10000);

	$.date = function(dateObject) {
	    var d = new Date(dateObject);
	    var day = d.getDate();
	    var month = d.getMonth() + 1;
	    var hours = d.getHours();
	    var minutes = d.getMinutes();
	    var seconds = d.getSeconds();
	    var year = d.getFullYear();
	    if (day < 10) {
	        day = "0" + day;
	    };
	    if (month < 10) {
	        month = "0" + month;
	    }
	    if (hours < 10) {
	    	hours = "0" + hours;
	    };
	    if (minutes < 10) {
	    	minutes = "0" + minutes;
	    };
	    if (seconds < 10) {
	    	seconds = "0" + seconds;
	    };
	    var date = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;

	    return date;
	};

	function sendMessage(event){
    	var id = $('#contactId').val();
    	var recId = $('#recipientId').val();
    	var pathUrl = window.location.pathname;
    	var jsonUrl = window.location.pathname + "?addMessage";
    	var message = $("#messageText").val();
    	if ((message == undefined) || (message === "")){
    		alert("Пустые сообщения не допускаются!");
    		return;
    	}

    	var json = {content:message,from:{id:id},to:{id:recId}};
    	$.ajax({
    		url: jsonUrl,
    		data: JSON.stringify(json),
    		type: "POST",
    		
    		beforeSend: function(xhr){
    			xhr.setRequestHeader("Accept","application/json");
    			xhr.setRequestHeader("Content-Type","application/json");
    		},
    		success: function(messageJson){
    	    	$('#messagesTable').append('<tr style="text-align: left"><td><b>'
    	    			+ messageJson.content + "</b><p />" 
    	    			+$.date(messageJson.date) + '</td></tr>');
    	    	$('#messageText').val("");
    	    	$("#scrollTable").animate({ scrollTop: $('#scrollTable')[0].scrollHeight}, 1000);
     		}
    	});
    }
	$('#sendMessage').click(sendMessage);
	$('#messageText').keyup(function(e){
		if(e.keyCode==13){
			sendMessage();
		}
	});
});
