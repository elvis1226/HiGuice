package org.dgf;

import java.util.Map;

public interface Pricing {
    double price(Map<KIND, Double> goods);
}
