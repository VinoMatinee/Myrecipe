<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>SMS 인증</title>
<link href="../css/style.css" rel="stylesheet" type="text/css">
<style>
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f0f2f5;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .container {
        background: #fff;
        padding: 30px 40px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
        text-align: center;
        max-width: 400px;
        width: 100%;
    }
    h1 {
        font-size: 24px;
        margin-bottom: 20px;
        color: #333;
    }
    .form-group {
        margin-bottom: 20px;
        position: relative;
    }
    .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        color: #555;
    }
    .form-group input[type="text"] {
        width: calc(100% - 20px);
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
        outline: none;
        transition: border-color 0.3s;
    }
    .form-group input[type="text"]:focus {
        border-color: #007BFF;
    }
    .form-group small {
        display: block;
        margin-top: 5px;
        color: #777;
    }
    .form-group input[type="submit"] {
        width: 100%;
        padding: 12px;
        border: none;
        border-radius: 4px;
        background-color: #007BFF;
        color: #fff;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s;
    }
    .form-group input[type="submit"]:hover {
        background-color: #0056b3;
    }
    .info {
        font-size: 0.9em;
        color: #777;
        margin-top: 15px;
    }
</style>
</head>

<body>
  <div class="container">
    <h1>인증번호 받기</h1>
    <form name="smsForm" action="sms/proc.do" method="post">
      <input type="hidden" name="action" value="go"> 
      <input type="hidden" name="smsType" value="S"> <!-- 발송 타입 -->
      <input type="hidden" name="subject" value=""> <!-- 장문(LMS)인 경우(한글30자이내) -->
      <input type="hidden" name="aid" th:value="${aid}"> 
      <input type="hidden" name="aname" th:value="${aname}"> 
      <input type="hidden" name="no" th:value="${no}"> 
      <input type="hidden" name="returnurl" maxlength="64" value="./proc_next.do" size="80">

      <div class="form-group">
        <label for="rphone">전화 번호 입력:</label>
        <input type="text" id="rphone" name="rphone" value="010-" autofocus>
        <small>예) 010-0112-1112 , '-' 포함해서 입력.</small>
      </div>

      <input type="hidden" name="sphone1" value="010"> <!-- 전화번호 첫째자리 -->
      <input type="hidden" name="sphone2" value="2722"> <!-- 전화번호 둘째자리 -->
      <input type="hidden" name="sphone3" value="9751"> <!-- 전화번호 셋째자리 -->

      <div class="form-group">
        <input type="submit" value="전송">
      </div>
      <div class="info">
        이통사 정책에 따라 발신번호와 수신번호가 같은 경우 발송되지 않습니다.
      </div>
    </form>
  </div>
</body>
</html>
