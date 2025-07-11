package com.pro.release_tracker_api.controllers;

import com.pro.release_tracker_api.dtos.ReleaseWebhookDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ReleaseWebHookController {


//
    @PostMapping("/api/release-webhook")
    public Mono<Void> pushRelease( @RequestBody Mono<ReleaseWebhookDto> mono) {

        return  Mono.empty();

    }
}
