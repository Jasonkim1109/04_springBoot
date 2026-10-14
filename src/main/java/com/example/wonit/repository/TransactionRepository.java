package com.example.wonit.repository;

import com.example.wonit.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository                                               // <엔티티명, 엔티티의 pk 자료형>
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
