<!DOCTYPE html >
<!--  Website template by freewebsitetemplates.com  -->
<html>

<head>
	<title>IMDB 2.0 | Home</title>
	<meta  charset=iso-8859-1" />
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<!--[if IE 6]>
		<link href="css/ie6.css" rel="stylesheet" type="text/css" />
	<![endif]-->
	<!--[if IE 7]>
        <link href="css/ie7.css" rel="stylesheet" type="text/css" />  
	<![endif]-->
</head>

<body>

	  
			<div id="header">
	           		<a href="home.php" id="logo"><img src="CS411/logo.png" width="310" height="114" alt="" title=""></a>
					<ul class="navigation">
						<li class="active"><a href="home.php">Home</a></li>
						<li><a href="myshows.php">My Shows</a></li>
						<li><a href="mymovies.php">My Movies</a></li>
						<li><a href="actors.php">My Actors</a></li>
						<li><a href="recommendations.php">Recommendations</a></li>
						<li><a href="activemembers.php">Active Members</a></li>
						<li><a href="insert.html">Insert</a></li>
					</ul>
			</div>
			
			<div id="body">
			       <div id="content">
						<div class="search">
                                                        <form role="search" method="POST" action="search.php">
                                                                <input type="text" name="search" placeholder="Search..." required>
                                                                <button type="submit" id="search" formaction="search.php"></button>
                                                                <label for="shows"><input name="searchButton" type="radio" id="shows" value="shows" checked> Shows </label>
                                                                <label for="movies"><input name="searchButton" type="radio" id="movies" value="movies" > Movies </label>
                                                                <label for="actors"><input name="searchButton" type="radio" id="actors" value="actors"> Actors </label>
                                                        </form>
                                                </div>	
				</div>				
				
				<div id="scrollingBanner" style="width: 800px; margin: 0 auto; font-size: 25px; position: relative; top: -60px;">
					<?php
                                      		$servername = "fa16-cs411-22.cs.illinois.edu";
                                                $username = "test";
                                                $password = "test";
                                                $dbname = "imdb_new";
                                                $conn = new mysqli($servername, $username, $password, $dbname);
                                                if ($conn->connect_error) {
                                                	die("Connection failed: " . $conn->connect_error);
                                                }
						include('session.php');
						$u = $_SESSION['login_user'];
						echo "<p align='center' style='font-size: 25px;'>Welcome $u!</p>";
						echo '<p style="font-size: 20px; margin-bottom: 0;">Users active in the past 24 hours:</p> <marquee behavior="slide" direction="left" loop=100  BGCOLOR=#FFAD00>';
						$sql = "SELECT username FROM admin where admin.date > DATE_SUB(NOW(), INTERVAL 24 HOUR)";
						$insert = $conn->query($sql);
						if($insert->num_rows > 0){
							$firstIter = true; 
							while($row = $insert->fetch_assoc()){
								if(!$firstIter)
									echo '   ::   ';
								echo $row['username'];
								$firstIter = false; 	
							}
						} else { 
							echo "No active users in past 24 hours";
						}
						echo '</marquee>';
                                        ?>	

					
				</div>


	
				<div class="banner" style="position: relative; top: -40px;">
					
					
					<div class="w3-content w3-display-container" style="margin: 0 auto; max-width:1000px; max-height:500px;">
  						<img class="mySlides" src="CS411/pic1.jpg-1.jpeg" style="width:800px; height: 500px; margin: 0 auto;">
  						<img class="mySlides" src="CS411/pic2.jpg-1.jpeg" style="width:800px; height: 500px; margin: 0 auto;">
  						<img class="mySlides" src="CS411/pic3.jpg.jpeg" style="width:800px; height: 500px; margin: 0 auto;">
						<img class="mySlides" src="CS411/pic5.jpg-1.jpeg" style="width:800px; height: 500px; margin: 0 auto;">
  						<img class="mySlides" src="CS411/pic4.jpg-1.jpeg" style="width:800px; height: 500px; margin: 0 auto;">
						<img class="mySlides" src="CS411/pic6.png" style="width:800px; height: 500px; margin: 0 auto;">
						<img class="mySlides" src="CS411/pic7.jpg.jpeg" style="width:800px; height: 500px; margin: 0 auto;">
					</div>

					<script> 
						var myIndex = 0;
						carousel();

						function carousel() {
    						var i;
    						var x = document.getElementsByClassName("mySlides");
    						for (i = 0; i < x.length; i++) {
       							x[i].style.display = "none";  
    						}
    						myIndex++;
    						if (myIndex > x.length) {myIndex = 1}    
    							x[myIndex-1].style.display = "block";  
    							setTimeout(carousel, 2000); // Change image every 2 seconds
						}
					</script> 
				</div>
			</div>	
			
			<div id="footer" style="position: relative; top: -30px;">
					<div id="footnote">
						<div class="section">
						   &copy; 2016 <a href="home.php">IMDB 2.0</a>. Powered by MDJS database.
						</div>
					</div>
			</div>	
</body>
</html>
