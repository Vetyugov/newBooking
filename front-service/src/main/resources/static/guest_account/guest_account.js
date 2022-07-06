angular.module('new-booking-front').controller('guestAccountController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/account/';

    $scope.loadGuestAccount = function () {
        $http({
            url: contextPath + 'api/v1/guest_account' + $scope.user.id,
            method: 'GET'
        }).then(function (response) {
            $scope.GuestAccount = response.data;
        });
    };



    $scope.loadGuestAccount();
});