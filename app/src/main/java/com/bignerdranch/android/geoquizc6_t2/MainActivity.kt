package com.bignerdranch.android.geoquizc6_t2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView


    private val quizViewModel: QuizViewModel by lazy() {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0)?:0
        quizViewModel.currentIndex = currentIndex


        questionTextView = findViewById(R.id.question_text_view)
        nextButton= findViewById(R.id.next_button)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        cheatButton = findViewById(R.id.cheat_button)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener { //view: View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(
                intent,
                REQUEST_CODE_CHEAT
            )

        }

        updateQuestion()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)



        if (resultCode != Activity.RESULT_OK)
        {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT)
        {
            quizViewModel.setQuestionIsCheated(data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false)
        }

    }


    override fun onStart()
    {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }
    override fun onResume()
    {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    override fun onPause()
    {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onInstanceState")
       // savedInstanceState.putBoolean(KEY_CHEAT, quizViewModel.isCheater)
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop()
    {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }
    override fun onDestroy()
    {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }


    private fun updateQuestion()
    {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean)
    {
        val correctAnswer = quizViewModel.currentQuestionAnswer
       /* val messageResId =
            if (userAnswer == correctAnswer)
                R.string.correct_toast
            else
                R.string.incorrect_toast*/

        val messageResId = when
        {
            quizViewModel.currentQuestionCheatStat -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }

}