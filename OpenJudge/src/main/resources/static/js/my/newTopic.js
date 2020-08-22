/*全局变量*/
var questionId = 0; //当前题目id


/*页面加载完毕时调用*/
$(document).ready(function(){
	/*上传题目按钮*/
	// $("#topicContent button#uploadQuestion").click(uploadQuestion);
	/*上传数据点按钮*/
	$("#uploadDataPoint button").click(uploadDataPoint);
	/*给第三个tab标签绑定函数*/
	$(".nav-tabs li").eq(2).click(getAllTestcase);
	/*保存设置按钮*/
	$("#dataPointSet .buttonbox button:eq(1)").click(updateLimit);
	/*同步修改输入框*/
	$("#dataPointSet input").eq(0).bind('input propertychange', function() {changeTimeLimit($(this).val())});
	$("#dataPointSet input").eq(1).bind('input propertychange', function() {changeMemLimit($(this).val())});
	/*迁移题库按钮*/
	$("#topicTransfer button").click(updatePublicType);
	getAllLabel();
});


/*获取题目标签*/
function getAllLabel() {
	$.get(
		Host + RequestUrl.getAllLabel,
		function (data) {
			data = data.content;
			code = "";
			for (var i = data.length - 1; i >= 0; i--) {
				code = `<div class="col-md-1"><input type="checkbox" value="`+data[i].id+`">`+data[i].theLabelName+`</div>`+code;
			}
			$("#theLabels").html(code);
		}
	);
}

//上传题目
function uploadQuestion() {
	//获取输入内容
	var inputBoxes = $("#topicContent").find('.form-control');
	var questionTitle = inputBoxes.eq(0).val();
	var questionBackground = inputBoxes.eq(1).val();
	var questionDescription = inputBoxes.eq(2).val();
	var questionInput = inputBoxes.eq(3).val();
	var questionOutput = inputBoxes.eq(4).val();
	var questionMemLimit = inputBoxes.eq(5).val();
	var questionTimeLimit = inputBoxes.eq(6).val();
	var questionPubType = $("input[name='optionsRadios']:checked").val();
	//开始上传题目
	$.post(
		Host+RequestUrl.uploadQuestion,
		{
			"questionName": questionTitle,
			"background": questionBackground,
			"describtion": questionDescription,
			"input": questionInput,
			"output": questionOutput,
			"publicTypeNum": questionPubType,
			"memoryLimit": questionMemLimit,
			"timeLimit": questionTimeLimit
		},
		function (data) {
			if (data > 0) {
				questionId = data;
				alert("上传成功！接下来请上传测试点。");
				inputBoxes.each(function(){
					$(this).val("");
				});
				$('.nav-tabs li:eq(1) a').tab('show');
			}
		}
	);
}

//上传数据点
function uploadDataPoint() {
	if (questionId == 0) {
		alert("还没有上传题目内容！");
		$('.nav-tabs li:eq(0) a').tab('show');
		return;
	}
	var file = $("#uploadDataPoint input#exampleInputFile").prop('files');
	if (file.length == 0) {
		alert('请选择文件');
		return;
	}
	// var form = new FormData();// 可以增加表单数据
	// form.append("file", file);// 文件对象
	// form.append("questionId", questionId);
	// $.ajax({
	// 	url: Host+RequestUrl.upload,
	// 	type: "post",
	// 	data: form,
	// 	//关闭序列化
	// 	processData: false,
	// 	contentType: false,
	// 	error: function () {
	// 		alert("上传失败");
	// 	},
	// 	success: function (data) {
	// 		alert("上传成功");
	// 	}
	// });
	var form = $("#fileUploadForm");
	form.find("input[name='questionId']").val(questionId);
	var formdata = new FormData(form.get(0));
	// console.log(formdata);
	$.ajax({
		url: Host+RequestUrl.upload,
		type: 'POST',
		cache: false,
		data: formdata,
		processData: false,
		contentType: false,
		error: function () {
			alert("上传失败");
		},
		success: function (data) {
			alert(data);
		}
	});
}

