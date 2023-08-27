$(function(){
	$(".follow-btn").click(follow);
});

function follow() {
	var btn = this;
	if($(btn).hasClass("btn-info")) {
		var entityId=$(btn).prev().val();
		$.post(
			CONTEXT_PATH+"/follow",
			{"entityType":3,"entityId":entityId},
			function (data) {
				console.log(data);
			}
		);
		// 关注TA
		$(btn).text("已关注").removeClass("btn-info").addClass("btn-secondary");
	} else {
		var entityId=$(btn).prev().val();
		$.post(
			CONTEXT_PATH+"/unfollow",
			{"entityType":3,"entityId":entityId},
			function (data) {
				console.log(data);
			}
		);
		// 取消关注
		$(btn).text("关注TA").removeClass("btn-secondary").addClass("btn-info");

	}
}
