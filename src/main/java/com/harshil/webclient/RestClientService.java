package com.harshil.webclient;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
public class RestClientService {

    public ResponseEntity<BulkReviewMediaDTO> uploadAllMedia (String userId, String orderId, MultipartFile[] files) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("order-id", orderId);
        headers.add("user-id", userId);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        /*Arrays.stream(files).forEach(file -> {
            try {
                body.add("files", new ByteArrayResource(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/

        Arrays.stream(files).forEach(file -> {
            body.add("files", file.getResource());
        });

        System.out.println(body);

        String serverUrl = "http://localhost:8080/reviews/v1/upload-media";

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        System.out.println(requestEntity);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BulkReviewMediaDTO> response = restTemplate
                .postForEntity(serverUrl, requestEntity, BulkReviewMediaDTO.class);

        return response;
    }
}
