package model;

import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mihkel on 5.04.2018.
 */
public class Transaction implements Comparable<Transaction>, Ordered {

    private @NonNull String name;
    private @NonNull int order;
    private Map<Member,Double> payers = new HashMap<>();
    private Map<Member,Double> spenders  = new HashMap<>();

    public Transaction(String name, int order) {
        this.name = name;
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(Transaction o) {
        return (this.getOrder() < o.getOrder() ? -1 :
                (this.getOrder() == o.getOrder() ? 0 : 1));
    }

}
