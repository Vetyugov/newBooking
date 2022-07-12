angular.module('new-booking-front').controller('bookingController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/booking/';

    $scope.loadBooking = function () {
        $http({
            url: contextPath + 'api/v1/booking/' + $localStorage.springWebIncognitoBookingId,
            method: 'GET'
        }).then(function (response) {
            $scope.booking = response.data;
        });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.clearBooking = function () {
        $http.get(contextPath + 'api/v1/booking/' + $localStorage.springWebIncognitoBookingId + '/clear')
            .then(function (response) {
                $scope.loadBooking();
            });
    }

    $scope.checkOut = function () {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            $scope.loadBooking();
            $scope.orderDetails = null
        });
    };

    $scope.loadBooking();
});