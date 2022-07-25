//angular.module('new-booking-front').controller('storeController', function ($scope, $http, $location, $localStorage) {
//    const contextPath = 'http://localhost:5555/core/';
//
//    $scope.loadApartments = function (pageIndex = 1) {
//        $http({
//            url: contextPath + 'api/v1/apartments',
//            method: 'GET',
//            params: {
//                p: pageIndex,
//                category_part: $scope.filter ? $scope.filter.category_part : null,
//                title_part: $scope.filter ? $scope.filter.title_part : null,
//                min_price: $scope.filter ? $scope.filter.min_price : null,
//                max_price: $scope.filter ? $scope.filter.max_price : null,
//                start_date: $scope.filter ? $scope.filter.start_date : null,
//                finish_date: $scope.filter ? $scope.filter.finish_date : null
//            }
//        }).then(function (response) {
//            $scope.ApartmentsPage = response.data;
//            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.ApartmentsPage.totalPages);
//        });
//    };
//
//    $scope.generatePagesIndexes = function (startPage, endPage) {
//        let arr = [];
//        for (let i = startPage; i < endPage + 1; i++) {
//            arr.push(i);
//        }
//        return arr;
//    }
//
//    $scope.addToBooking = function (apartmentId) {
//        $http({
//            url: 'http://localhost:5555/booking/api/v1/booking/'+ $localStorage.springWebIncognitoBookingId + '/add',
//            method: 'GET',
//            params: {
//                id: apartmentId,
//                start_date: "2022-08-01",//$scope.filter ? $scope.filter.start_date : null,
//                finish_date: "2022-08-03"//$scope.filter ? $scope.filter.finish_date : null
//            }
//        }).then(function (response) {
//        });
//    }
//
//    $scope.loadApartments();
//});


angular.module('new-booking-front').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadApartments = function (pageIndex = 1) {
        $http({
            url: contextPath + 'api/v1/apartments',
            method: 'GET',
            params: {
                p: pageIndex,
                city_part: $scope.filter ? $scope.filter.city_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                min_square_meters: $scope.filter ? $scope.filter.min_square_meters : null,
                max_square_meters: $scope.filter ? $scope.filter.max_square_meters : null,
                number_of_guests: $scope.filter ? $scope.filter.number_of_guests : null,
                number_of_rooms: $scope.filter ? $scope.filter.number_of_rooms : null,
                number_of_beds: $scope.filter ? $scope.filter.number_of_beds : null,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                category_part: $scope.filter ? $scope.filter.category_part : null,
                start_date: $scope.filter ? $scope.filter.start_date : null,
                finish_date: $scope.filter ? $scope.filter.finish_date : null
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
        $http({
            url: 'http://localhost:5555/booking/api/v1/booking/'+ $localStorage.springWebIncognitoBookingId + '/add',
            method: 'GET',
            params: {
                id: apartmentId,
                start_date: "2022-08-01",//$scope.filter ? $scope.filter.start_date : null,
                finish_date: "2022-08-03"//$scope.filter ? $scope.filter.finish_date : null
            }
        }).then(function (response) {
        });
    }

    $scope.loadApartments();
});