package com.gateway.web.nvnservice.model;

import com.gateway.web.nvnservice.modelaggr.NCNRequest;
import lombok.Data;

@Data
public class ChatNewsRequest {

    private Integer newsId;
    private String comment;
    private String name;

    public ChatNewsRequest(NCNRequest ncnRequest) {
        this.newsId = ncnRequest.getNewsId();
        this.comment = ncnRequest.getComment();
        this.name = ncnRequest.getName();
    }
}
