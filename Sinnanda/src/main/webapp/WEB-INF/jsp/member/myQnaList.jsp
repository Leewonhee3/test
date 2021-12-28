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
						<h2>내가 작성한 QnA</h2>
						<div class="col-md-12 grid-margin stretch-card">
							<div class="card position-relative">
								<div class="card-body">
				<section class="ftco-section testimony-section bg-light">
				<div class="container1">
				<select id="qnaCategory" name="qnaCategory" class="form-control-sm" onchange="location.href=this.value" style="float: right; margin-bottom: 40px;">
					<option value="">선택</option>
					<option value="/member/myQnaList">전체문의</option>
					<option value="/member/myQnaList?qnaCategory=기타문의">기타문의</option>
					<option value="/member/myQnaList?qnaCategory=결제문의">결제문의</option>
					<option value="/member/myQnaList?qnaCategory=이용문의">이용문의</option>
					<option value="/member/myQnaList?qnaCategory=예약문의">예약문의</option>
					<option value="/member/myQnaList?qnaCategory=숙소문의">숙소문의</option>
				</select>
			</div>
			<table class="table table-board" style="width: 100%;">
				<tr style="text-align:center">
					<th width="5%">번호</th>
					<th width="40%">제목</th>
					<th width="10%">문의유형</th>
					<th width="10%">작성자</th>
					<th width="10%">작성일</th>
				</tr>
				<c:forEach items="${myQnaList}" var="qna">
					<tr>
						<td style="text-align:center">${qna.qnaNo}</td>
						<td>
							<a href="/member/myQnaOne?qnaNo=${qna.qnaNo}">${qna.qnaTitle}</a>
							<c:if test="${qna.qnaSecret == '비밀문의'}">
								<img src="/images/qna/lockImg.png" width="20px" height="20px">
							</c:if>
						</td>
						<td style="text-align:center">${qna.qnaCategory}</td>
						<td style="text-align:center">${qna.memberName}</td>
						<td style="text-align:center">${qna.createDate}</td>
					</tr>
				</c:forEach>
			</table>

			<!-- Paging -->			
			<div class="row mt-5">
		    	<div class="col text-center">
		            <div class="block-27">
						<ul>
							<!-- '이전' 버튼 -->
							<c:if test="${beginRow > (ROW_PER_PAGE * 10)}">
								<li><a href="/member/myQnaList?currentPage=${pageNo-1}&qnaCategory=${qnaCategory}">&lt;</a></li>
							</c:if>
							
							<!-- Page 번호 -->
							<c:set var="doneLoop" value="false"></c:set>
							<c:forEach var="i" begin="${pageNo}" end="${pageNo + 9}">
							
								<!-- Page 숫자 10개 출력 -->
								<c:if test="${not doneLoop}">
									<c:choose>
										<c:when test="${currentPage == i}">				
											<li class="active"><span>${i}</span></li>
										</c:when>
					    				<c:otherwise>
											<li><a href="/member/myQnaList?currentPage=${i}&qnaCategory=${qnaCategory}">${i}</a></li>	
										</c:otherwise>		
									</c:choose>
									<!-- LastPage이면 다음 페이지 번호를 출력하지 않는다 -->
									<c:if test="${i == lastPage}">
										<c:set var="doneLoop" value="true"></c:set>
									</c:if>
								</c:if>
							</c:forEach>
							
							<!-- '다음' 버튼 -->
							<c:if test="${currentPage + 10 <= lastPage}">
								<li><a href="/member/myQnaList?currentPage=${pageNo+10}&qnaCategory=${qnaCategory}">&gt;</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
			<!-- Paging -->
</section>
		</div>
	
	<!-- 본문 QnA 목록 부분 - END -->
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