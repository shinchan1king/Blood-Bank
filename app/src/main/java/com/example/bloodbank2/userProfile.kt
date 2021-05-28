package com.example.bloodbank2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_user_profile.*

class userProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val Blood_Group:String = intent.getStringExtra("Blood_Group").toString()

        val Name:String = intent.getStringExtra("name").toString()
        val phon_number:String = intent.getStringExtra("phn_number").toString()
        val add:String = intent.getStringExtra("Address").toString()
        val tpe:String=intent.getStringExtra("type").toString()

        blood_group.text=Blood_Group
        etname.text=Name
        address.text=add
        phn_number.text=phon_number
        type.text=tpe
    }
}