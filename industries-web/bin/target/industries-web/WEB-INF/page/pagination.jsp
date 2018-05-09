<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<jsp:include page="global/global.jsp"/>
	<script src="${basePath}/third-party/jquery.min.js"></script>
	<script src="${basePath}/third-party/pagination/jquery.pagination.js"></script>
	<link rel="stylesheet" type="text/css" href="${basePath}/third-party/pagination/pagination.css">
	<script type="text/javascript">
		$(function(){
			var initPagination = function() {
				var num_entries = $("#hiddenresult div.result").length;
				// 创建分页
				$("#Pagination").pagination(10, {
					num_edge_entries: 1, //边缘页数
					num_display_entries: 4, //主体页数
					callback: pageSelectCallback,
					items_per_page:1 //每页显示1项
				});
			}();

			function pageSelectCallback(page_index, jq){
				console.debug(page_index);
				var page_index = page_index + 1;
				var url = '${basePath}/enterPrise/indexSearch.do';
				$.get(url, {currentPage: page_index},function(data){
					var pageCounts = data.data.pageCounts;
					if(pageCounts == null){
						$.data("pageCounts",pageCounts);
					}

				});
			}
		});
	</script>
</head>
<body>
<div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
</body>
</html>