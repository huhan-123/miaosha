package cn.edu.wust.miaosha.common.redis;

/**
 * @Author: huhan
 * @Date 2020/6/22 4:17 下午
 * @Description
 * @Verion 1.0
 */
public class UserKeyKeyPrefix extends BaseKeyPrefix {
    private UserKeyKeyPrefix(String prefix){
        super(prefix);
    }

    public static final UserKeyKeyPrefix getById = new UserKeyKeyPrefix("id");
    public static final UserKeyKeyPrefix getByName = new UserKeyKeyPrefix("name");

}
