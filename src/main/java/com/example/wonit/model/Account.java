package com.example.wonit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequriedArgsConstructor
@Entity
public class Account {

    @Id // Primary Key(고유값을 의미하는 컬럼)을 지정해줘야 이 컬럼을 기반으로 쿼리들이 만들어집니다.
    private Integer accountId;
    private String customerId;
    private String accountNo;
    private String accountType;
    private String status;
    private long balance;
}
