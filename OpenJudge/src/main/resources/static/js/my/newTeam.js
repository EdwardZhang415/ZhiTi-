function creatTeam() {
	var teamName = $("#teamName").val();
	var opennessType = $("#opennessType").val();
	var teamDeclaration = $("#teamDeclaration").val();
	$.post(
		Host+RequestUrl.newTeam,
		{
			teamName: teamName,
			opennessType: opennessType,
			teamDeclaration: teamDeclaration
		},
		function (data) {
			/*可能出现的结果：
			 *Already Exist
			 *对不起，请先登录
			 *ERROR
			 *SUCCESS
			 */
			switch(data) {
				case 'Already Exist':
					alert("你已经加入了队伍！");
					location.href = "myTeam.html";
					break;
				case '对不起，请先登录':
					alert("对不起，请先登录");
					location.href = "login.html";
					break;
				case 'ERROR':
					alert("服务器错误");
					break;
				case 'SUCCESS':
					alert("创建成功");
					location.href = "myTeam.html";
					break;
				default:
					alert(data);
			}
			$("#newTeam").attr("disabled", false);
			console.log(data);
		}
	);
}

