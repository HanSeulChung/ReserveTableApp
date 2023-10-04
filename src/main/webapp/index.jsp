<%--
  Created by IntelliJ IDEA.
  User: w0w12
  Date: 2023-10-04
  Time: 오전 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Reserve Table App</title>
  </head>
  <body>
    <h1><%= "Reserve Table App" %></h1>
    <a href=" ">홈 </a>
    <a> | </a>
    <a href="auth/signup.jsp">회원 가입 </a>
    <a> | </a>
    <a href="auth/signin.jsp">로그인</a>
    <a> | </a>
    <a href="owner/owner.jsp">Owner 전용</a>
    <a> | </a>
    <a href="user/user.jsp">가게 검색</a>
    <a> | </a>
    <!-- Check if user is logged in with ROLE_USER or ROLE_OWNER -->
    <c:if test="${request.isUserInRole('ROLE_USER') || request.isUserInRole('ROLE_OWNER')}">
      <a href="mypage/mypage.jsp">마이 페이지</a>
    </c:if>
    <p></p>

  </body>
</html>
