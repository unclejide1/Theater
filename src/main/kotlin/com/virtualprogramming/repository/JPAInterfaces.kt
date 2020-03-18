package com.virtualprogramming.repository

import com.virtualprogramming.model.Performance
import com.virtualprogramming.model.Seat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


interface SeatRepository: JpaRepository<Seat, Long>

interface PerformanceRepository: JpaRepository<Performance, Long>