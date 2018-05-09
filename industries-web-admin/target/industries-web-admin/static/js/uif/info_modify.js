function confirmMdify() {

    if ($("#userInfoModify").valid()) {

        showLoading("密码修改成功，正在跳转...请稍后...");
        $('#userInfoModify').ajaxSubmit({
            type: "post",
            dataType: "json",
            url: basePath + "/uif/aUser/modifyUserInfo.do",
            success: function (data) {

                if (data != null && data.status == "200") {  //如果密码修改成功,条状到登录界面
                    setTimeout(function () {
                        window.location.href = basePath + "/admin/index.do";
                    }, 2000);
                } else {
                    hideLoading();
                    var txt = data.msg;
                    window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
                }
            }
        });
    }
}

$(function () {
    $(".nav-list > li").click(function () {
        if ($("#userInfoModify").valid()) {
            var flag = confirm("是否保存新密码？");
            if (flag) {
                confirmMdify();
            } else {
                return;
            }
        } else {
            $("#oldPassword-error").hide();
            $("#userPasswordNew-error").hide();
            $("#userPasswordConfirm-error").hide();
        }
    });

    $("#loginBtn").on('click', function () {
        confirmMdify();
    });


    $("input[name = 'userPassword']").on('click', function () {
        $("label[for='userPasswordConfirm']").removeClass();
        $("label[for='userPasswordConfirm']").addClass('error').css("color", "red");
        // $("#passwdTips").css('display', 'none');
        $("label[for='userPasswordNew']").removeClass();
        $("label[for='userPasswordNew']").addClass('error').css("color", "red");
    });

    $("body").on('click', function () {
        var newPwdValue = $("input[name = 'userPasswordNew']").val();
        if (newPwdValue == undefined || newPwdValue == '') {
            $("label[for='userPasswordNew']").hide();

        } else {
            $("label[for='userPasswordNew']").addClass('error');
            $("label[for='userPasswordConfirm']").removeClass();
            $("label[for='userPasswordConfirm']").addClass('error').css("color", "red");
        }
    });

    $("input[name = 'userPasswordNew']").on('click', function () {
        $("label[for='userPasswordConfirm']").removeClass();
        $("label[for='userPasswordConfirm']").addClass('error').css("color", "red");

        var newPwdValue = $("input[name = 'userPasswordNew']").val();
        // console.log(newPwdValue);
        // if (!newPwdValue) {
        //     var errorMsg = $("label[for='userPasswordNew']");
            // errorMsg.hide();
            // $("#passwdTips").show();
        // }

    });
    //验证提交表单
    $("#userInfoModify").validate({
        submitHandler: confirmMdify,
        rules: getRules(),
        messages: validateMessages(

        ),
        success: function (label) {
            label.html('').addClass('glyphicon glyphicon-ok').css("color", "green");
        }
    });


    $(".ud-pwd-md").keydown(function (event) {
        var that = $(this).closest('div').find('label');
        var newPasswdName = $(this).attr('name');
        // if (newPasswdName == 'userPasswordNew') {
        //     $("#passwdTips").css('display', 'none');
        // }
        that.removeClass();
        that.addClass('error').css("color", "red");
    });

});



