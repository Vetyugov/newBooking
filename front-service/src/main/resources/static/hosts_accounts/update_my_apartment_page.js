angular.module('new-booking-front').controller('updateMyApartmentController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1/apartments';

    $scope.loadApartmentForUpdate = function () {
            $http({
                url: contextPath + '/' + $localStorage.apartmentId,
                method: 'GET'
            }).then(function (response) {
                $scope.apartment = response.data;
                console.log(response.data)
            });
    };

    $scope.tryToUpdateApartment = function() {
        $http.put(contextPath, $scope.update_apartment)
        .then(function successCallback(response) {
              alert('Success! Апартамент обновлен');
              $location.path('/my_apartments_page');
        }
    )};


    $scope.loadApartmentForUpdate();

});