angular.module('new-booking-front').controller('guestsAccountController', function ($scope, $http, $route, $location, $localStorage) {
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