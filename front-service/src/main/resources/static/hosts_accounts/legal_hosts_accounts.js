angular.module('new-booking-front').controller('legalHostsAccountController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/api/v1/hosts_accounts/';

    $scope.loadLegalHostAccount = function () {
        $http({
            url: 'http://localhost:5555/auth/api/v1/hosts_accounts/legal/' + $localStorage.springWebUser.username,
            method: 'GET'
        }).then(function (response) {
            $scope.hostAccount = response.data;
        });
    };

    $scope.tryToUpdateLegalHostAccount = function() {
        $http.post(contextPath + 'legal/', $scope.updateLegalHostAccount)
            if ($scope.updateLegalHostAccount.name == null || $scope.updateLegalHostAccount.surname == null || $scope.updateLegalHostAccount.patronymic == null
            ||  $scope.updateLegalHostAccount.titleFirm == null || $scope.updateLegalHostAccount.country == null || $scope.updateLegalHostAccount.address == null
            || $scope.updateLegalHostAccount.postcode == null || $scope.updateLegalHostAccount.inn == null || $scope.updateLegalHostAccount.account == null ) {
                alert("Заполните все поля ввода!");
            }
            if ($scope.updateLegalHostAccount.name != null && $scope.updateLegalHostAccount.surname != null && $scope.updateLegalHostAccount.patronymic != null
            && $scope.updateLegalHostAccount.titleFirm != null  &&  $scope.updateLegalHostAccount.country != null && $scope.updateLegalHostAccount.address != null
            && $scope.updateLegalHostAccount.postcode != null && $scope.updateLegalHostAccount.inn != null && $scope.updateLegalHostAccount.account != null ) {
                $scope.updateLegalHostAccount = null;
                alert('Success! Данные обновлены');
                $route.reload();
            }
    };


    $scope.loadLegalHostAccount();
});
