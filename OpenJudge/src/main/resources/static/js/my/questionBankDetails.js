/*全局变量*/
var userId = 0; //用户id
var questionName; //题目标题

/*页面加载完毕时调用*/
$(document).ready(function () {
	$("#releaseQuestionSolution").attr("onclick", "location.href='releaseQuestionSolution.html?id=" + $_GET['id'] + "'");
	$("#wzxq button").click(function () {
		submitCode();
	});
	getQuetionDetail();
	$("#codeModal").modal('hide');
	$("#showHistory").click(function () {
		getSubmits();
	})
});

/*仿php的接收get参数*/
var $_GET = (function () {
	var url = window.document.location.href.toString();
	var u = url.split("?");
	if (typeof (u[1]) == "string") {
		u = u[1].split("&");
		var get = {};
		for (var i in u) {
			var j = u[i].split("=");
			get[j[0]] = j[1];
		}
		return get;
	} else {
		return {};
	}
})();

/*获取题目的各种信息*/
function getQuetionDetail() {
	//获取题目内容
	$.get(
		Host + RequestUrl.getQuestionById,
		{ questionId: $_GET['id'] },
		function (data) {
			// var jsonData = JSON.parse(data);
			var jsonData = data;
			//判断公开
			if (jsonData.publicType != "PUBLIC") {
				alert("此题不公开！");
				return;
			}
			//题目标题
			questionName = jsonData.questionName;
			$('.content_header .breadcrumb li.active').html(questionName);
			$('.content_header .cg_bt:eq(0)').html(questionName);
			$("#tj .tj_r .row:eq(0) .col-md-6").html(questionName);
			//题目背景
			$('#wzxq .row .col-md-8 .wzxq_1:eq(0) div').html(jsonData.background.replace("\n", "<br>"));
			//题目描述
			$('#wzxq .row .col-md-8 .wzxq_1:eq(1) div').html(jsonData.describtion.replace("\n", "<br>"));
			//题目输入
			$('#wzxq .row .col-md-8 .wzxq_1:eq(2) #wzxq_wz1 div').html(jsonData.input.replace("\n", "<br>"));
			//题目输出
			$('#wzxq .row .col-md-8 .wzxq_1:eq(3) #wzxq_wz1 div').html(jsonData.output.replace("\n", "<br>"));
			//时间限制
			$('#myTabContent #wzxq .col-md-4 .row:eq(2) .col-md-6').eq(1).html(jsonData.timeLimit + "ms");
			//内存限制
			$('#myTabContent #wzxq .col-md-4 .row:eq(3) .col-md-6').eq(1).html(jsonData.memoryLimit + "MB");
			//获取输入输出样例
			getTheExamples();
			//获取所有题解
			getTiJie();

		}
	);
	//获取提交次数
	$.get(
		Host + RequestUrl.getSubmitNum,
		{ questionId: $_GET['id'] },
		function (data) {
			$("#myTabContent #wzxq .col-md-4 .wzxq_wz2").eq(0).html("已提交" + data + "次");
		}
	);
	//获取通过人数，计算通过率
	$.get(
		Host + RequestUrl.getPercent,
		{ questionId: $_GET['id'] },
		function (data) {
			$("#myTabContent #wzxq .col-md-4 .wzxq_wz2").eq(1).html("通过率：" + data + "%");
		}
	);
	//获取题目标签
	$.get(
		Host + RequestUrl.getLable,
		{ questionId: $_GET['id'] },
		function (data) {
			// var jsonData = JSON.parse(data);
			var jsonData = data;
			// 三种颜色标签
			var labelColor = ["label-success", "label-danger", "label-primary"];
			var spanCode = "";
			for (var i = jsonData.length - 1; i >= 0; i--) {
				spanCode += '<span class="label ' + labelColor[i % 3] + '">' + jsonData[i].theLabelName + '</span>';
			}
			$('#myTabContent #wzxq .col-md-4 .row:eq(1) .col-md-8').html(spanCode);
		}
	);
	//获取提交记录
	getSubmits();

}

/*提交代码*/
function submitCode() {
	$.ajax({
		url: Host + RequestUrl.submitCode,
		type: 'GET',
		xhrFields: { withCredentials: true },
		data: {
			questionId: $_GET['id'],
			languageType: "PYTHON",
			targetCode: editor1.getValue()
		},
		success: function () {
			getSubmits();
			$("#codeText").val("");
			$('#myTab li:eq(1) a').tab('show');
		}
	});
}

