/**
 * Created by Administrator on 2016/7/12.
 */
//发送AJAX同时响应页面做展示   不同页面显示不同内容
$(function () {
    show(basePath + "/building/abuilding/listByModel.do");
    //ajax提交表单
    function updateData(form) {
//			var year = $("#indexYear").val();
//			var month = $("#indexMonth").val();
        $('#updatedata').ajaxSubmit({
            type: "post",
            dataType: "json",
            url: basePath + "/building/abuilding/add.do",
            success: function (data) {
                var status = data.status;
                var msg = data.msg;
                if (status == "1001") {
                    window.wxc.xcConfirm("您添加的当前时间(" + year + "年" + month + "月" + ")数据已存在，不能重复添加", window.wxc.xcConfirm.typeEnum.error);
                } else {
                    window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.info, {
                        onOk: function (v) {
                            window.location.reload();
                        },
                        onClose: function () {
                            window.location.reload();
                        }
                    });
                }
            }
        });
    }

});
function show(url) {
    showLoading("数据正在加载中...");
    $.get(url, function (data) {
        var str = "";
        var infolist = jQuery.parseJSON(data);
        page = infolist.page;
        if (infolist.entities.length != 0) {
            if(page.currentPage != 1){
                var countSort = (page.currentPage-1)*10;
                }
            else{
                var countSort = 0;
                }
            var newCountSort = parseInt(countSort);
            for (var i = 0; i < infolist.entities.length; i++) {
                newCountSort += 1;
                var o = infolist.entities[i];
                str += "<tr>" +
                    "<td class='center'>" + newCountSort+ "</td>" +
                    "<input type='hidden' id='buildingId' class='ace' str-id=" + o.streetId + " com-id=" + o.communityId + "  name='subBox' value=\"" + o.id + "\"/>" +
                    "<td class='center bName'  style='cursor: pointer;color: #563BF5'>" + changeValue(o.name) + "</td>" +
                    "<td class='center'>" + changeValue(o.address) + "</td>" +
                    "<td class='center'>" + changeValue(o.streetName) + "</td>" +
                    "<td class='center'>" + changeValue(o.communityName) + "</td>" +
                    "<td class='center bName'><a style='cursor: pointer'>编辑</a></td>" +
                    "<td class='center'><a style='cursor: pointer' onclick='delBuilding(" + o.id + ")'>删除</a></td>"
                "</tr>";
            }
            document.getElementById('hiddenBuClass').style.display = "none";
            document.getElementById('pageBuClass').style.display = "block";
            $("#tbody").html(str);
            //设定page的属性
            setPage(infolist);
            buildingBtn();
        }
        else if (infolist.entities.length == 0) {
            $("#tbody").html(str);
            document.getElementById('hiddenBuClass').style.display = "block";
            document.getElementById('pageBuClass').style.display = "none";
        }
        hideLoading();
    });
}
//点击修改按钮对弹框内的值做初始化  修改、回显
function update() {
    $("#title_id").text("修改信息");
    obj = document.getElementsByName("subBox");
    var id;
    var i = 0;
    for (k in obj) {
        if (obj[k].checked) {
            i++;
            if (i > 1) {
                var txt = "只能选择单个";
                window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
                return;
            }
            id = obj[k].value;
        }
    }
    if (i <= 0) {
        var txt = "请选择";
        window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
        return;
    }
    $("#updateApi").modal();
    //弹框同时加载数据
    $.get(basePath + "/building/abuilding/detail.do?id=" + id,
        function (data) {
            var infolist = jQuery.parseJSON(data);
            $("#updateId").val(infolist.entity.id);
            $("#name").val(infolist.entity.name);
            $("#address").val(infolist.entity.address);
            $("#streetName").val(infolist.entity.streetName);
            $("#communityName").val(infolist.entity.communityName);
            $("#enterpriseName").val(infolist.entity.enterpriseName);
            $("#enterpriseRegistNumber").val(infolist.entity.enterpriseRegistNumber);
        });
}

