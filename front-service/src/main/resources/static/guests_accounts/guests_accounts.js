angular.module('new-booking-front').controller('guestsAccountController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/api/v1/guests_accounts/';

    $scope.loadGuestAccount = function () {
        $http({
             url: contextPath + 'me',
             method: 'GET'
        }).then(function (response) {
            $scope.guestAccount = response.data;
        });
    };

    $scope.tryToUpdateGuestAccount = function() {
        $http.put(contextPath, $scope.updateGuestAccount)
                 $scope.updateGuestAccount = null;
                 alert('Success! Данные обновлены');
                 $route.reload();
                 $route.reload();
    };


    $scope.loadGuestAccount();
});