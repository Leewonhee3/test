<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의사항 목록 페이지</title>
</head>
<body>
	<h1>QnA List</h1>
	<div>
		<a href="addQna">문의사항 작성</a>
	</div>
	<div>
		<select id="qnaCategory" name="qnaCategory">
			<option value="기타문의">기타문의</option>
			<option value="결제문의">결제문의</option>
			<option value="이용문의">이용문의</option>
			<option value="예약문의">예약문의</option>
			<option value="숙소문의">숙소문의</option>
		</select>
		<a href="/qnaList?qnaCategory=${qnaCategory}">선택</a>
	</div>
	<table border="1">
		<tr>
			<td>문의 번호</td>
			<td>카테고리</td>
			<td>문의한 회원</td>
			<td>제목</td>
			<td>비밀글 여부</td>
			<td>작성일</td>
		</tr>
		<c:forEach items="${qnaList}" var="qna">
			<tr>
				<td>${qna.qnaNo}</td>
				<td>${qna.qnaCategory}</td>
				<td>${qna.memberNo}</td>
				<td>
					<a href="/qnaOne?qnaNo=${qna.qnaNo}"></a>${qna.qnaTitle}</td>
				<td>${qna.qnaSecret}</td>
				<td>${qna.createDate}</td>
			</tr>
		</c:forEach>
	</table>
	<div>
		<c:if test="${currentPage > 1}" >
			<a href="qnaList?currentPage=${currentPage-1}">이전</a>
		</c:if>
		<c:if test="${currentPage < lastPage}" >
			<a href="qnaList?currentPage=${currentPage+1}">다음</a>
		</c:if>
	</div>
</body>
</html>