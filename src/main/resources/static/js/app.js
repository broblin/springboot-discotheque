var sbdManagerModule = angular.module('categoryManagerApp', ['ngAnimate']);

sbdManagerModule.controller('categoryManagerController', function ($scope,$http) {
	
	var urlBase="";
	$scope.range=[];
	$scope.totalElements=0;
	$scope.pageNumber=1;
	$scope.toggle=true;
	$scope.selection = [];
	$scope.message="";
	$http.defaults.headers.post["Content-Type"] = "application/json";

    function findAllCategories() {
        //get all tasks and display initially
        $http.get(urlBase + '/categories?pageNumber='+$scope.pageNumber+'&pageSize=3').
            success(function (data) {
                if (data.content != undefined) {
                    $scope.categories = data.content;
                } else {
                    $scope.categories = [];
                }
                $scope.categoryName="";
                $scope.categoryId="";
                $scope.toggle='!toggle';
                //pagination range
                var range = [];
                for(var i=1;i<=data.totalPages;i++) {
                  range.push(i);
                }
                $scope.range = range;
                $scope.totalElements=data.totalElements;
                if(data.totalElements == 0){
                    $scope.message = "No category has been registered !";
                }
            });
    }

    findAllCategories();

    $scope.updateList = function updateList(pageNumber){
        $scope.pageNumber=pageNumber;
        findAllCategories();
    }

	//add a new category
	$scope.addCategory = function addCategory() {
	//if($scope.categoryName=="" || $scope.taskDesc=="" || $scope.taskPriority == "" || $scope.taskStatus == ""){
		if($scope.categoryName==""){
			alert("Insufficient Data! Please provide values for task name");
		}
		else{
		 $http.post(urlBase + '/category', {
             name: $scope.categoryName
         }).
		  success(function(data, status, headers) {
			 $scope.message="Category added";
             findAllCategories();
		    });
		}
	};

  // toggle selection for a given task by category id
  $scope.toggleSelection = function toggleSelection(categoryId) {
    var idx = $scope.selection.indexOf(categoryId);

    // is currently selected
    if (idx > -1) {
      $scope.selection.splice(idx, 1);
    }
    // is newly selected
    else {
      $scope.selection.push(categoryId);
    }
  };

	// Delete Categories
	  $scope.deleteCategories = function deleteCategories() {
          $scope.selection.forEach(function(categoryId) {
              if (categoryId != undefined) {
                $http.delete(urlBase + '/category/'+categoryId);
              }
          });
          $scope.message="Successfully deleted";
          findAllCategories();
	  };
	
});

//Angularjs Directive for confirm dialog box
sbdManagerModule.directive('ngConfirmClick', [
	function(){
         return {
             link: function (scope, element, attr) {
                 var msg = attr.ngConfirmClick || "Are you sure?";
                 var clickAction = attr.confirmedClick;
                 element.bind('click',function (event) {
                     if ( window.confirm(msg) ) {
                         scope.$eval(clickAction);
                     }
                 });
             }
         };
 }]);