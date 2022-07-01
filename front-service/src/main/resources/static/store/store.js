angular.module('new-booking-front').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadApartments = function (pageIndex = 1) {
        $http({
            url: contextPath + 'api/v1/apartments',
            method: 'GET',
            params: {
                p: pageIndex,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.ApartmentsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.ApartmentsPage.totalPages);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.addToBooking = function (apartmentId) {
        $http.get('http://localhost:5555/booking/api/v1/booking/' + $localStorage.springWebIncognitoBookingId + '/add/' + apartmentId)
            .then(function (response) {
            });
    }

    $scope.loadApartments();
});