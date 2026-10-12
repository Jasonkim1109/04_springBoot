package com.example.wonit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts") // localhost:8080/api/accounts
public class AccountController {

    // Inversion Of Control (개발자가 Bean으로 등록해놓은 객체는 스프링 컨테이너가 관리하므로 개발자는 가져다 쓰기만 하면 됩니다.)ㄴ
    // AccountService aService = new AccountService();

    // 모든 accounts를 조회
    // jpa의 규칙은 Repository에서만 영향을 미칩니다.
    @GetMapping
    public List<Account> findAll() {
        return aService.findAll();
    }

    // 특정 계좌번호의 세부 정보를 조회  localhost:8080/api/accounts/1002-345-678901
//    @GetMapping("/{accountNo}")
//    public

}
