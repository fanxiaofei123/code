<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="sidebar" id="sidebar">
    <ul class="nav nav-list">
        </li>
        <li id="company">
            <a href="${basePath}/enterprise/list.do">
                <i class="glyphicon glyphicon-globe"></i>
                <span class="menu-text">企业管理</span>
            </a>
        </li>
        <li id="floor">
            <a href="${basePath}/building/list.do">
                <i class="glyphicon glyphicon-align-justify"></i>
                <span class="menu-text">楼宇管理</span>
            </a>
        </li>
        <li id="street">
            <a href="${basePath}/street/show.do">
                <i class="glyphicon glyphicon-road"></i>
                <span class="menu-text">街道管理</span>
            </a>
        </li>
        <li id="import">
            <a href="#" class="dropdown-toggle">
                <i class="glyphicon glyphicon-import"></i>
                <span class="menu-text"> 数据导入</span>
                <b class="arrow icon-angle-down"></b>
            </a>

            <ul id="data_import_submenu" class="submenu" style="display: block;">
                <li>
                    <a href="${basePath}/data/enterpriseDataUpdates.do">
                        <i class="icon-double-angle-right"></i>企业数据更新
                    </a>
                </li>
                <li>
                    <a href="${basePath}/data/buildingDataUpdates.do">
                        <i class="icon-double-angle-right"></i>楼宇数据更新
                    </a>
                </li>

                <%--<li>--%>
                    <%--<a href="${basePath}/data/changeDataUpdates.do">--%>
                        <%--<i class="icon-double-angle-right"></i>变更数据导入--%>
                    <%--</a>--%>
                <%--</li>--%>

                <li>
                    <a href="${basePath}/data/enterpriseOperationDataUpdates.do">
                        <i class="icon-double-angle-right"></i>企业运行数据导入
                    </a>
                </li>
            </ul>
        </li>
        <li id="indexSet">
            <a href="${basePath}/indexDefinition/getIndexDefinitionByIndustry.do" class="dropdown-toggle">
                <span class="glyphicon glyphicon-cog"></span>
                <span class="menu-text">指标权重设置</span>
            </a>
        </li>
        <li id="chart">
            <a href="${basePath}/uif/user/modify.do" class="dropdown-toggle">
                <span class="glyphicon glyphicon-edit"></span>
                <span class="menu-text">密码修改</span>
            </a>
        </li>
    </ul>
</div>
<script>
    eval('$("#'+focus+'")').addClass("left_action")
</script>