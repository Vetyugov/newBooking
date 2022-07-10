angular.module('new-booking-front').controller('ordersController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadOrders  = function () {
        $http({
            url: contextPath + 'api/v1/orders',
            method: 'GET'
        }).then(function (response) {
            $scope.MyOrders = response.data;
        });
    };

    $scope.loadOrdersHistory  = function () {
         $http({
            url: contextPath + 'api/v1/orders/history',
            method: 'GET'
         }).then(function (response) {
            $scope.MyOrdersHistory = response.data;
         });
    };


    $scope.goToPay = function (orderId) {
        $location.path('/order_pay/' + orderId);
    }


    $scope.loadOrders();
    $scope.loadOrdersHistory();
});