package cn.edu.wust.miaosha.vo;

import cn.edu.wust.miaosha.common.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author: huhan
 * @Date 2020/6/23 10:33 上午
 * @Description
 * @Verion 1.0
 */
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
