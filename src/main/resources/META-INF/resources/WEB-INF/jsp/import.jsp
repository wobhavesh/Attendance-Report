<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/file-upload.css">
<%-- <link rel="stylesheet" href="<c:url value="/css/file-upload.css" />"> --%>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="row mt-3">
			<div class="col-12">
				<h4>Attendance sheet import</h4>
			</div>
		</div>
		
		<form action="/upload" method="post" enctype="multipart/form-data" id="file-upload-form">
			<fieldset class="upload_dropZone text-center mb-3 p-4">
				<svg class="upload_svg" width="60" height="60" aria-hidden="true">
					<use href="#icon-imageUpload"></use>
				</svg>

				<p class="small my-2">
					Drag &amp; Drop background image(s) inside dashed region<br>
					<i>or</i>
				</p>

				<input id="upload_image_background"
					data-post-name="file"
					data-post-url="/upload"
					class="position-absolute invisible" type="file" name="file"
					accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" /> <label
					class="btn btn-upload mb-3" for="upload_image_background">Choose
					file(s)</label>

				<div class="upload_gallery d-flex flex-wrap justify-content-center gap-3 mb-0"></div>

			</fieldset>
			<!-- <fieldset>
				<button type="submit">Submit</button>
			</fieldset> -->
		</form>
		<div class="row mt-3" id="success-import-panel" style="display: none;">
			<div class="col-12">
				<h4 class="font-weight-bold text-green p-2 text-center" style="background: #28a74545!important;">Successful Import</h4>
			</div>
		</div>
		<div class="row mt-3" id="failed-import-panel" style="display: none;">
			<div class="col-12">
				<h4 class="font-weight-bold text-green p-2 text-center" style="background: #f8d7da!important;">Import Failed</h4>
			</div>
		</div>
		<div id="cell-error-panel" style="display: none;">
			<div class="row mt-3">
				<div class="col-12">
					<h4 class="border-bottom font-weight-bold">Error found in cells</h4>
				</div>
			</div>
			<div class="">
				<table class="table table-sm">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">Cell(Row:Column)</th>
							<th scope="col">Error Message</th>
							<th scope="col">Actual value</th>
						</tr>
					</thead>
					<tbody id="error-table">
						<c:choose>
						    <c:when test="${empty cellErrors}">
						        <tr>
									<td scope="col" colspan="4">No errors in sheet</td>
								</tr>
						    </c:when>
						</c:choose>
						<c:forEach items="${cellErrors}" var="cell" varStatus="loop">
							<tr>
								<th scope="row">${loop.count}</th>
								<td scope="col">${cell.cellAddress}</td>
								<td scope="col">${cell.message}</td>
								<td scope="col">${cell.actualValue}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"
		integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
		crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/public/js/file-upload.js"></script>
</body>
</html>