package com.virtualprogramming.service

import com.virtualprogramming.model.Booking
import com.virtualprogramming.model.Performance
import com.virtualprogramming.model.Seat
import com.virtualprogramming.repository.BookingRepository
import com.virtualprogramming.repository.SeatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    @Autowired
    lateinit var seatRepository: SeatRepository

    fun isSeatFree(seat: Seat, performance: Performance):Boolean{
        val matchedBooking = findBooking(seat, performance)
        return matchedBooking == null
    }

    fun findSeat(seatNum: Int, seatRow: Char) :Seat?{
        val allSeats = seatRepository.findAll()
        val foundSeat = allSeats.filter { it.num == seatNum && it.row == seatRow }
        return foundSeat.firstOrNull();
    }

    fun findBooking(seat: Seat, performance: Performance): Booking?{
        var bookings = bookingRepository.findAll()
        val foundBookings = bookings.filter { it.seat == seat && it.performance == performance }
        return foundBookings.firstOrNull()
    }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking{
        val booking = Booking(0, customerName)
        booking.performance = performance
        booking.seat = seat
        return bookingRepository.save(booking)
    }
}