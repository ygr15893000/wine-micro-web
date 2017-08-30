//var codeurl = 'http://192.168.1.112:8080';
var codeurl  = 'http://1w771143s8.iok.la/wine-mirco-web/'; 
var shareurl = 'http://1w771143s8.iok.la/';

angular.module('starter.services', [])

.factory('Graphical',function($http){
	return {
		regurl:function(data){
            var reg =codeurl+'/user/register?username='+data.username+'&password='+data.password+'&checkCode='+data.securityCode+'&phoneCode='+data.mobilecode+'&openid='+data.openid;
            console.log(reg)
            return $http({url:reg,method: 'POST',data:angular.toJson(data)});
		},
        logurl:function(data){
            var log =codeurl+'/user/login?username='+data.username+'&password='+data.password;
            return $http({url:log,method: 'POST',data:angular.toJson(data)});
        },
        details : function(){
            var list = codeurl+'/item/getAllItem';
            return $http.get(list);
        },
        circumstance : function(userid){
            var details = codeurl+'order/getListOrderByUid?userId='+userid;
            return $http.get(details);
        },
        order_information : function(OrderId){
            var Order = codeurl+'/item/getItem?id='+OrderId;
            return $http.get(Order);
        },
        getOrderInformationById : function(OrderId){
            var Order = codeurl+'order/getOrderInformationById?id='+OrderId;
            return $http.get(Order);
        },
        Payment_interface:function(infor){
            //var uurl = codeurl+'/wx/wxPay?body='+infor.body+'&total_fee='+infor.total_fee+'&openid='+infor.openidlocal;
            var uurl = codeurl+'/wx/wxPay?body='+infor.body+'&total_fee='+infor.total_fee+'&openid='+infor.openidlocal+'&url='+infor.url;

            console.log(uurl)
            return $http.get(uurl);
        },
        Shared_address : function(addressUrl){
            var address = codeurl+'/wx/getSignature?url='+addressUrl;
            console.log(address)
            return $http.get(address);
            //return $http.post(address);
        },
        User_details : function(User){
            var detail = codeurl+'/user/getUserById?id='+User;
            return $http.get(detail);
        },
        Rebate : function(user){
            var benefit = codeurl+'user/finddecutdetail?userId='+user;
            return $http.get(benefit);
        },
        unpaid : function(id,url){
            var pay = codeurl+'wx/wxPay?id='+id+'&url='+url;
            return $http.get(pay);
        },
        Add_money : function(user){
            var _money = codeurl+'/user/getUserTwith?userId='+user;
            return $http.get(_money);
        },
        Generate_order : function(msg){

            _order = codeurl+'wx/wxPay?itemId='+msg.id+'&num='+msg.num+
                '&amount='+msg.amount+'&userId='+msg.userid+
                '&itemConfigAgioId='+msg.itemConfigAgioId+'&receiverName='+msg.receiverName+
                '&receiverMobile='+msg.receiverMobile+'&receiverState='+msg.receiverState+
                '&receiverCity='+msg.receiverCity+'&receiverRegion='+msg.receiverRegion+
                '&receiverAddress='+msg.receiverAddress+'&receiverZip='+msg.receiverZip+
                '&body='+msg.body+'&total_fee='+msg.total_fee+'&url='+msg.url;
            if(msg.type==2){
                
                var _order_ = codeurl+'wx/wxPay?num=1&amount='+msg.amount+'&userId='+msg.userid+
                '&body='+msg.body+'&total_fee='+msg.total_fee+'&url='+msg.url+'&type=2';
                console.log(_order_)
                return $http.get(_order_);
            }else{
                _order+='&type=1';
                console.log(_order)
                return $http.get(_order);
            }
        }
	};
})

