package com.pro.release_tracker_api.controllers;

import com.pro.release_tracker_api.dtos.ReleaseWebhookDto;
import com.pro.release_tracker_api.services.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ReleaseWebHookController {
    private final ReleaseService releaseService;


//
    @PostMapping("/api/release-webhook")
    public Mono<Void> pushRelease( @RequestBody ReleaseWebhookDto releaseWebhookDto) {

        return releaseService.createRelease(releaseWebhookDto);

    }
}
