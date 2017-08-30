angular.module('starter.directive', [])
.directive('payment', function(Graphical,regService,$ionicPopup,$state,$rootScope,$state) {
return {
    restrict: 'A',
    scope: true,
    replace: true,
    	link: function($scope, $iElement, $iAttrs) {
	    	$iElement.bind('click', function (event) {
	    		if($scope.address=="您还没有选择地址"){
	    			$ionicPopup.alert({
					    title: '提示',
					    template: '<div style="text-align:center;">请填写收货地址</div>',
				    });
				    return
	    		}
	    		var Detailed_parameters = {
			        openidlocal:sessionStorage.getItem('openid'),
			        body:'思燃-酒类',
			        url:$scope.addressUrl,
			        total_fee : eval($scope.selected.replace('件','')*sessionStorage.getItem('price'))
			    },itemConfigAgioId;
	    		if($scope.selected==undefined||$scope.selected==''||$scope.selected==null){
	    			$scope.selected = '1件';
	    		}
	    		if($scope.discount=='预支付订单总额100享8折'){
	    			Detailed_parameters.total_fee = 100;
	    			itemConfigAgioId = 2;
	    		}else if($scope.discount=='预支付订单总额200享7.5折'){
	    			Detailed_parameters.total_fee = 200;
	    			itemConfigAgioId = 3;
	    		}else if($scope.discount=='预支付订单总额300享7折'){
	    			Detailed_parameters.total_fee = 300;
	    			itemConfigAgioId = 4;
	    		}else{
	    			Detailed_parameters.total_fee = eval($scope.selected.replace('件','')*sessionStorage.getItem('price'));
	    			itemConfigAgioId = 1;
	    		}
    		    var parameter = {
			        id:sessionStorage.getItem('idWX'),
			        num:$scope.selected.replace('件',''),
			        amount:($scope.selected.replace('件','')*sessionStorage.getItem('price')),
			        userid:sessionStorage.getItem("userid"),
			        itemConfigAgioId:itemConfigAgioId,
			        receiverName:sessionStorage.getItem('receiverName'),
			        receiverMobile:sessionStorage.getItem('receiverMobile'),
			        receiverState:sessionStorage.getItem('receiverState'),
			        receiverCity:sessionStorage.getItem('receiverCity'),
			        receiverRegion:sessionStorage.getItem('receiverRegion'),
			        receiverAddress:sessionStorage.getItem('receiverAddress'),
			        receiverZip	:sessionStorage.getItem('receiverZip'),
			        body:'思燃-酒类',
			        total_fee:Detailed_parameters.total_fee*100,
			        //total_fee:1,
			        url:$scope.addressUrl
			    };
			    // alert('id'+parameter.id+'num'+parameter.num+'amount'+parameter.amount+
			    // 	'userid'+parameter.userid+'itemConfigAgioId'+parameter.itemConfigAgioId+
			    // 	'receiverName'+parameter.receiverName+'receiverMobile'+parameter.receiverMobile+
			    // 	'receiverState'+parameter.receiverState+'receiverCity'+parameter.receiverCity+
			    // 	'receiverRegion'+parameter.receiverRegion+'receiverAddress'+parameter.receiverAddress+
			    // 	'receiverZip'+parameter.receiverZip+'body'+parameter.body+'total_fee'+parameter.total_fee+
			    // 	'url'+parameter.url)

			    Graphical.Generate_order(parameter).success(function(msg){
			    	//alert(sessionStorage.getItem('openid'))
			    	// alert('公众号:'+msg.appId+'时间戳:'+msg.timeStamp+'随机字符:'+msg.nonceStr
			    	// 	+'签名:'+msg.signature+'订单ID:'+msg.package+'支付签名:'+msg.paySign)
			    	regService.defrayal(msg);
			    }).error(function(data){
			    	$ionicPopup.alert({
			            title: '提示',
			            template: '请检查网络'
			        });
			    });
		    })
		}
	}
})
.directive('sharaddress', function(Graphical) {
return {
    restrict: 'A',
    replace: true,
    	link: function(scope, iElement, iAttrs) {
	    	iElement.bind('click', function () {
	    		//alert(scope.addressUrl)
			    Graphical.Shared_address(scope.addressUrl).success(function(msg){
			    	//sessionStorage.setItem('address',msg)
			    // alert('公众号:'+msg.appid+'时间戳:'+msg.timestamp+
			    // 	  '随机字符:'+msg.noncestr+'签名:'+msg.signature)
				    wx.config({
				        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				        appId: msg.appid, // 必填，公众号的唯一标识
				        timestamp: msg.timestamp, // 必填，生成签名的时间戳
				        nonceStr: msg.noncestr, // 必填，生成签名的随机串
				        signature: msg.signature,// 必填，签名，见附录1
				        //jsApiList: ['chooseWXPay','openAddress','translateVoice'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2，如果只是支付，只用这一个参数就够了
				        jsApiList: ['checkJsApi','openAddress','getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2，如果只是支付，只用这一个参数就够了
				    });
					wx.ready(function(){
		                wx.openAddress({
		                    success: function (res) {
		                    	sessionStorage.setItem('addres',res.provinceName+res.cityName+res.countryName+res.detailInfo);
		                    	sessionStorage.setItem('receiverName',res.userName);
		                    	sessionStorage.setItem('receiverMobile',res.telNumber);
		                    	sessionStorage.setItem('receiverState',res.provinceName);
		                    	sessionStorage.setItem('receiverCity',res.cityName);
		                    	sessionStorage.setItem('receiverRegion',res.countryName);
		                    	sessionStorage.setItem('receiverAddress',res.detailInfo);
		                    	sessionStorage.setItem('receiverZip',res.postalCode);
		                    	
		                    	sessionStorage.setItem('selected',scope.selected);
		                    	sessionStorage.setItem('discount',scope.discount);
		                    	window.location.reload();
		                    },
		                    cancel : function (res) {
		                    	console.log(res)
		                        //alert('用户取消拉出地址');
		                    },
		                    fail: function (res) {
		                        //alert(JSON.stringify(res));
		                        alert('失败')
		                    }
		                });
		            });
		            wx.error(function(res){
		                alert('内部错误')
		                console.log(res)
		            });
		        });
		    })
		}
	}
});