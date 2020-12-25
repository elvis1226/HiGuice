package org.dgf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toMap;

public class Order {
    private final UUID id;
    private final LocalTime created = LocalTime.now();
    private final Set<OrderEntry> entries;

    public Order(Set<OrderEntry> entries, UUID id) {
        this.entries = entries;
        this.id = id;
    }

    public Map<KIND, Double> getGoods() {
        Map res = this.entries.stream().collect(toMap(e->e.getKind(), e->e.getQuantity()));
        return ImmutableMap.copyOf(res);
    }

    public UUID getId() {
        return id;
    }

    public static class OrderEntry {
        private final KIND kind;
        private final double quantity;

        @JsonCreator
        public OrderEntry(@JsonProperty("kind") KIND kind, @JsonProperty ("count") double quantity) {
            this.kind = kind;
            this.quantity = quantity;
        }

        public KIND getKind() {
            return kind;
        }

        public double getQuantity() {
            return quantity;
        }
    }

}
