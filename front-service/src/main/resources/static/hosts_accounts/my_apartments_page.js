 angular.module('new-booking-front').controller('myApartmentController', function ($scope, $http, $route, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1/apartments';
    $scope.loadMyApartments = function () {
            $http({
                url: contextPath + '/my_active',
                method: 'GET'
            }).then(function (response) {
                $scope.myApartments = response.data;
            });
    };

    $scope.loadHostAccount = function () {
    $http({
          url: 'http://localhost:5555/auth/api/v1/user/me/',
          method: 'GET'
    }).then(function (response) {
        $scope.hostAccount = response.data;
        });
    };

    $scope.transferToUpdate = function (apartmentId) {
        $localStorage.apartmentId = apartmentId;
        console.log("local " +  $localStorage.apartmentId);
        $location.path('/update_my_apartment_page');
    };

    $scope.loadMyApartments();
    $scope.loadHostAccount();

 });