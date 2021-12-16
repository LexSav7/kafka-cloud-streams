package com.kafkatutorial.domainservice.kafka;

import com.kafkatutorial.domainservice.model.Domain;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class DomainKafkaConsumer {

    @Bean
    public Consumer<KStream<String, Domain>> domainProcessor() {
        return kStream -> kStream.foreach(
                (key, domain) -> log.info("Received active domain {}", domain.getDomain())
        );
    }
}
