package com.example.wonit.dto;

/*
화면에 실제로 필요한 내용들을 구분해서 service와 controller 사이에서 주고받음

counterparty varchar(50)
tx_type varchar(10)
category varchar(20)
memo varchar(100)
amount bigint
tx_datetime datetime

 */
public record TransactionResponse(String counterparty, String txType, String category, String memo, long amount, String datetime) {
}
