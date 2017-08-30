var  myApp = angular.module('myApp', []).controller('namesCtrl', function ($scope) {
    $scope.names = [
      {name: 'Jani', country: 'Norway'},
      {name: 'Hege', country: 'Sweden'},
      {name: 'Kai', country: 'Denmark'}
    ];
  });


  var  myApp1 =  angular.module('myApp1', []).controller('namesCtrl', function ($scope) {
    $scope.names = [
        {name: 'Jani', country: 'Norway'},
        {name: 'Hege', country: 'Sweden'},
        {name: 'Kai', country: 'Denmark'}
    ];
});

var myApp2 =angular.module("myApp2",["ngRoute"]);
myApp2.controller("namesCtrl",function($scope,$location,$http,$timeout){
    $scope.url = $location.absUrl();

    $http.get("http://1w771143s8.iok.la/wine-mirco-web/wx/wxPay?itemId=4&itemConfigAgioId=1&num=1&amount=1000&userId=34&receiverName=1&receiverMobile=1&receiverState=1&receiverCity=1&receiverRegion=1&receiverAddress=1&receiverZip=1&body=1&total_fee=1"
    ).then(function(response){
        $scope.result = response.data;
        // $scope.resultJson = angular.toJson(response.data,number);
        console.log($scope.result);
        
        console.log($scope.resultJson);
        $timeout(function () {
            $scope.result = "How are you today?";
        }, 2000);
        
});
    $scope.lastName = "";
    $scope.firstName = "";

    //监听lastName的变化，更新fullName
    $scope.$watch('lastName', function() {
    $scope.fullName = $scope.lastName + " " + $scope.firstName;
    });

    //监听firstName的变化，更新fullName
    $scope.$watch('firstName', function() {
    $scope.fullName = $scope.lastName + " " + $scope.firstName;
    });

    $scope.reset = function() {
        $scope.user = angular.copy($scope.master);
    };
});
//默认情况下， ng-include 指令不允许包含其他域名的文件。
// 如果你需要包含其他域名的文件，你需要设置域名访问白名单：
// myApp2.config(function($sceDelegateProvider) {
//     $sceDelegateProvider.resourceUrlWhitelist([
//         'http://c.runoob.com/runoobtest/**'
//     ]);

    // myApp2.config(['$routeProvider', function($routeProvider){
    //     $routeProvider
    //     .when('/',{template:'这是首页页面'})
    //     .when('/computers',{template:'这是电脑分类页面'})
    //     .when('/printers', {
    //         templateUrl: 'templete1/printers.html',
    //     })
    //     .otherwise({redirectTo:'/'});
    // }]);

