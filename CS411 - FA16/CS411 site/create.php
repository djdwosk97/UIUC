<?php
        $servername = "fa16-cs411-22.cs.illinois.edu";
        $username = "test";
        $password = "test";
        $dbname = "imdb_new";

        $db = mysqli_connect($servername, $username, $password, $dbname);

        if($_SERVER["REQUEST_METHOD"] == "POST") {
                // username and password sent from form 
                $myusername = mysqli_real_escape_string($db,$_POST['username']);
                $mypassword = mysqli_real_escape_string($db,$_POST['password']);
		
		$sql = "INSERT INTO admin (username, passcode,date) VALUE ('$myusername', '$mypassword',(Select NOW() limit 1))";
		
		$result = mysqli_query($db,$sql);
		echo $result; 
			
		if($result == 1) { 
			#echo "<p>hello3</p>";
			session_start(); 

			$sql = "SELECT username FROM admin WHERE username = '$myusername' and passcode = '$mypassword'";
                	$result = mysqli_query($db,$sql);
			$row = mysqli_fetch_array($result,MYSQLI_ASSOC);
	               	$active = $row['active'];

                	$count = mysqli_num_rows($result);
                	// If result matched $myusername and $mypassword, table row must be 1 row
                	if($count == 1) {
                        	$_SESSION['login_user'] = $myusername;
                        	header("location: home.php");
                	} else {
                        	$error = "Your Login Name or Password is invalid";
                	}
		} else {
			echo '<script language="javascript" type="text/javascript"> 
				alert("That username already exists, please try again.");
                		window.location = "login.php";
        			</script>';
		}
       }
?>
