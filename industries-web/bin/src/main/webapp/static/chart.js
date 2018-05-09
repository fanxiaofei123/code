// 线图或柱图  divUrl：标签元素的id; dataInfo:数据源; chartType：图表类型;
function barOrLineChar(divUrl,dataInfo,chartType){
    var obj = document.getElementById(divUrl);
	var myChart = echarts.init(obj);
	var items = dataInfo.datas;
	var keys = []; // 一级指标的名称
	var quarters = []; // 指标季度
	if(!chartType){
		chartType="line"; // 默认图标为线图
	}
	option = {
		tooltip : {
			trigger: 'axis'
		},
		legend: {
			data:function(){
				for (var key in items) {
                    keys.push(key);
				}
				return keys;
			}()
		},
		grid: {
			left: '3%',
			right: '1%',
			bottom: '8%',
			containLabel: true
		},
		xAxis : [
			{
				type : 'category',
				axisLabel: {
					interval:0,
					rotate:30
				},
				boundaryGap : false,
				data:function(){
					for (var key in items["环境支撑"]) { // 取某一个集合获取季度
						quarters.push(key);
					}
					return quarters;
				}()
			}
		],
		yAxis : [
			{
				type : 'value'
			}
		],
		series : function(){
			var item = [];
            for (var key in items) {
                var tempObject = items[key];
				item.push({
					name:key,
					type:chartType,
					// stack: "总量",
					// label: {
					// 	normal: {
					// 		show: true,
					// 		position: 'top'
					// 	}
					// },
					// smooth:true,
					// areaStyle: {normal: {}},
					// areaStyle:function(){
					// 	return {normal: { // 区域填充
					// 		color:"#d7f7f0"
					// 	}};
					// }(),
					data:function(){
                        var tempValue = [];
                        for (var key in tempObject) {
                            tempValue.push(tempObject[key]);
                        }
                        return tempValue;
                    }(),
                    symbolSize:8,
                    lineStyle:{
                        normal:{
                            width:3
                        }
                    }
				})
			}
			return item;
		}()
	};
	myChart.setOption(option);
	return myChart;
}