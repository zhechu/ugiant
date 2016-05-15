<#include "/pages/layout/blank_layout.ftl"/> 
<@layout>

	<link href="/frame/metronic/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link href="/frame/metronic/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
	<link href="/frame/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="/frame/metronic/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
	<link href="/frame/metronic/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN THEME GLOBAL STYLES -->
	<link href="/frame/metronic/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
	<link href="/frame/metronic/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
	<!-- END THEME GLOBAL STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES -->
	<link href="/frame/metronic/assets/pages/css/login-4.min.css" rel="stylesheet" type="text/css" />
	<!-- END PAGE LEVEL STYLES -->
	<!-- BEGIN THEME LAYOUT STYLES -->
	<!-- END THEME LAYOUT STYLES -->
	<link rel="shortcut icon" href="favicon.ico" />

	<div class="login">
		<!-- BEGIN LOGO -->
		<div class="logo">
			<!--  
		    <a href="index.html">
		        <img src="/frame/metronic/assets/pages/img/logo-big.png" alt="" /> </a>
		        -->
		</div>
		<!-- END LOGO -->
		<!-- BEGIN LOGIN -->
		<div class="content">
		    <!-- BEGIN LOGIN FORM -->
		    <form class="login-form" action="/doLogin" method="post">
		        <h3 class="form-title">系统登录</h3>
		        <!--  
		        <div class="alert alert-danger display-hide">
		            <button class="close" data-close="alert"></button>
		            <span>请输入您的用户名和密码. </span>
		        </div>
		        -->
		        <div class="form-group">
		            <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
		            <label class="control-label visible-ie8 visible-ie9">用户名</label>
		            <div class="input-icon">
		                <i class="fa fa-user"></i>
		                <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" name="username" /> </div>
		        </div>
		        <div class="form-group">
		            <label class="control-label visible-ie8 visible-ie9">密码</label>
		            <div class="input-icon">
		                <i class="fa fa-lock"></i>
		                <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password" /> </div>
		        </div>
		        <div class="form-group">
		            <label class="control-label visible-ie8 visible-ie9">验证码</label>
		            <div class="input-icon">
		                <i class="fa fa-lock"></i>
		                <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="验证码" name="captcha" /> 
		            </div>
		            <img id="captchaImg" alt="点击换一张" title="点击换一张" style="cursor:pointer;" src="/captchaImg?rand="+Math.random()>
		        </div>
		        <div class="form-actions">
		            <label class="checkbox">
		                <input type="checkbox" name="remember" value="1" /> 记住密码</label>
		            <button type="submit" class="btn green pull-right"> 登录 </button>
		        </div>
		    </form>
		    <!-- END LOGIN FORM -->
		</div>
		<!-- END LOGIN -->
		<!-- BEGIN COPYRIGHT -->
		     <div class="copyright"> 
		    	<div>潮州市查违信息管理系统</div> 
				<div>技术支持：广东云能计算机科技有限公司</div>
		     </div>
	</div>
	
	<!-- END COPYRIGHT -->
	<!--[if lt IE 9]>
	<script src="/frame/metronic/assets/global/plugins/respond.min.js"></script>
	<script src="/frame/metronic/assets/global/plugins/excanvas.min.js"></script> 
	<![endif]-->
	<!-- BEGIN CORE PLUGINS -->
	<script src="/frame/metronic/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
	<script src="/frame/metronic/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="/frame/metronic/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
	<script src="/frame/metronic/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="/frame/metronic/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="/frame/metronic/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="/frame/metronic/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="/frame/metronic/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="/frame/metronic/assets/global/plugins/bootstrap-growl/jquery.bootstrap-growl.min.js" type="text/javascript"></script>
	<script src="/frame/metronic/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="/frame/metronic/assets/global/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN THEME GLOBAL SCRIPTS -->
	<script src="/frame/metronic/assets/global/scripts/app.min.js" type="text/javascript"></script>
	<!-- END THEME GLOBAL SCRIPTS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript" src="/frame/base/js/base64.js"></script>  
	<script src="/frame/metronic/assets/pages/scripts/login-4.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<!-- END THEME LAYOUT SCRIPTS -->

</@layout>