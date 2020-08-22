

/*页面加载完毕时调用*/
$(document).ready(function(){
	checkUser();
	getProvingGrounds();
});

/*检测是否登录*/
function checkUser() {
	var uname = window.localStorage.getItem("username");
	if (!uname) {
		window.location.href = "login.html";
	}
	$("#userWelcome").html(uname);
}

/*获取试炼场*/
function getProvingGrounds() {
	$.get(
		Host+RequestUrl.getProvingGrounds,
		function (data) {
			// var jsonData = JSON.parse(data);
			var jsonData = data;
			jsonData = jsonData.content;
			var provingGroundsBox = $(".provingGrounds");
			provingGroundsBox.html("");
			var content_header = $(".content_header");
			for (var i = jsonData.length - 1; i >= 0; i--) {
				provingGroundsBox.html(
					`<div class="col-xs-6 col-md-4 cg_div` + jsonData[i].id + ` cscline-height"  href="#xsc"><p class="cg_wz">` + jsonData[i].cardName + `</p></div>`
					+ provingGroundsBox.html()
				);
			}
			for (var i = 0; i < jsonData.length; i++) {
				getMissionsByProvingGrounds(jsonData[i].id);
				content_header.html(
					content_header.html() + 
					`<div ground-id="` + jsonData[i].id + `">
						<p class="cg_xscwz" id="xsc">` + jsonData[i].cardName + `</p>
						<p class="cg_xscwz1">` + jsonData[i].explanations + `</p>
						<div class="progress" style="width:90%">
							<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">0%</div>
						</div>
						<div class="missionsBox"><div class="row"></div></div>
					</div>`
				);
			}
		}
	);
}

function getMissionsByProvingGrounds(ProvingGroundsId) {
	$.get(
		Host+RequestUrl.getMissionsByProvingGrounds,
		{ProvingGroundsId: ProvingGroundsId},
		function (data) {
			console.log(data);
			var jsonData = data;
			jsonData = jsonData.content;
			var missionsBox = $("div[ground-id='" + ProvingGroundsId + "'] div.missionsBox");
			var missionsBox_row = missionsBox.find("div.row");
			for (var i = jsonData.length - 1; i >= 0; i--) {
				isFinished(jsonData[i].id);
				missionsBox_row.html(
						`<div class="wrap">
							<div class="missionBox" class="col-xs-6 col-md-4" mission-id="` + jsonData[i].id + `"><div class="row">
								<p class="xsc" id="missionTitle">` + jsonData[i].missionName + `</p>
								<p class="xsc_wz">关卡` + ProvingGroundsId + `-` + (i+1) + `，` + parseInt(jsonData[i].numberOfRightAnswers) + ` 道题</p>
								<p class="xsc_dl">` + jsonData[i].explanations + `</p>
								<div class="row">
									<div class="col-xs-5 cg_ndxswz">难度系数:</div>
									<div class="col-xs-6 cg_star star_` + jsonData[i].degreeOfDifficulty + `"></div>
								</div>
								<p class="cg_tgrs">` + jsonData[i].numberOfRightAnswers + `通过</p>
							</div></div>
						</div>` +
					missionsBox_row.html()
				);
			}
			for (var i = jsonData.length - 1; i >= 0; i--) {
				var missionQuestionsCode = getQuestionsByMission(jsonData[i].id);
				missionsBox.html(
					missionsBox.html() +
					`<div class="bigWindow" style="display:none;" mission-id="` + jsonData[i].id + `">
						<div class="row">
							<div class="col-md-10"><strong>` + jsonData[i].missionName + `</strong> </div>
							<div class="col-md-2"><a class="btn btn-primary" name="closeBigWindow">X</a></div>
						</div>
						<div class="bigContent">
							<div class="row">
								<div class="col-md-4">
									<p>
										关卡<span name="nowid">` + ProvingGroundsId + `-` + (i+1) + `</span>，
										` + parseInt(jsonData[i].numberOfRightAnswers) + `道题
									</p>
								</div>
								<div class="col-md-8"><p>要完成这个任务，通过下列` + parseInt(jsonData[i].numberOfRightAnswers) + `道题：</p></div>
							</div>
							<div class="row">
								<div class="col-md-4"><strong>任务说明：</strong><span class="lg-small">` + jsonData[i].explanations + `</span></div>
								<div class="col-md-8">
									` + missionQuestionsCode + `
									<!--
									<p><a href="questionBankDetails.html" target="_blank"><strong class=""><i class="am-icon-minus"></i></strong></a><a class="colored" style="padding-left: 3px" href="questionBankDetails.html" target="_blank"><b>P1000</b> 超级玛丽游戏</a></p>
									<p><a href="questionBankDetails.html" target="_blank"><strong class=""><i class="am-icon-minus"></i></strong></a><a class="colored" style="padding-left: 3px" href="questionBankDetails.html" target="_blank"><b>P1421</b> 小玉买文具</a></p>
									<p><a href="questionBankDetails.html" target="_blank"><strong class=""><i class="am-icon-minus"></i></strong></a><a class="colored" style="padding-left: 3px" href="questionBankDetails.html" target="_blank"><b>P1421</b> 小玉买文具</a></p>
									-->
									<p class="lg-small">
									练习要循序渐进，不能急于求成哦！<br>
									当然洛谷也允许在一定条件下跳过某些关卡。最多可以跳过3次。<br>
									</p>
								</div>
							</div>
						</div>
						<div class="bigBtn">
							<a class="btn btn-danger disabled">任务尚未完成~</a>
							<a class="btn btn-primary" name="skip">跳过任务</a>
						</div>
					</div>`
				);
			}
			$(".missionBox").unbind("click");
			$(".missionBox").bind("click", function () {
				console.log("fuck target")
				$("div.bigWindow[mission-id]").hide();
				var missionid = $(this).attr("mission-id");
				$("div.bigWindow[mission-id='" + missionid + "']").show();
			});
			$("[name='closeBigWindow']").click(function () {
				$(this).parents("[mission-id]").hide();
			});
		}
	);
}

