package model;

import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;


/**
 * Created by mihkel on 5.04.2018.
 */

public class Member implements Comparable<Member>, Ordered {
    private @NonNull String name;
    private String nickname;
    private String email;
    private String bankaccount;
    private @NonNull int order;

    public Member(String name, int order) {
        this.name = name;
        this.order = order;
    }


    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(Member o) {
        return (this.getOrder() < o.getOrder() ? -1 :
                (this.getOrder() == o.getOrder() ? 0 : 1));
    }
}
