package com.gateway.web.cwservices.model;

import com.sun.javafx.css.CalculatedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {

    Integer id;
    UUID walletUUID;
    Integer value;

}
