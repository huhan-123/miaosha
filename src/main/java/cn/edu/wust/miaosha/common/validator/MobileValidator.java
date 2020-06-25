package cn.edu.wust.miaosha.common.validator;

import cn.edu.wust.miaosha.common.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: huhan
 * @Date 2020/6/23 3:14 下午
 * @Description 参数校验器，验证是否为手机号格式，配合@IsMobile注解使用
 * @Verion 1.0
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required;

    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!required && StringUtils.isEmpty(s)) {
            return true;
        }
        return ValidatorUtil.isMobile(s);
    }
}
