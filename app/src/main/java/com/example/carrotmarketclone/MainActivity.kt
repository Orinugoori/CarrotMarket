package com.example.carrotmarketclone


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.carrotmarketclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: RecyclerAdapter

    private lateinit var launcher : ActivityResultLauncher<Intent>
    private var notifyPosition = 0


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

        binding.icBell.setOnClickListener {
            notification()
        }



        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            object : ActivityResultCallback<ActivityResult> {
                override fun onActivityResult(result: ActivityResult) {
                    if (result.resultCode == RESULT_OK){
                        result.data?.let {
                            val resultData = it.getIntExtra("position",-1)
                            notifyPosition = resultData
                            recyclerAdapter.notifyItemChanged(resultData)
                        }
                    }
                }
            }
        )

        val itemClick = {item : MyItem, position:Int ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra("position",position)
            launcher.launch(intent)
        }

        val itemLongClick = {item : MyItem, position : Int ->
            AlertDialog.Builder(this@MainActivity)
                .setMessage("해당 게시글을 삭제하시겠습니까?")
                .setIcon(R.drawable.ic_apple)
                .setPositiveButton("삭제") { dialog, which ->
                    recyclerAdapter.removeItem(position)
                }
                .setNegativeButton("취소", null)
                .create()
                .show()
        }

        recyclerAdapter = RecyclerAdapter(MyItemObject.dataList, itemClick, itemLongClick)

        binding.recyclerView.apply {
            this.adapter = recyclerAdapter
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.addItemDecoration(StickyHeaderItemDecoration(
                this,
                isHeader = { position -> recyclerAdapter.isHeader(position) }
            ))
        }



        val floatingBtn = binding.floatingBtn
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 400 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 400 }
        var isTop = true

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.floatingBtn.startAnimation(fadeOut)
                    binding.floatingBtn.visibility = View.GONE
                    isTop = true
                } else if (isTop) {
                    floatingBtn.visibility = View.VISIBLE
                    floatingBtn.startAnimation(fadeIn)
                    isTop = false
                }
            }
        })

        floatingBtn.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0)
        }

        //시스템 뒤로가기 누를때
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onResume() {
        super.onResume()
        recyclerAdapter.notifyItemChanged(notifyPosition)
    }


    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
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


    private fun notification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "My Channel One Description"
                setShowBadge(true)
                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)

        } else {
            builder = NotificationCompat.Builder(this)
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        builder.run {
            setSmallIcon(R.drawable.ic_apple)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다!")
            addAction(R.drawable.ic_apple, "Action", pendingIntent)
        }

        manager.notify(11, builder.build())
    }



}




