<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="ko">
<head>
<meta charset="utf-8" />
<meta name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>Yorizori Cookbook</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link
  href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
  rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="/css/styles.css" rel="stylesheet" />
</head>

<body>
  <%-- Navigation --%>
  <jsp:include page="/WEB-INF/views/include/nav.jsp" />

  <%-- Section--%>
  <section class="py-5">
    <div class="container">
      <div class="page-header">
        <h2>회원 목록(총 : ${page.pagination.totalElements}명)</h2>
      </div>

      <div class="row">
        <div class="col-8">
          <form class="row">
            <div class="col-auto">
              <input type="text" class="form-control" placeholder="아이디, 이름" name="search">
            </div>
            <div class="col-auto">
              <button type="submit" class="btn btn-primary">검 색</button>
            </div>
          </form>
        </div>
        <div class="col-4">
          <a href="/member/signup.do" class="btn btn-primary float-end">회원
            등록</a>
        </div>
      </div>
      <hr class="my-2">
      <div>
        <table class="table">
          <thead>
            <tr>
              <th>아이디</th>
              <th>이름</th>
              <th>이메일</th>
              <th>가입일자</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${page.list}" var="member">
              <tr>
                <td><a href="#">${member.id}</a></td>
                <td>${member.name}</td>
                <td><a href="mailto:${member.email}">${member.email}</a></td>
                <td>${member.regdate}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      
      <%-- 페이징 처리 --%>
      <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
          <%-- 처음으로 출력 여부 --%>
          <c:if test="${page.pagination.showFirst }">
            <li class="page-item">
              <a class="page-link" href="?page=1&search=${page.params.search}" aria-label="Previous">처음으로</a>
            </li>
          </c:if>
          
          <%-- 이전 목록 출력 여부 --%>
          <c:if test="${page.pagination.showPrevious }">
            <li class="page-item">
              <a class="page-link" href="?page=${page.pagination.previousStartPage}&search=${page.params.search}" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a>
            </li>
          </c:if>
          
          <%-- 페이지 번호 출력 --%>
          <c:forEach var="i" begin="${page.pagination.startPage}" end="${page.pagination.endPage }">
            <c:choose>
              <c:when test="${i == page.params.requestPage }">
                <li class="page-item active" aria-current="page"><a class="page-link">${i}</a></li>
              </c:when>
              <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${i}&search=${page.params.search}">${i}</a></li>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          
          <%-- 다음 목록 출력 여부 --%>
          <c:if test="${page.pagination.showNext }">
            <li class="page-item">
              <a class="page-link" href="?page=${page.pagination.nextStartPage}&search=${page.params.search}" aria-label="Previous"> <span aria-hidden="true">&raquo;</span></a>
            </li>
          </c:if>
          
          <%-- 끝으로 출력 여부 --%>
          <c:if test="${page.pagination.showLast }">
            <li class="page-item">
              <a class="page-link" href="?page=${page.pagination.totalPages}&search=${page.params.search}" aria-label="Previous">끝으로</a>
            </li>
          </c:if>
        </ul>
      </nav>
      
    </div>
  </section>

  <%-- Footer--%>
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />

  <!-- Bootstrap core JS-->
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
  <!-- Core theme JS-->
  <script src="/js/scripts.js"></script>
</body>

</html>