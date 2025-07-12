package com.pro.release_tracker_api.services;

import com.pro.release_tracker_api.dtos.CombineResult;
import com.pro.release_tracker_api.dtos.ReleaseWebhookDto;
import com.pro.release_tracker_api.entities.*;
import com.pro.release_tracker_api.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReleaseService {
    private final EimRepository eimRepository;
    private final ReleaseRepository releaseRepository;
    private final ComponentRepository componentRepository;
    private final EnvironmentRepository environmentRepository;
    private final ServerRepository serverRepository;
    private final ServerDeploymentRepository serverDeploymentRepository;

    @Modifying
    public Mono<Void> createRelease(ReleaseWebhookDto input) {


        //    Check if EIM exists by name — create if not.
//    Check if Component exists under EIM — create if not.
        //
//    Check if Environment exists under Component — create if not.
//
//    Check if each Server exists under Environment — create if not.
//
//    Create Release entry (version + artifact).
//

// doto://    Optionally pull Jira ticket details using Jira API.
//    Optionally pull Jira ticket summary/status using Jira API.

        return Mono.just(input).
                flatMap(x -> eimRepository.findByEimNumber(x.getEIMId())
                        .switchIfEmpty(eimRepository.save(new Eim(0, x.getEIMId(), x.getEimName(), x.getEimName()))))

                .flatMap(eim -> {


                    CombineResult combineResult = new CombineResult();
                    combineResult.setEim(eim);
                    return Mono.just(combineResult);
                })
                .log()
                .flatMap(combineResult -> componentRepository.findByNameAndEimId(input.getComponentName(), combineResult.getEim().getId())
                        .switchIfEmpty(componentRepository.save(new Component(0, input.getComponentName(), input.getComponentName(), combineResult.getEim().getId()))
                        )
                        .flatMap(component -> {
                            ;
                            combineResult.setComponent(component);
                            return Mono.just(combineResult);
                        }))

                .log()
                .flatMap(combineResult -> environmentRepository.findByNameAndComponentId(input.getEnvironmentName(), combineResult.getComponent().getId())
                        .switchIfEmpty(environmentRepository.save(new Environment(0, input.getEnvironmentName(), combineResult.getComponent().getId())))
                        .flatMap(environment -> {
                            combineResult.setEnvironment(environment);
                            return Mono.just(combineResult);
                        }))
                .log()
                .publishOn(Schedulers.boundedElastic())
//                .flatMap(combineResult -> serverRepository.findByEnvironmentId(combineResult.getEnvironment().getId())
//                        .switchIfEmpty(  saveServers(input, combineResult, server);)
//
//                        .flatMap(server -> {
//
//
//
//
//
//                            return Mono.just(combineResult);
//
//
//                        }))
                .map
                        (combineResult -> {
                            // Create Release entry
                            var release = new Release();
                            release.setEnvironmentId(combineResult.getEnvironment().getId());
                            release.setArtifactVersion(input.getArtifactVersion());
                            release.setArtifactUrl(input.getArtifactUrl());
                            release.setComponentId(combineResult.getComponent().getId());
                            release.setBranchUrl(input.getBranchUrl());
                            release.setJiraTicketId(input.getJiraTicket());
                            release.setChgNumber(input.getChgNumber());
                            release.setVultureUrl(input.getVultureUrl());
                            release.setPsid(input.getPsid());
                            release.setReleaseNotes(input.getReleaseNotes());
                            release.setEimId(combineResult.getEim().getId());


                            return releaseRepository.save(release).subscribe();

                        }).then();

    }

    private void saveServers(ReleaseWebhookDto x, CombineResult combineResult2, List<Server> server) {
        Set<String> b = x.getServers().stream().map(ReleaseWebhookDto.ServerDto::getHostname).collect(Collectors.toSet());
        Set<String> a = server.stream().map(Server::getHostname).collect(Collectors.toSet());
        b.removeAll(a);

        if (!b.isEmpty()) {
            b.stream().map(addserver -> {
                ReleaseWebhookDto.ServerDto serverDto = x.getServers().stream().filter(ser -> ser.getHostname().equals(addserver)).findFirst().get();
                Server ser = new Server(0, serverDto.getHostname(), serverDto.getIp(), combineResult2.getEnvironment().getId(), serverDto.getHostname());
                return ser;
            }).forEach(ser -> {
                serverRepository.save(ser).subscribe();
            });
        }
    }


}
