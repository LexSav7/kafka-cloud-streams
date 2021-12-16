package com.kafkatutorial.domaincrawler.service;

import com.kafkatutorial.domaincrawler.model.Domain;
import com.kafkatutorial.domaincrawler.model.DomainList;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class DomainCrawlerService {

    private final static String TOPIC = "web-domains";

    @Value("${domain.api.url}")
    private String domainApiUrl;

    private final KafkaTemplate<String, Domain> kafkaTemplate;

    public void crawl(String domainName) {

//        WebClient webClient = WebClient.builder()
//                .clientConnector(new ReactorClientHttpConnector(
//                                HttpClient.create()
//                                        .resolver(DefaultAddressResolverGroup.INSTANCE)
//                        )
//                ).build();
//        Mono<DomainList> domainListMono = WebClient.create()
//            .get()
//            .uri(builder -> builder.path(domainApiUrl)
//                    .queryParam("domain", domainName)
//                    .queryParam("zone", "com")
//                    .build())
//            .accept(MediaType.APPLICATION_JSON)
//            .retrieve()
//            .bodyToMono(DomainList.class);

        Mono<DomainList> domainListMono = WebClient.create()
            .get()
            .uri("https://api.domainsdb.info/v1/domains/search?domain=" + domainName + "&zone=com")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(DomainList.class);

        domainListMono.subscribe(domainList -> {
            domainList.getDomains().forEach(domain -> {
                kafkaTemplate.send(TOPIC, domain);
                log.info("Send domain {} to {} Kafka topic", domain.getDomain(), TOPIC);
            });
        });

    };
}
