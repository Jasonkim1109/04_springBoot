package com.example.wonit.sevice;

import com.example.wonit.model.Account;
import com.example.wonit.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Controller와만 통신하며 요청에 대한 실제 로직을 담당
@Service
public class AccountService {

    @Autowired // 서로서로 연결된 bean임을 알려줍니다. 생략하면 Springboot가 넣어줌
    private AccountRepository accountRepository;

    // 모든 계좌를 리턴
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

}
