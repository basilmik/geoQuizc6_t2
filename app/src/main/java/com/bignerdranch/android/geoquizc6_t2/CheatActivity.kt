package com.bignerdranch.android.geoquizc6_t2

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

private const val TAG_CA = "CheatActivity"
private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquizc6_t2.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquizc6_t2.answer_shown"

private const val KEY_CHEAT = "cheater"



class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answerIsTrue = false
    private var isCheater = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        isCheater = savedInstanceState?.getBoolean(KEY_CHEAT,false)?: false
        if (isCheater)
           setAnswerShownResult(true)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener{
            val answerText = when
            {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }

            answerTextView.setText(answerText)
            isCheater = true
            setAnswerShownResult(true)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG_CA, "onInstanceStateInCheatActivity")
        savedInstanceState.putBoolean(KEY_CHEAT, isCheater)
    }



    private fun setAnswerShownResult(isAnswerShown: Boolean)
    {
        val data = Intent().apply{
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue:Boolean):Intent
        {
            return Intent(packageContext,CheatActivity::class.java).apply{
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

}