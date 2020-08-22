function login() {
	window.localStorage.clear();
	var username = $("input[name='username']").val();
	var password = $("input[name='pwd']").val();
	if ($("#checkbox").is(':checked')) {
		window.localStorage.setItem("login_username", username);
		window.localStorage.setItem("login_password", password);
	}
	$.ajax({
		url: Host+RequestUrl.login,
		type: 'post',
		crossDomain: true,
		xhrFields: {
			withCredentials: true
		},
		data: { email: username, password: password },
		success: function (data, textStatus, request) {
			console.log(JSON.stringify(textStatus));
			console.log(JSON.stringify(request));
			console.log(data);
			if (data.code == 0) {
				window.localStorage.setItem("userid", data.data.id);
				window.localStorage.setItem("username", data.data.username);
				window.localStorage.setItem("email", data.data.email);
				window.localStorage.setItem("phone", data.data.phone);
				window.localStorage.setItem("headpic", data.data.headpic);
				window.localStorage.setItem("personalitySignature", data.data.personalitySignature);
				location.href = "homepage.html";
			} else {
				alert(data.msg);
			}
		}
	});
}
function getUP() {
	var username = window.localStorage.getItem("login_username");
	var password = window.localStorage.getItem("login_password");
	if (username) {
		$("input[name='username']").val(username);
		$("input[name='pwd']").val(password);
	}
}