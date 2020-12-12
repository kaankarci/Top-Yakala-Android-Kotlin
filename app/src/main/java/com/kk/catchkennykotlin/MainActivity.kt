package com.kk.catchkennykotlin

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var score = 0
    lateinit var savedBestScore : SharedPreferences
    var imageArray = ArrayList<ImageView>()
    var runnabler: Runnable = Runnable { }
    var handler: Handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageArray.add(imageView)
        imageArray.add(imageView2)
        imageArray.add(imageView3)
        imageArray.add(imageView4)
        imageArray.add(imageView5)
        imageArray.add(imageView6)
        imageArray.add(imageView7)
        imageArray.add(imageView8)
        imageArray.add(imageView9)

        hideImages()

        savedBestScore=this.getPreferences(Context.MODE_PRIVATE)


        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeText.text = "Time: ${millisUntilFinished / 1000}"
            }
            override fun onFinish() {
                val alert = AlertDialog.Builder(this@MainActivity)
                var bestScore=savedBestScore.getInt("best",0)
                if (score>bestScore){
                    bestScore=score
                    savedBestScore.edit().putInt("best",score).apply()

                }
                timeText.text="Best Score: ${bestScore}"
                handler.removeCallbacks(runnabler)
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                alert.setTitle("Game Over!")
                alert.setMessage("Skorunuz= ${score} \n Play Again?")
                alert.setPositiveButton("Yes") { dialog, which ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alert.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(this@MainActivity, "Game Over", Toast.LENGTH_LONG).show()
                }
                alert.show()

            }
        }.start()
    }

    fun scoreplus(view: View) {
        score++
        scoreText.text = "Score: ${score}"
    }

    fun hideImages() {

        runnabler = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(9)

                imageArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(this, 500)
            }
        }

        handler.post(runnabler)

    }

}