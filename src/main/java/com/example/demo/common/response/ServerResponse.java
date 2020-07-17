package com.example.demo.common.response;

public class ServerResponse {

    private int code;

    private String  msg;

    private Object  data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private ServerResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse() {
    }

    public static ServerResponse success(){
        ServerResponse serverResponse=new ServerResponse(200,"ok",null);
        return serverResponse;
    }

    public static ServerResponse success(Object data){
        ServerResponse serverResponse=new ServerResponse(200,"ok",data);
        return serverResponse;
    }

    public static ServerResponse error(){
        ServerResponse serverResponse=new ServerResponse(-1,"error",null);
        return serverResponse;
    }

    public static ServerResponse error(int code,String msg){
        ServerResponse serverResponse=new ServerResponse(code,msg,null);
        return serverResponse;
    }

    public static ServerResponse error(ResponseEnum responseEnum){
        ServerResponse serverResponse=new ServerResponse(responseEnum.getCode(),responseEnum.getMsg(),null);
        return serverResponse;
    }
}
