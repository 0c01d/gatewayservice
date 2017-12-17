package com.gateway.web.nvnservice.model;

import lombok.Data;

@Data
public class ChatNewsResponse {
    private Integer id;
    private Integer newsId;
    private String comment;
    private String name;

}