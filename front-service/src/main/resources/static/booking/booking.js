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

    $scope.checkOut = function (itemId) {
        $http({
            url: contextPath + 'api/v1/booking' + $localStorage.springWebIncognitoBookingId + '/choose',
            method: 'GET',
            params: {
                id: itemId
            }
        }).then(function (response) {
            $scope.loadBooking();
        });
    };

    $scope.removeBookingItem = function (apartmentId, startDate, finishDate) {
        $http({
            url: contextPath + 'api/v1/booking/' + $localStorage.springWebIncognitoBookingId + '/remove',
            method: 'GET',
            params: {
                id: apartmentId,
                start_date: startDate,
                finish_date: finishDate
            }
        }).then(function (response) {
            $scope.loadBooking();
        });
    };

    $scope.loadBooking();
});