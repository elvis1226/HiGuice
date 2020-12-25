package org.dgf;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.inject.Inject;
import java.util.Objects;

public class Main {
    private Ordering order;
    private Pricing pricer;

    @Inject
    public Main(Ordering order, Pricing pricer){
        this.order = order;
        this.pricer = pricer;
    }

    public boolean pricingOrder() {
        Objects.requireNonNull(this.order, "null order");
        Objects.requireNonNull(this.pricer, "null pricer");
        Order od = this.order.getOrder();

        if (od.getGoods().size() <= 0) {
            return false;
        }

        double totalPrice = this.pricer.price(od.getGoods());
        System.out.println("total price : " + totalPrice);
        return true;
    }

    public static void main(String[] argv) {
        Injector injector = Guice.createInjector(new GuiceModule());
        Main main = injector.getInstance(Main.class);
        main.pricingOrder();
    }
}
