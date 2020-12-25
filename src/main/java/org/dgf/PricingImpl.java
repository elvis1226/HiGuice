package org.dgf;

import javax.inject.Inject;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class PricingImpl implements Pricing {
    private final Map<KIND, Product> productMap;

    @Inject
    public PricingImpl(Quote quote) {
        this.productMap = quote.getProducts().stream().collect(toMap(x-> x.getKind(), p -> p));
    }

    private double adjustWeight(UNIT actual) {
        if (actual.equals(UNIT.G)) {
            return 0.5;
        }
        else if (actual.equals(UNIT.OUNCE)) {
            return 0.02;
        }

        return 1.0;
    }

    @Override
    public double price(Map<KIND, Double> goods) {
        double res = goods.entrySet().stream().map(e-> e.getValue() //count
                                      * productMap.get(e.getKey()).getPrice() //unit price
                                      * adjustWeight(productMap.get(e.getKey()).getUnit()))
                                .reduce(0.0, Double::sum);
        return res;
    }

}
