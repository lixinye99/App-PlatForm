$(function () {
    let appId = parseInt(window.location.href.split("=")[1]);
    $("#postInfo").bind("click",function () {
        $("#appDetails").ajaxSubmit({
            url:"/app/update/"+appId,
            type:"PUT",
            dataType:"text",
            success:function (data) {
                const json = $.parseJSON(data);
                alert(json.msg);
                if(json.code == 1){
                    window.location.reload();
                }
            },
            error:function () {

            },
            clearForm: false,
            resetForm: false
        },null,"text",null)
        return false;
    });
})