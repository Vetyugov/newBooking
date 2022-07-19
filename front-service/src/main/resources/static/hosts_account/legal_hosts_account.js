angular.module('new-booking-front').controller('legalHostsAccountController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/api/v1/hosts_account/';

    $scope.loadLegalHostAccount = function () {
        $http({
            url: 'http://localhost:5555/auth/api/v1/hosts_account/legal/' + $localStorage.springWebUser.username,
            method: 'GET'
        }).then(function (response) {
            $scope.hostAccount = response.data;
        });
    };

    $scope.tryToUpdateLegalHostAccount = function() {
        $http.post(contextPath, $scope.updateHostAccount)
            .then(function successCallback(response) {
                $scope.updateHostAccount = null;
                alert('Success! Данные обновлены');
                $location.path('/legal_hosts_account');
            }
    };


    $scope.loadLegalHostAccount();
});
