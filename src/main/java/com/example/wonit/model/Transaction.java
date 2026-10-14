package com.example.wonit.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// 보안이 중요한 데이터를 실어나를 때는 @Setter를 사용하지 않습니다.
@Data
@Entity
// 예약어로 거부당하는 경우 ` `(백틱)으로 감싸서 문자열임을 분명히 명시
@Table(name = "`transaction`") // DB의 테이블과 우리가 자바에서 사용하는 클래스명이 다를 경우 호환해서 사용하기 위해 명기
public class Transaction {

    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tx_id") // 컬럼명이 테이블과 우리가 사용하는 자바의 필드명이 다를 경우 구체적으로 명기
    private Long txId;

    // 제약조건 NOT NULL
    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "transfer_id")
    private Long transferId;

    // length  최대 글자수를 제한
    @Column(name = "tx_type", nullable = false, length = 10)
    private String txType;

    @Column(nullable = false)
    private long amount;

    @Column(name = "balance_after", nullable = false)
    private long balanceAfter;

    @Column(length = 20)
    private String category;

    @Column(length = 100)
    private String memo;

    @Column(length = 50)
    private String counterparty;

    // 시간을 의미하는 자바의 자료형: LocalDateTime
    @Column(name = "tx_datetime", nullable = false)
    private LocalDateTime txDatetime;

    protected Transaction() {}
}