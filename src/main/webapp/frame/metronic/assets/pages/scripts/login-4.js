var Login = function () {

	var handleLogin = function() {
		$('.login-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                /*captcha: {
	                	required: true
	                },*/
	                remember: {
	                    required: false
	                }
	            },

	            messages: {
	                username: {
	                    required: "用户名不能为空."
	                },
	                password: {
	                    required: "密码不能为空."
	                }/*,
	                captcha: {
	                	required: "验证码不能为空."
	                }*/
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                //$('.alert-danger', $('.login-form')).show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
//	                form.submit();
	        		var username = $("input[name='username']").val();
	    			var password = $("input[name='password']").val();
	    			var captcha = $("input[name='captcha']").val();
	    			$.ajax({
	    				url : '/doLogin',
	    				data : {'username':username,'password':base64encode(password),'captcha':base64encode(captcha)},
	    				dataType : 'json',
	    				type : 'post',
	    				success : function(json){
	    					if(json.success){
	    						top.location.href = "/admin";
	    					}else{
	    		            	$("#captchaImg")[0].src = "/captchaImg?rand="+Math.random(); // 重新加载验证码
	    						$.bootstrapGrowl(json.msg, {
    							  ele: 'body', // which element to append to
    							  type: 'info', // (null, 'info', 'error', 'success')
    							  offset: {from: 'bottom', amount: 20}, // 'top', or 'bottom'
    							  align: 'right', // ('left', 'right', or 'center')
    							  width: 250, // (integer, or 'auto')
    							  delay: 2000,
    							  allow_dismiss: true,
    							  stackup_spacing: 10 // spacing between consecutively stacked growls.
    							});
	    					}
	    				}
	    			});
	            }
	        });

	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                    $('.login-form').submit();
	                }
	                return false;
	            }
	        });
	}

    
    return {
        //main function to initiate the module
        init: function () {
        	
            handleLogin();
            // init background slide images
		    $.backstretch([
		        "/frame/metronic/assets/pages/media/bg/1.jpg",
		        "/frame/metronic/assets/pages/media/bg/2.jpg",
		        "/frame/metronic/assets/pages/media/bg/3.jpg",
		        "/frame/metronic/assets/pages/media/bg/4.jpg"
		        ], {
		          fade: 1000,
		          duration: 8000
		    	}
        	);
        }
    };

}();

jQuery(document).ready(function() {
    Login.init();
    
    $("#captchaImg").click(function(){
    	this.src = "/captchaImg?rand="+Math.random();
    });
});