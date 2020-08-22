var size = 10;
var pageIndex = 0;
var pageTotal = 1;

$(document).ready(function () {
	/*检测是否登录*/
	checkUser();
	getRankInfo();
});

/*检测是否登录*/
function checkUser() {
	var uname = window.localStorage.getItem("username");
	if (!uname) {
		window.location.href = "login.html";
	}
	$("#userWelcome").html(uname);
}

function updateRankPage() {
	$.ajax({
		url: Host + RequestUrl.getRank,
		type: 'GET',
		data: { page: pageIndex, size: size },
		success: function (data) {
			console.log(data);
			var userData = data.data.content;
			var thcode = `<tr><th>排名</th><th>用户</th><th style="width: 35%;">个性签名</th><th>解决题数</th><th>提交题数</th><th>通过率</th></tr>`;
			var trcode = "";
			var local = "";
			for (var i = 0; i < userData.length; i++) {
				trcode += '<tr>';
				trcode += '<td>' + (1 + i + pageIndex * size) + '</td>';
				trcode += '<td>' + (userData[i].username) + '</td>';
				trcode += '<td>' + (userData[i].personalitySignature == null ? 'Nothing left......' : userData[i].personalitySignature) + '</td>';
				trcode += '<td>' + (userData[i].acNumber) + '</td>';
				trcode += '<td>' + (userData[i].submmitNumber) + '</td>';
				trcode += '<td>' + (parseInt(100 * (userData[i].acNumber) / (userData[i].submmitNumber))) + '%</td>';
				trcode += '</tr>';
			}
			$("#rankTable").html(thcode + trcode);
			pageTotal = data.data.totalPages;
			updatePageChanger();
		}
	})
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
			updateRankPage();
		});
	})
}



function getRankInfo() {
	updateRankPage();
}