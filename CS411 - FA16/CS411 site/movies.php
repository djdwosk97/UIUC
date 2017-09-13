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

<body onload="document.getElementById('search').click()">
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
		</div>
                
		<div id="body">        
	               <!--div id="content">               
				<div class="search">
                                	<form role="search" method="POST" action="search.php">
                                        	<input type="text" name="search" placeholder="Search..." required>
                                                <button type="submit" id="search" formaction="search.php"></button>
                                                <label for="shows"><input name="searchButton" type="radio" id="shows" value="shows" checked> Shows </label>
                                                <label for="movies"><input name="searchButton" type="radio" id="movies" value="movies" > Movies </label>
                                                <label for="actors"><input name="searchButton" type="radio" id="actors" value="actors"> Actors </label>
                                        </form>
                                </div>
                       </div-->
	
		       <div class="container">
  		       		<div class="search" style="display: none;">
    					<div id="fetch">
						<form name="frm1" action = "">
							<?php
								if(isset($_GET["data"]))
                                			               $data = $_GET["data"];
                               			         	$servername = "fa16-cs411-22.cs.illinois.edu";
                                        			$username = "test";
                                       				$password = "test";
                                        			$dbname = "imdb_new";
                                        			$conn = new mysqli($servername, $username, $password, $dbname);
                                        			if ($conn->connect_error) {
                                        				die("Connection failed: " . $conn->connect_error);
                                        			}

                                        			$insert = $conn->query("SELECT title FROM MOVIES WHERE id=$data");
                                        			$row1 = $insert->fetch_assoc();
                                        			$name = $row1["title"];	
								echo '<input type="test" value="'.$name . '" id="term" />';
							?>
      							<button id="search">Find</button>
						</form>
    					</div>
  				</div>

				<div id="title" style="text-align:center;">
					<?php
                        			if(isset($_GET["data"]))
                                			$data = $_GET["data"];
                                		$servername = "fa16-cs411-22.cs.illinois.edu";
                                		$username = "test";
                                		$password = "test";
                                		$dbname = "imdb_new";
                                		$conn = new mysqli($servername, $username, $password, $dbname);
                                		if ($conn->connect_error) {
                                   			die("Connection failed: " . $conn->connect_error);
                                		}

                                		$insert = $conn->query("SELECT title FROM MOVIES WHERE id=$data");
                               			$row1 = $insert->fetch_assoc();
                                		$name = $row1["title"];
						echo '<h2 style="font-size:40px; font-weight: 700;">' . $name;
						
						$sql_rating = "SELECT info FROM movie_info_idx WHERE movie_id=$data and info_type_id=101";
						$insert_rating = $conn->query($sql_rating);
						if($insert_rating->num_rows > 0){
							$row_rating = $insert_rating->fetch_assoc();
							$rating = $row_rating["info"];
							echo " ($rating/10) </h2>";
						} else { 					
							echo '</h2>';
						}
					?>
				</div>
				
				<div id="movieContainer" style="width: 60%; margin: 0 auto;">	
  					<div id="poster" style="width: 335px; float: left;">
  						<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
						<script src="test.js"></script>
  					</div>
		
					<div id="pageContent" style="width: 50%; float: left; margin-left: 30px;">
						<p><b><u>Genres:</u></b></p>

						<?php
                                                	if(isset($_GET["data"]))
                                                        	$data = $_GET["data"];
                                                	$servername = "fa16-cs411-22.cs.illinois.edu";
                                                	$username = "test";
                                                	$password = "test";
                                                	$dbname = "imdb_new";
                                                	$conn = new mysqli($servername, $username, $password, $dbname);
                                                	if ($conn->connect_error) {
                                                	        die("Connection failed: " . $conn->connect_error);
                                                	}
							//genres
							$sql="SELECT DISTINCT info FROM movie_info, MOVIES WHERE MOVIES.id = movie_info.movie_id and info_type_id =3 and MOVIES.title = (SELECT title FROM MOVIES WHERE id = $data)";  
                                                	$insert = $conn->query($sql);
							$firstIter = true; 
                                               	 	while($row1 = $insert->fetch_assoc()){
								if(!$firstIter)
									echo ", ";
                                                		$genre = $row1["info"];
								echo $genre;
								$firstIter = false; 
							}
							
							//cast list
							echo '<p><b><u>Cast:</u></b></p>';
							echo '<div style="height: 400px; width: 500px; overflow: auto"';
							$sql = "select DISTINCT MOVIES.id, cast_info.person_id, cast_info.person_role_id, cast_info.role_id, ACTOR.name FROM ACTOR, cast_info, MOVIES where MOVIES.id = cast_info.movie_id AND cast_info.person_id = ACTOR.id AND cast_info.movie_id = $data";
							$insert = $conn->query($sql);
							
							echo '<p align="center"><table align="center" bgcolor="#d9d9d9" border="1">
								<tr><th>Actor</th><th>Character</th><th>Role</th></tr></p>';
                                                        while($row1 = $insert->fetch_assoc()){
                                                                $actor = $row1["name"];
								echo "<tr>";
								echo "<td>" . $actor . "</td>";
								$person_role_id = $row1["person_role_id"];
								$sql_char_name = "SELECT name from char_name WHERE id = $person_role_id";
								$insert_name = $conn->query($sql_char_name);
								if($insert_name->num_rows > 0){
									$row_name = $insert_name->fetch_assoc();
									$char_name = $row_name["name"];
									echo "<td>" . $char_name . "</td>";
								}
									
								$role_id = $row1["role_id"];
								$sql_role_name = "SELECT role from role_type WHERE id = $role_id";
								$insert_role = $conn->query($sql_role_name);
								if($insert_role->num_rows > 0){
									$row_role = $insert_role->fetch_assoc();
									$role_name = $row_role["role"];
									echo "<td>" . $role_name . "</td>";
								}
								echo "</tr>";
                                                        }
							echo "</table>";
							echo '</div>';
                                        	?>
					</div>

					<div id="spacer" style="padding-top: 530px;">
						<div id="plot" style="border: 1px solid #d3d3d3; width: 865px;">
							<?php
                                                        if(isset($_GET["data"]))
                                                                $data = $_GET["data"];
                                                        	$servername = "fa16-cs411-22.cs.illinois.edu";
                                                        	$username = "test";
                                                        	$password = "test";
                                                        	$dbname = "imdb_new";
                                                        	$conn = new mysqli($servername, $username, $password, $dbname);
                                                        	if ($conn->connect_error) {
                                                        	        die("Connection failed: " . $conn->connect_error);
                                                        	}
                                                        	
                                                        	$sql = "SELECT info FROM movie_info WHERE info_type_id=98 and movie_id=$data";
								$insert = $conn->query($sql);
								$row = $insert->fetch_assoc();
								$plot = $row["info"];
								echo "<h2 style='padding: 0 10px;'>Plot:</h2>";
								echo "<p style='padding: 0 10px;'>$plot</p>";
                                                	?>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
