/**
Author : Edric Laksa Putra
Since : June 2017
*/
cbtApp.controller('TesterListUsersController', ['$scope', '$state', 'TesterServices', function($scope, $state, TesterServices){

	TesterServices.getAllUser().then(function(res){
		$scope.arrayUsers = res.data;
	})

	$scope.toDelete = function(id) {
		var valid = confirm("Are you sure want to delete user ?");
		if(valid == true){
			TesterServices.deleteUser(id).then(function(res){
				$state.go("hometester.userlist");
				TesterServices.getAllUser().then(function(res){
					$scope.arrayUsers = res.data;
				});
			});
		};
    }
}])