function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}
// function init () { 
//      $(".ui-growl-image-info").parent().parent().addClass("g-info");
//      $(".ui-growl-image-warn").parent().parent().addClass("g-warn");
//      $(".ui-growl-image-error").parent().parent().addClass("g-error");
//      $(".ui-growl-image-fatal").parent().parent().addClass("g-fatal");
//   }

$(function() {

    $('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});

});