package com.octo.android.robospice.persistence.retrofit2.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.OutputStream;

public class RetrofitJacksonResponseConverter implements RetrofitResponseConverter {

    private final ObjectMapper objectMapper;

    public RetrofitJacksonResponseConverter() {
        this(new ObjectMapper());
    }

    public RetrofitJacksonResponseConverter(ObjectMapper mapper) {
        if (mapper != null) {
            this.objectMapper = mapper;
        } else {
            this.objectMapper = new ObjectMapper();
        }
    }

    @Override
    public void saveObject(Object object, Class<?> clzz, OutputStream out) throws Exception {
        final JsonGenerator generator = objectMapper.getFactory()
                .createGenerator(out);
        generator.writeObject(object);
    }

    @Override
    public Object restoreObject(InputStream objectStream, Class<?> clzz) throws Exception {
        final JsonParser parser = this.objectMapper.getFactory()
                .createParser(objectStream);
        return this.objectMapper.readValue(parser, clzz);
    }

}
