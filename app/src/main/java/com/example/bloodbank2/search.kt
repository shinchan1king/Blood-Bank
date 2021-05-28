package com.example.bloodbank2

import android.content.Intent
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*

class search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
   fun EtSearch(view: View)
   {
       val Blood_Group=EtBGRequired.text.toString().replace(" ","")

       val city=EtCity.text.toString().replace(" ","")
       val state=EtState.text.toString().replace(" ","")
       val country=EtCountry.text.toString().replace(" ","")
       val intent = Intent(this, Search_Result::class.java)
       intent.putExtra("Blood_group",Blood_Group)

       intent.putExtra("City",city)
       intent.putExtra("State",state)
       intent.putExtra("Country",country)
       if(country==""||Blood_Group=="")
       {
           Toast.makeText(applicationContext,"Please enter atleast country and Blood Group Required",Toast.LENGTH_SHORT).show()
       }
       else
       {
           startActivity(intent)
       }
   }
}