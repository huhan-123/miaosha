package cn.edu.wust.miaosha.common.redis;

/**
 * @Author: huhan
 * @Date 2020/6/23 4:54 下午
 * @Description
 * @Verion 1.0
 */
public class MiaoShaUserKeyPrefix extends BaseKeyPrefix {
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public MiaoShaUserKeyPrefix(String prefix) {
        super(prefix);
    }

    public MiaoShaUserKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static final MiaoShaUserKeyPrefix getByToken = new MiaoShaUserKeyPrefix(TOKEN_EXPIRE,"tk");
    public static final MiaoShaUserKeyPrefix getById = new MiaoShaUserKeyPrefix("id");
}
