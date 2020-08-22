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

function submitSolution() {
	if ($_GET['id'] == null) {
		return;
	}
	var code = $("textarea").val();
	$.ajax({
		url: Host+RequestUrl.submitSolution,
		type: 'POST',
		data: {code: code, questionId: $_GET['id']},
		xhrFields: { withCredentials: true },
		success: function (data) {
			console.log(data);
			if (data.code == 0) {
				$("textarea").val("");
			}
			alert(data.msg);
		}
	});
}