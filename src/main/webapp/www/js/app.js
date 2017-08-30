// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic', 'starter.controllers', 'starter.services','ui.router','starter.directive','starter.filter'])

.run(function($ionicPlatform,$rootScope,$state,$http,$timeout) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);

    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });

    var url = location.href,
    locatio = url.substring(url.indexOf('code='),url.indexOf('&')),
    codeWebsite = codeurl+'/wx/getOpenId?'+locatio;
    sessionStorage.setItem('codeWebsite',codeWebsite);
    /*
    $http.get(codeWebsite).success(function(data){
      alert(1)
      sessionStorage.setItem('openid',data.openid)
      sessionStorage.setItem('userid',data.userId)
      /*
      if(data.userId==0||data.userId==null||data.userId==''||data.userId==undefined){
        $timeout(function() {
          $rootScope.reg_modal.show();
        }, 1000);
      }else{
        var Personal = codeurl+'user/getUserById?id='+data.userId;
          $http.get(Personal).success(function(date){
              sessionStorage.setItem("username", date.data.username);
              sessionStorage.setItem("usableSum", date.data.usableSum);
              sessionStorage.setItem("userid", date.data.id);
              $rootScope.$broadcast('loginComplete',date);
              $state.go('tab.dash');
              $timeout(function() {
                $rootScope.inlet_modal.hide();
              }, 1000);
          });
      }*/

    //});

  $rootScope.$on('$ionicView.beforeEnter', function() {
      var statename = $state.current.name;
      if(statename ==='tab.account'||statename ==='tab.dash'){
          $rootScope.hideTabs = false;
      }else{
          $rootScope.hideTabs = true;
      }

      var user = sessionStorage.getItem("username");
      var re = /\d{11}/;
      if(!(re.test(location.href))){
        if(user==''||user==null||user==undefined){
          $state.go('tab.account');
        }
      }
  });

})
.config(function($httpProvider){
  
  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
  
  $httpProvider.defaults.withCredentials = true;//解决验证码和后台不同步问题

})

.config(function($stateProvider, $urlRouterProvider,$ionicConfigProvider) {
        $ionicConfigProvider.platform.ios.tabs.style('standard'); 
        $ionicConfigProvider.platform.ios.tabs.position('bottom');
        $ionicConfigProvider.platform.android.tabs.style('standard');
        $ionicConfigProvider.platform.android.tabs.position('bottom');

        $ionicConfigProvider.platform.ios.navBar.alignTitle('center'); 
        $ionicConfigProvider.platform.android.navBar.alignTitle('center');

        $ionicConfigProvider.platform.ios.backButton.previousTitleText('').icon('ion-ios-arrow-thin-left');
        $ionicConfigProvider.platform.android.backButton.previousTitleText('').icon('ion-android-arrow-back');        

        $ionicConfigProvider.platform.ios.views.transition('ios'); 
        $ionicConfigProvider.platform.android.views.transition('android');
  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider

  // setup an abstract state for the tabs directive
    .state('tab', {
    url: '/tab',
    abstract: true,
    templateUrl: 'templates/tabs.html'
  })

  // Each tab has its own nav history stack:

  .state('tab.dash', {
    url: '/dash',
    //cache:false,
    views: {
      'tab-dash': {
        templateUrl: 'templates/tab-dash.html',
        controller: 'DashCtrl'
      }
    }
  })

  .state('tab.order', {
    url: '/order',
    params:{'advance':null},
    cache:false,
    views: {
      'tab-dash': {
        templateUrl: 'templates/tab-order.html',
        controller: 'orderCtrl'
      }
    }
  })

  .state('tab.detail-shar', {
    url: '/detail/shar/:id/:username',
    cache:false,
    views: {
      'tab-dash': {
        templateUrl: 'templates/tab-detail.html',
        controller: 'detailCtrl'
      }
    }
  })

  .state('tab.detail', {
    url: '/detail',
    params:{'circumstance':null},
    cache:false,
    views: {
      'tab-dash': {
        templateUrl: 'templates/tab-detail.html',
        controller: 'detailCtrl'
      }
    }
  })

  .state('tab.orderDetail', {
    url: '/orderDetail', params:{'id':null},
    cache:false,
    views: {
      'tab-account': {
        templateUrl: 'templates/tab-orderDetail.html',
        controller: 'orderDetailCtrl'
      }
    }
  })
  
  .state('tab.discount', {
    url: '/discount',
    //cache:false,
    views: {
      'tab-account': {
        templateUrl: 'templates/discount.html',
        controller: 'discountCtrl'
      }
    }
  })

  .state('tab.invite', {
    url: '/invite',
    //cache:false,
    views: {
      'tab-account': {
        templateUrl: 'templates/tab-invite.html',
        controller: 'inviteCtrl'
      }
    }
  })

  .state('tab.invite_capital', {
    url: '/invite_capital',
    //cache:false,
    views: {
      'tab-account': {
        templateUrl: 'templates/invite_capital.html',
        controller: 'invite_capitalCtrl'
      }
    }
  })

  .state('tab.classify', {
    url: '/classify',
    cache:false,
    views: {
      'tab-account': {
        templateUrl: 'templates/classification.html',
        controller: 'classifyCtrl'
      }
    }
  })

  .state('tab.echarge', {
    url: '/echarge',
    cache:false,
    views: {
      'tab-account': {
        templateUrl: 'templates/Recharge.html',
        controller: 'RechargeCtrl'
      }
    }
  })

  .state('tab.account', {
    url: '/account',
    cache:false,
    views: {
      'tab-account': {
        templateUrl: 'templates/tab-account.html',
        controller: 'AccountCtrl'
      }
    }
  });

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/tab/dash');

});
