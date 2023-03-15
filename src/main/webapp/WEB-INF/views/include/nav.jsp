<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container px-4 px-lg-5">
    <a class="navbar-brand" href="#!">Yorizori Cookbook</a>
    <button class="navbar-toggler" type="button"
      data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent" aria-expanded="false"
      aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
        <li class="nav-item"><a class="nav-link active"
          aria-current="page" href="#">HOME</a></li>
        <li class="nav-item"><a class="nav-link" href="">요리책 목록</a></li>
        <li class="nav-item"><a class="nav-link" href="#">요리책 등록</a></li>
        <li class="nav-item"><a class="nav-link" href="/member/list.do">회원 목록</a></li>
      </ul>

      <c:choose>
        <c:when test="${empty loginMember}">
          <div class="row row-cols-lg-auto g-3 align-items-center">
            <div class="col-12">
              <a href="/member/login.do" class="btn btn-primary">로그인</a>
            </div>
            <div class="col-12">
              <a href="/member/signup.do" class="btn btn-warning">회원가입</a>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <div class="row row-cols-lg-auto g-3 align-items-center">
            <div class="col-12">
              <span style="color: blue">${loginMember.name}(${loginMember.email})님 로그인중</span>
            </div>
            <div class="col-12">
              <a href="/member/logout.do" class="btn btn-warning">로그아웃</a>
            </div>
          </div>
        
        </c:otherwise>
      </c:choose>



    </div>
  </div>
</nav>






