package me.huf.im.common.serializer;

import java.util.HashMap;
import java.util.Map;

public interface Serializer {

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    Serializer JSON_SERIALIZER = new JsonSerializer();

    Map<Byte, Serializer> ALGORITHM_SERIALIZER_MAP = new HashMap<Byte, Serializer>() {{
        put(JSON_SERIALIZER.getSerializerAlgorithm(), JSON_SERIALIZER);
    }};

    static Serializer fromAlgorithm(byte algorithm) {
        return ALGORITHM_SERIALIZER_MAP.get(algorithm);
    }
}
