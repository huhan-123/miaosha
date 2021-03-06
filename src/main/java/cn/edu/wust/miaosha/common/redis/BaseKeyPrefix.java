package cn.edu.wust.miaosha.common.redis;

/**
 * @Author: huhan
 * @Date 2020/6/22 4:05 下午
 * @Description
 * @Verion 1.0
 */
public abstract class BaseKeyPrefix implements KeyPrefix {
    private int expireSeconds;

    private String prefix;

    public BaseKeyPrefix(String prefix) {
        this(0, prefix);
    }

    public BaseKeyPrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int getExpireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
