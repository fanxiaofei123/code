	$(function(){
		$("#search-button").bind('click', search);
		//退出按钮
		$('#setadmin').popover(
		        {
		            trigger:'focus',
		            placement:'bottom',
		            content:'<a class="btn btn-info btn-xs" role="button" style="whith:30px;" href="'+basePath+'//logout.do">退出</a>',
		            html: true,
		        }
		);
		$("#updateApi").modal({backdrop: 'static', keyboard: false,show:false});
		$("#updateApi").draggable({   
		    handle: ".modal-header",   
		    cursor: 'move',   
		    refreshPositions: false  
		}); 
		 $('.dropdown-menu').find('a').click(function(){
	                    var $text = $(this).html();
	                    var index = $(this).index('a.myselect');
	                    var index1 = $(this).parents('ul').index('.dropdown-menu');
	                    $(this).parentsUntil('div').siblings('input').val($text);
	                    var $myOption = $(this).parentsUntil('div.form-group').siblings('select').children('option').eq(index);
	                    var $myOptionText = $($myOption).text();
	                    var $myOptionVal = $($myOption).val();
	                    $('.mySelect').eq(index1).val($myOptionVal);
                        $('.mySelect').eq(index1).find("option[text='myOptionText']").attr("selected",true);
	                    if($(this).attr('type')=='yes'){
	                        $('.myselect').val('1');
	                        $('.myselect').find("option[text='是']").attr("selected",true);
	                    }
	                    else{
	                        $('.myselect').val('0');
	                        $('.myselect').find("option[text='否']").attr("selected",true);
	                    }
	          });
			$("button[name=close]").click( 
					function () {
						$(':input','#updatedata') 
						.not(':button, :submit, :reset, :hidden') 
						.val('') 
						.removeAttr('checked') 
						.removeAttr('selected');
						$('#addimgurlId').attr('src','');
						$('.edui-body-container').empty();
						$('label.error').remove();
						});

		});
	
	//点击保存按钮   弹出弹框
	function save(){
		$("#updateId").val("");
		//弹框
		$("#title_id").text("新建信息");
		$("#updateApi").modal();
	}
	//批量删除       传入url包括参数名字
	function del(url){
		obj = document.getElementsByName("subBox");
    	check_val = [];
    	var i=0;
    	for(k in obj){
    		if(obj[k].checked){
    			check_val.push(obj[k].value);
    			i++;
    		}
    	}
    	if(i>=1){
    		var txt=  "是否删除选中项？";
    		 var option = {
                     title: "提示",
                     icon: "-48px 0",
                     btn: parseInt("0011",2),
                     onOk: function(){
                    	 $.get(url+"?ids="+check_val,function(data){
                     		// 目前没有返回值  
                     		var infolist = jQuery.parseJSON(data);
                 			window.location.reload();
                     	});
                     }
                 }
    	}else{
    		var txt=  "请选中再删除";
    		 var option = {
                     title: "提示",
                     icon: "-48px 0",
                     btn: parseInt("0011",2)
                 }
    	}
//    	 给出参数
        window.wxc.xcConfirm(txt, "custom", option);
	}
	//设置页面的列表相关的信息
	function setPage(infolist){
		// 加载页面 发ajax 初始化全局变量     不要改动
		$("#homepage").attr("val",infolist.page.lastList+"&cPg=1");
		$("#lastpage").attr("val",infolist.page.lastList+"&cPg="+infolist.page.previousPage);
		$("#nextpage").attr("val",infolist.page.lastList+"&cPg="+infolist.page.nextPage);
		$("#endpage").attr("val",infolist.page.lastList+"&cPg="+infolist.page.totalPages);
		// 对页面做初始化       不要改动
		$("#totalRows").text(infolist.page.totalRows);
		$("#currentPage").text(infolist.page.currentPage);
		$("#totalPages").text(infolist.page.totalPages);
	}
	//分页跳转
	function reloadPaging(obj){
		var url = $(obj).attr("val");
		show(url);
	}
	// 	搜索



	function search() {
		var url=$("form[name='searchForm']").attr("action")+"?"+$("form[name='searchForm']").serialize();
		show(url);
	}



	//确认保存或者修改          提交表单
	function confirmUpdate(obj){
	//确定修改 或者保存
		$.get($("form[name='subForm']").attr("action")+"?"+$("form[name='subForm']").serialize(), 
				  function(data){
						var infolist = jQuery.parseJSON(data);
						alert(infolist.msg);
						window.location.reload();
				});
	}
	$(function(){
		 $("#search").keydown(function(e){
			 var e = e || event,//兼容ie
			 keycode = e.which || e.keyCode;
			 if (keycode==13) {
				 $("#search").append("<input type='text' style='display: none'/>");
				 //$("form[name='searchForm']").attr("action","");
				 $(".btn btn-search glyphicon glyphicon-search").attr("onclick",search());
			 }
			});
		
	});
	
