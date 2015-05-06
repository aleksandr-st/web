$(document).ready(function(){
	function checkId(){
		var id = $('#id').val();
		if (((id === "") || (id == undefined))) {
			id = $('#id').text();
			if (((id === "") || (id == undefined))) {
				return false
			};
		};
		return true;
	}
	
    $("#contactData").click(function(){
        $("#contactDataTab").removeClass("hiddenTab");
        $("#contactHobbiesTab").addClass("hiddenTab");
        $("#contactPlacesTab").addClass("hiddenTab");
        $("#contactFriendsTab").addClass("hiddenTab");
        $("#contactDetailsTab").addClass("hiddenTab");
        
        $("#contactData").addClass("activeTab");
        $("#contactHobbies").removeClass("activeTab");
        $("#contactPlaces").removeClass("activeTab");
        $("#contactFriends").removeClass("activeTab");
        $("#contactDetails").removeClass("activeTab");
    });

    $("#contactHobbies").click(function(){
    	if (checkId() == false){
    		return false;
    	}
        $("#contactDataTab").addClass("hiddenTab");
        $("#contactHobbiesTab").removeClass("hiddenTab");
        $("#contactPlacesTab").addClass("hiddenTab");
        $("#contactFriendsTab").addClass("hiddenTab");
        $("#contactDetailsTab").addClass("hiddenTab");
        
        $("#contactData").removeClass("activeTab");
        $("#contactHobbies").addClass("activeTab");
        $("#contactPlaces").removeClass("activeTab");
        $("#contactFriends").removeClass("activeTab");
        $("#contactDetails").removeClass("activeTab");
    });

    $("#contactPlaces").click(function(){
    	if (checkId() == false){
    		return false;
    	}
        $("#contactDataTab").addClass("hiddenTab");
        $("#contactHobbiesTab").addClass("hiddenTab");
        $("#contactPlacesTab").removeClass("hiddenTab");
        $("#contactFriendsTab").addClass("hiddenTab");
        $("#contactDetailsTab").addClass("hiddenTab");
        
        $("#contactData").removeClass("activeTab");
        $("#contactHobbies").removeClass("activeTab");
        $("#contactPlaces").addClass("activeTab");
        $("#contactFriends").removeClass("activeTab");
        $("#contactDetails").removeClass("activeTab");
    });

    $("#contactFriends").click(function(){
    	if (checkId() == false){
    		return false;
    	}
        $("#contactDataTab").addClass("hiddenTab");
        $("#contactHobbiesTab").addClass("hiddenTab");
        $("#contactPlacesTab").addClass("hiddenTab");
        $("#contactFriendsTab").removeClass("hiddenTab");
        $("#contactDetailsTab").addClass("hiddenTab");
        
        $("#contactData").removeClass("activeTab");
        $("#contactHobbies").removeClass("activeTab");
        $("#contactPlaces").removeClass("activeTab");
        $("#contactFriends").addClass("activeTab");
        $("#contactDetails").removeClass("activeTab");
    });

    $("#contactDetails").click(function(){
    	if (checkId() == false){
    		return false;
    	}
        $("#contactDataTab").addClass("hiddenTab");
        $("#contactHobbiesTab").addClass("hiddenTab");
        $("#contactPlacesTab").addClass("hiddenTab");
        $("#contactFriendsTab").addClass("hiddenTab");
        $("#contactDetailsTab").removeClass("hiddenTab");
        
        $("#contactData").removeClass("activeTab");
        $("#contactHobbies").removeClass("activeTab");
        $("#contactPlaces").removeClass("activeTab");
        $("#contactFriends").removeClass("activeTab");
        $("#contactDetails").addClass("activeTab");
    });

    $("#contactSubmit").click(function(event){
    	var firstName = $('#firstName').val();
    	var lastName = $('#lastName').val();
    	var birthDate = $('#birthDate').val();
    	var version = $('#version').val();
    	var id = $('#id').val();
    	var jsonUrl = $("#contactUpdateForm").attr("action");
		if (((id === "") || (id == undefined))) {
			jsonUrl += "?jsonCreate";
		} else {
			jsonUrl += "/" + id +"?jsonUpdate"
		};
    	var hadErrors = false;
    	if ((firstName === "") || (firstName == undefined)) {
    		alert("First name is required!");
    		hadErrors = true;
    	};
    	if ((lastName === "") || (lastName == undefined)) {
    		alert("Last name is required!");
    		hadErrors = true;
    	};
    	if ((birthDate === "") || (birthDate == undefined)) {
    		alert("Birth date is required!");
    		hadErrors = true;
    	};
    	if (hadErrors){
    		return false;
    	}
    	var json = {id:id,version:version,firstName:firstName,lastName:lastName,
    			birthDate:birthDate};
    	$.ajax({
    		url: jsonUrl,
    		data: JSON.stringify(json),
    		type: "POST",
    		
    		beforeSend: function(xhr){
    			xhr.setRequestHeader("Accept","application/json");
    			xhr.setRequestHeader("Content-Type","application/json");
    		},
    		success: function(contactJson){
    	    	$('#firstName').val(contactJson.firstName);
    	    	$('#lastName').val(contactJson.lastName);
    	    	var strBirthDate = contactJson.birthDate; 
    	    	$('#birthDate').val(strBirthDate.substring(0,10));
    	    	$('#version').val(contactJson.version);
    	    	$('#id').val(contactJson.id);

    	    	$('#operationResultMessage').addClass("success");
    	    	$('#operationResultMessage').html('Contact saved successful!');
    	    	
    	    	$('#viewContact').attr('href',$('#contactUpdateForm').attr('action')+'/'+contactJson.id);
    		}
    	});
    	
    	event.preventDefault();
    });

    $("#MoveRight,#MoveLeft").click(function(event){
        var id = $(event.target).attr("id");
        var selectFrom = id == "MoveRight" ? "#usedHobbies" : "#unusedHobbies";
        var moveTo = id == "MoveRight" ? "#unusedHobbies" : "#usedHobbies";

        var selectedItems = $(selectFrom + " :selected").toArray();
        $(moveTo).append(selectedItems);
        selectedItems.remove;
    });
    
    $("#MovePlaceRight,#MovePlaceLeft").click(function(event){
        var id = $(event.target).attr("id");
        var selectFrom = id == "MovePlaceRight" ? "#usedPlaces" : "#unusedPlaces";
        var moveTo = id == "MovePlaceRight" ? "#unusedPlaces" : "#usedPlaces";

        var selectedItems = $(selectFrom + " :selected").toArray();
        $(moveTo).append(selectedItems);
        selectedItems.remove;
    });
    
    $('#contactHobbiesSave').click(function(event){
    	var version = $('#version').val();
    	var id = $('#id').val();
    	var jsonUrl = $("#contactUpdateForm").attr("action")+"/" + id +"?jsonHobbyUpdate";
    	var hobbies = $('#usedHobbies option').map(function(){
    		return this.value;
        }).get();
    	var json = {id:id,version:version,hobbies:hobbies};
    	$.ajax({
    		url: jsonUrl,
    		data: JSON.stringify(json),
    		type: "POST",
    		
    		beforeSend: function(xhr){
    			xhr.setRequestHeader("Accept","application/json");
    			xhr.setRequestHeader("Content-Type","application/json");
    		},
    		success: function(contactJson){
    	    	$('#firstName').val(contactJson.firstName);
    	    	$('#lastName').val(contactJson.lastName);
    	    	var strBirthDate = contactJson.birthDate; 
    	    	$('#birthDate').val(strBirthDate.substring(0,10));
    	    	$('#version').val(contactJson.version);
    	    	$('#id').val(contactJson.id);
    	    	var hobbiesReseived = contactJson.hobbies;

    	    	$('#saveHobbyResult').addClass("success");
    	    	$('#saveHobbyResult').html('Contact saved successful!');
     		}
    	});
    });
    
    $('#contactPlacesSave').click(function(event){
    	var version = $('#version').val();
    	var id = $('#id').val();
    	var jsonUrl = $("#contactUpdateForm").attr("action")+"/" + id +"?jsonPlaceUpdate";
    	var places = $('#usedPlaces option').map(function(){
    		return this.value;
        }).get();
    	var json = {id:id,version:version,places:places};
    	$.ajax({
    		url: jsonUrl,
    		data: JSON.stringify(json),
    		type: "POST",
    		
    		beforeSend: function(xhr){
    			xhr.setRequestHeader("Accept","application/json");
    			xhr.setRequestHeader("Content-Type","application/json");
    		},
    		success: function(contactJson){
    	    	$('#firstName').val(contactJson.firstName);
    	    	$('#lastName').val(contactJson.lastName);
    	    	var strBirthDate = contactJson.birthDate; 
    	    	$('#birthDate').val(strBirthDate.substring(0,10));
    	    	$('#version').val(contactJson.version);
    	    	$('#id').val(contactJson.id);
    	    	var placesReseived = contactJson.places;

    	    	$('#savePlaceResult').addClass("success");
    	    	$('#savePlaceResult').html('Contact saved successful!');
     		}
    	});
    });
    
    $('body').on('click', '.d_del', function(event) {
    	var version = $('#version').val();
    	var id = $('#id').val();
    	var json = {id:id,version:version};

    	$.ajax({
			url : $(event.target).attr("href"),
    		data: JSON.stringify(json),
			type : "DELETE",
	
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},
	
			success : function(contactJson) {
    	    	$('#firstName').val(contactJson.firstName);
    	    	$('#lastName').val(contactJson.lastName);
    	    	var strBirthDate = contactJson.birthDate; 
    	    	$('#birthDate').val(strBirthDate.substring(0,10));
    	    	$('#version').val(contactJson.version);
    	    	$('#id').val(contactJson.id);
				var rowToDelete = $(event.target).closest("tr");
				rowToDelete.remove();
				
    	    	$('#changeDetailResult').addClass("success");
    	    	$('#changeDetailResult').html('Detail was deleted successful!');
			}
		});

    	event.preventDefault();
    });

    $('body').on('click', '.f_del', function(event) {
    	var version = $('#version').val();
    	var id = $('#id').val();
    	var json = {id:id,version:version};

    	$.ajax({
			url : $(event.target).attr("href"),
    		data: JSON.stringify(json),
			type : "DELETE",
	
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},
	
			success : function(contactJson) {
    	    	$('#firstName').val(contactJson.firstName);
    	    	$('#lastName').val(contactJson.lastName);
    	    	var strBirthDate = contactJson.birthDate; 
    	    	$('#birthDate').val(strBirthDate.substring(0,10));
    	    	$('#version').val(contactJson.version);
    	    	$('#id').val(contactJson.id);
				var rowToDelete = $(event.target).closest("tr");
				rowToDelete.remove();
				
    	    	$('#changeFriendsResult').addClass("success");
    	    	$('#changeFriendsResult').html('Friend was deleted successful!');
			}
		});

    	event.preventDefault();
    });

    $('body').on('click', '.f_add', function(event) {
    	var version = $('#version').val();
    	var id = $('#id').val();
    	var json = {id:id,version:version};

    	$.ajax({
			url : $(event.target).attr("href"),
    		data: JSON.stringify(json),
			type : "POST",
	
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},
	
			success : function(contactJson) {
    	    	$('#firstName').val(contactJson.firstName);
    	    	$('#lastName').val(contactJson.lastName);
    	    	var strBirthDate = contactJson.birthDate; 
    	    	$('#birthDate').val(strBirthDate.substring(0,10));
    	    	$('#version').val(contactJson.version);
    	    	$('#id').val(contactJson.id);
				var rowToDelete = $(event.target).closest("tr");
				rowToDelete.remove();
				
    	        $('#friendsTable tr').each(function (i, row) {
    	        	var $row = $(row);
    	        	if (i > 0){
    	               $row.remove(); 
    	           }
    	        });
    	        $.each(contactJson.friends, function(i, obj){
        	    	$('#friendsTable').append('<tr><td>'+obj.firstName
        	    			+'</td><td>'+obj.lastName
        	    			+'</td><td><a href="'+$('#contactUpdateForm').attr('action')
        	    			+'/'+$('#id').val()+'?deleteFriend='+obj.id
        	    			+'" class="f_del">Delete</a></td></tr>');
    	    	});

    	        $('#changeFriendsResult').addClass("success");
    	    	$('#changeFriendsResult').html('Friend was added successful!');
			}
		});

    	event.preventDefault();
    });

    $('#findFriends').click(function(event){
    	var version = $('#version').val();
    	var id = $('#id').val();
    	var firstName = $("#findFriendsText").val();
    	var jsonUrl = $("#contactUpdateForm").attr("action")+"/" + id +"?findFriends="+firstName;

    	var json = {id:id,version:version};
    	$.ajax({
    		url: jsonUrl,
    		data: JSON.stringify(json),
    		type: "GET",
    		
    		beforeSend: function(xhr){
    			xhr.setRequestHeader("Accept","application/json");
    			xhr.setRequestHeader("Content-Type","application/json");
    		},
    		success: function(contactsJson){
    	        $('#foundFriendsTable tr').each(function (i, row) {
    	        	var $row = $(row);
    	        	if (i > 0){
    	               $row.remove(); 
    	           }
    	        });
    	        var foundContacts = 0;
    	        $.each(contactsJson, function(i, obj){
        	    	$('#foundFriendsTable').append('<tr><td>'+obj.firstName
        	    			+'</td><td>'+obj.lastName
        	    			+'</td><td><a href="'+$('#contactUpdateForm').attr('action')
        	    			+'/'+$('#id').val()+'?addFriend='+obj.id
        	    			+'" class="f_add">Add</a></td></tr>');
        	    	foundContacts++;
    	    	});
 
    	    	$('#searchFriendsResult').html("<span>По запросу найдено "+foundContacts+" контактов</span>");
     		}
    	});
    });

    $('#newDetailSave').click(function(event){
    	var version = $('#version').val();
    	var id = $('#id').val();
    	var jsonUrl = $("#contactUpdateForm").attr("action")+"/" + id +"?AddDetail";
    	var type = $("#newDetailType").val();
        var data = $('#newDetailData').val();

    	var json = {id:id,version:version,contactDetails:[{type:type,data:data}]};
    	$.ajax({
    		url: jsonUrl,
    		data: JSON.stringify(json),
    		type: "POST",
    		
    		beforeSend: function(xhr){
    			xhr.setRequestHeader("Accept","application/json");
    			xhr.setRequestHeader("Content-Type","application/json");
    		},
    		success: function(contactJson){
    	    	$('#firstName').val(contactJson.firstName);
    	    	$('#lastName').val(contactJson.lastName);
    	    	var strBirthDate = contactJson.birthDate; 
    	    	$('#birthDate').val(strBirthDate.substring(0,10));
    	    	$('#version').val(contactJson.version);
    	    	$('#id').val(contactJson.id);
    	    	var type = $("#newDetailType").val();
    	        var data = $('#newDetailData').val();
    	    	var detailId = 0;
    	    	var details = contactJson.contactDetails;
    	    	
    	    	$.each(contactJson.contactDetails, function(i, obj){
    	    	    if ((obj.type == type) & (obj.data == data)){ 
    	    	    	if (obj.id > detailId){
    	    	    		detailId = obj.id;
    	    	    	}
    	    	    }
    	    	});
 
    	    	$('#detailsTable').append('<tr><td>'+$("#newDetailType").val()
    	    			+'</td><td>'+$('#newDetailData').val()
    	    			+'</td><td><a href="'+$('#contactUpdateForm').attr('action')
    	    			+'/'+contactJson.id+'?deleteDetail='+detailId
    	    			+'" class="d_del">Delete</a></td></tr>');
    	    	$('#changeDetailResult').addClass("success");
    	    	$('#changeDetailResult').html('Contact saved successful!');
     		}
    	});
    });

	$('body').on('click','.c_del',(function(event) {
		var deleteId = $(event.target).val();
		var json = {id:deleteId};
		
		$.ajax({
			url : $(event.target).attr("href"),
			data : JSON.stringify(json),
			type : "POST",
		
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},

			success : function(responseText) {
				var respContent = "";
				var rowToDelete = $(event.target).closest("tr");

				rowToDelete.remove();

				respContent += "<span class='success'>"+responseText.answer+"</span>";

				$("#deleteResult").html(respContent);
			}
		});

		event.preventDefault();
	})
	);
});