$(function () {
    $("#postInfo").bind("click",function () {
        $("#appDetails").ajaxSubmit({
            url:"/app/addAppInfo",
            type:"POST",
            dataType:"text",
            headers:{
                Authorization:localStorage.getItem("token")
            },
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