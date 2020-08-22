function pageLoad() {
	checkUser();
	getHomeworkQuestions();
}

/*检测是否登录*/
function checkUser() {
	var uname = window.localStorage.getItem("username");
	if (!uname) {
		window.location.href = "login.html";
	}
	$("#userWelcome").html(uname);
}

/*仿php的接收get参数*/
var $_GET = (function(){
	var url = window.document.location.href.toString();
	var u = url.split("?");
	if(typeof(u[1]) == "string"){
		u = u[1].split("&");
		var get = {};
		for(var i in u){
			var j = u[i].split("=");
			get[j[0]] = j[1];
		}
		return get;
	} else {
		return {};
	}
})();

function getHomeworkQuestions() {
	$.get(
		Host+RequestUrl.getHomeworkQuestions,
		{homeworkId: $_GET['id']},
		function (data) {
			console.log(data);
			data = data.content;
			var code = "";
			for (var i = data.length - 1; i >= 0; i--) {
				code = `<tr><td>`+data[i].questionName+`</td><td><span><a href="questionBankDetails.html?id=`+data[i].id+`">进入</a></span></td></tr>`+code;
			}
			console.log(code);
			$("#homeworkTable").html(code);
		}
	);
}