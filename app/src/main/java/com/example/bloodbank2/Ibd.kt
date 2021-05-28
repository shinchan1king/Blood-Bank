package com.example.bloodbank2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_ibd.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Ibd : AppCompatActivity() {
    private var mAuth: FirebaseAuth?=null
    var database = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ibd)
        mAuth = FirebaseAuth.getInstance()
    }
    fun buSubmit(view: View)
    {
        val currentuser= mAuth!!.currentUser
        val type ="IBD"
        val Name = etName.text.toString()
        val Age = etAge.text.toString()
        val Phone = etPhone.text.toString()
        val Blood_Group= etBlood.text.toString().replace(" ","").toUpperCase()
        val House= etHouse.text.toString()
        val Street=etStreet.text.toString()
        val City= etCity.text.toString().replace(" ","").toLowerCase().capitalize()
        val State=etState.text.toString().replace(" ","").toLowerCase().capitalize()
        val Country=etCountry.text.toString().replace(" ","").toLowerCase().capitalize()
        val BG_Country=Blood_Group+"_"+Country
        val BG_State=Blood_Group+"_"+Country+"_"+State
        val BG_City=Blood_Group+"_"+Country+"_"+State+"_"+City
        if(Name==""||Age==""||Phone==""||Blood_Group==""||Street==""||City==""||State==""||Country=="")
        {
            Toast.makeText(applicationContext,"Please Fill All the Fields",Toast.LENGTH_SHORT).show()
        }
        else {
            if(Age.toInt()<18)
            {
                Toast.makeText(applicationContext,"Please Make Sure You Are Above 18",Toast.LENGTH_SHORT).show()
            }
            else {
                val user: HashMap<String, String> = hashMapOf(
                    "type" to type,
                    "Name" to Name,
                    "Age" to Age,
                    "Phone" to Phone,
                    "Blood_Group" to Blood_Group,
                    "House" to House,
                    "Street" to Street,
                    "City" to City,
                    "State" to State,
                    "Country" to Country,
                    "BG_Country" to BG_Country,
                    "BG_State" to BG_State,
                    "BG_City" to BG_City
                )
                val myRef = database.getReference("users")
                val newRef = myRef.child(currentuser!!.uid)

                newRef.setValue(user).addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Value Added SuccessFully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    val intent = Intent(this, MainActivity::class.java)

                    startActivity(intent)
                }

            }
        }

    }
}