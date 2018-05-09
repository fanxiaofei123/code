<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>成都市中小微企业运行情况分析系统 - 标权重设置</title>
    <jsp:include page="/common/base_tags.jsp" flush="true"/>
    <link href="${basePath}/images/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <link rel="stylesheet" href="${basePath}/static/css/building/building.css">
    <script>
        var focus="indexSet";
        //        company floor street import indexSet chart
    </script>
    <style>
        .tron {background:white !important;}
        .table-striped>tbody>tr:nth-child(odd)>td, .table-striped>tbody>tr:nth-child(odd)>th {
            background: none;
        }
    </style>
    <%@include file="/common/treetable.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#treeTable").treeTable({expandLevel: 3});
            if($("#msg").val() != null && $("#msg").val() != "") {
                top.layer.alert($("#msg").val());
            }

            $("table tr:gt(0)").hover(function(){ //tr:gt(0)表示不选第一行，因为第一行往往是标题
                $(this).addClass("tron");
            },function(){
                $(this).removeClass("tron");
            });
        });
    </script>
</head>
<body>
<jsp:include page="/common/head.jsp" flush="true"/>
<div class="main-container" id="main-container">
    <input type="hidden" id="msg" name="msg" value="${msg}">
    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <%@include file="/common/menu.jsp" %>
        <div class="main-content">
            <div class="page-content st-navi">
                <div class="page-header">
                    <h1>
                        <small>
                            <i class="icon-double-angle-right"></i>
                            <span>指标权重设置</span>
                        </small>
                    </h1>
                </div>
                <div style="padding: 10px">
                    请选择产业类型：<select id="selectIndex" onchange="onChangeIndex()">
                    <option value="1" <c:if test="${industryId eq 1}">selected</c:if>>第一产业</option>
                    <option value="2" <c:if test="${industryId eq 2}">selected</c:if>>第二产业</option>
                    <option value="3" <c:if test="${industryId eq 3}">selected</c:if>>第三产业</option>
                    </select>
                </div>
                <form id="industryForm" name="industryForm" method="post" action="${basePath}/indexRate/save.do">
                    <input type="hidden" id="industryId" name="industryId" value="${industryId}">
                   <div class="row" id="building">
                    <table id="treeTable" class="table table-striped table-bordered table-condensed">
                        <tr>
                            <th width="30%">指标名称</th>
                            <th width="20%">数据来源</th>
                            <th width="20%">指标等级</th>
                            <th width="30%">权重(%)</th>
                        </tr>
                        <c:set var="index" value="0" />
                        <c:forEach items="${entities}" var="indexDefinition" varStatus="status">
                            <tr id="${indexDefinition.id}" pId="${indexDefinition.parent.id ne 0?indexDefinition.parent.id:0}">
                                <td>${indexDefinition.name}</td>
                                <td>${indexDefinition.dataSource}</td>
                                <c:if test="${indexDefinition.level eq 1}">
                                    <c:set var="level" value="一级"/>
                                </c:if>
                                <c:if test="${indexDefinition.level eq 2}">
                                    <c:set var="level" value="二级"/>
                                </c:if>
                                <c:if test="${indexDefinition.level eq 3}">
                                    <c:set var="level" value="三级"/>
                                </c:if>
                                <td>${level}</td>
                                <td>
                                    <c:if test="${indexDefinition.level eq 1 or indexDefinition.level eq 2}">
                                        <input type="hidden" id="indexRateChildList${index}_pid" name="indexRateChildList[${index}]._pid" value="${indexDefinition.parent.id}"/>
                                        <input type="hidden" id="indexRateChildList${index}_id" name="indexRateChildList[${index}].id" value="${indexDefinition.indexRate.id}"/>
                                        <input type="hidden" id="indexRateChildList${index}_indexId" name="indexRateChildList[${index}].indexId" value="${indexDefinition.id}"/>
                                        <input type="hidden" id="indexRateChildList${index}_level" name="indexRateChildList[${index}].level" value="${indexDefinition.level}"/>
                                        <span><font color="red">*</font></span><input id="indexRateChildList${index}_rate" name="indexRateChildList[${index}].rate" value="${indexDefinition.indexRate.rate}" type="text" style="width: 60px"/>%
                                        <span id="msg${index}_id"></span>
                                        <c:set var="index" value="${index+1}"/>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div style="margin-left: 50%">
                        <button type="button" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="saveRate()" title="保存"><i class="fa fa-plus"></i> 保存</button>
                    </div>
                </div>
            </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${basePath}/static/js/util/map.js"></script>
