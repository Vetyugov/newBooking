angular.module('new-booking-front').controller('guestAccountController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.loadGuestAccount = function () {
        $http({
            url: contextPath + 'api/v1/guest_account/' + $scope.guestAccount.username,
            method: 'GET'
        }).then(function (response) {
            $scope.guestAccount = response.data;
        });
    };



    $scope.loadGuestAccount();
});