/*获取所有测试点*/
function getAllTestcase() {
	if (questionId == 0) {
		alert("还没有上传题目内容！");
		$('.nav-tabs li:eq(0) a').tab('show');
		return;
	}
	$.get(
		Host+RequestUrl.getAllTestcase,
		{questionId: questionId},
		function (data) {
			// var jsonData = JSON.parse(data);
			var jsonData = data;
			var dataPointBox = $("#dataPointBox");
			dataPointBox.html("");
			for (var i = jsonData.length - 1; i >= 0; i--) {
				dataPointBox.html(
					`<div class="row mr" testcase-id="` + jsonData[i].id + `">
					<div class="col-md-4">` + jsonData[i].testCaseName + ` ` + jsonData[i].remoteFileName + `</div>
					<div class="col-md-4"><div class="input-group b">
					<input type="text" value="` + jsonData[i].timeLimit + `" class="form-control" placeholder="60" aria-describedby="basic-addon2">
					<span class="input-group-addon" id="basic-addon2">ms</span></div></div>
					<div class="col-md-4"><div class="input-group b">
					<input type="text" value="` + jsonData[i].memoryLimit + `" class="form-control" placeholder="60" aria-describedby="basic-addon2">
					<span class="input-group-addon" id="basic-addon2">kb</span></div></div>
					</div>`
					+ dataPointBox.html()
				);
				
			}
		}
	);
}

/*更新测试点内存和时间限制*/
function updateLimit() {
	if (questionId == 0) {
		alert("还没有上传题目内容！");
		$('.nav-tabs li:eq(0) a').tab('show');
		return;
	}
	postData=[];
	$("#dataPointBox").find("[testcase-id]").each(function (index) {
		$(this).find("input").each(function() {
			if ($(this).val() == "") {
				$(this).val(60);
			}
		});
		var a = {
			"id": $(this).attr("testcase-id"),
			"timeLimit": $(this).find("input").eq(0).val(),
			"memoryLimit": $(this).find("input").eq(1).val()
		};
		postData.push(a);
	});
	$.ajax({
		url: Host+RequestUrl.updateLimit,
		type: 'post',
		contentType: 'application/json',
		data: {list: JSON.stringify(postData).replace(/\":\"/g,'":').replace(/\",\"/g,',"').replace(/\"}/g,"}")},
		success: function (data) {
			alert(data);
		}
	});
}


function changeTimeLimit(data) {
	$("#dataPointBox input").each(function (index) {
		if (index % 2 == 0) {
			$(this).val(data);
		}
	});
}


function changeMemLimit(data) {
	$("#dataPointBox input").each(function (index) {
		if (index % 2 == 1) {
			$(this).val(data);
		}
	});
}


function updatePublicType() {
	if (questionId == 0) {
		alert("还没有上传题目内容！");
		$('.nav-tabs li:eq(0) a').tab('show');
		return;
	}
	var questionPubType = $("#topicTransfer input[name='optionsRadios']:checked").val();
	$.post(
		Host+RequestUrl.updatePublicType,
		{
			questionId: questionId,
			code: questionPubType
		},
		function (data) {
			alert(data);
		}
	);
}

function newinoutExample() {
	$('#inoutExamples').html($('#inoutExamples').html()+"<div class='row'><div class='col-md-6'><textarea class='form-control' placeholder='输入样例' style='height: 100px'></textarea></div><div class='col-md-6'><textarea class='form-control' placeholder='输出样例' style='height: 100px'></textarea></div></div>");
}

function uploadQuestion_new() {
	var theExamples = [];
	var inoutExamples = $("#inoutExamples textarea");
	for (var i = inoutExamples.length - 1; i >= 0; i=i-2) {
		theExamples.push({
			exampleInput: inoutExamples.eq(i-1).val(),
      		exampleOutput: inoutExamples.eq(i).val()
      	});
	}

	var labels = [];
	$("#theLabels input[type='checkbox']:checked").each(function () {
		labels.push(parseInt($(this).val()));
	});
	var postData = {
		"background": $("#background").val(),
		"degreeOfDifficulty": $("input[name='difficult']:checked").val().toString(),
		"describtion": $("#describtion").val(),
		"hintsAndInstructions": "",
		"input": $("#input").val(),
		"labels": labels,
		"memoryLimit": parseFloat($("#memoryLimit").val()),
		"output": $("#output").val(),
		"publicTypeNum": parseFloat($("input[name='publicTypeNum']:checked").val()),
		"questionBankId": 0,
		"questionName": $("#questionName").val(),
		"theExamples": theExamples,
		"timeLimit": parseFloat($("#timeLimit").val())
	};
	$.ajax({
		url: Host+RequestUrl.uploadQuestion,
		data: JSON.stringify(postData),
		contentType: "application/json",
		datatype: 'json',
		type: 'post',
		success: function (data) {
			if (data > 0) {
				questionId = data;
				alert("上传成功！接下来请上传测试点。");
				$("input").each(function(){
					$(this).val("");
				});
				$("textarea").each(function(){
					$(this).val("");
				});
				$('.nav-tabs li:eq(1) a').tab('show');
			}
		}
	});
}