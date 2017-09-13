<!DOCTYPE HTML>
<html>
<head>
	<title>Search</title>
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
                                                <li class = "active"><a href="activemembers.php">Active Members</a></li>
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
						$search = $_POST['search'];
    						//setting up mysql details
 						$servername = "fa16-cs411-22.cs.illinois.edu";
						$username = "test";
						$password = "test";
						$dbname = "imdb_new";
	
   	 					//connecting to sql database
						$conn = new mysqli($servername, $username, $password, $dbname);
						// Check connection
						if ($conn->connect_error) {
		    					die("Connection failed: " . $conn->connect_error);
						} 
    						$sql = "SELECT DISTINCT username, id FROM `admin` ";	
               			 		$insert = $conn->query($sql);
                                                        if ($insert->num_rows > 0) {
                                                                echo "
                                                                <p align='center'>
                                                                <font color=blue  size='6pt'>
                                                                <table  align='center' bgcolor='#d9d9d9' border='1'>
                                                                <tr>
                                                                <th>Username</th>
                                                                <th>Watchlist</th>
                                                                </tr>
                                                                </font>
                                                                </p>";

                                                                // output data of each row
                                                                while($row = $insert->fetch_assoc()) {
                                                                        echo "<tr>";
                                                                        echo "<td>" . $row["username"] . "</td>";
                                                                        echo "<td>" . '<a href="usershows.php?data=' . $row["id"] . '">' . 'Their Watchlist</a>';
                                                                        echo "</tr>";
                                                                }
                                                                echo "</table>";
                                                        } else {
                                                                echo "0 results";
                                                        }
	
						

						$conn->close();
					?>
				</div>

			</div>			
		
		<!--div style="position: absolute; bottom: 50px;" id="footer"-->
		<div id="footer" style="position: relative; top: 50px;">
			<div id="footnote">
				<div class="section">&copy; 2016 <a href="home.php">IMDB 2.0</a>. Powered by MDJS database.</div>
			</div>
		</div>
 	</body>
</html>
