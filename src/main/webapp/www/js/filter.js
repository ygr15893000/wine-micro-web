angular.module('starter.filter', [])

.filter('goldNumber', function () {
    return function (input) {
        return input.replace('件','')
    }

})
.filter('evilmoney', function () {
    return function (price) {
    	if(price==null||price==undefined||price==''){
            price = '0.00'
			return price;
    	}else{
    		return parseFloat(price).toFixed(2);
    	}
        
    }
})
.filter('condition', function () {
    return function (estate) {
        var array = ['未付款','待付尾款','已付款','退款','退款完成','待发货',
                     '已发货','退货','退货完成','确认收货','系统确认收货','交易关闭'];
        for(var i=0;i<array.length;i++){
            if(estate==i+1){
                estate = array[i];
                return estate;
            }
        }
    }
})
.filter('status', function () {
    var arry = ['默认审核中','已提现','取消','转账中','失败'];
    return function (stau) {
        for(var i = 0;i<arry.length;i++){
            if(stau==i+1){
                stau = arry[i];
                return stau;
            }
        }
    }
});