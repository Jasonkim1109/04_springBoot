package com.example.wonit.controller;

import com.example.wonit.dto.AccountResponse;
import com.example.wonit.model.Account;
import com.example.wonit.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "swagger 테스트 API", description = "swagger 테스트를 진행하는 API")
@RestController
@RequestMapping("/api/accounts") // localhost:8080/api/accounts
public class AccountController {

    // Inversion Of Control (개발자가 Bean으로 등록해놓은 객체는 스프링 컨테이너가 관리하므로 개발자는 가져다 쓰기만 하면 됩니다.)ㄴ
    // AccountService aService = new AccountService();
    // 어차피 스프링컨테이너가 관리하는 bean이므로 private으로 막아둡니다.
    @Autowired // 서로서로 연결된 bean임을 알려줍니다. 생략하면 Springboot가 넣어줌
    private AccountService accountService;

    // 모든 accounts를 조회
    // jpa의 규칙은 Repository에서만 영향을 미칩니다.
    @Operation(summary = "정보 모두 조회", description = "Account의 전체 정보를 조회합니다.")
    @GetMapping
    public List<AccountResponse> findAll() {
    //public List<Account> findAll() {
        // stream().filter() : 값이 true인 것만 반환
        // stream().map() : 모든 값에 같은 메서드를 일괄 적용
        return accountService.findAll().stream().map(AccountResponse::from).toList();
    }

    // @RequestBody : 숨기고 싶은 정보, 주소줄에 전달하기에 너무 긴 값들을 Body에 싣고 전달하는 방법
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody AccountCreateRequest request
    ) {
        Account account = accountService.createAccount(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/accounts/{accountNo}")
                .buildAndExpand(account.getAccountNo())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(AccountResponse.from(account));
    }

    // 특정 계좌번호의 세부 정보를 조회  localhost:8080/api/accounts/1002-345-678901
    @GetMapping("/{accountNo}")
    public AccountResponse findOne(@PathVariable("accountNo") String accountNo){
    // public Account findOne(@PathVariable("accountNo") String accountNo){
        return AccountResponse.from(accountService.getAccount(accountNo));
    }

    // 계좌 전체를 조회해서 그중에 '정상' 계좌만 계좌번호만 출력
    @GetMapping("/report1")
    public List<String> normalAccountNos() {
        return accountService.normalAccountNos();
    }

    // 전체 계좌중에 account_type이 '적금'인 경우만 금액을 모두 더해서 리턴
    // /api/accounts/report2 라는 주소로
    @GetMapping("/report2")
    public long savingBalanceSum() {
        return accountService.savingBalanceSum();
    }

    // min~ max balance 사이의 계좌 정보를 검색
    // http://localhost:8080/api/accounts/balance?minBalance=500000&maxBalance=1000000
    @GetMapping("/balance")
    public List<Account> getAccountBetweenBalance(@RequestParam long minBalance, @RequestParam long maxBalance) {
        return accountService.getAccountBetweenBalance(minBalance, maxBalance);
    }

    // balance 순서대로 작은 금액에서 큰 금액 순서로 모든 계좌 조회
    @GetMapping("/sort") // localhost:8080/api/accounts/sort
    public List<AccountResponse> findAllAccountsByBalanceAsc(){
        return accountService.findAllAccountsByBalanceAsc().stream().map(AccountResponse::from).toList();
    }

    // status 에서 정상 / 휴면 / 지급정지 들어오면 각 상황에 맞는 계좌만 필터링
    // localhost:8080/api/accounts/status=정상
    @GetMapping("/status/{status}") // localhost:8080/api/accounts/accounts/정상 -> 같은 방식으로 값을 입력받는 함수가 2개 있음
    public List<AccountResponse> findAllAccountsByStatus(@PathVariable String status){
        return accountService.findAllAccountsByStatus(status).stream().map(AccountResponse::from).toList();
    }

    // account_type에 적금 / 입출금 들어오면 각 상황에 맞는 계좌만 필터링
    // 1. accountType (camelCase: 자바스타일)
    // 2. account_type (snake_case: DB스타일)
    // 3. account-type (kebab-case: HTTP스타일) - REST API의 권장사항
    @GetMapping("/account-type/{accountType}") // localhost:8080/api/accounts/account-type/입출금 -> 같은 방식으로 값을 입력받는 함수가 2개 있음
    public List<AccountResponse> findAllAccountsByAccountType(@PathVariable String accountType){
        return accountService.findAllAccountsByAccountType(accountType).stream().map(AccountResponse::from).toList();
    }
}
