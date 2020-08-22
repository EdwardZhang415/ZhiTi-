$(document).ready(function () {
	/*检测是否登录*/
	checkUser();
});

/*检测是否登录*/
function checkUser() {
	var uname = window.localStorage.getItem("username");
	if (!uname) {
		window.location.href = "login.html";
	}
	$("#userWelcome").html(uname);
}

function setHomeWork() {
	var homeworkName = $("input#homeworkName").val();
	var homeworkType = $("input#homeworkType").val();
	var theQuestions = $("input#theQuestions").val();
	var temp = theQuestions.split(" ");
	theQuestions = [];
	for (var i = temp.length - 1; i >= 0; i--) {
		if (temp[i].length != 0) {
			theQuestions.push(temp[i]);
		}
	}
	if (theQuestions.length == 0) {
		alert("还没选择题目！");
		return;
	}
	$.post(
		Host+RequestUrl.setHomeWork,
		{
			homeworkName:homeworkName.toString(),
			homeworkType:homeworkType.toString(),
			theQuestions:theQuestions
		},
		function (data) {
			switch (data) {
				case 'SUCCESS':
					alert("新建作业成功");
					$("input#homeworkName").val("");
					$("input#homeworkType").val("");
					$("input#theQuestions").val("");
					return;
				case '': case 'ERROR':
					alert("服务器异常");
				default: alert(data);
			}
		}
	);
}

function searchQuestion () {
	var keyword = $("input#questionKeyword").val();
	$.get(
		Host+RequestUrl.getQuestionsByName,
		{name: keyword, degree: '0'},
		function (data) {
			data = data.content;
			var code = "";
			for (var i = data.length - 1; i >= 0; i--) {
				code = code + `<tr><td><input type="checkbox" value="` + data[i].id + `"/>`+data[i].questionName+`</td></tr>`;
			}

			$("#questionTable").html(code);
			$("#questionTable input[type=checkbox]").click(function () {
				console.log(111);
				if ($(this).is(':checked')) {
					$("input#theQuestions").val($(this).val() + " " + $("input#theQuestions").val());
				} else {
					$("input#theQuestions").val($("input#theQuestions").val().replace($(this).val() + " ", "").replace(" "+$(this).val()," "));
				}
			});
		}
	);
}