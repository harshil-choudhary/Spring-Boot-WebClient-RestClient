package com.harshil.webclient;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

@Service
public class WebClientService {
    public BulkReviewMediaDTO uploadAllMedia (String userId, String orderId, MultipartFile[] files) {

        WebClient client = WebClient.create("http://localhost:8080");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("order-id", orderId);
        headers.add("user-id", userId);

        BulkReviewMediaDTO response =
                client.post()
                .uri("http://localhost:8080/reviews/v1/upload-media")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("order-id", orderId)
                .header("user-id", userId)
                .body(BodyInserters.fromMultipartData(fromFile(files)))
                .retrieve()
                .bodyToMono(BulkReviewMediaDTO.class)
                .block();

        return response;
    }

    public MultiValueMap<String, HttpEntity<?>> fromFile(MultipartFile[] files) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        Arrays.stream(files).forEach(file -> {
            try {
                builder.part("files", new ByteArrayResource(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return builder.build();
    }
}
