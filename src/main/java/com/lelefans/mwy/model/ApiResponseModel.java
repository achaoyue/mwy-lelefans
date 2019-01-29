package com.lelefans.mwy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseModel<T> {
    public static final int SUCCEED = 0;
    /**
     * 响应码
     */
    private int code;
    /**
     * 数据
     */
    private T data;
    /**
     * 附带信息，如错误信息
     */
    private String msg;

    public static<T> ApiResponseModel<T> success(T data){
        ApiResponseModel<T> model = new ApiResponseModel<>();
        model.setCode(SUCCEED);
        model.setData(data);
        return model;
    }
}
