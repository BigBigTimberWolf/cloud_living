package org.ki.cloud.living.logic.model;

import lombok.Data;
import org.ki.cloud.living.logic.bean.TActionCode;

@Data
public class RegisterResponseModel {
    private String deviceSignatureCode;

    private String productKey;

    private String deviceName;
    private String deviceSecret;



    public RegisterResponseModel() {
    }

    public RegisterResponseModel(TActionCode code) {
        this.deviceSignatureCode = code.getDeviceSignatureCode();
        this.productKey = code.getProductKey();
        this.deviceName = code.getDeviceName();
        this.deviceSecret = code.getDeviceSecret();
    }
}