function buildingBtn() {
    $(".bName").on('click', function () {
        var thee = $(this);
        var ClickName = thee[0].innerText;
        var that = $(this).closest("tr");
        //signUpdate
        showUpdate();
        var id = {};
        var parm = that.find("td");
        parmId = that.find("input").attr("str-id");
        karmId = that.find("input").attr("com-id");
        initHead(parm);
        buildingId = that.find('input').val();
        initSelect(basePath + "/admin/street/selectAll.do", $("#addStreet"), parm);
        initSelect1(basePath + "/admin/community/selectAll.do", $("#addCommunity"), parmId, karmId);
        showEp(buildingId,ClickName);
        $("#building_id").val(buildingId);
        changeHead(this);

        $(".nav-list > li").click(function () {
            var $inputs = $("#mytable").find("input[disabled = 'disabled']");
            var $inputClass = $(".inputClass");
            var newBuildingEnterprises = [];
            var newLength = $inputs.length;
            for(var j = 0;j < newLength;j++) {
                newBuildingEnterprises.push($inputs.eq(j).val());
            }
            for(var j = 0;j < $inputClass.length;j++) {
                newBuildingEnterprises.push($inputClass.eq(j).val());
            }
            if(newBuildingEnterprises.sort().toString() != buildingEnterprises.sort().toString()) {
                changeFlag = true;
            }
            if (changeFlag) {
                var flag = confirm("是否保存当前页面修改？");
                if (flag) {
                    updateInfo();
                } else {
                    return;
                }
            }
        });
    });

    searchEp();
}

/*改变title*/
function changeHead(that) {
    var navi = $(".st-navi").find('small');
    $(navi[0]).find('span').html("<span onclick='window.location.reload()' style='cursor: pointer'>楼宇管理</span>&nbsp;&nbsp;&nbsp; <i class='icon-double-angle-right'></i>数据更新");
}

/*下拉框街道鼠标选中时改变对应的社区*/
function myStreet() {
    var streetId = $("#addStreet").val();
//sign
    $.ajax({
        type: "post",
        url: basePath + "/building/queryByStreetId.do",
        data: {"id": streetId},
        dataType: "json",
        success: function (data) {
            var str = "";
            for (var i = 0; i < data.length; i++) {
                var o = data[i];
                str += "<option value=" + o.communityId + ">" + o.communityName + "</option>"
                $("#addCommunity").html(str);
            }
        }
    });
}


function searchEp() {
    $(".search-ep").on('click', function () {
        var that = $(this);
        var inputValue = that.closest('tr').find("input").val();
        $.ajax({
            type: "get",
            url: basePath + "/building/selectByPrimaryKey.do",
            data: {"registerNumber": inputValue},
            success: function (obj) {
                if (obj.data == null) {
                    window.wxc.xcConfirm(obj.msg, "消息提示");
                    that.closest('tr').find("input").val("");
                    that.closest('tr').next().find('td')[1].innerHTML = "";
                } else {
                    enterpriseId = obj.data.id;
                    var enterpriseName;
                    enterpriseName = obj.data.name;
                    that.closest('tr').find("input[name='enterpriseId']").val(enterpriseId);
                    //that.closest('tr').find("input")[1].val(enterpriseId);
                    that.closest('tr').next().find('td')[1].innerHTML = enterpriseName;
                }
            },
            error: function () {
                alert("请输入合法的企业注册号");
            }
        });
    });
}
function showEp(id,name) {
    var clickName = name;
    var buildingId = id;
    $.ajax({
        type: "post",
        url: basePath + "/building/queryBuildingInfo.do",
        data: {"id": buildingId},
        dataType: "json",
        success: function (data) {
            var enterpriseId;
            if (data && data.length > 0) {
                var $str2 = $("#mytable").find("tr").eq(0);
                var $str3 = $("#mytable").find("tr").eq(1);
                $("#mytable").empty();
                $("#mytable").append($str2);
                $("#mytable").append($str3);
                for (var i = 0; i < data.length; i++) {
                    enterpriseId = data[i].enterpriseId;
                    enterpriseName = data[i].enterpriseName;
                    var registerNumber = data[i].registerNumber;
                    var $str1 = $("<tr class='tr-set'> <td> <span>企业注册号</span> </td> <td> " +
                        "<input type='text'  value=" + registerNumber + " disabled='disabled' style='float: left'>" +
                        "&nbsp;<i class='glyphicon glyphicon-remove-circle delClass' onclick='delByBuildingIdAndEnterpriseId(" + enterpriseId + "," + buildingId + ",this)' style='display: none;font-size: 18px;color: #6E6E74'></i>" +
                        "</td>" +
                        "</tr>");
                    $("#mytable").append($str1);
                    //sign
                    var $str = $("<tr class='tr-set'><td> <input  type='hidden'   name='enterpriseId'    value=" + data[i].enterpriseId + "   /><span>企业名称:</span> </td> <td> <span>" + enterpriseName + "</span>" +
                        "</td>" +
                        "</tr>");
                    $("#mytable").append($str);
                }
                initData();
            }
            if (clickName=="编辑"){
                editClick();
            }
        },
        error: function () {
        }
    });
}