// 	搜索
	function changeValue(value) {
		if(null==value||"undefined"==value){
			return "";
		}
		return value;
	}
	
// 	加载loading
	function showLoading(str){
		$("#youeLoadingStr").empty().append(str);
		$("#youeLoading").removeClass('hide').dialog({
			dialogClass: "loading-no-close",
			minHeight: 50,
			resizable: false,
			modal: true,
			show:{effect:"fade"},hide:{effect:"fade"}
		});
	};
	function hideLoading(){
		$("#youeLoading").dialog("close");
	};
	
	//上传图片-预览图片
	function setImagePreview(fileName,preview) {
	    var docObj = document.getElementById(fileName);
	    var imgObjPreview = document.getElementById(preview);
	    if (docObj.files && docObj.files[0]) {
	        //火狐下，直接设img属性 
	        imgObjPreview.style.display = 'block';
	        imgObjPreview.style.width = '100px';
	        imgObjPreview.style.height = '100px';
	        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式 
	        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
	    }
	}
	
	//修改页面-预览图片
	function previewImg(imgId, url){
		if (YOUE.Object.notNull(imgId) && YOUE.Object.notNull(url)) {
			var imgObjPreview = document.getElementById(imgId);
			if (imgObjPreview != undefined) {
		        //火狐下，直接设img属性 
		        imgObjPreview.style.display = 'block';
		        imgObjPreview.style.width = '100px';
		        imgObjPreview.style.height = '100px';
		        imgObjPreview.src = url;
			}
		}
	}
	
	//月份转换
	function changeMonth(month){
		if (month == 1) {
			return "一月";
		} else if (month == 2){
			return "二月";
		} else if (month == 3){
			return "三月";
		} else if (month == 4){
			return "四月";
		}else if (month == 5){
			return "五月";
		}else if (month == 6){
			return "六月";
		}else if (month == 7){
			return "七月";
		}else if (month == 8){
			return "八月";
		}else if (month == 9){
			return "九月";
		}else if (month == 10){
			return "十月";
		}else if (month == 11){
			return "十一月";
		}else if (month == 12){
			return "十二月";
		}else {
			return "无";
		}
	}
	
	//季度转换
	function changeQuarter(quarter){
		if (quarter == 1) {
			return "第一季度";
		} else if (quarter == 2){
			return "第二季度";
		} else if (quarter == 3){
			return "第三季度";
		} else if (quarter == 4){
			return "第四季度";
		}else {
			return "无";
		}
	}
	//选取月份，季度禁用
	function setMonth(obj){ 
		$("#indexMonth").attr("disabled",false);
		$("#indexQuarter").attr("disabled",false);
	    var val = obj.value;  
	    if(val != 0){  
	        $("#indexQuarter").attr("disabled",true);
	    }   
	} 
	//选取季度，月份禁用
	function setQuarter(obj){ 
		$("#indexMonth").attr("disabled",false);
		$("#indexQuarter").attr("disabled",false);
	    var val = obj.value;  
	    if(val != 0){  
	        $("#indexMonth").attr("disabled",true); 
	    }  
	} 
	
	