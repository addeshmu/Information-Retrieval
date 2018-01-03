
$('md-autocomplete').keypress(function(){
  $('.search').addClass('fixed');
  $('.banner').css("text-align","left");
  $('.logo').css("width","25%");
  $('.logo').css("height","100%");
});






/*$('input').blur(function(){
  $('.search').removeClass('fixed');
});*/



var myApp = angular.module('myApp',['angularUtils.directives.dirPagination','ngToast','angular-loading-bar','ngAnimate','ngSanitize','ngAria','ngMaterial']).config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.parentSelector = '#loading-bar-container';
    cfpLoadingBarProvider.spinnerTemplate = '<div><span class="fa fa-refresh fa-spin fa-spinner fa-4x"></div>';
  }]);


myApp.controller('controller',['$scope','$http','ngToast',function($scope,$http,ngToast,ngSanitize,ngAnimate,ngAria,ngMaterial){
	
	$scope.httpPost= function(mode,q){
		
		console.log(mode);
		$scope.user.mode=mode;
		$scope.user.searchtext=q;
		$scope.results=[];

		$http.post('/form',$scope.user).then(function(response)
		{
			console.log($scope.user.mode);
			console.log(response);
			if($scope.user.mode=="didyouMean"){
				var s="";
				$scope.ShowingResults ="Showing results for "+response.data[0].spellcheck;
				$scope.SearchInstead ="";
				$scope.DidYouMean ="";
				$scope.TimeTaken="Response time: "+response.data[0].qtime+" ms approx.";			
			}
			else if ($scope.user.mode=="mainSubmit"){
				if (response.data[0].qtype=="orig"){
					$scope.ShowingResults ="Showing results for "+response.data[0].spellcheck;
					$scope.TimeTaken="Response time: "+response.data[0].qtime+" ms approx.";
					$scope.SearchInstead ="";
					$scope.DidYouMean ="";
				}else{
					$scope.ShowingResults ="Showing results for "+response.data[0].spellcheck;
					$scope.SearchInstead ="Search Instead for " +$scope.user.searchtext;
					$scope.DidYouMean ="";
					$scope.TimeTaken="Response time: "+response.data[0].qtime+" ms approx.";
				}
			}else {
				$scope.ShowingResults="";
				$scope.SearchInstead ="";
				$scope.DidYouMean ="did you mean: "+response.data[0].spellcheck;
				$scope.TimeTaken="Response time: "+response.data[0].qtime+" ms approx.";				
			}	
			if (response.data[0].NumberofResultsFound!="0"){
				console.log(response.data[1]);
				for(i = 1;i<=10;i++){
					$scope.results.push(response.data[i]);
					if (i<=10){
						s=s+"\n"+i.toString()+")"+" "+response.data[i].url;
					}
				}

			}
		});
	};


	$scope.nodeCall=function(q){
		console.log("hi");
		$scope.httpPost("mainSubmit",q);
	};
	$scope.didyouMean=function(q){
		$scope.httpPost("didyouMean",q);	
	};
	$scope.loadInstead=function(q){
		$scope.httpPost("loadInstead",q);
	};
	$scope.pagerankCall=function(e){
    	
		$http.post('/updatePageRank',{check:$scope.check}).then(function(response){
			//$scope.status = "Page Rank Algorithm "+response.value;
			ngToast.dismiss();
			ngToast.create("Page Rank Algorithm: "+response.data.value);
		}
		);

	};
	$scope.query = function(searchText) {
    $scope.user = new Object;
    $scope.user.searchText = searchText;
    return $http
      .post('/suggest',$scope.user )
      .then(function(data) {
        // Map the response object to the data object
        for (var i=0;i<data.data.length;i++){
        	data.data[i]=data.data[i].replace("–","").replace(",","").replace("-","").replace("-","").replace(".","");
        	
        }
        return data.data;
      });
  };




	
}]);

