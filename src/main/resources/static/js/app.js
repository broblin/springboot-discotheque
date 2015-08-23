var sbdManagerModule = angular.module('categoryManagerApp', ['ngAnimate']);

sbdManagerModule.controller('categoryManagerController', function ($scope,$http) {
	
	var urlBase="";
	$scope.range=[];
	$scope.totalElements=0;
	$scope.pageNumber=1;
	$scope.toggle=true;
	$scope.selection = [];
	$scope.message="";
	$scope.update=false;
	$scope.pageSize=3;
	$http.defaults.headers.post["Content-Type"] = "application/json";

    function findAllCategories() {
        //get all tasks and display initially
        $http.get(urlBase + '/categories?pageNumber='+$scope.pageNumber+'&pageSize='+$scope.pageSize).
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

    $scope.displayUpdateForm = function displayUpdateForm(categoryId,categoryName){
        $scope.update = true;
        $scope.categoryId=categoryId;
        $scope.categoryName=categoryName;
        $scope.toggle = !$scope.toggle;
    }

    $scope.displayCreateForm = function displayCreateForm(){
        $scope.update = false;
        $scope.toggle = !$scope.toggle;
    }

	//add a new category
	$scope.addCategory = function addCategory() {
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

	//update a new category
    $scope.updateCategory = function updateCategory() {
        if($scope.categoryName=="" || $scope.categoryId=="" ){
            alert("Insufficient Data! Please provide values for task name or id");
        }
        else{
         $http.put(urlBase + '/category', {
             name: $scope.categoryName,
             id: $scope.categoryId
         }).
          success(function(data, status, headers) {
             $scope.message="Category updated";
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

	 function refreshIfAllDeleted(index){
	    //tous les éléments du tableau sont passé
	    if(index+1 == $scope.selection.length){
            //if it remains no element on the current page, we pass to the previous page
            if($scope.pageNumber > 1 && $scope.pageSize*($scope.pageNumber-1)==$scope.totalElements-$scope.selection.length){
                $scope.pageNumber--;
            }
            $scope.message="Successfully deleted";
            $scope.selection=[];
            findAllCategories();
	    }
	 }

	  $scope.deleteCategories = function deleteCategories() {
          $scope.selection.forEach(function(categoryId,index) {
              if (categoryId != undefined) {
                $http.delete(urlBase + '/category/'+categoryId).success(function (data) {
                    refreshIfAllDeleted(index);
                });
              }
          });
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