function getQuestionsByMission(missionid) {
	//检查先决关卡是否完成
	$.get(
		Host + RequestUrl.getPreconditions,
		{missionId: missionid},
		function (data) {
			var xianjueCode = "";
			data = data.content;
			// console.log(data);
			for (var i = data.length - 1; i >= 0; i--) {
				if (missionIsFinished(data[i].lastMissionId) == false) {
					xianjueCode = $(".missionsBox .row:eq(0) div[mission-id="+ data[i].lastMissionId +"] #missionTitle").html()  + xianjueCode;
				}
				// console.log(xianjueCode);
			}
			if (xianjueCode != "") {
				$("div.bigWindow[mission-id='" + missionid + "'] .bigContent .row:eq(1) .col-md-8").html(
					xianjueCode
				);
			} else {
				$.get(
					Host+RequestUrl.getQuestionsByMission,
					{missionId: missionid},
					function (data) {
						// console.log(data);
						data = data.content;
						code = "";
						for (var i = data.length - 1; i >= 0; i--) {
							code = `<p>
								<a href="questionBankDetails.html" target="_blank">
									<strong class=""><i class="am-icon-minus"></i></strong>
								</a>
								<a class="colored" style="padding-left: 3px" href="questionBankDetails.html?id=` + data[i].id + `" target="_blank">
									<b>` + data[i].id + `</b> ` + data[i].questionName + `
								</a>
							</p>` + code;
						}
						$("div.bigWindow[mission-id='" + missionid + "'] .bigContent .row:eq(1) .col-md-8").html(
							code + `<p class="lg-small">
							练习要循序渐进，不能急于求成哦！<br>
							当然洛谷也允许在一定条件下跳过某些关卡。最多可以跳过3次。<br>
							</p>`
						);
					}
				);
			}
		}
	);
	
}

function missionIsFinished(missionid) {
	var result;
	$.ajax({
		url: Host+RequestUrl.isFinished,
		async: false,
		data: {missionId: missionid},
		type: 'get',
		success: function (data) {
			result = data;
		}
	});
	return result;
}

function isFinished(missionid) {
	$.get(
		Host+RequestUrl.isFinished,
		{missionId: missionid},
		function (data) {
			var missionTitle = $(".wrap div[mission-id='" + missionid + "'] #missionTitle");
			if (data == true) {
				missionTitle.html(missionTitle.html()+`<span class="label label-success">已完成</span>`);
				$("div.bigWindow[mission-id='"+missionid+"'] .bigBtn a").attr("class", "btn btn-primary disabled");
				$("div.bigWindow[mission-id='"+missionid+"'] .bigBtn a").eq(0).html("已完成").attr("class", "btn btn-success disabled");
			} else {
				missionTitle.html(missionTitle.html()+`<span class="label label-danger">未完成</span>`);
			}
				// switch (jsonData[i].status) {
				// 	case "HAVE_IN_HAND":
				// 		statusCode = `<span class="label label-success">进行中</span>`;
				// 		break;
				// 	case "COMPLETED":
				// 		statusCode = `<span class="label label-success">已完成</span>`;
				// 		break;
				// 	case "NOT_THROUGH":
				// 		statusCode = `<span class="label label-success">未开始</span>`;
				// 		break;
				// }
		}
	);
}