<%@ page contentType="text/html; charset=utf-8"%>

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
      <div class="row">
        <div class="col-lg-12">

          <div class="page-header">
            <h2 id="container">회원 가입하기</h2>
          </div>

          <div class="well">
            <p>회원가입을 위해 아래 내용들을 작성해 주세요.</p>
            <form class="form-horizontal" action="/member/register.do" method="post">
              <fieldset>
                <div class="form-group">
                  <label class="col-2 control-label">아이디</label>
                  <div class="col-12">
                    <input type="text" class="form-control" name="id">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-2 control-label">비밀번호</label>
                  <div class="col-12">
                    <input type="password" class="form-control" name="password">
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-2 control-label">이름</label>
                  <div class="col-12">
                    <input type="text" name="name" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-2 control-label">이메일</label>
                  <div class="col-12">
                    <input type="email" name="email"  class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-2 control-label">나이</label>
                  <div class="col-12">
                    <input type="number" name="age" class="form-control">
                  </div>
                </div>

                <hr class="my-3">

                <div class="row">
                  <div class="col-2">
                    <button type="submit" class="w-100 btn btn-primary">회원
                      등록</button>
                  </div>
                  <div class="col-2">
                    <button class="w-100 btn btn-secondary">취소</button>
                  </div>
                </div>
              </fieldset>
            </form>
          </div>
        </div>
      </div>

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