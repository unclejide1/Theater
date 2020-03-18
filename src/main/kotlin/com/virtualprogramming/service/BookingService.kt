package com.virtualprogramming.service

import com.virtualprogramming.model.Seat
import org.springframework.stereotype.Service

@Service
class BookingService {

    fun isSeatFree(seat: Seat):Boolean{
        return true
    }
}