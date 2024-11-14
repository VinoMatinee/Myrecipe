<%@ page contentType="text/html; charset=UTF-8" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<title>인증 페이지</title> 
 
<link href="../css/style.css" rel="Stylesheet" type="text/css">
<script type="text/JavaScript"
          src="http://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 
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

  .container input[type="text"] {
    width: 100%;
    padding: 10px;
    margin-top: 10px;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
  }

  .container button {
    width: 100%;
    padding: 10px;
    background-color: #007BFF;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }

  .container button:hover {
    background-color: #0056b3;
  }
</style>
</head> 
<body>
  <div class="container">
    <form action="./confirm.do" method="post">
      <input type="hidden" name="no" th:value="${no}"> 
      <p>문자 메시지 전송후 처리되는 파일입니다.</p>
      예)<br>
      전송된 인증된 번호를 입력해주세요<br>
      <input type='text' name='auth_no' size='15' autofocus="autofocus">
      <button type='submit'>인증 확인</button> 
    </form>
  </div>
  
</body>
</html>
