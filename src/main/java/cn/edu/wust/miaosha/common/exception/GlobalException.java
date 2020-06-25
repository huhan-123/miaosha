package cn.edu.wust.miaosha.common.exception;

import cn.edu.wust.miaosha.common.CodeMsg;

/**
 * @Author: huhan
 * @Date 2020/6/23 4:22 下午
 * @Description
 * @Verion 1.0
 */
public class GlobalException extends RuntimeException {
    public static final long serialVersionUID = 1L;
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
