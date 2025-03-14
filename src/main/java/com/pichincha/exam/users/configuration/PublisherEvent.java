package com.pichincha.exam.users.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class PublisherEvent {
    private final StreamBridge streamBridge;

    public Mono<Boolean> sendMessage(Object object) {
        return Mono.just(streamBridge.send("topic_exam_account_movements", object))
                .doOnSuccess(aBoolean -> log.info("Success message send to create the client {}", object))
                .doOnError(throwable -> log.error("Error for creation {}", throwable.getMessage()));
    }

}
