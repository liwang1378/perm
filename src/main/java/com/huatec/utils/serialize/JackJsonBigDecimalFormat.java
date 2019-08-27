package com.huatec.utils.serialize;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 货币统一格式为2位小数
 * @author liuhongyan2
 *
 */
@Component
public class JackJsonBigDecimalFormat extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException {
    	value = value==null ? BigDecimal.valueOf(0):value;
    	value = value.setScale(2, RoundingMode.HALF_UP);
        jgen.writeNumber(value);
    }


}
