angular.module('new-booking-front').controller('guestsAccountController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/api/v1/guests_account/';

    $scope.loadGuestAccount = function () {
        $http({
             url: 'http://localhost:5555/auth/api/v1/guests_account/' + $localStorage.springWebUser.username,
             method: 'GET'
        }).then(function (response) {
            $scope.guestAccount = response.data;
        });
    };

    $scope.tryToUpdateGuestAccount = function() {
        $http.post(contextPath, $scope.updateHostAccount)
            .then(function successCallback(response) {
                $scope.updateHostAccount = null;
                alert('Success! Данные обновлены');
                $location.path('/legal_hosts_account');
            });
    };


    $scope.loadGuestAccount();
});