package com.example.wonit.repository;

import com.example.wonit.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByOrderByTxDatetimeDescTxIdDesc();

    List<Transaction> findByCategoryOrderByTxDatetimeDescTxIdDesc(String category);

    // JPQL: 쿼리를 하이버네이트가 만들었는데 동작이 비효율적이거나 JPA의 규칙으로 만들기 어려운 쿼리인 경우
    // 직접 입력값만 함수 시그니처로 정의해놓고 위에 해당 변수들을 받아서 사용할 쿼리를 작성해줍니다.

    @Query("""
            select t from Transaction t
            where (:category is not null and t.category = :category)
               or (:memo is not null and lower(t.memo) like lower(concat('%', :memo, '%')))
            order by t.txDatetime desc, t.txId desc
            """)
    List<Transaction> searchByCategoryOrMemo(
            @Param("category") String category,
            @Param("memo") String memo
    );

    List<Transaction> findByAmountBetweenOrderByTxDatetimeDescTxIdDesc(
            long minAmount,
            long maxAmount
    );
}