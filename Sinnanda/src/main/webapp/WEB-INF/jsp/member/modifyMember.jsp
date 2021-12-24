<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- plugins:css -->
	
	<link rel="stylesheet" href="/skydash/vendors/feather/feather.css">
	<link rel="stylesheet" href="/skydash/vendors/ti-icons/css/themify-icons.css">
	<link rel="stylesheet" href="/skydash/vendors/css/vendor.bundle.base.css">
	<!-- endinject -->
	<!-- Plugin css for this page -->
	<link rel="stylesheet" href="/skydash/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
	<link rel="stylesheet" href="/skydash/vendors/ti-icons/css/themify-icons.css">
	<link rel="stylesheet" type="text/css" href="/skydash/js/select.dataTables.min.css">
	<!-- End plugin css for this page -->
	<!-- inject:css -->
	<link rel="stylesheet" href="/skydash/css/vertical-layout-light/style.css">
	<!-- endinject -->
	<link rel="shortcut icon" href="/skydash/images/favicon.png" />
	
	<title>회원 정보수정 페이지</title>
</head>
<body>
	<div class="container-scroller">
	
		<!-- [이승준] 마이 페이지 상단 내비바 - START -->
		<%@ include file="/WEB-INF/partials/myPageNavbar.jsp" %>
		<!-- [이승준] 마이 페이지 상단 내비바 - END -->
	
	    <!-- [이승준] 마이 페이지 본문 - START -->
		<div class="container-fluid page-body-wrapper">
		
			<!-- [이승준] 마이 페이지 좌측 사이드바 - START -->
			<%@ include file="/WEB-INF/partials/myPageSidebar.jsp" %>
			<!-- [이승준] 마이 페이지 좌측 사이드바 - END -->
			
			<!-- [이승준] 마이 페이지 본문 - END -->
		      <!-- partial -->
			<div class="main-panel">
				<div class="content-wrapper">
					<!-- 내용1 -->
					<div class="row">
						<h2>아아</h2>
						<div class="col-md-12 grid-margin stretch-card">
							<div class="card position-relative">
								<div class="card-body">
									<form method="post" action ="/member/modifyMember">
	<input type = "hidden" name ="memberNo" value = "${loginUser.member.memberNo}">
		<table class="table table-hover">
			<tr>
				<td>아이디</td>
				<td><input type = "text" class="form-control" name ="memberId" value = "${loginUser.member.memberId}" readonly></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type = "text" class="form-control" name ="memberName" value = "${loginUser.member.memberName}" readonly></td>
			</tr>
			<tr>
				<td>나이</td>
				<td><input type ="text" class="form-control" name ="memberAge" value = "${loginUser.member.memberAge}"></td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td><input type ="text" class="form-control" name ="memberTel" value = "${loginUser.member.memberTel}"></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type ="text" class="form-control" name ="memberEmail" value = "${loginUser.member.memberEmail}"></td>
			</tr>
			<tr>
				<td>최근 수정일</td>
				<td><input type ="text" class="form-control" name ="updateDate" value = "${loginUser.member.updateDate}" readonly></td>
			</tr>
		</table>
		<button type = "submit" class="btn btn-primary">수정완료</button>
	</form>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
		</div>
	</div>
	
	<!-- [이승준] 하단 Footer - SATRT -->
	<%@ include file="/WEB-INF/partials/footer.jsp" %>
	<!-- [이승준] 하단 Footer - END -->


  <!-- plugins:js -->
  <script src="/vendors/js/vendor.bundle.base.js"></script>
  <!-- endinject -->
  <!-- Plugin js for this page -->
  <script src="/vendors/chart.js/Chart.min.js"></script>
  <script src="/vendors/datatables.net/jquery.dataTables.js"></script>
  <script src="/vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
  <script src="/js/dataTables.select.min.js"></script>

  <!-- End plugin js for this page -->
  <!-- inject:js -->
  <script src="/js/off-canvas.js"></script>
  <script src="/js/hoverable-collapse.js"></script>
  <script src="/js/template.js"></script>
  <script src="/js/settings.js"></script>
  <script src="/js/todolist.js"></script>
  <!-- endinject -->
  <!-- Custom js for this page-->
  <script src="/js/dashboard.js"></script>
  <script src="/js/Chart.roundedBarCharts.js"></script>
  <!-- End custom js for this page-->
</body>

</html>