function getSubmits() {
	$("#tjjl table").eq(0).html("");
	$.ajax({
		url: Host + RequestUrl.getSubmits,
		type: 'GET',
		xhrFields: { withCredentials: true },
		data: { questionId: $_GET['id'], userId: userId },
		success: function (data) {
			// var jsonData = JSON.parse(data);
			var jsonData = data;
			jsonData = jsonData.content;
			for (var i = 0; i < jsonData.length; i++) {
				var labelCode;
				switch (jsonData[i].status) {
					case "ACCEPTED":
						labelCode = `<span class="label label-success">` + jsonData[i].status + `</span>`;
						break;
					case "WRONG":
						labelCode = `<span class="label label-danger">` + jsonData[i].status + `</span>`;
						break;
					case "ERROR":
						labelCode = `<span class="label label-danger">` + jsonData[i].status + `</span>`;
						break;
					case "PENDING":
						labelCode = `<span class="label label-warning">` + jsonData[i].status + `</span>`;
						break;
				}
				$("#tjjl table").append(
					`<tr>
						<td>` + timestampToTime(jsonData[i].createTime) + `</td>
						<td>` + jsonData[i].userId + `</td>
						<td>` + jsonData[i].timeConsuming + `ms</td>
						<td>` + jsonData[i].memoryOccupancy + `MB</td>
						<td>` + labelCode + `</td>
						<td>0</td>
						<td><a class="showCode btn btn-default" role="button" code='`+ jsonData[i].targetCode + `'>查看</a></td>
					</tr>`
				);
			}
			$(".showCode").each(function () {
				$(this).click(function () {
					editor2.setValue($(this).attr('code'));
					editor2.gotoLine(0, 0);
					$("#codeModal").modal('show');
				})
			})
		}
	});
}

function timestampToTime(timestamp) {
	// var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
	var date = new Date(timestamp);
	Y = date.getFullYear() + '-';
	M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
	D = date.getDate() + ' ';
	h = date.getHours() + ':';
	m = date.getMinutes() + ':';
	s = date.getSeconds();
	return Y + M + D + h + m + s;
}

/*获取输入输出样例*/
function getTheExamples() {
	$.get(
		Host + RequestUrl.getTheExamples,
		{ questionId: $_GET['id'] },
		function (data) {
			// console.log(data);
			var code = "";
			for (var i = data.length - 1; i >= 0; i--) {
				code = `<div class="wzxq_1">
										<h3>输入样例`+ (i + 1) + `</h3>
										<div id="wzxq_bgc">
											<div id="wzxq_wz1">
												<div>`+ data[i].exampleInput + `</div>
											</div>
										</div>
									</div>
									<div class="wzxq_1">
										<h3>输出样例`+ (i + 1) + `</h3>
										<div id="wzxq_bgc">
											<div id="wzxq_wz1">
												<div>`+ data[i].exampleOutput + `</div>
											</div>
										</div>
									</div>
									<hr>`+ code;
			}
			$("#examples").html(code);
		}
	);
}

function getTheSolutionAuthor(id) {
	var name;
	$.ajax({
		url: Host + RequestUrl.getTheSolutionAuthor,
		data: { theSolutionId: id },
		type: 'get',
		async: false,
		success: function (data) {
			name = data;
		}
	});
	return name;
}

function getComment(id) {
	var code = "";
	$.ajax({
		url: Host + RequestUrl.getComments,
		data: { theSolutionId: id },
		type: 'get',
		async: false,
		success: function (data) {
			data = data.content;
			for (var i = data.length - 1; i >= 0; i--) {
				code = `<a href="#" class="list-group-item">
			    <h4 class="list-group-item-heading">`+ getTheCommentAuthor(data[i].userId) + `</h4>
			    <p class="list-group-item-text">`+ data[i].comment + `</p>
			  </a>`+ code;

			}
		}
	});
	return code;
}

function getTheCommentAuthor(theCommentId) {
	var name;
	$.ajax({
		url: Host + RequestUrl.getTheCommentAuthor,
		data: { theCommentId: theCommentId },
		type: 'get',
		async: false,
		success: function (data) {
			name = data;
		}
	});
	return name;
}

function submitComment(solutionId, content) {
	$.ajax({
		url: Host + RequestUrl.submitSolutionComment,
		data: { solutionId: solutionId, content: content },
		type: 'post',
		success: function (data) {
			if (data.code == 0) {
				getTiJie();
			}
			alert(data.msg);
		}
	});
}

function getTiJie() {
	$.get(
		Host + RequestUrl.getTheSolutions,
		{ questionId: $_GET['id'] },
		function (data) {
			console.log(data);
			// var jsonData = JSON.parse(data);
			var jsonData = data;
			jsonData = jsonData.content;
			//题解总数量
			$("#tj .tj_r .row:eq(1) .col-md-6").html(jsonData.length + "篇");
			//
			var code = "";
			for (var i = jsonData.length - 1; i >= 0; i--) {
				code =
					`<div class="row tj_fl">
<div class="col-xs-12 col-sm-6 col-md-8 tj_r2"><div class="tj_rh2">`+ questionName + ` 题解</div>
<div class="row tj_rwz">
  <div class="col-md-4">0<span>阅读</span></div>
  <div class="col-md-4">0<span>评论</span></div>
  <div class="col-md-4">0<span>`+ getTheSolutionAuthor(jsonData[i].id) + `</span></div>
</div>
<div class="tj_rc">`+(jsonData[i].code == null ? 'code here' : jsonData[i].code)+`</div>
</div>
<div class="col-xs-6 col-md-4">

  <div class="list-group">
  `+ getComment(jsonData[i].id) + `
</div>
<textarea class="form-control" rows="3"></textarea>
<button type="button" class="btn btn-zdy" onclick='submitComment(`+ jsonData[i].id + `,$(this).siblings("textarea").val());$(this).siblings("textarea").val("");'>评论</button>
</div>
</div>` + code;
			}
			$("#tijieBox").html(code);
		}
	);
}