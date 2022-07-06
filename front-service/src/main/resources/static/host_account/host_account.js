angular.module('new-booking-front').controller('hostAccountController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/host_account/';

    $scope.loadHostAccount = function () {
        $http({
            url: contextPath + 'api/v1/account' + $scope.user.username,
            method: 'GET'
        }).then(function (response) {
            $scope.HostAccount = response.data;
        });
    };



    $scope.loadHostAccount();
});