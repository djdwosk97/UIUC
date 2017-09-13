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
    						
						//inserting details into table
    						if(isset($_POST['searchButton']) && $_POST['searchButton']=="movies"){
							$sql = "Select * FROM MOVIES where title = '$search'";
							$insert = $conn->query($sql);
                                                	
							if ($insert->num_rows > 0) {
                                        			echo "
                                                        	<p align='center'>
                                                        	<font color=blue  size='6pt'>
                                                        	<table  align='center' bgcolor='#d9d9d9' border='1'>
                                                        	<tr>
                                                        	<th>Title</th>
                                                        	<th>Production Year</th>
                                                        	<th>Movie Page</th>
                                                        	<th>Add to My Movies</th>
                                                        	<th>Likes</th>
								<th>Like This Movie</th>
								</tr>
                                                        	</font>
                                                        	</p>";                	
				
								// output data of each row
                                                        	while($row = $insert->fetch_assoc()) {
                                                                	echo "<tr>";
                                                                	echo "<td>" . $row["title"] . "</td>";
                                                                	echo "<td>" . $row["production_year"] . "</td>";
									echo "<td>" . '<a href="movies.php?data=' . $row["id"] . '">' . 'Go to Movies</a>';
									echo "<td>" . '<a href="addToMovies.php?data=' . $row["id"] . '">' . 'Add to My Movies</a>';	
									echo "<td>" . $row["likes"] . "</td>";
									echo "<td>" . '<a href="likes.php?data=' . $row["id"] . '">' . 'Like this Movie</a>';
									echo "</tr>";
                                                        	}
                                                        	echo "</table>";
                                                	} else {
                                                        	echo "0 results";
                                                	}
    						} elseif(isset($_POST['searchButton']) && $_POST['searchButton']=="shows"){
							$sub = "SELECT id FROM TV WHERE title='$search'";
							$sql = "SELECT title, season_nr, episode_nr FROM TV_episodes WHERE episode_of_id = ($sub) ORDER BY season_nr, episode_nr";
							$insert = $conn->query($sql);
							
                                			if ($insert->num_rows > 0) {
                                        			echo '
                                                        	<p align="center"> '.
                                                        	'<a style="font-size: 16px;" href="shows.php?data=' . $search . '">' . 'Go to Shows</a>'
                                                        	.'<a style="font-size: 16px; margin-left: 10px;" href="addToShows.php?data=' . $search . '">' . 'Add to My Shows</a>' .'
                                                        	<font color=blue  size="6pt">
                                                        	<table  align="center" bgcolor="#d9d9d9" border="2">
                                                        	<caption>'. $search .'\'s episodes are</caption>'
                                                        	.'<tr>
                                                                	<th>Title of the Episode</th>
                                                                	<th>Season</th>
                                                                	<th>Episode</th>
                                                        	</tr>
                                                        	</font>
                                                        	</p>';

								// output data of each row
                                        			while($row = $insert->fetch_assoc()) {
                                                			echo "<tr>";
                                                			echo "<td>" . $row["title"] . "</td>";
                                                			echo "<td>" . $row["season_nr"] . "</td>";
                                                			echo "<td>" . $row["episode_nr"] . "</td>";
                                                			echo "</tr>";
                                        			}
                                        			echo "</table>";
                                			} else {
                                        			echo "0 results";
                                			}
						} elseif(isset($_POST['searchButton']) && $_POST['searchButton']=="actors"){
							$words = explode(" ", $search);
                					$first = $words[0];
                					$last = $words[count($words)-1];
                					$actor_name = $last . ", " . $first;
                					$sql = "select MOVIES.title, MOVIES.production_year from MOVIES inner join cast_info on MOVIES.id = cast_info.movie_id and person_id = (SELECT id FROM ACTOR WHERE name = '$actor_name') order by MOVIES.production_year desc";
                					$insert = $conn->query($sql);
							
                					if ($insert->num_rows > 0) {
								echo '
                                                        	<p align="center"> '.
								'<a style="font-size: 16px;" href="addactor.php?data=' . $actor_name . '">' . 'Add to My Actors</a>'
                                                        	.'<font color=blue  size="6pt">
                                                        	<table  align="center" bgcolor="#d9d9d9" border="1">
                                                        	<caption>' . $search .' stars in</caption>
                                                        	<tr>
                                                        		<th>Title</th>
                                                        		<th>Production Year</th>
                                                        	</tr>
                                                        	</font>
                                                        	</p>';


                    						// output data of each row
                    						while($row = $insert->fetch_assoc()) {
                        						echo "<tr>";
                        						echo "<td>" . $row["title"] . "</td>";
                        						echo "<td>" . $row["production_year"] . "</td>";
									echo "</tr>";
                    						}
                    						echo "</table>";
                					} else {
                    						echo "0 results";
                					}
						}
               			 		$conn->close();
					?>
				</div>

			</div>			
		
		<div id="footer" style="position: relative; top: 50px;">
			<div id="footnote">
				<div class="section">&copy; 2016 <a href="home.php">IMDB 2.0</a>. Powered by MDJS database.</div>
			</div>
		</div>
 	</body>
</html>
