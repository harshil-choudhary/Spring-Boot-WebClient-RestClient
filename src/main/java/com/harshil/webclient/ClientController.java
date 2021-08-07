package com.harshil.webclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/web-client")
public class ClientController {

    @Autowired
    RestClientService restClientService;

    @Autowired
    WebClientService webClientService;

    @PostMapping(value="/post")
    public ResponseEntity<BulkReviewMediaDTO> uploadFiles(
            @RequestHeader("user-id") String userId,
            @RequestParam("order-id") String orderId,
            @RequestParam("files") MultipartFile[] files) {
        //return restClientService.uploadAllMedia(userId, orderId, files);
        return new ResponseEntity<>(
                webClientService.uploadAllMedia(userId, orderId, files),
                HttpStatus.OK
        );
    }
}
