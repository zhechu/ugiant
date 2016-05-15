<#macro layout>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>潮州市查违信息管理系统</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <!--
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
        -->
        <link href="/frame/metronic/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="/frame/metronic/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="/frame/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="/frame/metronic/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="/frame/metronic/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="/frame/metronic/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="/frame/metronic/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        
        <!--  BEGIN THEME COMMON STYLES -->
        <link href="/frame/metronic/assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.css" rel="stylesheet" type="text/css" />
        <!-- END THEME COMMON STYLES -->
        
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="/frame/metronic/assets/layouts/layout3/css/layout.min.css" rel="stylesheet" type="text/css" />
        <!--
        <link href="/frame/metronic/assets/layouts/layout3/css/themes/light.min.css" rel="stylesheet" type="text/css" id="style_color" />
        -->
        <link href="/frame/metronic/assets/layouts/layout3/css/themes/kongjun.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="/frame/metronic/assets/layouts/layout3/css/custom.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> 
        
        <!--[if lt IE 9]>
		<script src="/frame/metronic/assets/global/plugins/respond.min.js"></script>
		<script src="/frame/metronic/assets/global/plugins/excanvas.min.js"></script> 
		<![endif]-->
        <!-- BEGIN CORE PLUGINS -->
        <script src="/frame/metronic/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <!--
        <script src="/frame/metronic/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        -->
        <script src="/frame/metronic/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="/frame/metronic/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BRGIN COMMON SCRIPTS -->
        <script src="/frame/metronic/assets/global/plugins/jquery-form/jquery.form.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/bootstrap-confirmation/bootstrap-confirmation.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/bootstrap-growl/jquery.bootstrap-growl.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>
        <!-- BEGIN COMMON SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="/frame/metronic/assets/layouts/layout3/scripts/layout.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/layouts/layout3/scripts/demo.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
        <!-- END THEME LAYOUT SCRIPTS -->
        
        <script src="/frame/base/js/prototype_extend.js" type="text/javascript"></script>
        <script type="text/javascript" src="/frame/base/js/base64.js"></script>
        <script src="/pages/admin/js/admin.js" type="text/javascript"></script>
        <script src="/frame/base/js/validation_message_cn.js" type="text/javascript"></script>
    </head>
	<body class="page-container-bg-solid page-boxed">
		<div class="page-header">
            <div class="page-header-top">
                <div class="container">
                    <div style="float:left;text-align:center;font-size:22px;height:50px;line-height:50px;margin-top:15px;">
	                    	<a href="/">
	                        	<img src="/images/logo.jpg" alt="logo" /> </a>
	                        </a>
                    	潮州市查违信息管理系统
                    </div>
                  
                    <div class="top-menu">
                        <ul class="nav navbar-nav pull-right">
                            <li class="dropdown dropdown-user dropdown-dark">
                            		<#if username??>
                                    <img alt="" class="img-circle" src="/images/user_head.png">
                                    <span class="username username-hide-mobile">
                                    	${username!('')}
                                    	<a href="/admin/logout"><i class="fa fa-power-off"></i>退出</a>
                                    	<a href="/admin/sys_auth_user/to_reset_password"><i class="fa fa-edit"></i>修改密码</a>
                                     </span>
                                   <#else>
                                    	<a  id="demo_5"><i class="icon-key"></i>登录</a>
                                    </#if>        
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!--  菜单部分 -->
            <#if (username)??  >
            <div class="page-header-menu " style="display: block;">
            	<div class="container">
            		<div class="hor-menu  ">
						<ul id="div_menu" class="nav navbar-nav">
							
						</ul>            			
            		</div>
            	</div>
            </div>
            </#if>
            <!--  菜单部分 -->
            
        </div>
        
        <div class="page-container">
            <div class="page-content-wrapper">
                <div class="page-content">
                    <div class="container">
                		<#nested>
                	</div>
                </div>
            </div>
        </div>
		<div class="page-footer" >
			<div class="copyright" style="text-align:center">
				<div>潮州市查违信息管理系统</div> 
				<div>技术支持：广东云能计算机科技有限公司</div>
			</div>
			<div class="scroll-to-top">
				<i class="icon-arrow-up"></i>
			</div>
		</div>
        <!-- END FOOTER -->
        <input type="hidden" id="menuIds" value="${menuIds!''}" >
    </body>
</html>
</#macro>