package org.baeldung.spring.reactiveweb.demo.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.baeldung.spring.reactiveweb.demo.model.Foo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Random;

@RestController
public class FooController {

    private Foo createRandomFoo(Long second) {
        Foo foo = new Foo();
        foo.setId(new Random().nextLong());
        foo.setName(RandomStringUtils.randomAlphanumeric(25));
        return foo;
    }

    @GetMapping(value = "/foo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Foo> getFooFlux() {
        return Flux.interval(Duration.ofSeconds(1))
            .subscribeOn(Schedulers.single())
            .map(this::createRandomFoo);
    }
}
