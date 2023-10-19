package com.homeapp.nonsense_BE.repository;

import com.homeapp.nonsense_BE.models.bike.FullBike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FullBikeDao extends JpaRepository<FullBike, Long> {
}