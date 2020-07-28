<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
		<button class="btn btn-secondary" onclick="history.back()">뒤로가기</button>
		<c:if test="${board.user.id == principal.user.id }">
			<a href="/board/${board.id }/updateForm" class="btn btn-warning" }>수정</a>
			<button id="btn-delete" class="btn btn-danger">삭제</button>
		</c:if>
		<br /><br />
		<div>
			글 번호 : <span id="id"><i>${board.id }</i></span>
			작성자 : <span id="userid"><i>${board.user.username }</i></span>
			조회수 : <span id="id"><i>${board.count }</i></span>
		</div>
		<br />
		<div>
			<h3>${board.title }</h3>
		</div>
		<hr />
		<div>
			<h1>${board.content }</h1>
		</div>
		<hr />
</div>
<script src="/js/board.js"></script>
<%@include file="../layout/footer.jsp"%>>