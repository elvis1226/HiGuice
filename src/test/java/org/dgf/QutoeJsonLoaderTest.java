package org.dgf;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class QutoeJsonLoaderTest {
    private static final Logger logger = LoggerFactory.getLogger(JsonLoader.class);

    @Test
    public void testLoadProductFromJson() {
        JsonLoader qjl = new JsonLoader("src/test/resources/products.json", "src/test/resources/order_details.json");
        List<Product> productList = qjl.getProducts();
        assertEquals(3, productList.size());
        productList.forEach(p -> logger.info("{}, {}, {}", p.getKind(), p.getPrice(), p.getUnit()));

        Order order = qjl.getOrder();
        assertEquals(3, order.getGoods().size());
    }
}
