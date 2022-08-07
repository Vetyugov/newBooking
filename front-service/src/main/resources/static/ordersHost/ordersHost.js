angular.module('new-booking-front').controller('ordersHostController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadOrders  = function () {
        $http({
            url: contextPath + 'api/v1/orders/host',
            method: 'GET'
        }).then(function (response) {
            $scope.MyOrders = response.data;
        });
    };

    $scope.outCash = function (orderId) {
        $http({
            url: contextPath + 'api/v1/orders/host/outCash/'+orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadOrders();
            alert(response.data.message);
        });
    };

    $scope.confirmOrder = function (orderId) {
                $http({
                   url: contextPath + 'api/v1/orders/confirmOrder/'+orderId,
                   method: 'GET'
                }).then(function (response) {
                   $scope.loadOrders();
                   $scope.loadOrdersHistory();
                });
    };

    $scope.isStatusConfirmedStay = function (status) {
             if (status == 'выполнен') {
                  return true;
             } else {
                  return false;
             }
        };


    $scope.isStatusPaid = function (status) {
         if (status == 'оплачен') {
              return true;
         } else {
              return false;
         }
    };

    $scope.loadOrders();
});