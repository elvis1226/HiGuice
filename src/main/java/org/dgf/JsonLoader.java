package org.dgf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class JsonLoader implements Quote, Ordering{
    private static final Logger logger = LoggerFactory.getLogger(JsonLoader.class);

    private final ObjectMapper objectMapper;

    private final String quoteFile;
    private final String orderFile;

    @Inject
    public JsonLoader(@Named("quoteFile") String quoteFile, @Named("orderFile") String orderFile) {
        this.quoteFile = quoteFile;
        this.orderFile = orderFile;
        this.objectMapper = new ObjectMapper();

        // SerializationFeature for changing how JSON is written

        // to enable standard indentation ("pretty-printing"):
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // to allow serialization of "empty" POJOs (no properties to serialize)
        // (without this setting, an exception is thrown in those cases)
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // to write java.util.Date, Calendar as number (timestamp):
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // DeserializationFeature for changing how JSON is read as POJOs:
        // to prevent exception when encountering unknown property:
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // to allow coercion of JSON empty String ("") to null Object value:
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    @Override
    public List<Product> getProducts() {
        try {
            return this.objectMapper.readValue(new File(quoteFile),
                    new TypeReference<List<Product>>(){});
        } catch (IOException e) {
            logger.warn("Error with getProucts - {}", e.getMessage());
            return ImmutableList.of();
        }
    }

    @Override
    public Order getOrder() {
        try {
            Set<Order.OrderEntry> entries = this.objectMapper.readValue(new File(orderFile),
                                              new TypeReference<Set<Order.OrderEntry>>(){});

            return new Order (entries, UUID.randomUUID());
        } catch (IOException e) {
            logger.warn("Error with getOrder - {}", e.getMessage());
            return null;
        }
    }
}
