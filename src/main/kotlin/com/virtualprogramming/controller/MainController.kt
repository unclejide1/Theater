package com.virtualprogramming.controller

import com.virtualprogramming.model.Booking
import com.virtualprogramming.model.Performance
import com.virtualprogramming.model.Seat
import com.virtualprogramming.repository.PerformanceRepository
import com.virtualprogramming.repository.SeatRepository
import com.virtualprogramming.service.BookingService
import com.virtualprogramming.service.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {
    @Autowired
    lateinit var theaterService: TheaterService

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun homePage(): ModelAndView {
        val model = mapOf("bean" to CheckAvailabilityBackingBean(), "performances" to performanceRepository.findAll(),
                "seatNums" to 1..36,
         "seatRows" to 'A'..'O'  )
        return ModelAndView("seatBooking", model)
    }

    @RequestMapping(value = ["checkAvailability"], method = arrayOf(RequestMethod.POST))
    fun checkAvailability(bean: CheckAvailabilityBackingBean): ModelAndView{
        val selectedSeat: Seat = bookingService.findSeat(bean.selectedSeatNum, bean.selectedSeatRow)!!
        val selectedPerformance = performanceRepository.findById(bean.selectedPerformance!!).get()
        bean.seat = selectedSeat
        bean.performance = selectedPerformance
        val result = bookingService.isSeatFree(selectedSeat, selectedPerformance)
        bean.available = result

        if(!result){
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)
        }
        val model = mapOf("bean" to bean, "performances" to performanceRepository.findAll(),
                "seatNums" to 1..36,
                "seatRows" to 'A'..'O'  )
        return ModelAndView("seatBooking", model)
    }

    @RequestMapping("booking", method = arrayOf(RequestMethod.POST))
    fun bookASeat(bean: CheckAvailabilityBackingBean): ModelAndView{
        val booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)

        return ModelAndView("bookingConfirmed", "booking", booking)
    }

}

class CheckAvailabilityBackingBean() {
    var selectedSeatNum: Int = 1
    var selectedSeatRow: Char = 'A'
    var selectedPerformance: Long? = null
    var customerName: String = ""
    var available: Boolean? = null
    var seat: Seat? = null
    var performance: Performance? = null
    var booking: Booking? = null
}