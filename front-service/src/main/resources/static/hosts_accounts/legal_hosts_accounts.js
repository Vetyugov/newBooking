angular.module('new-booking-front').controller('legalHostsAccountController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/api/v1/hosts_accounts/';

    $scope.loadLegalHostAccount = function () {
        $http({
            url: contextPath + '/legal',
            method: 'GET'
        }).then(function (response) {
            $scope.hostAccount = response.data;
        });
    };

    $scope.transferToMyApartmentsPage  = function() {
                $location.path('/my_apartments_page');
    };

    $scope.transferToPageForAddNewApartment = function() {
                $location.path('/add_new_apartment');
    };

    $scope.tryToUpdateLegalHostAccount = function() {
        $http.put(contextPath + 'legal/', $scope.updateLegalHostAccount)
                $scope.updateLegalHostAccount = null;
                alert('Success! Данные обновлены');
                $route.reload();
                $route.reload();
    };


    $scope.loadLegalHostAccount();
});
