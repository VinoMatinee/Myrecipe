<%@ page contentType="text/html; charset=UTF-8" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<title>인증 확인</title> 
 
<link href="../css/style.css" rel="Stylesheet" type="text/css">
<script type="text/JavaScript"
          src="http://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 
<script type="text/javascript">
  $(function(){ 
     
  });
</script>

<style>
  body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f0f0f0;
  }

  .container {
    width: 100%;
    max-width: 500px;
    margin: 50px auto;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
  }

  .container p {
    font-size: 16px;
    color: #333;
  }

  .container a {
    display: inline-block;
    margin-top: 20px;
    padding: 10px 20px;
    background-color: #007BFF;
    color: white;
    text-decoration: none;
    border-radius: 4px;
  }

  .container a:hover {
    background-color: #0056b3;
  }
</style>
 
</head> 
<!-- ----------------------------------------- -->
<body leftmargin="0" topmargin="0">
<!-- ----------------------------------------- -->
 
  <div class="container">
    <p>${msg}</p>
  </div>
 
<!-- -------------------------------------------- -->
</body>
<!-- -------------------------------------------- -->
</html>
