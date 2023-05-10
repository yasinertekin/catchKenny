package com.yasinertekin.kennyyakalama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.yasinertekin.kennyyakalama.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val imageView = binding.imageView
        start()

    }
    fun increaseScore(view:View) {
        score++
        binding.textView2.text = "Score: $score"

    }

    fun start() {
        object: CountDownTimer(16000,1000){
            override fun onFinish() {
               binding.textView.text = "Time: 0"
                binding.textView2.text = "Score: $score"
                binding.imageView.visibility = View.INVISIBLE

            }
            override fun onTick(p0: Long) {
                binding.textView.text = "Time: ${p0/1000}"

                if (p0/1000 == 0L) {
                    binding.imageView.visibility = View.VISIBLE
                    onCreateDialog()
                }

            }

        }.start()

        val handler = Handler(Looper.getMainLooper())
        handler.post(object: Runnable{
            override fun run() {
                val random = Random()
                val x = random.nextInt(1000)
                val y = random.nextInt(2000)
                binding.imageView.x = x.toFloat()
                binding.imageView.y = y.toFloat()
                handler.postDelayed(this,500)
            }

        })

    }

    fun onCreateDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Oyun Bitti. Tekrar Oynamak İster Misiniz?")
            .setPositiveButton("Evet") { dialog, id ->
                score = 0
                binding.imageView.visibility = View.VISIBLE // Resmi tekrar görünür hale getir

                // Belleği temizlemek ve oyunu yeniden başlatmak için
                val intent = intent
                finish()
                startActivity(intent)
            }
            .setNegativeButton("Hayır") { dialog, id ->
                finish()
            }
        builder.create().show()
    }

}