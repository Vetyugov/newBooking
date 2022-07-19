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
           method: 'POST'
        }).then(function (response) {
             $scope.modalMessage = response.data.message;
        });

    }
    $scope.loadOrders();
});