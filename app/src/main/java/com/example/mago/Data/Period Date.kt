package com.example.mago.Data

sealed class Period(val value: Long)

class Weeks(weeks: Long) : Period(weeks)
class Months(months: Long) : Period(months)
class Years(years: Long) : Period(years)