.factory('regService', function($ionicModal,$rootScope,$timeout,$ionicPopup,$ionicLoading,Graphical,$state) {
    $rootScope.regData = {};
    $rootScope.loginData = {};
    //入口的弹窗
    $ionicModal.fromTemplateUrl('templates/tab-intro.html', {
        scope: $rootScope,
        animation: 'slide-right-left'
    }).then(function (modal) {
        $rootScope.inlet_modal = modal;
    });
	//注册的弹窗
    $ionicModal.fromTemplateUrl('templates/reg.html', {
        scope: $rootScope,
        animation: 'slide-right-left'
    }).then(function (modal) {
        $rootScope.reg_modal = modal;
    });
    //登录的弹窗
    $ionicModal.fromTemplateUrl('templates/log.html', {
        scope: $rootScope,
        animation: 'slide-right-left'
    }).then(function (modal) {
        $rootScope.log_modal = modal;
    });
    //分享的弹窗
    $ionicModal.fromTemplateUrl('templates/succuss.html', {
        scope: $rootScope,
        animation: 'slide-right-left'
    }).then(function (modal) {
        $rootScope.succuss_modal = modal;
    });
    //二维码弹窗
    $ionicModal.fromTemplateUrl('templates/qrcode.html', {
        scope: $rootScope,
        animation: 'slide-right-left'
    }).then(function (modal) {
        $rootScope.qrcode_modal = modal;
    });
    //隐藏分享弹框
    $rootScope.WXchathide =function(){
        $state.go('tab.dash');
        $rootScope.succuss_modal.hide();
    }
    //跳到登录
    $rootScope.debarkation = function(){
        $rootScope.inlet_modal.hide();
        $rootScope.log_modal.show();
    }
    //跳到注册
    $rootScope.enroll = function(){
        $rootScope.inlet_modal.hide();
        $rootScope.reg_modal.show();
    }
    $rootScope.codehiding = function(){
        $rootScope.qrcode_modal.hide();
    }
    //点击注册按钮
    $rootScope.doReg = function () {
		if (typeof($rootScope.regData.username) == "undefined" || $rootScope.regData.username.length <= 0) {
            $ionicPopup.alert({
                title: '注册异常',
                template: '请输入手机号'
            });
            return;
        }
        if(!(/^1[34578]\d{9}$/.test($rootScope.regData.username))){
            $ionicPopup.alert({
                title: '注册异常',
                template: '手机号有误'
            });
            return;
        }
        if (typeof($rootScope.regData.password) == "undefined" || $rootScope.regData.password.length <= 0) {
            $ionicPopup.alert({
                title: '注册异常',
                template: '请输入正确的密码'
            });
            return;
        }
        if (typeof($rootScope.regData.securityCode) == "undefined" || $rootScope.regData.securityCode.length <= 0) {
            $ionicPopup.alert({
                title: '注册异常',
                template: '请输入图形验证码'
            });
            return;
        }
        if (typeof($rootScope.regData.mobilecode) == "undefined" || $rootScope.regData.mobilecode.length <= 0) {
            $ionicPopup.alert({
                title: '注册异常',
                template: '请输入手机验证码'
            });
            return;
        }
        $ionicLoading.show({
            template: '注册中...'
        });
        sessionStorage.setItem("User_name",$rootScope.regData.username);
        $rootScope.regData.openid = sessionStorage.getItem('openid');
        Graphical.regurl($rootScope.regData).success(function(data){
            if(data.code!=997){
                $ionicLoading.hide();
                $ionicPopup.alert({
                    title: '注册异常',
                    template: data.desc
                });
                return;
            }else{
                $ionicLoading.hide(); 
                $ionicPopup.alert({
                    title: '恭喜你',
                    template: '<div style="text-align:center">'+data.desc+'<div>'
                });
                $rootScope.reg_modal.hide();//注册框隐藏
                $rootScope.log_modal.show();//登录框显示
            }
        }).error(function(data){
            $ionicLoading.hide(); 
            $ionicPopup.alert({
                title: '注册异常',
                template: '请检查网络'
            });
        });
    }
    //点击登录
    $rootScope.login = function(){
        // $rootScope.loginData.username = 18566683401;
        // $rootScope.loginData.password = 111111;
        if (typeof($rootScope.loginData.username) == "undefined" || $rootScope.loginData.username.length <= 0) {
            $ionicPopup.alert({
                title: '登录异常',
                template: '请输入手机号'
            });
            return;
        }
        if (typeof($rootScope.loginData.password) == "undefined" || $rootScope.loginData.password.length <= 0) {
            $ionicPopup.alert({
                title: '登录异常',
                template: '请输入正确的密码'
            });
            return;
        }/*
        if(typeof($rootScope.loginData.wechat) == "undefined" || $rootScope.loginData.wechat.length <= 0){
            $ionicPopup.alert({
                title: '登录异常',
                template: '请输入微信号'
            });
            return;
        }
        if($rootScope.loginData.toggle==undefined||$rootScope.loginData.toggle==false){
            $ionicPopup.alert({
                title: '登录异常',
                template: '请输绑定微信'
            });
            return;
        }*/
        $ionicLoading.show({
            template: '登录中...'
        });

        Graphical.logurl($rootScope.loginData).success(function(log){
            $rootScope.information = log;
            if(log.code!=999){
                $ionicLoading.hide();
                $ionicPopup.alert({
                    title: '提示',
                    template: log.desc
                });
                return
            }
            sessionStorage.setItem("username", $rootScope.loginData.username);
            //sessionStorage.setItem("usableSum", log.data.usableSum);
            sessionStorage.setItem("userid", log.data.id);
            $ionicLoading.hide(); 
            $rootScope.log_modal.hide();
            //$rootScope.inlet_modal.hide();
            $rootScope.$broadcast('loginComplete',log);
        }).error(function(data){
            $ionicLoading.hide(); 
            $ionicPopup.alert({
                title: '登录异常',
                template: '请检查网络'
            });
        });
    }
    //点击更换验证码
    $rootScope.imgcode = codeurl+'/code/validateImage';
    $rootScope.imgcodeboon = true;
    $rootScope.checkcode = function(){
        $rootScope.imgcodeboon = !$rootScope.imgcodeboon;
        $rootScope.imgcode = codeurl+'/code/validateImage';
    }
    
    return {
        reg : function(){
      	    $timeout(function() {
      		    $rootScope.reg_modal.show();
      	    }, 100);
        },
        login : function(){
      	    $timeout(function() {
      		    $rootScope.log_modal.show();
      	    }, 100);
        },
        Entrance : function(){
            try{
                $timeout(function() {
                    $rootScope.inlet_modal.show();
                },1000);
            }catch(e){
                alert('未知错误，刷新页面')
            }
        },
        defrayal : function(msg){
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: msg.appId, // 必填，公众号的唯一标识
                timestamp: msg.timeStamp, // 必填，生成签名的时间戳
                nonceStr: msg.nonceStr, // 必填，生成签名的随机串
                signature: msg.signature,// 必填，签名，见附录1
                //jsApiList: ['chooseWXPay','openAddress','translateVoice'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2，如果只是支付，只用这一个参数就够了
                jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2，如果只是支付，只用这一个参数就够了
            });
            wx.ready(function(){
                wx.chooseWXPay({
                    appId: msg.appId,
                    timestamp: msg.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                    nonceStr: msg.nonceStr, // 支付签名随机串，不长于 32 位
                    package: msg.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                    signType: msg.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                    paySign: msg.paySign, // 支付签名

                    success: function (res) {
                        // 支付成功后的回调函数
                        // alert('支付成功')
                        //console.log(res)
                        $ionicPopup.alert({
                            title: '提示',
                            template: '<div style="text-align:center;">您已经付款成功</div>',
                            buttons: [
                                {
                                    text: '确定',
                                    type: 'button-positive',
                                    onTap: function(e) {
                                        $state.go('tab.dash');
                                    }
                                }
                            ]
                        });
                    },
                    //如果你按照正常的jQuery逻辑，下面如果发送错误，一定是error，那你就太天真了，当然，jssdk文档中也有提到
                    fail: function(res) {
                        alert('支付失败')
                        console.log(res)
                        //接口调用失败时执行的回调函数。
                    },
                    complete: function(res) {
                        // alert('调用完成')
                        //接口调用完成时执行的回调函数，无论成功或失败都会执行。
                    },
                    cancel: function(res) {
                        $ionicPopup.alert({
                            title: '提示',
                            template: '<div style="text-align:center;">您取消了付款</div>',
                            buttons: [{
                                text: '再想想',
                                type: 'button-positive'},
                                {text: '确定',}
                            ]
                        });
                    //用户点击取消时的回调函数，仅部分有用户取消操作的api才会用到。
                    },
                    trigger: function(res) {
                        alert('监听Menu')
                        //监听Menu中的按钮点击时触发的方法，该方法仅支持Menu中的相关接口。
                    }
                });
            });
            wx.error(function(res){
                alert('内部错误')
                console.log(res)
            });
        }
    };
});
