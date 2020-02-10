import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, DetachedRouteHandle, RouteReuseStrategy } from '@angular/router';

/**
 * Interface for object which can store both:
 * An ActivatedRouteSnapshot, which is useful for determining whether or not you should attach a route (see this.shouldAttach)
 * A DetachedRouteHandle, which is offered up by this.retrieve, in the case that you do want to attach the stored route
 */
interface RouteStorageObject {
  snapshot: ActivatedRouteSnapshot;
  handle: DetachedRouteHandle;
}

@Injectable()
export class AppRouteReuseStrategy implements RouteReuseStrategy {
  /**
   * Object which will store RouteStorageObjects indexed by keys
   * The keys will all be a path (as in route.routeConfig.path)
   * This allows us to see if we've got a route stored for the requested path
   */
  storedRoutes: { [key: string]: RouteStorageObject } = {};

  /**
   * Decides when the route should be stored
   */
  shouldDetach(route: ActivatedRouteSnapshot): boolean {
    // we only reuse the workspace route as it's expensive to construct
    return route.routeConfig.path === 'workspace';
  }

  /**
   * Constructs object of type `RouteStorageObject` to store, and then stores it for later attachment
   */
  store(route: ActivatedRouteSnapshot, handle: DetachedRouteHandle): void {
    const storedRoute: RouteStorageObject = {
      snapshot: route,
      handle: handle
    };
    this.storedRoutes[route.routeConfig.path] = storedRoute;
  }

  /**
   * Determines whether or not there is a stored route and,
   * if there is, whether or not it should be rendered in place of requested route
   */
  shouldAttach(route: ActivatedRouteSnapshot): boolean {
    // this will be true if the route has been stored before
    const canAttach: boolean = !!route.routeConfig && !!this.storedRoutes[route.routeConfig.path];
    return canAttach;
  }

  /**
   * Finds the locally stored instance of the requested route, if it exists, and returns it
   */
  retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle {
    // return null if the path does not have a routerConfig OR if there is no stored route for that routerConfig
    if (!route.routeConfig || !this.storedRoutes[route.routeConfig.path]) {
      return null;
    }

    /** returns handle when the route.routeConfig.path is already stored */
    return this.storedRoutes[route.routeConfig.path].handle;
  }

  /**
   * Determines whether or not the current route should be reused
   */
  shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
    return future.routeConfig === curr.routeConfig;
  }
}