function showUpdate() {
    $("#building").css('display', 'none');
    $("#update").show();
}

/*给街道下拉框付默认值*/
function initSelect(url, obj, name) {
    var str = "";
    /*街道名称*/
    var streetName = name[3].innerHTML;
    $.get(url, function (data) {
        var data = jQuery.parseJSON(data);
        if (data.status == 1000) {
            for (var i = 0; i < data.entities.length; i++) {
                var o = data.entities[i];
                if (o.name == streetName) {
                    str += "<option value=" + o.id + " selected='true'>" + o.name + "</option>"
                    $("#addStreet").attr("disabled", true);
                    $("#addStreet").css("background-color", "#E8E8E8");
                }
                else {
                    str += "<option value=" + o.id + ">" + o.name + "</option>";
                }
            }
        }
        if (streetName == "" || streetName == null) {
            str += "<option selected = 'true'>" + "" + "</option>";
        }
        obj.html(str);
    });
}

/*给社区下拉框付默认值*/
function initSelect1(url, obj, strId, comId) {
    init(strId,comId);
}
function init(strId,comId) {
    var streetId = strId;
    var communityId = comId;
    $.ajax({
        type: "post",
        url: basePath + "/building/queryByStreetId.do",
        data: {"id": streetId},
        dataType: "json",
        success: function (data) {
            var str = "";
            for (var i = 0; i < data.length; i++) {
                var o = data[i];
                if (o.communityId == communityId) {
                    str += "<option value=" + o.communityId + " selected='true'>" + o.communityName + "</option>"
                    $("#addCommunity").attr("disabled", true);
                    $("#addCommunity").css("background-color", "#E8E8E8");
                }
                else {
                    str += "<option value=" + o.communityId + ">" + o.communityName + "</option>"
                }
            }
            $("#addCommunity").html(str);
        },
        error: function () {
        }
    });
}

//sign
function addNewEnterprise() {
    var str = "<tr class='tr-set'>" +
        "<td><span>企业注册号</span></td>" +
        "<td><input type='text' class='inputClass'  placeholder='企业注册号'>" +
        "<input	type='hidden' name='enterpriseId'  id='enterpriseId'> " +
        "<i class='glyphicon glyphicon-search search-ep' style='font-size: 18px;margin-left: -22px;color: #6E6E74'></i>" +
        "&nbsp;<i class='glyphicon glyphicon-remove-circle delete-ep' onclick='delNewEnterprise(this)' style='font-size: 18px;color: #6E6E74'></i>" +
        "</td>" +
        "</tr>" +
        "<tr class='tr-set' class='trClass'>" +
        "<td><input type='hidden'/><span>企业名称:</span ></td>" +
        "<td><span class='newView' id=spanId>" + "请搜索" + "</span></td>" +
        "</tr>";
    ;
    $("#mytable").append(str);
    searchEp();
}

function initHead(parm) {
    var name = parm[1].innerHTML;
    var address = parm[2].innerHTML;
    $("#bName").html(name);
    $("#bAddress").html(address);
}


function queryBuildingById(id) {
    $("#streetFrom").modal('show');

}

/*删除楼宇中的企业*/
function delByBuildingIdAndEnterpriseId(enterpriseId, buildingId, obj) {
    var that = $(obj);
    var btnobj = that.parent().parent();
    var btnobj1 = that.parent().parent().next();
    var eId;
    var bId;
    eId = enterpriseId;
    bId = buildingId;
    var gnl = confirm("确定删除吗?");
    if (gnl == false) {
        return;
    }
    $.ajax({
        type: "post",
        url: basePath + "/building/delByBuildingIdAndEnterpriseId.do",
        data: {"enterpriseId": eId, "buildingId": bId},
        dataType: "json",
        success: function (result) {
            btnobj.remove();
            btnobj1.remove();
        },
        error: function () {
        }
    });
}

