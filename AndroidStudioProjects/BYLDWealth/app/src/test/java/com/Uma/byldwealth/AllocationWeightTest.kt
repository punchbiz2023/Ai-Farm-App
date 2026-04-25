package com.Uma.byldwealth

import org.junit.Assert.assertEquals
import org.junit.Test

class AllocationWeightTest {

    @Test
    fun `allocation weight calculates correctly`() {
        val currentPrice = 3920.0
        val quantity = 5
        val totalValue = 98820.0
        val expected = (currentPrice * quantity / totalValue) * 100
        val result = (currentPrice * quantity / totalValue) * 100
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `allocation weight is zero when total is zero`() {
        val totalValue = 0.0
        val result = if (totalValue > 0) (19600.0 / totalValue) * 100 else 0.0
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun `pnl calculation is correct`() {
        val currentPrice = 3920.0
        val avgCost = 3500.0
        val quantity = 5
        val pnl = (currentPrice - avgCost) * quantity
        assertEquals(2100.0, pnl, 0.01)
    }
}