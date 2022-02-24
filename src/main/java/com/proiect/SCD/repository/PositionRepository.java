package com.proiect.SCD.repository;

import com.proiect.SCD.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PositionRepository extends JpaRepository<Position, Integer> {
    List<Position> getPositionByCreationDateBetweenAndTerminalId(Date startDate, Date endDate, String terminalId);
}
