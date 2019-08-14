package com.WhateverMyAge.WhateverMyAge.Guide.Settings

import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_setting.*
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.WhateverMyAge.WhateverMyAge.Main.PermissionCheck
import com.WhateverMyAge.WhateverMyAge.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //밝기 권한 받기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                //Toast.makeText(this, "onCreate: Already Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "권한 허용이라고 적힌 부분을 눌러주세요", Toast.LENGTH_LONG).show()
                //Toast.makeText(this, "그리고 뒤로가기를 눌러주세요", Toast.LENGTH_LONG).show()

                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + this.packageName)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }

        //무음모드 권한
        //TODO  권한 설정 설명 넣기
        val audioManager: AudioManager = getSystemService(AUDIO_SERVICE) as AudioManager


        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //오디오 관리자 서비스 받기
        bt_volup_ring.setOnClickListener {
            if (audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT) {
                if (!notificationManager.isNotificationPolicyAccessGranted) {
                    toast("저희 어플 맨 오른쪽의 동그란 버튼을 눌러주시겠어요?")
                    val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                    startActivityForResult(intent, 0)
                } else
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL)


            } else {
                val upIndex = audioManager.mediaCurrentVolume_ring + 1

                audioManager.setMediaVolume_ring(upIndex)

                toast("지금 벨소리 크기는 ${audioManager.mediaCurrentVolume_ring} 입니다.")
            }


        }

        bt_voldown_ring.setOnClickListener {
            if (audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT) {

                if (!notificationManager.isNotificationPolicyAccessGranted) {
                    toast("저희 어플 맨 오른쪽의 동그란 버튼을 눌러주시겠어요?")
                    val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                    startActivityForResult(intent, 0)
                } else
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL)


            } else {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL)
                val downIndex = audioManager.mediaCurrentVolume_ring - 1

                audioManager.setMediaVolume_ring(downIndex)
                toast("지금 벨소리 크기는 ${audioManager.mediaCurrentVolume_ring} 입니다.")
            }
        }

        //music
        bt_volup_movie.setOnClickListener {
            val upIndex = audioManager.mediaCurrentVolume_music + 1
            audioManager.setMediaVolume_music(upIndex)

            toast("지금 동영상 소리 크기는 ${audioManager.mediaCurrentVolume_music} 입니다.")
        }

        bt_voldown_movie.setOnClickListener {
            val downIndex = audioManager.mediaCurrentVolume_music - 1

            audioManager.setMediaVolume_music(downIndex)

            toast("지금 동영상 소리 크기는 ${audioManager.mediaCurrentVolume_music} 입니다.")
        }

        bt_brightup.setOnClickListener {

            //com.example.WhateverMyAge.Settings.System.putInt(contentResolver,"screen_brightness",255)
            if (Settings.System.getInt(
                    getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE
                ) != 0
            ) {
                Settings.System.putInt(
                    getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE, 0
                )
            }
            //자동 밝기가 되어있으면 자동밝기 취소하기
            Settings.System.putInt(
                this.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, 255
            )

            val lp = window.attributes
            lp.screenBrightness = 100f// 100 / 100.0f;
            window.attributes = lp

        }
        bt_brightdown.setOnClickListener {

            //com.example.WhateverMyAge.Settings.System.putInt(contentResolver,"screen_brightness",100)
            if (Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE) != 0) {
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, 0)
            }
            //자동 밝기가 되어있으면 자동밝기 취소하기
            Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 100)

            val lp = window.attributes
            lp.screenBrightness = 100f// 100 / 100.0f;
            window.attributes = lp
        }

        bt_addcontact.setOnClickListener {
            // if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            if (PermissionCheck(this, this).ContactCheck() == 0) {
                val intent = Intent(this, AddContactActivity::class.java)
                startActivity(intent)
            }
        }

        bt_rotation.setOnClickListener {
            if (Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
                Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0)
                toast("화면 자동 회전이 꺼졌습니다.")
            }
            else
                toast("화면 자동 회전이 이미 꺼져있어요.")
        }

        bt_back.setOnClickListener {
            finish()
        }
    }
}

//벨소리
fun AudioManager.setMediaVolume_ring(volumeIndex: Int) {
    this.setStreamVolume(
        AudioManager.STREAM_RING,//type 설정
        volumeIndex,//볼륨 크기
        AudioManager.FLAG_SHOW_UI //보여주는 방식
    )
}

val AudioManager.mediaCurrentVolume_ring: Int
    get() = this.getStreamVolume(AudioManager.STREAM_RING)


//음악
fun AudioManager.setMediaVolume_music(volumeIndex: Int) {
    this.setStreamVolume(
        AudioManager.STREAM_MUSIC,//type 설정
        volumeIndex,//볼륨 크기
        AudioManager.FLAG_SHOW_UI //보여주는 방식
    )
}

val AudioManager.mediaCurrentVolume_music: Int
    get() = this.getStreamVolume(AudioManager.STREAM_MUSIC)

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
/*
AudioManager.STREAM_ALARM : 알람 볼륨입니다.
AudioManager.STREAM_DTMF : DTMF 톤 볼륨입니다.
AudioManager.STREAM_MUSIC : 미디어 볼륨입니다.
AudioManager.STREAM_NOTIFICATION : 알림 볼륨입니다.
AudioManager.STREAM_RING : 벨소리 볼륨입니다.
AudioManager.STREAM_SYSTEM : 시스템 볼륨입니다.
AudioManager.SYSTEM_VOICE_CALL : 음성 통화 볼륨입니다.
*/