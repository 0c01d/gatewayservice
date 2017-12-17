package com.gateway.web.nvnservice.modelaggr;

import com.gateway.web.cwservices.model.AccountResponse;
import com.gateway.web.nvnservice.model.ChatNewsResponse;
import com.gateway.web.nvnservice.model.NewsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NCNResponse {
    private Integer id;
    private Integer newsId;
    private String comment;
    private String name;

    public NCNResponse(ChatNewsResponse chatNewsResponse, AccountResponse accountResponse, NewsResponse newsResponse) {
        this.id = chatNewsResponse.getId();
        this.comment = chatNewsResponse.getComment();
        /*this.name = chatNewsResponse.getName();
        this.newsId = chatNewsResponse.getNewsId();*/
        this.name = accountResponse.getNickname();
        this.newsId = newsResponse.getId();
    }
}