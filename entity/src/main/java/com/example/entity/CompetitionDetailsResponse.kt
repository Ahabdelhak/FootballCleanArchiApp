package com.example.entity

data class CompetitionDetailsResponse(
    val area: Area,
    val code: String,
    val currentSeason: CurrentSeason,
    val emblemUrl: String,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val plan: String,
    val seasons: List<Season>,
    val errorCode: Int,
    val message: String)