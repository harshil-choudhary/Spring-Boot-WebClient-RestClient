package com.harshil.webclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class ReviewMediaDTO {
    private String id;
    private String entity;
    private String orderId;
    private String userId;
    private String url;
    private String type;
}
