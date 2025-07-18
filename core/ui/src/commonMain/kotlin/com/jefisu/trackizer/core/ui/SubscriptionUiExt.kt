package com.jefisu.trackizer.core.ui

import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.util.nowUtc
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number
import kotlinx.datetime.plus

fun List<Subscription>.filterUpcomingBills(
    currentDate: LocalDate = nowUtc().date,
    isPerDay: Boolean = false,
): List<Subscription> {
    return filter { sub ->
        currentDate >= sub.firstPayment.date || sub.reminder
    }.map { sub ->
        if (sub.reminder) {
            val paymentDate = sub.firstPayment.date
            val difference = currentDate.month.number - paymentDate.month.number
            val newPaymentDate = paymentDate.plus(DatePeriod(months = difference))
            if (newPaymentDate < paymentDate) return@map sub
            sub.copy(
                firstPayment = LocalDateTime(newPaymentDate, sub.firstPayment.time),
            )
        } else {
            sub
        }
    }.filter {
        if (isPerDay) {
            val subMonth = it.firstPayment.date.month
            val subDay = it.firstPayment.date.day
            val currMonth = currentDate.month
            val currDay = currentDate.day
            return@filter subMonth == currMonth && subDay == currDay
        }
        true
    }.sortedByDescending { it.firstPayment }
}
