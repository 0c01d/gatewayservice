package com.gateway.web.nvnservice.model;

import lombok.Data;

@Data
public class NewsRequest {
    private Integer id;

    public NewsRequest(NewsResponse newsResponse) {
        this.id = newsResponse.getId();
    }
}