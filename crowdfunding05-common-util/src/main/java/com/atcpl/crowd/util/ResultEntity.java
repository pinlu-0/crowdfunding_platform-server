package com.atcpl.crowd.util;

/**
 *
 * 统一整个项目中Ajax请求的返回结果
 *
 * @author cpl
 * @date 2022/12/30
 * @apiNote
 */
public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    /**
     * 用来封装当前请求是失败还是成功
     */
    private String result;
    /**
     * 请求处理失败时返回的错误消息
     */
    private String errorMessage;
    /**
     * 要返回的数据
     */
    private T data;

    public ResultEntity() {

    }

    public ResultEntity(String result, String errorMessage, T data) {
        this.result = result;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * 成功处理请求且不需要返回数据
     *
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithoutData() {
        return new ResultEntity<Type>(SUCCESS, null, null);
    }

    /**
     * 成功处理请求且需要返回数据
     *
     * @param data
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data) {
        return new ResultEntity<Type>(SUCCESS, null, data);
    }


    /**
     * 请求处理失败
     * @param errorMessage
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(String errorMessage) {
        return new ResultEntity<Type>(FAILED,errorMessage,null);
    }

}
