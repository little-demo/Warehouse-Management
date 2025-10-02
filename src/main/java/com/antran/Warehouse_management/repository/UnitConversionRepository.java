package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.UnitConversion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface UnitConversionRepository extends JpaRepository<UnitConversion, Integer> {
    Optional<UnitConversion> findByProductIdAndRatioToBase(int productId, BigDecimal ratio);

    @Modifying
    @Transactional
    @Query("DELETE FROM UnitConversion uc WHERE uc.product.id = :productId AND uc.ratioToBase <> :baseRatio")
    void deleteByProductIdAndRatioToBaseNot(@Param("productId") int productId,
                                            @Param("baseRatio") BigDecimal baseRatio);
}