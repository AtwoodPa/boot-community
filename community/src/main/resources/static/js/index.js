$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");
	var title=$("#recipient-name").val();
	var content=$("#message-text").val();
	var url=CONTEXT_PATH+"/discusspost/add";
	$.ajax({
		type:"POST",
		url: url,
		data: {
			"title": title,
			"content": content
		},
		success:function (data) {
			console.log(data);
			console.log(data.code);
			$("#hintBody").text(data.msg);
			$("#hintModal").modal("show");
			setTimeout(function(){
				$("#hintModal").modal("hide");
				window.location.reload();
			}, 2000);
		},
		error:function (data) {
			console.log("错误内容"+data);
			$("#hintModal").modal("show");
			setTimeout(function(){
				$("#hintModal").modal("hide");
			}, 2000);
		}
	});

}
