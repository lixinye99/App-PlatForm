const app = new Vue({
    el:"#login_form",
    data:{
        devName: "",
        devPassword: ""
    },
    // 用户登录
    login() {
        this.postData = {
            devName: this.devName,
            devPassword: this.devPassword,
        };
        axios.post('/user/login', this.postData)
            .then(res => {
                console.log(res)
                if (res.data.code === 1) {
                    location.href("../view/dev/index.html")
                    //全局存储token
                    window.localStorage["token"] = JSON.stringify(res.data.data.token);
                } else {
                    this.forgetPassword = true;
                }
            }).catch(err => {
            console.log("登录失败");
        })
    }
});