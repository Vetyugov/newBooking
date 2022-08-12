 angular.module('new-booking-front').controller('individualHostsAccountController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/api/v1/hosts_accounts/';

        $scope.loadIndiHostAccount = function () {
            $http({
                url: contextPath + 'individual',
                method: 'GET'
            }).then(function (response) {
                $scope.hostAccount = response.data;
            });
        };

    $scope.tryToUpdateIndividualHostAccount = function() {
        $http.put(contextPath + 'individual/', $scope.updateIndividualHostAccount)
                $scope.updateIndividualHostAccount = null;
                alert('Success! Данные обновлены');
                $route.reload();
                $route.reload();
    };

    $scope.transferToMyApartmentsPage  = function() {
                $location.path('/my_apartments_page');
    };

    $scope.transferToPageForAddNewApartment = function() {
                $location.path('/add_new_apartment');
    };

    $scope.loadIndiHostAccount();

 });