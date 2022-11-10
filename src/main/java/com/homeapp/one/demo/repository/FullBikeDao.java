package com.homeapp.one.demo.repository;

import com.homeapp.one.demo.models.FullBike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FullBikeDao extends JpaRepository<FullBike, Long> {
}