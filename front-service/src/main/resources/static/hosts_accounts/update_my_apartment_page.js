angular.module('new-booking-front').controller('updateMyApartmentController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1/apartments';

    $scope.deactivateApartmentForUpdate = function () {
        $http({
            url: contextPath + '/deactivate/' + $localStorage.apartmentId,
            method: 'GET'
        }).then(function successCallback() {
            alert('Success! Апартамент деактивирован. Внесите изменения.');
            $scope.loadApartmentForUpdate();
        });
    };

    $scope.loadApartmentForUpdate = function () {
            $http({
                url: contextPath + '/inactive/' + $localStorage.apartmentId,
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

    $scope.deactivateApartmentForUpdate();


});