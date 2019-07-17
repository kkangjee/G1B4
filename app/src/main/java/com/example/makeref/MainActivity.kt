package com.example.makeref

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Random
import android.content.pm.PackageManager
import android.os.Build
import android.Manifest.permission.WRITE_CONTACTS
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION

class MainActivity : AppCompatActivity() {
    val permission_list = arrayOf(WRITE_CONTACTS, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        checkPermission()

        quote.text = quotelist[num].Quote
        quotedfrom.text = quotelist[num].QuotedFrom

        bt_guide.setOnClickListener {//setting 화면
            val intent = Intent(this, GuideActivity::class.java)
            startActivity(intent)
        }
        bt_love.setOnClickListener {//사랑방 화면
            val intent = Intent(this, LoveActivity::class.java)
            startActivity(intent)
        }
        bt_login.setOnClickListener {//로그인 화면
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        bt_travelandfood.setOnClickListener{
            val intent = Intent(this, TravelAndFood::class.java)
            startActivity(intent)

        }
    }

    override fun onStop() {
        super.onStop()
        num = random.nextInt(5)
        quote.text = quotelist[num].Quote
        quotedfrom.text = quotelist[num].QuotedFrom
    }



    fun checkPermission() {
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return

        for (permission in permission_list) {
            //권한 허용 여부를 확인한다.
            val chk = checkCallingOrSelfPermission(permission)

            if (chk == PackageManager.PERMISSION_DENIED) {
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list, 0)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            for (i in grantResults.indices) {
                //허용됬다면
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(applicationContext, "앱권한설정하세요", Toast.LENGTH_LONG).show()

                    //finish()
                }
                //bt_guide.text= i.toString()
            }

        }
    }
}