/*全局变量*/

var pageIndex = 0; //当前页码
var pageCount = 9; //每页内容条数
var pageTotal = 0; //总页数

/*常用DOM*/
//题库列表

function bindStarSelect() {
	$('#starSelect').change(function () {
		searchForQuestions();
	})
}

function bindSearchTags() {
	/*给搜索框的搜索按钮绑定上事件*/
	$(".search-button").click(function () {
		searchForQuestions();
	});
}
function bindNavSearch() {
	/*给导航条上的搜索按钮绑定上事件*/
	$(".navbar button").click(function () {
		var keyword = $(".navbar input").val();
		$.get(
			Host + RequestUrl.getQuestionsByTagsAndName,
			{ name: keyword, degree: 0, page: pageIndex, size: pageCount },
			function (data) {
				// var jsonData = JSON.parse(data);
				var jsonData = data;
				jsonData = jsonData.content;
				showQuestionList(jsonData);
			}
		);
	});
}

function updatePageChanger() {
	var pagination = $(".pagination");
	pagination.html('<li><a href="#" value="first">«首页</a></li>');
	pagination.append('<li><a href="#" value="before">上一页</a></li>')
	for (var i = 0; i < pageTotal; ++i) {
		pagination.append('<li><a href="#" value="' + i + '">' + (i + 1) + '</a></li>');
	}
	pagination.append('<li><a href="#" value="next">下一页</a></li>')
	pagination.append('<li><a href="#" value="last">尾页»</a></li>');
	$('.pagination li a[value="' + pageIndex + '"]').parent('li').addClass('active');

	$(".pagination li a").each(function () {
		$(this).unbind("click");
		$(this).bind("click", function () {
			var value = $(this).attr('value');
			if (value == "before") pageIndex = Math.max(0, pageIndex - 1);
			else if (value == "next") pageIndex = Math.min(pageTotal - 1, pageIndex + 1);
			else if (value == "first") pageIndex = 0;
			else if (value == "last") pageIndex = pageTotal - 1;
			else pageIndex = value;
			searchForQuestions();
		});
	})
}

function searchForQuestions() {
	var keyword = $(".content_header input").val();
	var labels = [];
	$(".selected").each(function () {
		labels.push($(this).data('type'));
	});
	$.get(
		Host + RequestUrl.getQuestionsByTagsAndName,
		{ name: keyword, theLabels: labels, degree: $("#starSelect").val(), page: pageIndex, size: pageCount },
		function (data) {
			var jsonData = data;
			pageTotal = jsonData.totalPages;
			jsonData = jsonData.content;
			showQuestionList(jsonData);
			updatePageChanger();
		}
	);
}

$(document).ready(function () {
	/*检测是否登录*/
	checkUser();
	getAllLabel();
	bindSearchTags();
	bindNavSearch();
	bindStarSelect();
	searchForQuestions();
});

/*检测是否登录*/
function checkUser() {
	var uname = window.localStorage.getItem("username");
	if (!uname) {
		window.location.href = "login.html";
	}
	$("#userWelcome").html(uname);
}

/*获取题目列表，页面加载完毕即可调用*/
function getQuetionList() {
	$.get(Host + RequestUrl.getAllQuestion, { page: pageIndex, size: pageCount }, function (data) {
		// console.log(data);
		// var jsonData = JSON.parse(data);
		var jsonData = data;
		jsonData = jsonData.content;
		showQuestionList(jsonData);
		pageTotal = jsonData.totalPages;
	});
}

/**
 * @method showQuestionList
 * @param 
 * @desc 把quetionData的内容布置到html上
 */
function showQuestionList(questionData) {
	$("#tkbox").html("");
	for (var i = questionData.length - 1; i >= 0; i--) {
		// 只显示PUBLIC题目
		if (questionData[i].publicType == "PUBLIC") {
			$("#tkbox").html(
				`<div class="tk1" tk-id="` + questionData[i].id + `">
					<div class="tkcontent">
						<div class="row">
							<div class="col-md-1"><span class="leftfont">` + questionData[i].id + `</span></div>
							<div class="col-md-2"><span class="f1" onclick="javascrtpt:window.location.href='questionBankDetails.html?id=` + questionData[i].id + `'">` + questionData[i].questionName + `</span></div>
							<div class="col-md-3 questionLabels">
								<span class="label label-success">加载中...</span>
								<!--
								<span class="label label-success">字符串</span>
								<span class="label label-danger">排序</span>
								<span class="label label-primary">算法</span>
								-->
							</div>
							<div class="col-md-2"><span class="tk_cwz">难度系数</span><br/><span><img src="images/Star_` + questionData[i].degreeOfDifficulty + `.svg"></span></div>
							<div class="col-md-2"><span class="tk_cwz">通过人数</span><br/><span class="tk_cfwz passedNum">加载中...</span></div>
							<div class="col-md-2"><span class="tk_cwz">提交人数</span><br/><span class="tk_cfwz submitNum">加载中...</span></div>
						</div>
					</div>
				</div>`
				+ $("#tkbox").html()
			);
			getLable(questionData[i].id);
			getPassedNum(questionData[i].id);
			getSubmitNum(questionData[i].id);
		}
	}
}


function getAllLabel() {
	$.get(
		Host + RequestUrl.getAllLabel,
		function (data) {
			// console.log(data);
			var jsonData = data;
			jsonData = jsonData.content;
			var labelBox = $("ul#select1");
			for (var i = jsonData.length - 1; i >= 0; i--) {
				labelBox.html(
					labelBox.html() +
					`<li class="search-button" data-type="` + jsonData[i].id + `"><a href="javascript:void(0);">` + jsonData[i].theLabelName + `</a></li>`
				);
			}
			initLabels();
			bindSearchTags();
		}
	);
}

function getLable(id) {
	$.get(
		Host + RequestUrl.getLable,
		{ questionId: id },
		function (data) {
			// var jsonData = JSON.parse(data);
			var jsonData = data;
			// 三种颜色标签
			var labelColor = ["label-success", "label-danger", "label-primary"];
			var spanCode = "";
			for (var i = jsonData.length - 1; i >= 0; i--) {
				spanCode += '<span class="label ' + labelColor[i % 3] + '">' + jsonData[i].theLabelName + '</span>';
			}
			$("div[tk-id='" + id + "'] .questionLabels").html(spanCode);
		}
	);
}

function getPassedNum(id) {
	$.get(
		Host + RequestUrl.getPassedNum,
		{ questionId: id },
		function (data) {
			$("div[tk-id='" + id + "'] .passedNum").html(data);
		}
	);
}

function getSubmitNum(id) {
	$.get(
		Host + RequestUrl.getSubmitNum,
		{ questionId: id },
		function (data) {
			$("div[tk-id='" + id + "'] .submitNum").html(data);
		}
	);
}

