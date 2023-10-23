package com.julian.springbacken.response;

import lombok.Data;

@Data
public class ApiResponse {
    private String status;
    private String msg;
    public ApiResponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
