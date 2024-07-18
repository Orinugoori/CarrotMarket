package com.example.carrotmarketclone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carrotmarketclone.databinding.ActivityDetailBinding
import com.example.carrotmarketclone.databinding.BottomBarBinding
import com.example.carrotmarketclone.databinding.DescriptionBinding
import com.example.carrotmarketclone.databinding.ProfileBarBinding
import com.google.android.material.snackbar.Snackbar

/*TODO:클릭 이벤트 처리 interface -> 인자로 받아서 처리 변경하기
       좋아요 처리 변경하기
 */


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


        val position = intent.getIntExtra("position", -1)
        val data = MyItemObject.dataList[position] as MyItem.Data

        binding.ivPhoto.setImageResource(data.photo)
        profileBarBinding.tvUsername.text = data.seller
        profileBarBinding.tvAddress.text = data.address
        descriptionBinding.tvDescription.text = data.desc
        descriptionBinding.tvProductName.text = data.name
        bottomBarBinding.tvPrice.text = priceRegex(data.price.toString())

        //뒤로가기 버튼
        binding.icBack.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("position",position)
            setResult(Activity.RESULT_OK,resultIntent)
            finish()
        }




        //좋아요 버튼 누를시
        val likeBtn = bottomBarBinding.icHeart

        if (data.interest){
            likeBtn.setImageResource(R.drawable.ic_heart_colored)
        }else{
            likeBtn.setImageResource(R.drawable.ic_heart)
        }



        likeBtn.setOnClickListener {
            data.interest = !data.interest
            if (data.interest) {
                data.likes += 1
                val snackbar = Snackbar.make(it, "관심 목록에 추가 하였습니다", Snackbar.LENGTH_SHORT)
                snackbar.show()
                likeBtn.setImageResource(R.drawable.ic_heart_colored)
            } else {
                data.likes -= 1
                val snackbar = Snackbar.make(it, "관심 목록에서 제거 하였습니다", Snackbar.LENGTH_SHORT)
                snackbar.show()
                likeBtn.setImageResource(R.drawable.ic_heart)
            }
            MyItemObject.dataList[position] = data
        }

    }
}