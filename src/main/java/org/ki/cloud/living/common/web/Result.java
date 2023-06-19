package org.ki.cloud.living.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public  class Result<T> {
    private String message;

    private Integer code;

    private T data;

    public  Result<T> ok(T data){
        Result<T> tResult = new Result<>();
        tResult.setData(data);
        tResult.setCode(200);
        return tResult;
    };
}
