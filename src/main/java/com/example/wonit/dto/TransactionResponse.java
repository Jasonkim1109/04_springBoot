package com.example.wonit.dto;

import com.example.wonit.model.Transaction;

import java.time.LocalDateTime;


/*
화면에 실제로 필요한 내용들을 구분해서 service와 controller 사이에서 주고받음

counterparty varchar(50)
tx_type varchar(10)
category varchar(20)
memo varchar(100)
amount bigint
tx_datetime datetime

 */

import com.example.wonit.model.Transaction;
import java.time.LocalDateTime;

public record TransactionResponse(
        String txType,
        long amount,
        String category,
        String memo,
        String counterparty,
        LocalDateTime txDatetime
) {
    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTxType(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getMemo(),
                transaction.getCounterparty(),
                transaction.getTxDatetime()
        );
    }
}