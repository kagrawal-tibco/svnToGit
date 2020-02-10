/**
 * Created by priyanka on 01/06/16
 */
beConfigModule.directive('infiniteScroll', ['$window', function ($window){
    
	return {
        link:function (scope, element, attrs) {
            var e = element[0];

            element.bind('scroll', function () {
                if (e.scrollTop + e.offsetHeight >= e.scrollHeight) {
                    scope.$apply(attrs.infiniteScroll);
                }
            });
        }
    };
}]);