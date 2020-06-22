package cn.edu.wust.miaosha.common;

/**
 * @Author: huhan
 * @Date 2020/6/21 8:31 下午
 * @Description
 * @Verion 1.0
 */
public class CodeMsg {
    private int code;

    private String msg;

    //CodeMsg不能手动创建，只能事先创建出来放在类中（易于扩展）
    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //通用异常
    public static final CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");

    //登录模块 5002XX

    //商品模块 5003XX

    //订单模块 5004XX

    //秒杀模块 5005XX

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
