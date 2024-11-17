package com.example.energyx.repository;

import com.example.energyx.entity.NiveisOperacionais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NiveisOperacionaisRepository extends JpaRepository<NiveisOperacionais, Long> {
}
