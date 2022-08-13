package com.example.entity

import com.example.entity.Competition
import com.example.entity.Filters

data class CompetitionResponse(
    var competitions: List<Competition>,
    val count: Int,
    val filters: Filters,
    val error: Int
)