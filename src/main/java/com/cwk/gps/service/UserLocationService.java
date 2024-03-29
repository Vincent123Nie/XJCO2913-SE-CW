package com.cwk.gps.service;

import java.util.concurrent.CompletableFuture;

public interface UserLocationService {
    CompletableFuture<String> uploadLocation(Long userId, Double longitude, Double latitude);
}
