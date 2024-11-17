package com.example.energyx.repository;

import com.example.energyx.entity.TipoReator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoReatorRepository extends JpaRepository<TipoReator, Long> {
}
