package com.cwk.gps.service;

import java.util.concurrent.CompletableFuture;

public interface RouteInfoService {
    CompletableFuture<String> updateRouteInfo(Long routeId, Long userId);
}
