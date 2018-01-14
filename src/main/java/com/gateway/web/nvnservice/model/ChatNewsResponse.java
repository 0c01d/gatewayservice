package com.gateway.web.nvnservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatNewsResponse {
    private Integer id;
    private Integer newsId;
    private String comment;
    private String name;

}