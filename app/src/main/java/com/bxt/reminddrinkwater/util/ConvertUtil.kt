package com.bxt.reminddrinkwater.util

fun Int.convertTimeShow() = if (this.toString().length < 2) "0$this" else this.toString()

