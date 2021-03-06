/**
Author : Edric Laksa Putra
Since : June 2017
*/
cbtApp.controller('AdminAddUserController', ['$scope', '$state', 'AdminServices', function($scope, $state, AdminServices){


	$scope.addUser = function(){
		// var status = AdminServices.getTester($scope.newtester.username);
		$scope.errorAllert = null;
		if(status = true){
			var formDataUser = {
				"username" : $scope.newuser.username,
				"password" : $scope.newuser.password,
				"name"	   : $scope.newuser.name,
				"email"	   : $scope.newuser.email,
				"roles" :{
					"roleId"	: $scope.newuser.roleId
				}
			};

			var roles = {
				"roles" :{
					"roleId"	: $scope.newuser.roleId
				}
			}

			AdminServices.addUser(formDataUser, roles).then(function(res){
				var alert = "New user added";
				$state.go("home.userlist", {alert});
			});
		}
		else{
			$scope.errorAllert = "Username already been used";
		}

		AdminServices.getAllUser().then(function(res){
			$scope.arrayUsers = res.data;
		})
	}
}])