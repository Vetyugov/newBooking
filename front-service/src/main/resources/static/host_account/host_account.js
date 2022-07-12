angular.module('new-booking-front').controller('hostAccountController', function ($scope, $http, $location, $localStorage) {
    //const contextPath = 'http://localhost:5555/auth/';

    $scope.loadHostAccount = function () {
        $http({
            url: 'http://localhost:5555/auth/api/v1/host_account/' + $scope.hostAccount.id,
            method: 'GET'
        }).then(function (response) {
            $scope.hostAccount = response.data;
        });
    };



    $scope.loadHostAccount();
});