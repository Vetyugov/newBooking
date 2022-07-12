
angular.module('new-booking-front').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/api/v1/profile';

    $scope.tryToRegistration = function () {
        $http.post(contextPath, $scope.new_user)
            .then(function successCallback(response) {
                $scope.new_user = null;
                alert('Success! Необходимо авторизоваться');
                $location.path('/index');
            }, function failureCallback(response) {
                if (response.data.userMessage == null) {
                    alert("Заполните поля ввода для регистрации!");
                } else if (Array.isArray(response.data.userMessage)) {
                    var result = "Не заполнены все обязательные поля: ";
                    for (let i = 0; i < response.data.userMessage.length; i++) {
                        result += "\r\n\r\n" + response.data.userMessage[i].message
                    }
                    alert(result.toString());
                } else {
                    alert(response.data.userMessage);
                }
            });
    };
