var memberEditMode = false;

$(document).ready(function () {
	/*检测是否登录*/
	checkUser();
	$("#teamEdit").click(function() {
		if ($(this).html() == "成员管理") {
			$(".button-setadmin").show();
			$(".button-deladmin").show();
			memberEditMode = true;
			$(this).html("确认更改");
		} else {
			$(".button-setadmin").hide();
			$(".button-deladmin").hide();
			memberEditMode = false;
			$(this).html("成员管理");	
		}
	})
});
function addMember(jqobj) {
	var uname = jqobj.parent().siblings(".modal-body").find("input").val();
	$.post(
		Host + RequestUrl.addTeamMember,
		{ userName: uname },
		function (data) {
			/**可能返回的结果：
			 *你还没有团队
			 *SUCCESS
			 *该用户已有团队
			 *找不到该成员
			 */
			switch (data) {
				case 'ERROR':
					alert("服务器异常");
					break;
				case 'SUCCESS':
					alert("添加成功");
					break;
				default:
					alert(data);
					break;
			}
		}
	);
}

/*检测是否登录*/
function checkUser() {
	var uname = window.localStorage.getItem("username");
	if (!uname) {
		window.location.href = "login.html";
	}
	$("#userWelcome").html(uname);
}

function deleteTeam() {
	if (!confirm("确定要退出队伍吗？如果是队长的话，将会解散该队伍。")) { return; }
	$.post(
		Host + RequestUrl.deleteTeam,
		function (data) {
			/*可能返回的数据
			 *ERROR
			 *
			 *
			 */
			switch (data) {
				case 'ERROR':
					alert("服务器异常");
					break;
				case 'SUCCESS':
					alert("退出成功");
					location.href = 'myTeam.html';
					break;
				default:
					alert(data);
					break;
			}
		}
	);
}

function setTeamMember() {
	// body...
}

function pageLoad() {
	getTeam();
	getAdmin();
	getHomeworks();
	getCreator();
	getNums();
}


function getAdmin() {
	$.ajax({
		url: Host + RequestUrl.getAdmin,
		type: 'GET',
		xhrFields: { withCredentials: true },
		success: function (data) {
			data = data.data;
			var code = '<ul class="list-group">';
			for (var i = data.length - 1; i >= 0; i--) {
				code += '<li class="list-group-item"><span class="button-deladmin label label-warning" data-id="'+data[i].userId+'" style="cursor: pointer; float: right; right; margin-left: 10px;">取消管理员</span><span class="badge" style="width: 65px;background-color: #d9534f !important;">管理员</span>' + data[i].name + '</li>';
			}
			code += '</ul>';
			$("#normalBox").html(code);

			$(".button-deladmin").each(function() {
				if (!memberEditMode) $(this).hide();
				$(this).unbind("click");
				$(this).bind("click", function() {
					var id = $(this).data('id');
					deleteAdmin(id);
					getAdmin();
				})
			})

			getOrdinary();
		}
	});
}
function getOrdinary() {
	$.ajax({
		url: Host + RequestUrl.getOrdinary,
		type: 'GET',
		xhrFields: { withCredentials: true },
		success: function (data) {
			data = data.data;
			for (var i = data.length - 1; i >= 0; i--) {
				$("#normalBox .list-group").append('<li class="list-group-item"><span class="button-setadmin label label-primary" data-id="'+data[i].userId+'" style="cursor: pointer; float: right; margin-left: 10px;">设为管理员</span><span class="badge" style="width: 65px;">普通成员</span>' + data[i].name + '</li>');
			}
			$(".button-setadmin").each(function() {
				if (!memberEditMode) $(this).hide();
				$(this).unbind("click");
				$(this).bind("click", function() {
					var id = $(this).data('id');
					setAdmin(id);
					getAdmin();
				})
			})
		}
	});
}

function bindMember() {
	$(".memberBox input").click(function () {
		if ($(this).is(":checked")) {
			setAdmin($(this).val());
		} else {
			deleteAdmin($(this).val());
		}
	});
}

function deleteAdmin(id) {
	var postData = [id];
	$.ajax({
		url: Host + RequestUrl.deleteAdmin,
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(postData),
		xhrFields: { withCredentials: true },
		success: function (data) {
			if (data.code == 0) {
				getAdmin();
			} else {
				alert(data.msg);
			}
		}
	});
}

function setAdmin(id) {
	var postData = [id];
	console.log(JSON.stringify(postData))
	$.ajax({
		url: Host + RequestUrl.setAdmin,
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(postData),
		xhrFields: { withCredentials: true },
		success: function (data) {
			console.log(data);
			if (data.code == 0) {
				getAdmin();
			} else {
				alert(data.msg);
			}
		}
	});
}

function getHomeworks() {
	$.ajax({
		url: Host + RequestUrl.getHomeworks,
		type: 'GET',
		xhrFields: { withCredentials: true },
		sucess: function (data) {
			data = data.content;
			var code = "";
			for (var i = data.length - 1; i >= 0; i--) {
				code = `<div class="row rm1">
                <div class="col-md-6 fbt"><span>第`+ i + `次</span><span> ` + data[i].homeworkName + `</span><span style='display="none"' value="` + data[i].id + `"></span></div>
              </div>` + code;
				data[i]
			}
			$("#zuoyeview").html(code);
			console.log(data);
		}
	});
}

function getTeam() {
	$.ajax({
		url: Host + RequestUrl.getTeamByUserId,
		type: 'GET',
		xhrFields: { withCredentials: true },
		success: function (data) {
			if (data.teamName == null || typeof (data.teamName) == "undefined") {
				alert("你还没加入团队，请创建");
				//location.href = "newTeam.html";
			}
			$("#TeamName").html(data.teamName);
			$("#myModalLabel").html(data.teamName);
			$("#createTime").html(timestampToTime(data.createTime));
			$("#teamDeclaration").html(data.teamDeclaration);
		}
	});

}

function setTeam() {
	var teamDeclaration = $("#teamDeclaration_input").val();
	var teamBulletin = "";
	var opennessType = $("#opennessType input[type=radio]:checked").val();
	$.ajax({
		url: Host + RequestUrl.setTeam,
		type: 'POST',
		xhrFields: { withCredentials: true },
		data: {
			teamDeclaration: teamDeclaration,
			teamBulletin: teamBulletin,
			opennessType: opennessType
		},
		function(data) {
			if (data == "SUCCESS") {
				history.go(0);
			}
			alert(data);
		}
	});
}

function timestampToTime(timestamp) {
	var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
	Y = date.getFullYear() + '-';
	M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
	D = date.getDate() + ' ';
	h = date.getHours() + ':';
	m = date.getMinutes() + ':';
	s = date.getSeconds();
	return Y + M + D + h + m + s;
}

function getCreator() {
	$.ajax({
		url: Host + RequestUrl.getCreator,
		type: 'GET',
		xhrFields: { withCredentials: true },
		success: function (data) {
			$("#TeamCreator").html(data);
		}
	});
}

function getNums() {
	$.ajax({
		url: Host + RequestUrl.getNums,
		type: 'GET',
		xhrFields: { withCredentials: true },
		success: function (data) {
			$("#TeamNum").html(data);
		}
	});
}

function setGeQian() {
	var gq = $('#gexingqianming').val();
	$.ajax({
		url: Host + RequestUrl.setPersonalitySignature,
		type: 'POST',
		xhrFields: { withCredentials: true },
		data: { personalitySignature: gq },
		function(data) {
			if (data == "SUCCESS") {
				history.go(0);
			}
			alert(data);
		}
	});
}