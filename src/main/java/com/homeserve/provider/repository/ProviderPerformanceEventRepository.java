package com.homeserve.provider.repository;

import com.homeserve.provider.entity.ProviderPerformanceEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderPerformanceEventRepository
        extends JpaRepository<ProviderPerformanceEvent, Long> {

    boolean existsByEventId(String eventId);
}