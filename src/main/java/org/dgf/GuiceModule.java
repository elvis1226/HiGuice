package org.dgf;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;


public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Ordering.class).to(JsonLoader.class);
        bind(Pricing.class).to(PricingImpl.class).asEagerSingleton();
        bind(Quote.class).to(JsonLoader.class);
        bind(String.class).annotatedWith(Names.named("quoteFile")).toInstance("src/test/resources/products.json");
        bind(String.class).annotatedWith(Names.named("orderFile")).toInstance("src/test/resources/order_details.json");
    }
}