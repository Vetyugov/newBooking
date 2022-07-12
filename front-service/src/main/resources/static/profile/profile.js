angular.module('new-booking-front').controller('profileController', function ($scope, $http) {

    $scope.loadAccount = function () {
        $http({
            url: 'http://localhost:5555/auth/api/v1/profile/me',
            method: 'GET'
            }).then(function (response) {
            $scope.userProfile = response.data;
        });
    };


    $scope.loadAccount();
});