package com.example.carrotmarketclone

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.carrotmarketclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataList = getReviewList()


        binding.recyclerView.apply {
            val adapter = RecylerAdapter(dataList)
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, VERTICAL)
            this.addItemDecoration(decoration)

            adapter.itemClick = object : RecylerAdapter.ItemClick{
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(this@MainActivity,DetailActivity::class.java)
                    intent.putExtra("dataList",dataList[position])
                    startActivity(intent)
                }
            }
        }


        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)


    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("사과 마켓을 종료하시겠습니까?")
            builder.setIcon(R.drawable.ic_apple)

            val listener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            finish()
                        }

                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog?.dismiss()
                        }
                    }
                }
            }
            builder.setPositiveButton("종료", listener)
            builder.setNegativeButton("취소", listener)
            builder.show()
        }


    }



}


