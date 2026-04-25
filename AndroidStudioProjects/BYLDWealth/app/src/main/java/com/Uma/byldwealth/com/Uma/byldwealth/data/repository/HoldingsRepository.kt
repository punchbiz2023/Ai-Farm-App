package com.Uma.byldwealth.data.repository

import com.Uma.byldwealth.data.model.Holding
import com.Uma.byldwealth.data.model.Transaction

interface HoldingsRepository {
    suspend fun getHoldings(): List<Holding>
    suspend fun getTransactions(holdingId: String): List<Transaction>
}