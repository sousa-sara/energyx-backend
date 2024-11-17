package com.example.energyx.repository;

import com.example.energyx.entity.Reatores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReatoresRepository extends JpaRepository<Reatores, Long> {
}
