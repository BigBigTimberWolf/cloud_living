package org.ki.cloud.living.logic.model;

import lombok.Data;

@Data
public class RegisterRequestModel {
    private String deviceSignatureCode;

    private String project;
}