//signs
function updateInfo() {
    var url = basePath + "/building/updateByBuildingId.do";
    $('#updateForm').ajaxSubmit({
        type: "post",
        dataType: "json",
        url: url,
        success: function (data) {
            if (data.code == 501) {
                window.wxc.xcConfirm(data.msg, "消息提示");
            }
            else if (data.code == 500) {
                window.wxc.xcConfirm(data.msg, "消息提示");
            }
            else if (data.code == 200) {
                window.wxc.xcConfirm(data.msg, "消息提示");
                updateBtn1();
            }
            initData();
        }
    });

}
function initData() {
    var $inputs = $("#mytable").find("input[disabled = 'disabled']");
    disabledLength = $inputs.length
    changeFlag = false;
    $("#addCommunity,#addStreet").change(function () {
        changeFlag = true;
    });
    buildingEnterprises = [];
    for (var j = 0; j < disabledLength; j++) {
        buildingEnterprises.push($inputs.eq(j).val());
    }
}


/*点击编辑切换按钮*/
function editClick() {
    $(".colseBtn").css('display', 'none');
    $(".editBtn").css('display', 'none');
    $(".restBtn").css('display', 'block');
    $(".addBtn").css('display', 'block');
    $(".delClass").css('display', 'block');
    $(".delClass").css('float', 'left');
    // $("#mytable input").attr('disabled',false)
    $(".addNewEnterprise").css('display', 'block');
    $("#addCommunity").attr("disabled", false);
    $("#addCommunity").css("background-color", "white");
    $("#addStreet").attr("disabled", false);
    $("#addStreet").css("background-color", "white");
   
}

/*关闭按钮*/
function closeBtn() {
    show(basePath + "/building/abuilding/listByModel.do");
    $("#building").show();
    $("#update").css('display', 'none');
}

/*取消按钮*/
function cancelBtn() {
    showEp(buildingId);
    $(".colseBtn").css('display', 'block');
    $(".editBtn").css('display', 'block');
    $(".restBtn").css('display', 'none');
    $(".addBtn").css('display', 'none');
    $("#addStreet").val(parmId);
    $("#addCommunity").val(karmId);
    var streetId = parmId;
    var communityId = karmId;
    init(parmId,karmId);

    $("#addCommunity").find("option[value =" + karmId + "]").attr("selected","true");
    $(".addNewEnterprise").css('display', 'none');
    $("#addCommunity").attr("disabled", true);
    $("#addCommunity").css("background-color", "#E8E8E8");
    $("#addStreet").attr("disabled", true);
    $("#addStreet").css("background-color", "#E8E8E8");

}

/*更新按钮*/
function updateBtn1() {
    $(".colseBtn").css('display', 'block');
    $(".editBtn").css('display', 'block');
    $(".restBtn").css('display', 'none');
    $(".addBtn").css('display', 'none');
    $(".addNewEnterprise").css('display', 'none');
    $("#addCommunity").attr("disabled", true);
    $("#addCommunity").css("background-color", "#E8E8E8");
    $("#addStreet").attr("disabled", true);
    $("#addStreet").css("background-color", "#E8E8E8");
    var addStreet1 = $("#addStreet").val();
    var addCommunity1 = $("#addCommunity").val();
    $("#addCommunity").val(addCommunity1);
    $("#addStreet").val(addStreet1);
    $(".inputClass").attr("disabled", true);
    $(".search-ep").css('display', 'none');
    $(".delete-ep").css('display', 'none');
    $(".delClass").css('display', 'none');
}

function delBuilding(building) {
    var id = building;
    $.ajax({
        type: "get",
        url: basePath + "/building/queryEnterpriseCount.do",
        data: {"id": id},
        success: function (result) {
            var count = result.data;
            var gnl = confirm("楼宇内有" + count + "家企业,确定删除?");
            if (gnl) {
                $.ajax({
                    type: "get",
                    url: basePath + "/building/deleteByPrimaryKey.do",
                    data: {"id": id},
                    success: function (result) {
                        alert("删除成功！");
                        window.location.reload();
                    },
                    error: function () {
                        alert("删除失败！")
                    }
                });
                return true;
            }
            else {
                return false;
            }
        },
        error: function () {
        }
    });
}

/*删除新增企业中的搜索框*/
function delNewEnterprise(obj) {
    var that = $(obj);
    var delbtnobj = that.parent().parent();
    var delbtnobj1 = that.parent().parent().next();
    delbtnobj.remove();
    delbtnobj1.remove();
}



