package com.gateway.web.nvnservice.modelaggr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class NCNRequest {
    private String comment;
    private Integer newsId;
    private String name;

}