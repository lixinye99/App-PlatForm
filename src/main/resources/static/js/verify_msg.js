const appId = parseInt(window.location.href.split("=")[1]);
const iform = new Vue({
    el: '#app_info_form',
    data: {
    },
    mounted: function () {
        axios.get('/app/read?appId=' + appId)
            .then(response => {
                console.log(response.data)
                if (response.data.code !== 1) {
                    alert(response.data.msg)
                }
                const appInfoVo = response.data.data
                // 渲染到 form 上
                $("#softwareName").val(appInfoVo.softwareName)
                $("#apkFileName").val(appInfoVo.apkname)
                $("#supportROM").val(appInfoVo.supportROM)
                $("#interfaceLanguage").val(appInfoVo.interfaceLanguage)
                $("#softwareSize").val(appInfoVo.softwareSize)
                $("#downloads").val(appInfoVo.downloads)
                $("#platformName").val(appInfoVo.platformName)
                let category = appInfoVo.categoryLevel1
                if (appInfoVo.categoryLevel2 != null)
                    category += " --> " + appInfoVo.categoryLevel2
                if (appInfoVo.categoryLevel3 != null)
                    category += " --> " + appInfoVo.categoryLevel3
                $("#category").val(category)
                $("#status").val(appInfoVo.status)
                $("#appInfo").val(appInfoVo.appInfo)
                $("#appLogo").attr("src", appInfoVo.imagePath)
            })
    }
});
const lversion = new Vue({
    el: '#latest_version',
    data: {
    },
    mounted: function () {
        axios.get('/app/read/latestversionid?appId=' + appId)
            .then(response => {
                console.log(response.data)
                if (response.data.code !== 1) {
                    alert(response.data.msg)
                }
                const versionId = response.data.data
                const url = "/appversion/version?versionId=" + versionId
                // 查出最新 versionId 拼接 url
                axios.get(url)
                    .then(response => {
                        console.log(response.data)
                        // 渲染到 form 上
                        const version = response.data.data
                        $("#lversionNo").val(version.versionNo)
                        $("#lversionSize").val(version.versionSize)
                        $("#lpublishStatusName").val(version.publishStatusName)
                        $("#lversionInfo").val(version.versionInfo)

                        $("#apkFilePath").text(version.apkFileName)
                        $("#apkFilePath").attr("href", version.apkFilePath)
                    })
            })
    }
});
$(function () {
    $("#verify_on_btn").bind("click", function () {
        axios.get('/appverify/on?appId=' + appId, {headers:{Authorization:localStorage.getItem("token")}})
            .then(response => {
                if (response.data.code !== 1) {
                    alert(response.data.msg)
                }
                else {
                    window.location.href = 'index.html?pageNum=1'
                }
            })
    })
    $("#verify_off_btn").bind("click", function () {
        axios.get('/appverify/off?appId=' + appId, {headers:{Authorization:localStorage.getItem("token")}})
            .then(response => {
                if (response.data.code !== 1) {
                    alert(response.data.msg)
                }
                else {
                    window.location.href = 'index.html?pageNum=1'
                }
            })
    })
})