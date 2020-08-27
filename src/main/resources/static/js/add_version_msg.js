const appId = parseInt(window.location.href.split("=")[1]);
const hlist = new Vue({
    el: '#history_version_list',
    data: {
        versionList: null
    },
    mounted: function () {
        axios.get('/appversion?appId=' + appId)
            .then(response => {
                console.log(response.data)
                if (response.data.code !== 1) {
                    alert(response.data.msg)
                }
                // 设置历史版本列表
                this.$data.versionList = response.data.data
            })
    },
    methods: {
        historyVersionClick: function(versionId) {
            console.log(versionId)
            // 获取版本信息显示到表单
            axios.get('/appversion/version?versionId=' + versionId)
                .then(response => {
                    console.log(response.data)
                    if (response.data.code !== 1) {
                        alert(response.data.msg)
                    }
                    vform.$data.updateVersion = response.data.data
                })
        }
    }
});
const vform = new Vue({
    el: '#app_version_form',
    data: {
        statusList: null,
        updateVersion: null
    },
    watch: {
        updateVersion: function () {
            // 将要更新的信息渲染到 form
            const version = vform.$data.updateVersion
            $("#versionNo").val(version.versionNo)
            $("#versionSize").val(version.versionSize)
            $("#publishStatusName").find("option:contains("+version.publishStatusName+")").attr("selected", true)
            $("#versionInfo").val(version.versionInfo)

            $("#apkFileName").val(version.apkFileName)
            $("#apkFilePath").text(version.apkFileName)
            $("#apkFilePath").attr("href", version.apkFilePath)
        }
    },
    mounted: function () {
        axios.get('/enum/publishstatus', {headers:{Authorization:localStorage.getItem("token")}})
            .then(response => {
                console.log(response.data)
                if (response.data.code !== 1) {
                    alert(response.data.msg)
                }
                // 设置发布状态列表
                this.$data.statusList = response.data.data
            })
    }
});
$(function() {
    $("#submit_btn").bind("click", function () {
        // 检查 form
        if (!formCheck()) {
            alert("参数为空")
            return false;
        }
        // 拼接 url
        let url
        if (vform.$data.updateVersion != null) {
            url = "/appversion/version?appId=" + appId + "&versionId=" + vform.$data.updateVersion.versionId
        } else {
            url = "/appversion?appId=" + appId
        }
        $("#app_version_form").ajaxSubmit({
                url: url,
                type: 'POST',
                dataType: "text",
                headers: {Authorization:localStorage.getItem("token")},
                success: function(jsonResult) {
                    const json = JSON.parse(jsonResult)
                    console.log(json)
                    if (json.code !== 1) {
                        alert(json.msg)
                    }
                    else {
                        window.location.reload()
                    }
                },
                error: function(data) {
                    alert("表单提交错误")
                    console.log(data)
                },
                clearForm: false,
                resetForm: false
            },
            null,
            "text",
            null
        )
        return false
    });

    // 无效？
    $("#versionSize").bind("onkeyup", function () {
        $(this).val($(this).val().replace(/[^0-9.]/g, ''));
    }).bind("paste", function () { //CTR+V事件处理
        $(this).val($(this).val().replace(/[^0-9.]/g, ''));
    }).css("ime-mode", "disabled"); //CSS设置输入法不可用
});
function formCheck() {
    return $("#versionNo").val().length > 0
    && $("#versionSize").val().length > 0
    && $("#versionInfo").val().length > 0
}