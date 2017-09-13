<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8" />
        
    </head>
    <body>
	</br>
        <a href="home.php">Home Page</a>
	</br>

<?php
    //checking if data has been entered
    //if( isset( $_POST['data'] ) && !empty( $_POST['data'] ) )
    //{
     //   $data = $_POST['data'];
    //} else {
        //header( 'location: form.html' );
     //   exit();
    //}
  	include('session.php');
	$title = $_POST['title'];
	$year = $_POST['year'];
	$id = $_POST['id'];
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
    		//echo $data;
    		//echo <br/>
    		$sql = "INSERT INTO MOVIES (title,production_year) VALUE ('$title','$year')";
    		//echo $sql;
    		//<br/>
    		$insert = $conn->query($sql);
    		if($insert)
		{
			echo "\r\n";
			echo "$title was successful inserted";
			//echo session_id();
			echo "\r\n";
			$sql1 = "Select * from MOVIES Order by id desc limit 1";
			//echo $sql1;
			$insert1 = $conn->query($sql1);
			 echo "
                		<p align='center'>
                		<font color=blue  size='6pt'>
                		<table  align='center' bgcolor='#d3d3d3' border='1'>
                		<tr>
                		<th>Title</th>
               	 		<th>Production Year</th>
                		<th>ID</th>
				</tr>
                		</font>
               	 		</p>";
				while($row = $insert1->fetch_assoc()) {
                        echo "<tr>";
                        echo "<td>" . $row["title"] . "</td>";
                        echo "<td>" . $row["production_year"] . "</td>";
                        echo "<td>" . $row["id"] . "</td>";
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
 	
	</body>
</html>
