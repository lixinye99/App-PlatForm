let pageNum = parseInt(window.location.href.split("=")[1]);
const app = new Vue({
    el:"#right-div",
    data:{
        categoryOneList:[],
        categoryTwoList:[],
        categoryThreeList:[],
        platFormList:[],
        statusList:[],
        totalNum:0,
        pageNum:pageNum,
        appInfoList:[],
        PrePage:1,
        NextPage:1,
        levelOne:"---请选择---",
        levelTwo:"---请选择---",
        levelThree:"---请选择---",
        selectStatus:"待审核",
        platformSelect:"手机",
        softName:""
    },
    methods:{
        searchByInfo:function () {
            const that = this;
            axios.post('/app/search',{
                "appName":that.softName,
                "appStatus":that.selectStatus,
                "categoryLevel1":that.levelOne,
                "categoryLevel2":that.levelTwo,
                "categoryLevel3":that.levelThree,
                "platformName":that.platformSelect
            },{headers:{Authorization:localStorage.getItem("token")}})
                .then(function (response) {
                    if(response.data.code == 50001){
                        alert("未查找到数据!")
                    }
                app.$data.appInfoList = [];
                app.$data.totalNum = 1;
                app.$data.pageNum = 1;
                app.$data.PrePage = 1;
                app.$data.NextPage = 1;
                app.$data.appInfoList.push(response.data.data)
            })
        },
        deleteApp: function (appId) {
            console.log("deleteApp: " + appId)
            axios.get('/app/delete?appId=' + appId)
                .then(response => {
                    console.log(response.data)
                    if (response.data.code !== 1) {
                        alert(response.data.msg)
                    } else {
                        window.location.reload()
                    }
                })
        },
        updateAppStatus: function (appId, status) {
            console.log("updateAppStatus: " + appId + " -- " + status)
            axios.get('/app/appStatus?appId=' + appId + "&status=" + status, {headers:{Authorization:localStorage.getItem("token")}})
                .then(response => {
                    console.log(response.data)
                    if (response.data.code !== 1) {
                        alert(response.data.msg)
                    } else {
                        window.location.reload()
                    }
                })
        }
    },
    mounted:function () {
        const that = this;
        axios.get('/app/list/'+that.pageNum,{headers:{Authorization:localStorage.getItem("token")}})
            .then(function (response) {
                if(response.data.code == 20001){
                    alert(response.msg)   ;
                    return;
                }
                app.$data.categoryOneList = response.data.data.categoryOneList;
                app.$data.platFormList= response.data.data.platFormList;
                app.$data.statusList = response.data.data.statusList;
                app.$data.appInfoList = response.data.data.appInfoList;
                app.$data.totalNum = response.data.data.totalCount;
                if(parseInt(that.pageNum)-1>0){
                    app.$data.PrePage = parseInt(that.pageNum)-1;
                }
                if(parseInt(that.pageNum)+1<=parseInt(that.totalNum/5)+1){
                    app.$data.NextPage = parseInt(that.pageNum)+1;
                }else{
                    app.$data.NextPage = pageNum;
                }
            });

    },
    watch:{
        levelOne(val,oldVal){
            this.levelTwo = "---请选择---";
            this.levelThree = "---请选择---";
            axios.post('/app/searchLevel',{"parentLevel":val})
                .then(function (response) {
                    app.$data.categoryTwoList = response.data.data
                })
        },
        levelTwo(val,oldVal){
            this.levelThree = "---请选择---"
            if(val == "---请选择---"){
                return;
            }
            axios.post('/app/searchLevel',{"parentLevel":val})
                .then(function (response) {
                    app.$data.categoryThreeList = response.data.data
                })
        }
    }
});