package hu.helper.bang.center.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.JsonElement;

import java.io.IOException;


/**
 * jackson的jsonElement序列化器
 * @author lin
 * @date 2023/03/18
 */
public class JsonElementSerializer extends JsonSerializer<JsonElement> {
        @Override
        public void serialize(JsonElement value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeRawValue(value.toString());
        }
    }

