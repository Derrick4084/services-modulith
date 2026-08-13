package com.derocode.EcommApp.cache;


import org.jspecify.annotations.Nullable;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class SharedRedisKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, @Nullable Object... params) {
        return method.getDefaultValue();
    }
}
