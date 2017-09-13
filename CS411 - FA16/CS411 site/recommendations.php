<!DOCTYPE html >
<!--  Website template by freewebsitetemplates.com  -->
<html>

<head>
	<title>Recommendations</title>
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
						<li class = "active"><a href="recommendations.php">Recommendations</a></li>
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
						$u = $_SESSION['login_user'];
						$servername = "fa16-cs411-22.cs.illinois.edu";
                				$username = "test";
                				$password = "test";
                				$dbname = "imdb_new";
        					$conn = new mysqli($servername, $username, $password, $dbname);
						if ($conn->connect_error) {
                    					die("Connection failed: " . $conn->connect_error);
                				}
                        		/*	echo "\r\n";
						$min = 2667820;
						$max =3929984;
                        			//$sql1 = "Select distinct info from movie_info where movie_info.movie_id IN (select distinct `movie_id from admin where username ='$u')";		
                        			$sql1 = "SELECT DISTINCT info FROM movie_info, MOVIES WHERE MOVIES.id = movie_info.movie_id and info_type_id =3 and MOVIES.title IN (select title from MOVIES where id IN (select movie_id from movies_watched where id = (select id from admin where username ='$u')))";
						$insert1 = $conn->query($sql1);
                                		echo '<div align = "center">';
						echo "Movies Recommended by Genre you have watched";
						echo '</div>';
						echo '<p align="center"><font color=blue size="6pt"><table align="center" bgcolor="#d3d3d3" border="1">
							<tr><th>Movie Title</th><th>Genre</th></tr></font></p>';								

						while($row = $insert1->fetch_assoc()) {
							$rand_num = mt_rand($min,$max);
							//echo $rand_num;
							$mike1 =$row["info"];
							$mike = "'" . $row["info"] . "'";
							$q = "Select MOVIES.title from MOVIES, movie_info Where MOVIES.id >= $rand_num and movie_info.info_type_id = 3 and movie_info.info = $mike and MOVIES.id = movie_info.movie_id LIMIT 1";
							$insert2 = $conn->query($q);
							$row2 =$insert2->fetch_assoc();
							echo "<tr>";
							echo "<td>" . $row2["title"] . "</td>";
                                                        echo "<td>" . $mike1 . "</td>";
							echo "</tr>";
                                		}
						echo "</table>";
					*/

						
						//$sql1 = "SELECT DISTINCT info FROM movie_info, MOVIES WHERE MOVIES.id = movie_info.movie_id and info_type_id =3 and MOVIES.title IN (select title from MOVIES where id IN (select movie_id from movies_watched where id = (select id from admin where username ='$u')))";
                                                $sql_id = "SELECT id from admin where username='$u'";
                                                $temp = $conn->query($sql_id);
                                                $row = $temp->fetch_assoc();
                                                $usr_id = $row["id"];
						$sql1 = "SELECT DISTINCT info FROM movie_info, movies_watched WHERE info_type_id=3 and movie_info.movie_id = movies_watched.movie_id and movies_watched.id = $usr_id";
						$insert1 = $conn->query($sql1);
                                                echo '<div align = "center">';
                                                echo "Movies Recommended by Genre you have watched";
                                                echo '</div>';
                                                echo '<p align="center"><font color=blue size="6pt"><table align="center" bgcolor="#d3d3d3" border="1">
                                                        <tr><th>Movie Title</th><th>Genre</th></tr></font></p>';

						if($insert1->num_rows <= 0){
							$sql2 = "SELECT DISTINCT info FROM movie_info, movies_watched WHERE info_type_id=3 and movie_info.movie_id = movies_watched.movie_id ORDER BY rand() LIMIT 15";
							$insert3 = $conn->query($sql2); 
							$i = 0;
							while($row3 = $insert3->fetch_assoc() and $i < 6){
								$val = $row3["info"];
								$genre = "'$val'";
								$new_sql = 
									"SELECT DISTINCT title 
									FROM movie_info, movies_watched, movie_info_idx, MOVIES
                                                        		WHERE 
                                                        		movie_info.info_type_id=3 and 
                                                        		movie_info.movie_id = movies_watched.movie_id and 
                                                        		movie_info.info = $genre and 

                                                        		movie_info_idx.info_type_id=101 and movie_info_idx.movie_id = movies_watched.movie_id and

                                                        		MOVIES.id = movie_info.movie_id
                                                        		and MOVIES.likes >= 1
                                                        		LIMIT 3";
								$insert4 = $conn->query($new_sql);
								$row4 = $insert4->fetch_assoc();
								if($row4["title"] != ""){
                                                                	echo "<tr>";
                                                                	echo "<td>" . $row4["title"] . "</td>";
                                                                	echo "<td>" . $val . "</td>";
                                                                	echo "</tr>";
									$i++;
                                                        	}	
							}
						} else {                                                                
                                                while($row = $insert1->fetch_assoc()) {
							$mike1 =$row["info"];
                                                        $mike = "'" . $row["info"] . "'";
							$real_sql = 
							"SELECT DISTINCT title
							FROM movie_info, movies_watched, movie_info_idx, MOVIES
							WHERE 
							movie_info.info_type_id=3 and 
							movie_info.movie_id = movies_watched.movie_id and 
							movie_info.info = $mike and 

							movie_info_idx.info_type_id=101 and movie_info_idx.movie_id = movies_watched.movie_id and

							movies_watched.movie_id NOT IN (SELECT DISTINCT movies_watched.movie_id FROM movie_info, movies_watched WHERE info_type_id=3 and movie_info.movie_id = movies_watched.movie_id and movies_watched.id = $usr_id) 

							and MOVIES.id = movie_info.movie_id
							and MOVIES.likes >= 1
							LIMIT 3";
							
							$insert2 = $conn->query($real_sql);
							$row2 = $insert2->fetch_assoc();
							if($row2["title"] != ""){
								echo "<tr>";
                                                        	echo "<td>" . $row2["title"] . "</td>";
								echo "<td>" . $mike1 . "</td>";
                                                        	echo "</tr>";
							} else {
							
							}
						}
						}
						echo "</table>";
    						$mysqli->close;
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

