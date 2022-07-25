 angular.module('new-booking-front').controller('myApartmentController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1/apartments';

    $scope.loadMyApartments = function () {
            $http({
                url: 'http://localhost:5555/core/api/v1/apartments/my_apartments/' + $localStorage.springWebUser.username,
                method: 'GET'
            }).then(function (response) {
                $scope.myApartments = response.data;
            });
        };


    $scope.loadMyApartments();

 });