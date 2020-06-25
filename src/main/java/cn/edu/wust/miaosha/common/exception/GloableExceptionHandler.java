package cn.edu.wust.miaosha.common.exception;

import cn.edu.wust.miaosha.common.CodeMsg;
import cn.edu.wust.miaosha.common.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: huhan
 * @Date 2020/6/23 3:48 下午
 * @Description
 * @Verion 1.0
 */
@RestControllerAdvice
public class GloableExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            return Result.error(exception.getCodeMsg());
        } else if (e instanceof BindException) {
            BindException exception = (BindException) e;
            List<ObjectError> allErrors = exception.getAllErrors();
            CodeMsg codeMsg = CodeMsg.BIND_ERROR.fillArgs(allErrors.get(0).getDefaultMessage());
            return Result.error(codeMsg);
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
