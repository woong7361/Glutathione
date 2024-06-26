package com.example.userservice.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;


/**
 * mapper factory class
 */
public class ModelMapperFactory {

    /**
     * 기본적으로 private field에도 접근할 수 있는 mapper가 반환된다.
     * @return model mapper
     */
    public static ModelMapper create() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return mapper;
    }
}
