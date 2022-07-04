angular.module('new-booking-front').controller('guestAccountController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/guest_account/';

    $scope.loadGuestAccount = function () {
        $http({
            url: contextPath + 'api/v1/account' + $scope.user.username,
            method: 'GET'
        }).then(function (response) {
            $scope.GuestAccount = response.data;
        });
    };



    $scope.loadGuestAccount();
});