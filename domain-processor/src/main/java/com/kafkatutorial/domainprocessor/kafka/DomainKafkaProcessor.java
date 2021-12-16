package com.kafkatutorial.domainprocessor.kafka;

import com.kafkatutorial.domainprocessor.model.Domain;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
public class DomainKafkaProcessor {

    @Bean
    public Function<KStream<String, Domain>, KStream<String, Domain>> domainProcessor() {
        return kStream -> kStream.filter((key, domain) -> {

           if (domain.isDead()) log.info("Domain {} is inactive", domain.getDomain());
           else log.info("Domain {} is active", domain.getDomain());

           return !domain.isDead();
        });
    }
}
