angular.module('starter.controllers', [])

.controller('DashCtrl', function($scope,$rootScope,$state,regService,Graphical,$location,$ionicPopup) {

    Graphical.details().success(function(data){
    	$scope.entry = data.data;
    	console.log($scope.entry)
    });
    $scope.circumstance = function(x){
    	$state.go('tab.detail');
        //sessionStorage.setItem('price',x.price);
        sessionStorage.setItem('Order_Id',x);
    }
    $scope.order = function(x){
    	$state.go('tab.order',{advance:x});
        sessionStorage.setItem('price',x.price);
    }
    $scope.share = function(c){
        console.log(c)
        sessionStorage.setItem('Order_Id',c.id);
        var phone = sessionStorage.getItem("username");
        $state.go('tab.detail-shar',{id:c.id,username:phone});
        $rootScope.succuss_modal.show();
        $scope.addressUrl = encodeURIComponent(location.href.split('#')[0]);
        Graphical.Shared_address($scope.addressUrl).success(function(data){
            console.log(data)
            wx.config({
                debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.appid, // 必填，公众号的唯一标识
                timestamp: data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.noncestr, // 必填，生成签名的随机串
                signature: data.signature,// 必填，签名，见附录1
                jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
            wx.ready(function(){
                //分享到朋友圈
                wx.onMenuShareTimeline({
                    title: c.title,
                    desc: c.sellPoint,
                    //link: shareurl+'#/tab/detail/shar/'+c.id,
                    link:'http://1w771143s8.iok.la/wine/#/tab/detail/shar/'+c.id+'/'+phone,
                    //imgUrl: 'http://1w771143s8.iok.la/upload/'+c.image,
                    imgUrl:shareurl+'upload/'+c.image,
                    trigger: function (res) {
                        // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
                        //alert('用户点击分享到朋友圈');
                    },
                    success: function (res) {
                        $ionicPopup.alert({
                            title: '提示',
                            template: '<div style="text-align:center;">分享成功</div>',
                        }).then(function(res) {
                            $state.go('tab.dash');
                            $rootScope.succuss_modal.hide();
                        });
                    },
                    cancel: function (res) {
                        $ionicPopup.alert({
                            title: '提示',
                            template: '<div style="text-align:center;">分享取消</div>',
                        }).then(function(res) {
                            $state.go('tab.dash');
                            $rootScope.succuss_modal.hide();
                        });
                    }
                });
                //分享给朋友
                wx.onMenuShareAppMessage({
                    title: c.title,
                    desc: c.sellPoint,
                    //link: shareurl+'#/tab/detail/shar/'+c.id,
                    link:'http://1w771143s8.iok.la/wine/#/tab/detail/shar/'+c.id+'/'+phone,
                    //imgUrl: 'http://1w771143s8.iok.la/upload/'+c.image,
                    imgUrl:shareurl+'upload/'+c.image,
                    trigger: function (res) {
                        // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
                        //alert('用户点击分享给朋友');
                    },
                    success: function () { 
                        // 用户确认分享后执行的回调函数
                        $ionicPopup.alert({
                            title: '提示',
                            template: '<div style="text-align:center;">分享成功</div>',
                        }).then(function(res) {
                            $state.go('tab.dash');
                            $rootScope.succuss_modal.hide();
                        });
                    },
                    cancel: function () { 
                        $ionicPopup.alert({
                            title: '提示',
                            template: '<div style="text-align:center;">分享取消</div>',
                        }).then(function(res) {
                            $state.go('tab.dash');
                            $rootScope.succuss_modal.hide();
                        });
                    }
                });
                        
            });
            wx.error(function(res){
                alert('内部错误')
                console.log(res)
            });
        }).error(function(){
            $ionicPopup.alert({
                title: '提示',
                template: '请检查网络'
            });
        });
    }
})

.controller('orderCtrl', function($scope,$rootScope,$state,$stateParams,Graphical,$http,$ionicHistory,$location) {
    $scope.data = $stateParams.advance;
    $scope.numbers = ['1件','2件','3件'];
    $scope.discounts = ['不预支付、无折扣','预支付订单总额100享8折','预支付订单总额200享7.5折','预支付订单总额300享7折'];
    if(sessionStorage.getItem('selected')==null){
        $scope.selected = $scope.numbers[1];
        $scope.discount = $scope.discounts[2];
    }else{
        $scope.selected = sessionStorage.getItem('selected');
        $scope.discount = sessionStorage.getItem('discount');
    }
    
    $scope.addressUrl = encodeURIComponent(location.href.split('#')[0]);

    if($scope.data==undefined||$scope.data==null||$scope.data==''){

        Graphical.order_information(sessionStorage.getItem('ordeid')).success(function(data){
            $scope.data = data.data;
        });
    }else{
        sessionStorage.setItem('ordeid',$scope.data.id)
    }

    if(sessionStorage.getItem('addres')==null||sessionStorage.getItem('addres')==undefined){
        $scope.address = '您还没有选择地址';
    }else{
        $scope.address = sessionStorage.getItem('addres');
    }
    console.log($scope.data)
    sessionStorage.setItem('idWX',$scope.data==null||$scope.data==''?sessionStorage.getItem('ordeid'):$scope.data.id);
})

.controller('classifyCtrl', function($scope,$state,Graphical,regService,$ionicPopup,$state,$ionicLoading) {
    $scope.index = 1;
    var userid = sessionStorage.getItem('userid');
    $ionicLoading.show({
        template: '请稍候...'
    });
    Graphical.circumstance(userid).success(function(data){
        $ionicLoading.hide();
    	console.log(data)
        $scope.particular = data.data;
        
    }).error(function(){
        $ionicLoading.hide();
    	$ionicPopup.alert({
            title: '提示',
            template: '请检查网络'
        });
    });

    $scope.particulars = function(id){
        $state.go('tab.orderDetail',{id:id});
        }


    $scope.logistics = function(){
        window.open('https://m.kuaidi100.com/index_all.html?type=yunda&postid=3979496654508#result');
    }

    $scope.Tobepaid = function(id){
        var urlWX = $scope.addressUrl = encodeURIComponent(location.href.split('#')[0]);
        Graphical.unpaid(id).success(function(msg,urlWX){
            regService.defrayal(msg);
        }).error(function(data){
            $ionicPopup.alert({
                title: '提示',
                template: '请检查网络'
            });
        });
    }
})

.controller('discountCtrl', function($scope,$state) {
    $scope.suffix = 1;
})

.controller('orderDetailCtrl', function($scope,$state,$stateParams,Graphical) {
    var id=$stateParams.id;
    Graphical.getOrderInformationById(id).success(function(data){
        $scope.msgdata=data.data;
    });
})

.controller('detailCtrl', function($scope,$state,$stateParams,$http,Graphical) {

    var Torderid = $stateParams.id;
    var orderid = sessionStorage.getItem('Order_Id');
    if(orderid==null||orderid==undefined||orderid==''){
        if(Torderid==null||Torderid==undefined||Torderid==''){
            $state.go('tab.dash');
        }else{
            Graphical.order_information(Torderid).success(function(data){
                $scope.msgdata=data.data;
                $scope.Actualprice = eval($scope.msgdata.price * ($scope.msgdata.remark*0.1));

                $scope.purchase = function(){
                    $state.go('tab.account');
                }
            });
        }
    }else{
        Graphical.order_information(orderid).success(function(data){
            $scope.msgdata=data.data;
            $scope.Actualprice = eval($scope.msgdata.price * ($scope.msgdata.remark*0.1));

            $scope.purchase = function(){
                if($stateParams.id==undefined||$stateParams.id==''||$stateParams.id==null){
                    $state.go('tab.order',{advance:data.data});//{}
                }
            }
        });
    }
})

.controller('inviteCtrl', function($scope,$state,$rootScope,Graphical) {

    var a = document.getElementsByClassName('User_list');
    var userid = sessionStorage.getItem('userid');
    $scope.suffix=1;

    var userName=sessionStorage.getItem("userName");
    //var wida=document.body.clientWidth;
    //var wid = wida/3.1;
    //var wid = wida;
    $scope.newuser = function(){

        $rootScope.qrcode_modal.show();
        var url = 'https://open.weixin.qq.com/connect/oauth2/authorize?'+
            'appid=wx1f99c4b7887e73e5&redirect_uri=http%3A%2F%2F1w771143s8.iok.la%2Fwine?'+
            'referrer='+sessionStorage.getItem('username')+'&response_type=code&scope=snsapi_userinfo#wechat_redirect';
        var t = 16,e = 'H',m='Byte',mb='UTF-8';
        document.getElementById('qrcodee').innerHTML = create_qrcode(url, t, e, m, mb);

         function create_qrcode  (text, typeNumber, errorCorrectionLevel, mode,mb) {

          qrcode.stringToBytes = qrcode.stringToBytesFuncs[mb];

          var qr = qrcode(typeNumber || 4, errorCorrectionLevel || 'M');
          qr.addData(text, mode);
          qr.make();

          return qr.createImgTag();
        };
    }
    for(var i = 0;i<a.length;i++){
        !function(i){
            a[i].onclick=function(){
                for(j=0;j<a.length;j++){
                    a[j].style.color="";
                    a[j].style.background='';
                    $scope.judge = false;
                }
            this.style.color="#fff";
            this.style.background="#000";
            }
        }(i)
        a[0].style.color="#fff";
        a[0].style.background="#000";
    }
    // Graphical.user_commend(userid).success(function(data){
    //     $scope.setdata = data.data;
    //     console.log(data)
    // });
    Graphical.Rebate(userid).success(function(value){
        console.log(value)
        $scope.setdata = value.data;
    });

})

.controller('invite_capitalCtrl', function($scope,$state,Graphical) {
    var userid = sessionStorage.getItem("userid");
    Graphical.User_details(userid).success(function(data){
        $scope.resut = data.data;
        // $scope.sum = parseFloat(data.data.usableSum).toFixed(2);
    });
    $scope.index = 1;
    $scope.records = function(x){
        $scope.index = x;
    }
    Graphical.Add_money(userid).success(function(duty){
        $scope.date = duty.data;
    });
})
.controller('RechargeCtrl', function($scope,$state,Graphical,regService) {

    $scope.admoney = function(unmber){
        var money = {
            id:'',num:1,amount:unmber,userid:sessionStorage.getItem('userid'),
            type:2,itemConfigAgioId:'',receiverName:'',receiverMobile:'',
            receiverState:'',receiverCity:'',receiverRegion:'',receiverRegion:'',
            receiverAddress:'',receiverZip:'',body:'充值',total_fee:unmber,
            url: encodeURIComponent(location.href.split('#')[0])
        };
       Graphical.Generate_order(money).success(function(data){
            console.log(data)
       });
    }
})

.controller('AccountCtrl', function($scope,$http,$ionicLoading,$rootScope,regService,$ionicPopup,$state,$timeout,Graphical,$http) {
   
    $ionicLoading.show({    
        template: '<ion-spinner icon="ios" class="spinner-calm"></ion-spinner>', //替换默认动画    
    });
    $http.get(sessionStorage.getItem("codeWebsite")).success(function(data){
        var userid = data.userId,userid1 = sessionStorage.getItem("userid");

        if((userid==null&&userid1==null)||(userid==''&&userid1=='')||(userid==undefined&&userid1==undefined)||(userid==0&&userid1==0)){
            regService.login();
            $ionicLoading.hide();
        }else{
            var open = sessionStorage.getItem('openid');
            if(open==undefined||open==null||open==''){
                sessionStorage.setItem('openid',data.openid);
            }
            if(userid1==null||userid1==''||userid1==undefined){
                Graphical.User_details(userid).success(function(date){
                    $ionicLoading.hide();
                    sessionStorage.setItem("username", date.data.username);
                    sessionStorage.setItem("userid", date.data.id);
                    $scope.dat = date.data;
                    var headimg = codeurl+'/wx/user/info?openid='+sessionStorage.getItem('openid');
                    $http.get(headimg).success(function(data_img){
                        alert(data_img.headimgurl)
                    });
                });
            }else{
                Graphical.User_details(userid1).success(function(date){
                    $scope.dat = date.data;
                    var headimg = codeurl+'/wx/user/info?openid='+sessionStorage.getItem('openid');
                    $http.get(headimg).success(function(data_img){
                        //alert(data_img.headimgurl)
                    });
                    $ionicLoading.hide(); 
                })
            }
            
        }
    }).error(function(){
        $ionicLoading.hide();
        $ionicPopup.alert({
            title: '提示',
            template: '请检查网络'
        });
    });

	$scope.$on('loginComplete',function(event,data){
        window.location.reload();
        /*
		console.log(data)
        $scope.dat = data.data;
        $scope.usableSum = parseFloat(data.data.usableSum).toFixed(2);
        $scope.username = data.data.username;*/
    });

    
});
