package cn.edu.wust.miaosha.common;

/**
 * @Author: huhan
 * @Date 2020/6/21 8:27 下午
 * @Description
 * @Verion 1.0
 */
public class Result<T> {
    private int code;

    private String msg;

    private T data;

    //构造方法设置为private，防止手动创建Result对象，只能通过静态方法获取Result对象
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //这里必须有属性的get方法，否则序列化时会报错：No Converter Found for Return Value of Type
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "success", data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<>(codeMsg.getCode(), codeMsg.getMsg(), null);
    }
}
