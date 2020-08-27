const app = new Vue({
    el:"#pannel-list",
    data:{
        categoryOneList:[],
        categoryTwoList:[],
        categoryThreeList:[],
        platFormList:[],
        levelOne:"---请选择---",
        levelTwo:"---请选择---",
        levelThree:"---请选择---"
    },
    mounted:function () {
        axios.get('/app/ready')
            .then(function (response) {
                app.$data.categoryOneList = response.data.data.categoryOneList;
                app.$data.platFormList=response.data.data.platFormList;
            })
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