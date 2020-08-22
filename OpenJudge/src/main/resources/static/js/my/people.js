$(document).ready(function () {
	checkUser();
	showUserInfo();

	getHistoryPercent();
	// getUserSubmits();
	getUnfinishedHomework();
	getHomeworks();
});

/*检测是否登录*/
function checkUser() {
	var uname = window.localStorage.getItem("username");
	if (!uname) {
		window.location.href = "login.html";
	}
	$("#userWelcome").html(uname);
}

/*布局用户信息*/
function showUserInfo() {
	$.ajax({
		url: Host + RequestUrl.getTeamByUserId,
		crossDomain: true,
		xhrFields: {
			withCredentials: true
		},
		success: function (data) {
			// console.log(data);
			var infoTableTrs = $("#infoPage").find("table tr");
			infoTableTrs.eq(0).find("td").eq(1).html(window.localStorage.getItem("username"));
			infoTableTrs.eq(1).find("td").eq(1).html(window.localStorage.getItem("email"));
			infoTableTrs.eq(2).find("td").eq(1).html(data.teamName);
			infoTableTrs.eq(3).find("td").eq(1).html(window.localStorage.getItem("personalitySignature"));
			// $("#infoPage").find("img").eq(0).attr("src", window.localStorage.getItem("headpic"));
		}
	});
}

/*获取累计通过率*/
function getHistoryPercent() {	
	$.ajax({
		url: Host + RequestUrl.getHistoryPercent,
		success: function (data) {
			// console.log(data);
			var month = [];
			var percent = [];
			for (var i = data.length - 1; i >= 0; i--) {
				month.push(data[i].month);
				percent.push(data[i].percent);
			}
			// console.log(month);
			// console.log(percent);
			var ctx_tglChart = document.getElementById("tglChart").getContext('2d');
			var tglChart = new Chart(ctx_tglChart, {
				type: 'line',
				data: {
					labels: month,
					datasets: [{
						label: '累计通过率 %',
						data: percent,
						backgroundColor: [
							'rgba(22,155,213,0.2)'
						],
						borderColor: [
							'rgba(22,155,213,1)'
						],
						borderWidth: 1
					}]
				},
				options: {
					scales: {
						yAxes: [{
							ticks: {
								beginAtZero: true // 纵坐标从0开始
							}
						}]
					}, elements: {
						line: {
							tension: 0 // 禁用贝塞尔曲线
						}
					}
				}
			});
		}
	});
}

/*获取提交记录*/
function getUserSubmits() {
	$.ajax({
		url: Host + RequestUrl.homeworkGetSubmit,
		type: 'GET',
		xhrFields: { withCredentials: true },
		success: function(data) {
			console.log("个人提交记录");
			console.log(data);
		}
	});
}

/*获取未完成作业*/
function getUnfinishedHomework() {
	$.ajax({
		url: Host + RequestUrl.getUnfinishedHomework,
		type: 'GET',
		xhrFields: { withCredentials: true },
		success: function (data) {
			console.log("获取未完成作业");
			console.log("fuck",data);
			data = data.content;
			var dwczyTable = $("#dwczy");
			var code = "";
			for (var i = data.length - 1; i >= 0; i--) {
				code = `<tr>
					<td>` + data[i].homeworkName + `</td>
					<td>` + data[i].content + `</td>
					<td><a href="workList.html?id=` + data[i].id + `&type=0"><button type="button" class="btn btn-default btn-sm">进入</button></a></td>
				</tr>` + code;
			}
			dwczyTable.html(code);
		}
	});
}

/*获取所有作业*/
function getHomeworks() {
	$.ajax({
		url: Host + RequestUrl.getHomeworks,
		type: 'GET',
		xhrFields: { withCredentials: true },
		success: function (data) {
			console.log("获取所有作业");
			console.log(data);
			data = data.content;
			var yjscsTable = $("#yjscs");
			var code = "";
			for (var i = data.length - 1; i >= 0; i--) {
				code = `<tr>
					<td>` + data[i].homeworkName + `</td>
					<td>` + data[i].content + `</td>
					<td><a href="workList.html?id=` + data[i].id + `&type=1"><button type="button" class="btn btn-default btn-sm">进入</button></a></td>
				</tr>` + code;
			}
			yjscsTable.html(code);
		}
	});
}