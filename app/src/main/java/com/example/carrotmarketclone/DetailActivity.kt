package com.example.carrotmarketclone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carrotmarketclone.databinding.ActivityDetailBinding
import com.example.carrotmarketclone.databinding.ActivityMainBinding
import com.example.carrotmarketclone.databinding.BottomBarBinding
import com.example.carrotmarketclone.databinding.DescriptionBinding
import com.example.carrotmarketclone.databinding.ProfileBarBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var profileBarBinding: ProfileBarBinding
    private lateinit var descriptionBinding: DescriptionBinding
    private lateinit var bottomBarBinding: BottomBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profileBarView = binding.layoutProfileBar
        profileBarBinding = ProfileBarBinding.bind(profileBarView.root)

        val descriptionView = binding.layoutDescription
        descriptionBinding = DescriptionBinding.bind(descriptionView.root)

        val bottomBarView = binding.layoutBottombar
        bottomBarBinding = BottomBarBinding.bind(bottomBarView.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataList = intent.getParcelableExtra<MyItem>("dataList")

        binding.ivPhoto.setImageResource(dataList!!.photo)
        profileBarBinding.tvUsername.text = dataList.seller
        profileBarBinding.tvAddress.text = dataList.address
        descriptionBinding.tvDescription.text = dataList.desc
        descriptionBinding.tvProductName.text = dataList.name
        bottomBarBinding.tvPrice.text = priceRegex(dataList.price.toString())

        //뒤로가기 버튼
        binding.icBack.setOnClickListener {
            finish()
        }




    }
}