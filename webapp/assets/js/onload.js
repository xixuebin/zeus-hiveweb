 function showLoading(){
	  $('body').addClass("masks");
	  $(".row-fluid1").show();
 }
 function clearLoading(){
	 $('body').removeClass("masks");
	 $(".row-fluid1").hide();
 }	
$(function (){
		$(".main-menu li a").click(function(){
			showLoading();
			$(".main-menu li").removeClass("active");
			$(this).parent().addClass("active");
		})
	});
