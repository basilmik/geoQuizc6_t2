package com.bignerdranch.android.geoquizc6_t2

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, var isCheated: Boolean = false)