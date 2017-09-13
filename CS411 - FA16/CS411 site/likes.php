<!DOCTYPE html >
<!--  Website template by freewebsitetemplates.com  -->
<html>

<head>
	<title>My Movies</title>
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
						<li class="active"><a href="myshows.php">My Shows</a></li>
						<li><a href="mymovies.php">My Movies</a></li>
						<li><a href="actors.php">My Actors</a></li>
						<li><a href="recommendations.php">Recommendations</a></li>
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
						#echo "$data";
						$u = $_SESSION['login_user'];
						$servername = "fa16-cs411-22.cs.illinois.edu";
                				$username = "test";
                				$password = "test";
                				$dbname = "imdb_new";
        					$conn = new mysqli($servername, $username, $password, $dbname);
						if ($conn->connect_error) {
                    					die("Connection failed: " . $conn->connect_error);
                				}
						$sub = "select id from admin where username ='$u'";
						#echo $sub;
						echo "<br>";
						$sub_sub = "select movie_id from user_likes_tracker where user_id = ($sub)";
						//$main = "select id FROM MOVIES where id = ($sub_sub)";
						
						#echo $sub_sub;
						echo "<br>";
						//$sql = "INSERT INTO tv_watched (id,tv_id) VALUE (($sub),($sub_sub))";
						#echo $sql;
						$sql = "update MOVIES set likes = likes + 1 where id = $data and $data NOT IN($sub_sub)";
						//echo $sql;
						echo "<br>";
						$insert = $conn->query($sql);
						//if($insert){
						//	echo "you have already liked this movie";
						//}
						//else{
						//	echo "hello";
						//}
						//echo $conn->rowCount();
						if($insert->num_rows == 0)
						{	
							$myText = "You have already liked this movie!";
							echo "<p align='center'> <font color=blue  size='6pt'>$myText</font> </p>";
							//echo "<td align=centre>$myText</td>"; 
							//echo "You have already liked this movie!";
							//echo"hello"; 
    							//die("Didn't Update"); 
						}
						$test = "insert into user_likes_tracker (user_id,movie_id) values (($sub),$data)";
						$insert1 = $conn->query($test);
						 if($insert)
                					{
                        					echo "\r\n";
                        					//echo "$title was successful inserted";
                        					echo "\r\n";
                        					$sql1 = "Select * from MOVIES where id = $data";
                        					//echo $sql1;
                        					$insert1 = $conn->query($sql1);
                         					echo "
                                <p align='center'>
                                <font color=blue  size='6pt'>
                                <table  align='center' bgcolor='#d3d3d3' border='1'>
                                <tr>
                                <th>Title</th>
                                <th>Production Year</th>
				<th>Go To Show</th>
                                <th>Likes</th>
				</tr>
                                </font>
                                </p>";
                                				while($row = $insert1->fetch_assoc()) {
                        							echo "<tr>";
                        							echo "<td>" . $row["title"] . "</td>";
                        							echo "<td>" . $row["production_year"] . "</td>";
                        							echo "<td>" . '<a href="mymovies.php?data=' . $row["title"] . '">' . 'Go to Movies</a>';
                                                                                //echo "<td>" . '<a href="removeFromShows.php?data=' . $row["id"] . '">' . 'Remove from My Shows</a>';
										echo "<td>" . $row["likes"] . "</td>";
										echo "</tr>";
                                				}
                    						echo "</table>";
                					}
                				else
                				{
                        			echo "Error";
                				}
    //closing mysqli connection
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

