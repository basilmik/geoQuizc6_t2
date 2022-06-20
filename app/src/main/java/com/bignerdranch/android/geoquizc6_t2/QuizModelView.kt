package com.bignerdranch.android.geoquizc6_t2

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_asia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,true),
        Question(R.string.question_americas,false),
        Question(R.string.question_australia,false)
    )

    var currentIndex = 0
   // var isCheater = false // questionBank[currentIndex].isCheated

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionCheatStat: Boolean
        get() = questionBank[currentIndex].isCheated


    fun moveToNext()
    {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun setQuestionIsCheated(_isCheated: Boolean)
    {
        questionBank[currentIndex].isCheated = _isCheated
    }

}