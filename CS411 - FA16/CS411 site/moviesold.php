<!DOCTYPE html >
<!--  Website template by freewebsitetemplates.com  -->
<html>

<head>
	<title>Movie</title>
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
						echo '<h2 align="center" style="font-size: 40px;">' .$name .'</h2>';
					
						echo '<div id="poster">';
						echo '<script>';
						echo "$('#term').focus(function(){\n"; 
echo "      var full = $(\"#poster\").has(\"img\").length ? true : false;\n"; 
echo "      if(full == false){\n"; 
echo "         $('#poster').empty();\n"; 
echo "      }\n"; 
echo "   });\n"; 
echo "\n"; 
echo "   var getPoster = function(){\n"; 
echo "\n"; 
echo "        var film = $('#term').val();\n"; 
echo "\n"; 
echo "         if(film == ''){\n"; 
echo "\n"; 
echo "            $('#poster').html('<div class=\"alert\"><strong>Oops!</strong> Try adding something into the search field.</div>');\n"; 
echo "\n"; 
echo "         } else {\n"; 
echo "\n"; 
echo "            $('#poster').html('<div class=\"alert\"><strong>Loading...</strong></div>');\n"; 
echo "\n"; 
echo "            $.getJSON(\"https://api.themoviedb.org/3/search/movie?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb&query=\" + film + \"&callback=?\", function(json) {\n"; 
echo "               if (json != \"Nothing found.\"){                 \n"; 
echo "console.log(json);\n"; 
echo "                     $('#poster').html('<p>Your search found: <strong>' + json.results[0].title + '</strong></p><img src=\\"http://image.tmdb.org/t/p/w500/' + json.results[0].poster_path + '\\" class=\\"img-responsive\\" >');\n"; 
echo "                  } else {\n"; 
echo "                     $.getJSON(\"https://api.themoviedb.org/3/search/movie?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb&query=goonies&callback=?\", function(json) {\n"; 
echo "                       \n"; 
echo "                       console.log(json);\n"; 
echo "                        $('#poster').html('<div class=\"alert\"><p>We\'re afraid nothing was found for that search.</p></div><p>Perhaps you were looking for The Goonies?</p><img id=\"thePoster\" src=\"http://image.tmdb.org/t/p/w500/' + json[0].poster_path + ' class=\"img-responsive\" />');\n"; 
echo "                     });\n"; 
echo "                  }\n"; 
echo "             });\n"; 
echo "\n"; 
echo "          }\n"; 
echo "\n"; 
echo "        return false;\n"; 
echo "   }\n"; 
echo "\n"; 
echo "   $('#search').click(getPoster);\n"; 
echo "   $('#term').keyup(function(event){\n"; 
echo "       if(event.keyCode == 13){\n"; 
echo "           getPoster();\n"; 
echo "       }\n"; 
echo "   });\n";
						echo '</script>';
						echo '</div>';

						
							



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
