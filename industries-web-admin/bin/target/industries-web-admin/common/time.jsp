<script src="${basePath }/static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${basePath }/static/plugins/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
$('input[name="params.startTimeStr"]').datetimepicker({
	format: 'yyyy-mm-dd',
	minView:3,
	maxView:4,
	autoclose: true
});
$('input[name="params.endTimeStr"]').datetimepicker({
	format: 'yyyy-mm-dd',
	minView:3,
	maxView:4,
	autoclose: true
});
</script>