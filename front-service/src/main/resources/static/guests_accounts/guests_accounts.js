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
        $http.post(contextPath, $scope.updateGuestAccount)
            if ($scope.updateGuestAccount.name == null || $scope.updateGuestAccount.surname == null || $scope.updateGuestAccount.patronymic == null) {
                alert("Заполните все поля ввода!");
            }
            if ($scope.updateGuestAccount.name != null && $scope.updateGuestAccount.surname != null && $scope.updateGuestAccount.patronymic != null) {
                 $scope.updateHostAccount = null;
                 alert('Success! Данные обновлены');
                 $route.reload();
            }
    };


    $scope.loadGuestAccount();
});