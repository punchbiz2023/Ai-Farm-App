package com.Uma.byldwealth.data.repository

import android.content.Context
import com.Uma.byldwealth.data.model.Holding
import com.Uma.byldwealth.data.model.Transaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JsonHoldingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : HoldingsRepository {

    private val gson = Gson()

    override suspend fun getHoldings(): List<Holding> {
        val json = context.assets.open("holdings.json")
            .bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Holding>>() {}.type
        return gson.fromJson(json, type)
    }

    override suspend fun getTransactions(holdingId: String): List<Transaction> {
        val json = context.assets.open("transactions.json")
            .bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Transaction>>() {}.type
        val all: List<Transaction> = gson.fromJson(json, type)
        return all.filter { it.holdingId == holdingId }
    }
}