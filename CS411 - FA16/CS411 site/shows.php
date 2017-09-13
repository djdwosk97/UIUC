<!DOCTYPE html >
<!--  Website template by freewebsitetemplates.com  -->
<html>

<head>
	<title>Show</title>
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
						<li><a href="home.php">Home</a></li>
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
			

				<div id="content">
					<?php
                                                include('session.php');
                                                if(isset($_GET["data"]))
                                                        $data = $_GET["data"];
						//echo $data;
						$servername = "fa16-cs411-22.cs.illinois.edu";
                                                $username = "test";
                                                $password = "test";
                                                $dbname = "imdb_new";
						$conn = new mysqli($servername, $username, $password, $dbname);
						if ($conn->connect_error) {
                                                        die("Connection failed: " . $conn->connect_error);
                                                }
						$new = str_replace(' ', '%20', $data);
						$title = "'$data'";
						$sql = "SELECT series_years from TV where title=$title";	
						$insert = $conn->query($sql);
						if($insert->num_rows > 0){
							$row = $insert->fetch_assoc();
							$years = $row["series_years"];
							echo "<p align='center' style='font-size: 25px;'>$years</p>";
						}
						echo '<iframe src=http://graphtv.kevinformatics.com/?q="' .$new .' height=600px width=900px scrolling="no"></iframe>';

					?>
				</div>	
			</div>	
			
			<div id="footer" style="position: relative; top: 50px;">
					<div id="footnote">
						<div class="section">
						   &copy; 2016 <a href="home.php">IMDB 2.0</a>. Powered by MDJS database.
						</div>
					</div>
			</div>	
</body>
</html>
