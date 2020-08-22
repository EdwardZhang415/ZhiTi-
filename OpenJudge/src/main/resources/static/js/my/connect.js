/*全局变量*/
var Host = "";
var RequestUrl = {
	getAllLabel					:"index/getAllLabel",									//获取所有搜索用的标签
	getAllQuestion				:"index/getAllQuestion",								//获取总题目
	getAllTestcase				:"UpdateQuestion/getAllTestcase",						//获取所有测试数据
	getComments					:"TheSubmit/getComments",								//获取评论
	getLable					:"index/getLable",										//获取标签
	getList						:"index/getList",										//获取题库
	getMissionsByProvingGrounds	:"theProvingGrounds/getMissionsByProvingGrounds",		//根据试炼场获取任务
	getPassedNum				:"index/getPassedNum",									//查询通过人数
	getPercent					:"QuestionDetail/getPercent",							//获取通过率
	getPreconditions			:"theProvingGrounds/getPreconditions",					//查询先决条件
	getProvingGrounds			:"theProvingGrounds/getProvingGrounds",					//获取试炼场
	getQuestionsByMission		:"theProvingGrounds/getQuestionsByMission",				//根据任务获取题目
	getQuestionsByName			:"index/getQuestionsByName",							//搜索题目名获取题目
	getQuestionsByStatus		:"index/getQuestionsByStatus",							//根据状态获取题目
	getQuestionsByTags			:"index/getQuestionsByTags",							//根据标签获取题目
	getQuestionsByTagsAndName	:"index/getQuestionsByTagsAndName",						//根据标签和关键词获取题目
	getQuestionById				:"QuestionDetail/getQuestionById",						//根据ID获取题目
	getQuestionById				:"index/getQuestionById",								//根据ID获取题目
	getSubmitNum				:"index/getSubmitNum",									//查询提交人数
	getSubmits					:"TheSubmit/getSubmits",								//获取提交记录
	getTheSolutionAuthor		:"TheSubmit/getTheSolutionAuthor",						//获取题解作者
	getTheSolutions				:"TheSubmit/getTheSolutions",							//获取所有题解
	isFinished					:"theProvingGrounds/isFinished",						//查询任务是否已完成
	submitCode					:"QuestionDetail/submitCode",							//提交代码
	updateLimit					:"UpdateQuestion/updateLimit",							//更新测试点内存和时间限制
	updatePublicType			:"UpdateQuestion/updatePublicType",						//更新公开类型
	upload						:"UpdateQuestion/upload",								//上传单个测试点
	uploadQuestion				:"UpdateQuestion/uploadQuestion",						//上传题目
	login:"user/login",
	getTeamByUserId:"Team/getTeamByUserId",
	getHistoryPercent:"user/getHistoryPercent",
	homeworkGetSubmit:"homework/getSubmit",
	getRank:"homework/getRank",
	newTeam:"Team/newTeam",
	deleteTeam:"Team/deleteTeam",
	setTeamMember:"Team/setTeamMember",
	addTeamMember:"Team/addTeamMember",
	setHomeWork:"Team/setHomeWork",
	getUnfinishedHomework:"Team/getUnfinishedHomework",
	getHomeworks:"Team/getHomeworks",
	sendMsg:"user/sendMsg",
	validateNum:"user/validateNum",
	getTheExamples:"QuestionDetail/getTheExamples",
	submitSolution:"TheSubmit/submitSolution",
	getTheCommentAuthor:"TheSubmit/getTheCommentAuthor",
	submitSolutionComment:"TheSubmit/submitSolutionComment",
	getAdmin:"homework/getAdmin",
	getOrdinary:"homework/getOrdinary",
	setAdmin:"homework/setAdmin",
	deleteAdmin:"homework/deleteAdmin",
	getHomeworks:"Team/getHomeworks",
	getCreator:"Team/getCreator",
	getNums:"Team/getNums",
	getHomeworkQuestions:"Team/getHomeworkQuestions",
	setTeam:"Team/setTeam",
	setPersonalitySignature:"user/setPersonalitySignature"
};