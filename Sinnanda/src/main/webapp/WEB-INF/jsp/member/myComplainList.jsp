<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- plugins:css -->
		<link rel="stylesheet" href="/css/style.css">
	<link rel="stylesheet" href="/css/animate.css">
	
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
	
	<title> 페이지</title>
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
						<h2>${loginUser.member.memberName}님의 컴플레인 목록</h2>
						<div class="col-md-12 grid-margin stretch-card">
							<div class="card position-relative">
								<div class="card-body">
								<section class="ftco-section testimony-section bg-light">
    	<div class="container3">
		<h1>내가 작성한 컴플레인</h1>
		<div>
			<select id="complainCategory" name="complainCategory" class="form-control-sm" onchange="location.href=this.value" style="float: right; margin-bottom: 20px;">
				<option value="">선택</option>
				<option value="/member/myComplainList">전체</option>
				<option value="/member/myComplainList?complainCategory=예약일 변경">예약일 변경</option>
				<option value="/member/myComplainList?complainCategory=예약 취소">예약 취소</option>
				<option value="/member/myComplainList?complainCategory=이용 불편">이용 불편</option>
				<option value="/member/myComplainList?complainCategory=기타">기타</option>
			</select>
		</div>
	
	<table class="table table-hover" style="width: 100%;">
		<tr>
			<th>컴플레인 번호</th>
			<th>작성자</th>
			<th>숙소이름</th>
			<th>컴플레인 제목</th>
			<th>컴플레인 종류</th>
			<th>작성일</th>
			<th>컴플레인 상세정보</th>
		</tr>
		
		<c:forEach items="${myComplainList}" var="complain">
			<tr>
				<td>${complain.complainNo}</td>
				<td>${loginUser.member.memberName}</td>
				<td>${complain.accomName}</td>
				<td>${complain.complainTitle}</td>
				<td>${complain.complainCategory}</td>
				<td>${complain.createDate}</td>
				<td><a href="/member/myComplainOne?complainNo=${complain.complainNo}">상세보기</a></td>
			</tr>
		</c:forEach>
	</table>
	</div>
	</section>
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