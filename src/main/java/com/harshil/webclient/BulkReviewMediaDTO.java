package com.harshil.webclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class BulkReviewMediaDTO {
    private String requestId;
    private List<ReviewMediaDTO> reviewMediaDTOList;
}

