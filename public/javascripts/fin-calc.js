angular.module('fin-calc', ['ngResource'])
    .factory('Schedule', ['$resource', function ($resource) {
        return $resource('/api/schedule', null, {
           find: { method: 'PUT', isArray: true }
        });
    }])
    .controller('FindScheduleController', ['$scope', '$filter', 'Schedule', function($scope, $filter, Schedule) {
        $scope.params = {
            "principal": 30000.00,
            "interestRate": 10.0,
            "start": $filter('date')(new Date(), 'yyyy-MM-dd'),
            "nofPayments": 24
        };

        $scope.payments = [];

        $scope.findPayments = function() {
            $scope.payments = Schedule.find(null, $scope.params);
        };
    }]);