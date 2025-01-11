package ua.nure.tsekhmister.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ua.nure.tsekhmister.controllers.client.CarClient;
import ua.nure.tsekhmister.controllers.client.DealClient;
import ua.nure.tsekhmister.controllers.client.UserClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@EnableDiscoveryClient
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CarClient carClient(DiscoveryClient discoveryClient) throws ExecutionException, InterruptedException, TimeoutException {
        return getClient(discoveryClient, "car-service", CarClient.class);
    }

    @Bean
    public UserClient userClient(DiscoveryClient discoveryClient) throws ExecutionException, InterruptedException, TimeoutException {
        return getClient(discoveryClient, "user-service", UserClient.class);
    }

    @Bean
    public DealClient dealClient(DiscoveryClient discoveryClient) throws ExecutionException, InterruptedException, TimeoutException {
        return getClient(discoveryClient, "deal-service", DealClient.class);
    }

    private <T> T getClient(DiscoveryClient discoveryClient, String serviceName, Class<T> clientClass) throws ExecutionException, InterruptedException, TimeoutException {
        RestClient restClient = CompletableFuture.supplyAsync(() -> {
            var instances = discoveryClient.getInstances(serviceName);
            while (instances.isEmpty()) {
                try {
                    System.out.println("Waiting for " + serviceName);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                instances = discoveryClient.getInstances(serviceName);
            }
            System.out.println(instances.getFirst());
            return RestClient
                    .builder()
                    .baseUrl(instances.getFirst().getUri())
                    .build();
        }).get(60, TimeUnit.SECONDS);

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(clientClass);
    }

}