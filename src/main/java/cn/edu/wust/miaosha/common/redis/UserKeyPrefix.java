package cn.edu.wust.miaosha.common.redis;

/**
 * @Author: huhan
 * @Date 2020/6/22 4:17 下午
 * @Description
 * @Verion 1.0
 */
public class UserKeyPrefix extends BasePrefix {
    private UserKeyPrefix(String prefix){
        super(prefix);
    }

    public static final UserKeyPrefix getById = new UserKeyPrefix("id");
    public static final UserKeyPrefix getByName = new UserKeyPrefix("name");

}
