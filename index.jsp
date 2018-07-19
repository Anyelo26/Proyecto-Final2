<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html >
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>OMEGA-AQP</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/animate.css">
	<link href="css/animate.min.css" rel="stylesheet"> 
	<link href="css/style.css" rel="stylesheet" />	
  </head>
  <body >	
	<header id="header" >
        <nav class="navbar navbar-default navbar-static-top "  role="banner">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>				
                <div class="navbar-collapse collapse">							
					<div class="menu" >
						<ul class="nav nav-tabs " role="tablist">
							<li role="presentation"><a href="index.jsp" class="active">Casa</a></li>
							<li role="presentation"><a href="/Role/Display">Roles</a></li>
							<li role="presentation"><a href="/User/Display">Usuarios</a></li>
							<li role ="presentation"><a href="/Resource/Display">Resource</a></li>
							<li role="presentation"><a href="/Access/Display">Accesos</a></li>
							<li role="presentation"><a href="/UserLogin">Login</a></li>
							<li role="presentation"><a href="/UserLogout">Logout</a></li>
							<li role="presentation"><a href="/User/Register">Registrarse</a></li>
							<li role="presentation"><a href="/Product/Display">Productos</a></li>
							<li role="presentation"><a href="/peces">Peces</a></li>
							<li role="presentation"><a href="/lab08_foro">Foro</a></li>
							<li role="presentation"><a href="/Recomendaciones">Recomendaciones</a></li>	
							<li role="presentation"><a href="https://github.com/Anyelo26/ProyectoFinal">GIT-HUB</a></li>		
							<!--  <li role="presentation"><a href="/Clientes">Clientes</a></li>-->
							<li role="presentation"><a href="https://www.youtube.com/playlist?list=PL-RTLkuAiManRdO2stx434P8hYErnxryA">Videos</a></li>	
							<li role="presentation"><a href="/Clientes">Clientes</a></li>	
							<li role="presentation" style="margin-left:14.8%">
							<div class="navbar-brand">
							
								<a href="index.jsp"><h1>OMEGA-AQP</h1></a>
							</div></li>
													
						</ul>
					</div>
				</div>		
            </div>
        </nav>		
    </header>
	<div class="slider">		
		<div id="about-slider">
			<div id="carousel-slider" class="carousel slide" data-ride="carousel">
				<ol class="carousel-indicators visible-xs">
					<li data-target="#carousel-slider" data-slide-to="0" class="active"></li>
					<li data-target="#carousel-slider" data-slide-to="1"></li>
					<li data-target="#carousel-slider" data-slide-to="2"></li>
				</ol>
				<div class="carousel-inner">
					<div class="item active">						
						<img src="img/peces.jpg" class="img-responsive" width="1480" height="160"> 
						<div class="carousel-caption" style="margin-bottom:5%">
							<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.3s">								
								<h2><span>Acuario</span></h2>
							</div>
							<div class="col-md-10 col-md-offset-1">
								<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.6s">								
									<p>Somos encargados de brindar peces y accesorios relacionados para el gusto economico del cliente </p>
								</div>
							</div>
						</div>
				    </div>
				    <div class="item">
						<img src="img/peces2.jpg" class="img-responsive" width="1380" height="160"> 
						<div class="carousel-caption" style="margin-bottom:15%">
							<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="1.0s">								
								<h2><span >Variedad De Peces</span></h2>
							</div>
							<div class="col-md-10 col-md-offset-1">
								<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.6s">								
									<p>Informate sobre la variedad de peces y compra aquellos que más te atraigan</p><br>
								</div>
							</div>
						</div>
				    </div> 
				    <div class="item">
						<img src="img/peces2 .jpg" class="img-responsive" width="1380" height="160"> 
						<div class="carousel-caption" style="margin-bottom:25%">
							<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.3s">								
								<h2>Servicios</h2>
							</div>
							<div class="col-md-10 col-md-offset-1">
								<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.6s">								
									<p>Brindamos asesoramiento al cliente sobre el cuidado de los peces y mantenimiento </p>
								</div>
							</div>
						</div>
				    </div> 
				</div>
				
				<a class="left carousel-control hidden-xs" href="#carousel-slider" data-slide="prev">
					<i class="fa fa-angle-left"></i> 
				</a>
				
				<a class=" right carousel-control hidden-xs"href="#carousel-slider" data-slide="next">
					<i class="fa fa-angle-right"></i> 
				</a>
			</div> 
		</div>
	</div>
	<div class="about">
		<div class="container">
			<div class="text-center">
				<h2>OMEGA AQP</h2>
				<div class="col-md-10 col-md-offset-1">
					<p>Somos un negocio dedicado a la venta exclusiva de Peces a traves de un catalogo , brindamos asesoramiento en  cuidado y en el uso correcto de los accesorios
					   Tenemos un Foro donde puedes dejar tus comentarios para ayudarnos a seguir mejorando
					</p>
				</div>
			</div>	
		</div>			
	</div>
	
	
	
    <script src="js/jquery.js"></script>		
    <script src="js/bootstrap.min.js"></script>	
	<script src="js/wow.min.js"></script>
	<script>
	wow = new WOW(
	 {
	
		}	) 
		.init();
	</script>	
  </body>
</html>



