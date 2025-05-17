package com.example.demo.vo;

public class BasicVo<T> {
    private int code = 200;

    private String msg;

    private T data;

    public BasicVo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BasicVo<T> success(T data) {
        return new BasicVo<>(200, "success", data);
    }

    public static <T> BasicVo<T> fail(int code, String msg) {
        return new BasicVo<>(code, msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
