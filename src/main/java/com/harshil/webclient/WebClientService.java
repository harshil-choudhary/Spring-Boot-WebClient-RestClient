package com.harshil.webclient;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
public class WebClientService {
    public BulkReviewMediaDTO uploadAllMedia (String userId, String orderId, MultipartFile[] files) {

        WebClient client = WebClient.create("http://localhost:8080");

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();


        Arrays.stream(files).forEach(file -> {

                body.add("files", file.getResource() );
                System.out.println(file.getResource());

        });


        BulkReviewMediaDTO response = new BulkReviewMediaDTO();

        try {
            response =
                    client.post()
                            .uri("http://localhost:8080/reviews/v1/upload-media")
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .header("order-id", orderId)
                            .header("user-id", userId)
                            .body(BodyInserters.fromMultipartData(body))
                            .retrieve()
                            .bodyToMono(BulkReviewMediaDTO.class)
                            .block();
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }

        return response;
    }

    public MultiValueMap<String, HttpEntity<?>> fromFile(MultipartFile[] files) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        Arrays.stream(files).forEach(file -> {
            builder.part("files", file.getResource());
        });
        return builder.build();
    }
}
