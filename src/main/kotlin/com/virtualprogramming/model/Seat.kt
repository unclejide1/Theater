package com.virtualprogramming.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "seats")
data class Seat(@Id @GeneratedValue(strategy = GenerationType.AUTO)
                val id: Long,
                val row: Char,
                @Column(name = "seatnum")
                val num: Int = 0,
                @Column(name = "seatPrice")
                val price: BigDecimal,
                val description: String) {
    override fun toString(): String = "Seat $row-$num $$price ($description)"
}
