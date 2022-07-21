 angular.module('new-booking-front').controller('individualHostsAccountController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/api/v1/hosts_accounts/';

        $scope.loadIndiHostAccount = function () {
            $http({
                url: 'http://localhost:5555/auth/api/v1/hosts_accounts/individual/' + $localStorage.springWebUser.username,
                method: 'GET'
            }).then(function (response) {
                $scope.hostAccount = response.data;
            });
        };

    $scope.tryToUpdateIndividualHostAccount = function() {
        $http.post(contextPath + 'individual/', $scope.updateIndividualHostAccount)
            if ($scope.updateIndividualHostAccount.name == null || $scope.updateIndividualHostAccount.surname == null || $scope.updateIndividualHostAccount.patronymic == null
            || $scope.updateIndividualHostAccount.inn == null || $scope.updateIndividualHostAccount.country == null || $scope.updateIndividualHostAccount.address == null
            || $scope.updateIndividualHostAccount.postcode == null || $scope.updateIndividualHostAccount.account == null ) {
            alert("Заполните все поля ввода!");
            }
            if ($scope.updateIndividualHostAccount.name != null && $scope.updateIndividualHostAccount.surname != null && $scope.updateIndividualHostAccount.patronymic != null
            && $scope.updateIndividualHostAccount.inn != null && $scope.updateIndividualHostAccount.country != null && $scope.updateIndividualHostAccount.address != null
            && $scope.updateIndividualHostAccount.postcode != null && $scope.updateIndividualHostAccount.account != null ) {
                $scope.updateIndividualHostAccount = null;
                alert('Success! Данные обновлены');
                $route.reload();
            }
    };

//        $scope.tryToUpdateIndividualHostAccount = function() {
//            $http.post(contextPath + '/individual/', $scope.updateIndividualHostAccount)
//                .then(function successCallback(response) {
//                    $scope.updateIndividualHostAccount = null;
//                    alert('Success! Данные обновлены');
//                    $location.path('/individual_hosts_account');
//                }
//        )};

    $scope.loadIndiHostAccount();

 });