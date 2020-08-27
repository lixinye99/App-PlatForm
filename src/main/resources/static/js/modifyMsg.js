let appId = parseInt(window.location.href.split("=")[1]);
const app = new Vue({
    el:"#pannel-list",
    data:{
        appId:appId,
        categoryOneList:[],
        categoryTwoList:[],
        categoryThreeList:[],
        platFormList:[],
        appInfo:{},
        status:"",
        levelOne:"---请选择---",
        levelTwo:"---请选择---",
        levelThree:"---请选择---",
        flag:false
    },
    mounted:function () {
        axios.get('/app/ready')
            .then(function (response) {
                app.$data.categoryOneList = response.data.data.categoryOneList;
                app.$data.platFormList= response.data.data.platFormList;
                app.$data.statusList = response.data.data.statusList;
            })
        axios.get('/app/readyForUpdate/'+appId)
            .then(function (response) {
                app.$data.categoryTwoList = response.data.data.LevelTwoNameList;
                app.$data.categoryThreeList = response.data.data.LevelThreeNameList;
                app.$data.appInfo = response.data.data.appInfo;
                app.$data.levelOne = response.data.data.appInfo.categoryLevel1;
                app.$data.levelTwo = response.data.data.appInfo.categoryLevel2;
                app.$data.levelThree = response.data.data.appInfo.categoryLevel3;
                app.$data.flag = true;
            })
    },
    watch:{
        levelOne(val,oldVal){
            if(this.flag){
                return;
            }
            this.levelTwo = "---请选择---";
            this.levelThree = "---请选择---";
            axios.post('/app/searchLevel',{"parentLevel":val})
                .then(function (response) {
                    app.$data.categoryTwoList = response.data.data
                })
        },
        levelTwo(val,oldVal){
            if(this.flag){
                this.flag = false;
                return;
            }
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