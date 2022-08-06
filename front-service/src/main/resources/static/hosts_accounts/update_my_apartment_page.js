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

    $scope.tryToUpdateApartment = function() {//этот метод работает, если с фронта заполняется city
        $http.put(contextPath, $scope.update_apartment)
        .then(function successCallback(response) {
              alert('Success! Апартамент обновлен');
              $location.path('/my_apartments_page');
        }
    )};

//    $scope.tryToUpdateApartment = function(apartmentId, apartmentUsername) {//нерабочий метод ошибка 500
//        $http({
//            url: contextPath,
//            method: 'PUT',
//            params: {
//                id: apartmentId,
//                username: apartmentUsername,
//                title: $scope.update_apartment ? $scope.update_apartment.title : null,
//                category: $scope.update_apartment ? $scope.update_apartment.category : null,
//                city: $scope.update_apartment.addressDto ? $scope.update_apartment.addressDto.city : null,
//                street: $scope.update_apartment.addressDto ? $scope.update_apartment.addressDto.street : null,
//                building_number: $scope.update_apartment.addressDto ? $scope.update_apartment.addressDto.building_number : null,
//                square_meters: $scope.update_apartment ? $scope.update_apartment.square_meters : null,
//                number_of_guests: $scope.update_apartment ? $scope.update_apartment.number_of_guests : null,
//                number_of_rooms: $scope.update_apartment ? $scope.update_apartment.number_of_rooms : null,
//                number_of_beds: $scope.update_apartment ? $scope.update_apartment.number_of_beds : null,
//                price_per_night: $scope.update_apartment ? $scope.update_apartment.price_per_night : null,
//                }
//        }).then(function successCallback(response) {
//              alert('Success! Апартамент обновлен');
//              $location.path('/my_apartments_page');
//        }
//    )};

    $scope.loadApartmentForUpdate();

});