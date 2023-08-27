function like(btn, entityType, entityId,entityUserId,postId) {
    $.post(
        CONTEXT_PATH+"/like",
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},
        function (data) {
            $(btn).children("b").text(data.likeCount!=0?data.likeCount:'');
            $(btn).children("span").text(data.likeStatus==1?'已赞':'赞');
        }
    );
}
