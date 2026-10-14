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