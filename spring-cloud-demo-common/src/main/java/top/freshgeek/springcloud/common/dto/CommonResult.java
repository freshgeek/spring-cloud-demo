package top.freshgeek.springcloud.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 9:17
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private Integer code;
    private String msg;
    private T data;


	public static CommonResult of(Integer code, String msg) {
        return new CommonResult(code, msg, null);
    }
    public static <T> CommonResult<T> of(Integer code, String msg,T data) {
        return new CommonResult<T>(code, msg, data);
    }
    public static <T> CommonResult<T> ofSuccess(
            T data) {
        return of(200,"成功",data);
    }
}
