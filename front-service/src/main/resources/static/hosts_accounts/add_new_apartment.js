 angular.module('new-booking-front').controller('apartmentController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1/apartments';

    $scope.loadHostAccount = function () {
    $http({
          url: 'http://localhost:5555/auth/api/v1/user/me/',
          method: 'GET'
    }).then(function (response) {
        $scope.hostAccount = response.data;
        });
    };


    $scope.tryToCreateApartment = function() {
        $http.post(contextPath, $scope.new_apartment)
            .then(function successCallback(response) {
                $scope.new_apartment = null;
                alert('Success! Апартамент добавлен');
                $location.path('/my_apartments_page');
            }
    )};

    $scope.tryToUpdateApartment = function() {
        $http.put(contextPath + '/update', $scope.new_apartment)
        .then(function successCallback(response) {
              $scope.new_apartment = null;
              alert('Success! Апартамент обновлен');
              $location.path('/my_apartments_page');
        }
    )};

    $scope.loadHostAccount();

 });