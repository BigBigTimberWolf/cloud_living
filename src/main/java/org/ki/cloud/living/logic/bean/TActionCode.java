package org.ki.cloud.living.logic.bean;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class TActionCode {
    @Id
    private Long id;

    private String productKey;

    private String deviceName;

    private String deviceSignatureCode;

    private String deviceSecret;
}
