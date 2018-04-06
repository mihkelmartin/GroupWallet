package model;

import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.lang.NonNull;

import java.util.UUID;


/**
 * Created by mihkel on 5.04.2018.
 */

public class Member implements Comparable<Member>, Ordered {

    @Id
    private final @NonNull String id;
    private @NonNull String name;
    private String nickName;
    @Indexed
    private String eMail;
    private String bankAccount;
    private @NonNull int order;

    public Member(String name, String nickName, String eMail, String bankAccount, int order) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.nickName = nickName;
        this.eMail = eMail;
        this.bankAccount = bankAccount;
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
