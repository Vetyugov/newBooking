 angular.module('new-booking-front').controller('myApartmentController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1/apartments';

    $scope.loadMyApartments = function () {
            $http({
                url: contextPath + '/my',
                method: 'GET'
            }).then(function (response) {
                $scope.myApartments = response.data;
            });
        };

    $scope.tryToUpdateApartment = function() {
        $http.put(contextPath + '/update', $scope.update_apartment)
        .then(function successCallback(response) {
              alert('Success! Апартамент обновлен');
              $location.path('/my_apartments_page');
        }
    )};

    $scope.loadHostAccount = function () {
    $http({
          url: 'http://localhost:5555/auth/api/v1/user/me/',
          method: 'GET'
    }).then(function (response) {
        $scope.hostAccount = response.data;
        });
    };


    $scope.loadMyApartments();
    $scope.loadHostAccount();

 });