
/*function get_hostname(url) {
    var m = url.match(/^http:\/\/[^/]+/);
    return m ? m[0] : null;
}*/
function checkTerms(){
	if(!document.getElementById("flag1").checked)
	{
	    alert('You must agree to the terms first.');
	    return false;
	}
	else {
		return true;
	}
}
$(document).ready( function() {
	
	if (Modernizr.touch) {
		$(".radio-options").bind("click", function(event) {
			if (!($(this).parent('.radio-container').hasClass("active"))) {
				$(this).parent('.radio-container').addClass("active");
				event.stopPropagation();
			}
		});
		$(".toggle").bind("click", function() {
			$(this).parents('.radio-container').removeClass("active");
			return false;
		});
	}
	
			var noOfMonthsVal = 6;
			var maxNumberofDeliveryVal = 0;
			if($("#pincode").val() == 0 ){
				$("#pincode").val("");
			}
			//var phoneNumberVal = $("#phoneNumber").val();
			if($("#phoneNumber").val() == 0 ){
				$("#phoneNumber").val("");
			}
			
			$("img").on(
					"error",
					function() {
						$(this).unbind("error").attr("src",
								document.getElementById("noImageLoc").value);
					});

			var $noOfBooksRadioObj = $("input:radio[name=noOfBooksRadio]");
			$noOfBooksRadioObj.on("change", function() {
				console.log("coming here noOfBooksRadio >>> ");
				noOfMonthsVal = $("input:radio[name=noOfMonthsRadio]:checked")
						.val();
				maxNumberofDeliveryVal = $(this).val();
				ajaxGet();
				 console.log( "$noOfBooksRadioObj: " + $(this).val() );
				// or
				// alert( "$noOfBooksRadioObj: " + $(this).val() );
			});

			var $noOfMonthsRadioObj = $("input:radio[name=noOfMonthsRadio]");
			$noOfMonthsRadioObj.on("change", function() {
				noOfMonthsVal = $(this).val();
				console.log("coming here noOfMonthsRadio >>> ");
				maxNumberofDeliveryVal = $(
						"input:radio[name=noOfBooksRadio]:checked").val();
				ajaxGet();
				// console.log( "$noOfBooksRadioObj: " + $(this).val() );
				// or
				// alert( "$noOfBooksRadioObj: " + $(this).val() );
			});

			function ajaxGet() {
				$.ajax({
					type : "GET",
					data : {
						"noofMonths" : noOfMonthsVal,
						"maxNumberofDelivery" : maxNumberofDeliveryVal
					},
					url : window.location + "/getSelectedSubscriptionDetails",
					success : function(result) {
						$('#subcAmount').text(result.amount);
						var totalBooks = result.noofMonths
								* result.maxNumberofDelivery;
						totalBooks = totalBooks * result.maxNumberofBooks;
						var totalDeliveries = result.noofMonths
								* result.maxNumberofDelivery;
						$('#subcId').val(result.subcId);
						$('#totalBooks').text(totalBooks + ' Books ');
						$('#totalDeliveries').text(
								totalDeliveries + ' free deliveries ');

						 console.log(result.amount);
						 console.log(result.amount);
						 console.log(result);
					},
					error : function(e) {

						console.log("ERROR: ", e);
					}
				});
			}

		});



//});

function updateCartNumber() {
	$.ajax({
		type : "GET",
		
		url :"/getCartNumber",
		success : function(result) {
			$("#cart-number2").html(result);
			$("#cart-number3").html(result);
//			 console.log(result);
			// console.log(result);
			// console.log(result);
		},
		error : function(e) {

			console.log("ERROR: ", e);
		}
	});
}

function saveRating(bookId, userId, rating) {
//	var div = document.getElementById('ratingDiv');
//	console.log("enter the ratings point");
//	alert("comming here");
//    alert(bookId);
//    alert(userId);
//    alert(rating);
	$
			.ajax({
				type : "POST",
				data : {
					"bookId" : bookId,
					"userId" : userId,
					"rating" : rating
				},
				url : window.location + "/addrating",
				success : function(data, status) {
//					alert(data.bookId);
					$("#ratingDiv_"+data.bookId).empty();	
					for (i = 1; i <= data.ratings; i++) {
//						div.innerHTML += "<i  class=\"fas fa-star\" style=\"color: #e34630;\"></i>";			
						$("#ratingDiv_"+data.bookId)
								.append("<i  class=\"fas fa-star\" style=\"color: #e34630;\"></i>");
					}
//				   	location.reload();
					// console.log(data);
					// console.log(status);
				},
				error : function(e) {

					console.log("ERROR: ", e);
				}
			});
}

function addBookToCart(bookId) {
	//alert(bookId);
   
	$
			.ajax({
				type : "POST",
				data : {
					"bookId" : bookId
				},
				url : "/addtocart",
				success : function(data, status,textStatus, xhr) {
					//console.log("Status" + xhr.status);
					/*switch (xhr.status) {
			        case 403:
			        	var url = window.location + "/login";
						window.location.href=url;
			             // Take action, referencing xhr.responseText as needed.
			    }*/
					
					if(status == 403 ){
						var url =  "/login";
						window.location.href=url;
					} else if(status == 409 ){
						var url =  + "/pricing";
						window.location.href=url;
					} else if(status == 416 ){
						var url =  + "/cart/";
						window.location.href=url;	
					} else if(status == 402 ){
						var url =  + "/dashboard";
						window.location.href=url;
					}

					updateCartNumber();
//					$("#rentDiv").replaceWith("Hello world!");
				},
				statusCode: {
				    403: function() {
				       // Only if your server returns a 403 status code can it come in this block. :-)
				        //alert("Username already exist"); $(location).attr('host')  +
				    	var url =  "/login";
						window.location.href=url;
				    },
				    409:function(){
				    	var url =  "/pricing";
						window.location.href=url;
				    },
				    416:function(){
				    	var url =  "/cart/";
						window.location.href=url;
				    },
				    402:function(){
				    	var url =  "/dashboard";
						window.location.href=url;
				    }
				},
				error : function(e) {

					console.log("ERROR: ", e);
				}
			});
}
function addBookToCartBookDetailsPage(bookId) {
	   //alert(bookId);
	$
			.ajax({
				type : "POST",
				data : {
					"bookId" : bookId
				},
				url : window.location + "/addtocartbookdtails",
				success : function(data, status) {
					updateCartNumber();
//					$("#rentDiv").replaceWith("Hello world!");
				},
				error : function(e) {

					console.log("ERROR: ", e);
				}
			});
}

function showDeliverySlot() {
//	console.log("Hello world!");
	$("#deliverySlotDiv").show();
	$("#confirmDeliveryDiv").show();
	$("#deliverySlotHeadDiv").show();
	//document.getElementById('deliverySlotDiv').style.display='block';
	//document.getElementById('confirmDeliveryDiv').style.display='block';
	//document.getElementById('deliverySlotHeadDiv').style.display='block';	
}
