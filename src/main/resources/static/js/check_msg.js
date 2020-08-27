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
    }
});
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