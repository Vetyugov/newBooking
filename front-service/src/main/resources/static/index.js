(function () {
    angular
        .module('new-booking-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/booking', {
                templateUrl: 'booking/booking.html',
                controller: 'bookingController'
            })
            .when('/ordersGuest', {
                templateUrl: 'ordersGuest/ordersGuest.html',
                controller: 'ordersGuestController'
            })
            .when('/ordersHost', {
                templateUrl: 'ordersHost/ordersHost.html',
                controller: 'ordersHostController'
            })
            .when('/order_pay/:orderId', {
                templateUrl: 'order_pay/order_pay.html',
                controller: 'orderPayController'
            })
            .when('/legal_hosts_account', {
                templateUrl: 'hosts_account/legal_hosts_account.html',
                controller: 'legalHostsAccountController'
            })
            .when('/individual_hosts_account', {
                templateUrl: 'hosts_account/individual_hosts_account.html',
                controller: 'individualHostsAccountController'
            })
            .when('/guests_account', {
                templateUrl: 'guests_account/guests_account.html',
                controller: 'guestsAccountController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.springWebUser) {
            try {
                let jwt = $localStorage.springWebUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.springWebUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.springWebUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
            }
        }
        if (!$localStorage.springWebIncognitoBookingId) {
            $http.get('http://localhost:5555/booking/api/v1/booking/generate')
                .then(function successCallback(response) {
                    $localStorage.springWebIncognitoBookingId = response.data.value;
                });
        }
    }
})();

angular.module('new-booking-front').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/api/v1/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, role: response.data.role, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $http.get('http://localhost:5555/booking/api/v1/booking/' + $localStorage.springWebIncognitoBookingId + '/merge')
                        .then(function successCallback(response) {
                        });

                    $location.path('/');
                }
            }, function errorCallback(response) {
            });
    };

    $rootScope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.navToRegistration = function () {
            $location.path('/registration');

    };

    $rootScope.isUserLegalHost = function () {

        if ($localStorage.springWebUser && $localStorage.springWebUser.role == 'ROLE_LEGAL_HOST') {
            return true;
        } else {
            return false;
        }
    };

    $rootScope.isUserIndividualHost = function () {
        if ($localStorage.springWebUser && $localStorage.springWebUser.role == 'ROLE_INDIVIDUAL_HOST') {
            return true;
        } else {
            return false;
        }
    };

    $rootScope.isUserGuest = function () {
        if ($localStorage.springWebUser && $localStorage.springWebUser.role == 'ROLE_GUEST') {
            return true;
        } else {
            return false;
        }
    };

});