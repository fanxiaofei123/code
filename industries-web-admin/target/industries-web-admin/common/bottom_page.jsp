<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="utf-8"%>
			<nav class="mypagination">
						<ul class="pager mypager">
						  <li><a val="${last_list}cPg=1" href="#"  class="btn-light" id="homepage" onclick="reloadPaging(this)"> 首页</a></li>
						  <li><a val="${last_list}cPg=" href="#" class="btn-light" id="lastpage" onclick="reloadPaging(this)"> 上一页</a></li> 
						  <li><a val="${last_list}cPg=" href="#" class="btn-light" id="nextpage" onclick="reloadPaging(this)"> 下一页</a></li> 
						  <li><a val="${last_list}cPg=" href="#" class="btn-light" id="endpage" onclick="reloadPaging(this)"> 尾页</a></li>
						  <li>共有 <strong id="totalRows"></strong> 条记录，</li>
						  <li>当前第 <strong id="currentPage"></strong> 页，共 <strong id="totalPages"></strong> 页</li>
						</ul>
			</nav>