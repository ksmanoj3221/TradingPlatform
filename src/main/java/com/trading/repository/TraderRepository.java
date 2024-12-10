package com.trading.repository;

import com.trading.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TraderRepository extends JpaRepository<Trader, Long> {

    Optional<Trader> findByEmail(String email);
}