<script type="text/javascript">

    function onChangeIndex() {
        var industryId = $("#selectIndex").val();
        $("#industryId").val(industryId);
        window.location = "${basePath}/indexDefinition/getIndexDefinitionByIndustry.do?industryId="+ industryId;
    }

    function saveRate() {
        var index = "${index}";
        //1.校验参数是否为空、是否为数字
        if (checkParam(index)) {
            return;
        }
        //2.校验指数的值是否为100%
        if (calculateSum(index) ) {
            return;
        }
        $("#industryForm").submit();
    }

    function checkParam(index) {
        var flag = false;
        for (var i = 0; i < index; i++) {
            var idStr = 'indexRateChildList' + i + '_rate';
            var msgStr = 'msg'+ i + '_id';
            if ($('#'+idStr+'').val() == null || $('#'+idStr+'').val().trim() == "") {
                $('#'+idStr+'').css("background-color", "red");
                $('#'+msgStr+'').html('权重不能为空');
                $('#'+msgStr+'').css("color", "red");
                flag = true;
            } else if (!(/^(\d+(\.\d{1,2})?)$/g.test($('#'+idStr+'').val().trim()))) {
                $('#'+idStr+'').css("background-color", "red");
                $('#'+msgStr+'').html('权重只能为数字，且最多两位小数');
                $('#'+msgStr+'').css("color", "red");
                flag = true;
            }  else {
                $('#'+idStr+'').css("background-color", "white");
                $('#'+msgStr+'').html('');
            }
        }
        return flag;
    }

    function calculateSum(index) {
        var flag = false;
        var oneLevelSum = 0; //一级指标总和
        var childMap = new Map();//除一级以外的指标map存放
        var oneLevelPid = 0; //一级指标父节点编号

        var unitCount = 100; //默认100为标准
        for (var i = 0; i < index; i++) {
            var idStr = 'indexRateChildList' + i + '_rate';//权重
            var levelStr = 'indexRateChildList' + i + '_level';//等级
            var pidStr = 'indexRateChildList' + i + '_pid';//父节点
            var pid_str = $('#'+pidStr+'').val();
            var levelInt = parseInt($('#'+levelStr+'').val());
            if (levelInt == 1) {
                oneLevelSum += parseFloat($('#'+idStr+'').val());
                oneLevelPid = parseInt(pid_str);
            } else if (levelInt > 1 ) {
                var newRate = $('#'+idStr+'').val();
                if (childMap.get(pid_str)) {
                    var rate = childMap.get(pid_str);
                    childMap.removeByKey(pid_str);
                    childMap.put(pid_str, rate + parseFloat(newRate));
                } else {
                    childMap.put(pid_str, parseFloat(newRate));
                }
            }
        }

        for (var j = 0; j< childMap.size(); j++) {
            var key = childMap.element(j) == null ? 0 : childMap.element(j).key;
            var value = childMap.element(j) == null ? 0 : childMap.element(j).value;
            if (value != unitCount) {
                for (var h = 0; h < index; h++) {
                    var idStr = 'indexRateChildList' + h + '_rate';
                    var msgStr = 'msg'+ h + '_id';
                    var pidStr = 'indexRateChildList' + h + '_pid';//pid
                    if ($('#'+pidStr+'').val() == key) {
                        $('#' + idStr + '').css("background-color", "red");
                        $('#' + msgStr + '').html('权重不等于100%，请重新调整权重');
                        $('#' + msgStr + '').css("color", "red");
                        flag = true;
                    }
                }
            }
        }

        if (oneLevelSum != unitCount) {
            for (var p = 0; p < index; p++) {
                var idStr = 'indexRateChildList' + p + '_rate';
                var msgStr = 'msg'+ p + '_id';
                var pidStr = 'indexRateChildList' + p + '_pid';//pid
                if (parseInt($('#'+pidStr+'').val()) == oneLevelPid) {
                    $('#' + idStr + '').css("background-color", "red");
                    $('#' + msgStr + '').html('权重不等于100%，请重新调整权重');
                    $('#' + msgStr + '').css("color", "red");
                    flag = true;
                 }
             }
        }

        return flag;
    }
</script>
</body>
<jsp:include page="/common/bottom.jsp" flush="true"/>
</html>