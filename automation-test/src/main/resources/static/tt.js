$(function() {
	$.ajaxSetup({
		error : function(result) {
			$("#errorLog").val(JSON.stringify(result));
			alert("操作异常，请联系管理员");
			unloading();
		}
	});
	
	if($("#impuFilter")) {
		$("#impuFilter").keyup(function(){
			var impu = $(this).val();
			if(impu) {
				$("#fileTable tr:gt(0)").each(function(idx, ele) {
					if($(ele).children(":first").text().indexOf(impu) < 0) {
						$(ele).hide();
					} else {
						$(ele).show();
					}
					
				});
			} else {
				$("#fileTable tr").show();
			}
		});
	}
});
function saveTask() {
	var impu = $("#impu").val();
	var traceType = $("input[name='traceType']:checked").val();
	if (!impu) {
		alert("请输入用户号码");
		return;
	}
	if (!traceType) {
		alert("请选择主被叫类型");
		return;
	}
	$('#myModal').modal('hide');
	loading();
	$.post("/neSigTrace/ne/saveTask", {
		"index" : $("#neIndex").val(),
		"traceId" : $("#traceId").val(),
		"impu" : impu,
		"traceType" : traceType
	}, function(result) {
		unloading();
		if (!result) {
			alert("系统异常！");
		} else {
			window.location.reload();
		}
	});
}

function clearModal() {
	$("#traceId").val("");
	$("#impu").val("");
	$("#impu").prop("disabled", false);
	$("input[name='traceType']:checked").prop("checked", false);
}

function add() {
	if ($("#taskTable tr").length == 16) {
		alert("系统最大能创建15个任务");
		return;
	}
	$("#myModalLabel").html("创建跟踪任务");
	clearModal();
	$('#myModal').modal('show');
}

function edit(index) {
	$("#myModalLabel").html("修改跟踪任务");
	clearModal();
	$("#traceId").val($("#traceId_" + index).val());
	$("#impu").val($("#impu_" + index).text());
	var traceType = $("#traceType_" + index).attr("data-traceType");
	$("input[name='traceType'][value='" + traceType + "']").prop("checked",
			true);
	$("#impu").prop("disabled", true);
	$('#myModal').modal('show');
}

function del(index) {
	var traceId = $("#traceId_" + index).val();
	$("#traceIdToDel").val(traceId);
	$("#delModal").modal('show');
}

function confirmDel() {
	$("#delModal").modal('hide');
	loading();
	$.post("/neSigTrace/ne/delTask", {
		"index" : $("#neIndex").val(),
		"traceId" : $("#traceIdToDel").val()
	}, function(result) {
		unloading();
		if (!result) {
			alert("系统异常！");
		} else {
			window.location.reload();
		}
	});
}

/**
 * 修改网元选择下来框
 * 
 * @returns
 */
function changeNetworkElement() {
	var index = $("#neSelect option:selected").val();
	if (index) {
		var href = window.location.href;
		window.location.href = href.substring(0, href.indexOf("/neSigTrace/ne") + 14) + "/"
				+ index;
	} else {
		window.location.href = "/neSigTrace/ne";
	}
}

function loading() {
	var windowHeight = window.innerHeight;
	var scrollHeight = $("body").prop("scrollHeight");
	var maxHeight = windowHeight > scrollHeight ? windowHeight : scrollHeight;
	$("#loading-mask").css("height", maxHeight + "px");
}

function unloading() {
	$("#loading-mask").css("height", "0px");
}

function fetchFile(file) {
	window.location.href="/neSigTrace/file/download?file="+encodeURIComponent(file);
}

function remove(file) {
	$("#fileToDel").val(file);
	$("#removeModal").modal('show');
}

function confirmRemove() {
	loading();
	$.post("/neSigTrace/file/remove", {
		"file" : $("#fileToDel").val(),
	}, function(result) {
		unloading();
		if (!result) {
			alert("系统异常！");
		} else {
			window.location.reload();
		}
	});
}
