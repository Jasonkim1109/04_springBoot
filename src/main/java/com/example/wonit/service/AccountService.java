/**
 * JavaDoc - 함수나 클래스에 대한 설명을 적어놓으면
 */

// 한 줄 주석

/*
여러 줄 주석
 */
package com.example.wonit.service;

import com.example.wonit.dto.AccountCreateRequest;
import com.example.wonit.dto.AccountResponse;
import com.example.wonit.exception.*;
import com.example.wonit.model.Account;
import com.example.wonit.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j // 로그를 이 클래스에서 사용하겠다라는 어노테이션
// Controller와만 통신하며 요청에 대한 실제 로직을 담당
@Service
@RequiredArgsConstructor
public class AccountService {


    @Autowired // 서로서로 연결된 bean임을 알려줍니다. 생략하면 Springboot가 넣어줌
    private final AccountRepository accountRepository;

    // 모든 계좌를 리턴
    public List<Account> findAll() {

        // 코드1
        List<Account> accounts =  accountRepository.findAll();

        // 로깅을 위해서 객체를 안에서 만들어 놓고
        log.info("전체 계좌 조회 건수 출력={}", accounts.size());
        log.info("전체 계좌 조회 건수 개별 출력={}", accounts.toString());
        // 리턴함

        // 코드3: 로그는 return이 일어나기 전에 보통 찍습니다.
        return accounts;
    }

    // 하나의 계좌 정보를 repo에서 받아서 리턴하거나 NPE을 막아주는 함수
    public Optional<Account> findOne(String accountNo) {
        return accountRepository.findByAccountNo(accountNo);
    }


    // 하나의 계좌 정보를 리턴 -> 받아서 Account 자료형으로 받어나 예외를 일으키는 함수

    /**
     * Controller에 특정 계좌번호의 검색 결과 단건을 반환한다.
     *
     * @param accountNo
     * @return Account
     */
    public Account getAccount(String accountNo) {
        log.info("계좌 조회={}", accountNo);
        return findOne(accountNo).orElseThrow(() -> new AccountNotFoundException(accountNo + "계좌를 찾을 수 없습니다"));
    }

    // 실제동작을 아래 함수에 작성해 줍니다. db에서 모든 계좌를 조회해서
    public List<String> normalAccountNos() {
        return accountRepository.findAll()
                .stream().filter(account -> account.getStatus().equals("정상"))
                .map(Account::getAccountNo).toList();  // .mapToLong(Account::getBalance).sum();
    }

    public long savingBalanceSum() {
        return accountRepository.findAll()
                .stream().filter(account -> account.getAccountType().equals("적금"))
                .mapToLong(Account::getBalance).sum();
    }

    public List<Account> getAccountBetweenBalance(long minBalance, long maxBalance) {
        return accountRepository.findByBalanceBetween(minBalance, maxBalance); // JPA의 규칙대로 작성한 메서드가 Hibernate에 의해 쿼리로 변환되어서 JDBC로 DB에 다녀옵니다.
    }

    public List<Account> findAllAccountsByBalanceAsc() {
        return accountRepository.findAllByOrderByBalanceAsc();
    }

    public List<Account> findAllAccountsByStatus(String status) {
        return accountRepository.findByStatusContains(status); // 일부일치
        // return accountRepository.findByStatusLike(status); // 완전일치
    }
    public List<Account> findAllAccountsByAccountType(String accountType) {
        return accountRepository.findByAccountTypeLike(accountType);
    }


    public Account createAccount(AccountCreateRequest request) {
        if (request.customerId() == null || request.customerId() <= 0) {
            throw new InvalidAccountRequestException("올바른 customerId가 필요합니다.");
        }

        if (!List.of("입출금", "적금").contains(request.accountType())) {
            throw new InvalidAccountRequestException("지원하지 않는 accountType입니다.");
        }

        String accountNo;
        do {
            accountNo = java.util.UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 20);
        } while (accountRepository.existsByAccountNo(accountNo));

        return accountRepository.save(
                Account.open(request.customerId(), accountNo, request.accountType())
        );
    }

}
