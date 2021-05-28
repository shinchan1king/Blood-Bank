package com.example.bloodbank2

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AlertDialogLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_ibd.*
import kotlinx.android.synthetic.main.activity_rbb.*
import java.util.*

class Rbb : AppCompatActivity() {
    val BGArray= arrayOf("A+","A-","B+","B-","O+","O-","AB+","AB-")
    var ch= booleanArrayOf(false,false,false,false,false,false,false,false)
    private var mAuth: FirebaseAuth?=null
    var database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        var BgString:String
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rbb)
        mAuth = FirebaseAuth.getInstance()
        //define blood group
        val bloodGroupArray= arrayOf("A+","A-","B+","B-","O+","O-","AB+","AB-")
        //defining which is available
        val checkedBloodGroupArray= booleanArrayOf(false,false,false,false,false,false,false,false)
        //textView
        val mTxtView=findViewById<TextView>(R.id.txtView)
          mTxtView.setOnClickListener {
           //Making a alert dialog
            val builder=AlertDialog.Builder(this@Rbb)
              //converting blood group array to list
            val BGList= Arrays.asList(*bloodGroupArray)
                //setting title
            builder.setTitle("Select Blood Broup Available")
                //setting up multiple chices
            builder.setMultiChoiceItems(bloodGroupArray,checkedBloodGroupArray){dialog:DialogInterface,which:Int,isChecked:Boolean->
                checkedBloodGroupArray[which]=isChecked

                val currentItem=bloodGroupArray[which]

                Toast.makeText(applicationContext,currentItem+" ",Toast.LENGTH_SHORT).show()

            }
              //setting up ok button which finalize the checked values
            builder.setPositiveButton("OK") {dialog:DialogInterface,which:Int ->

                mTxtView.text="Blood Group Available Are ... "
                for(i in checkedBloodGroupArray.indices)
                {
                    val checked=checkedBloodGroupArray[i]
                    if(checked)
                    {
                        mTxtView.text=mTxtView.text.toString()+BGList[i]+","
                    }
                }
            }
              //setting up cancel button
            builder.setNeutralButton("Cancel"){dialog:DialogInterface,which:Int->
                dialog.dismiss()

            }
              //creating the builder
            val dialog=builder.create()
            dialog.show()
        }
        ch=checkedBloodGroupArray

        val RbbSubmit=findViewById<Button>(R.id.RbbSubmit)
        RbbSubmit.setOnClickListener {
            val currentuser = mAuth!!.currentUser

            val type = "BB"
            val Name = etBbname.text.toString()

            val Phone = etPhn.text.toString()
            BgString = mTxtView.text.toString()
            var Blood_Group = BgString.replace("Blood Group Available Are ... ", ",")


            val Address = etAddress.text.toString()
            val City = etCt.text.toString().replace(" ","").toLowerCase().capitalize()
            val State = etSte.text.toString().replace(" ","").toLowerCase().capitalize()
            val Country = etCntry.text.toString().replace(" ","").toLowerCase().capitalize()

            val BG_State = Country + "_" + State
            val BG_City = Country + "_" + State + "_" + City
            if (Name == "" || Phone == "" || Address == "" || City == "" || State == "" || Country == "") {
                Toast.makeText(applicationContext, "Please Fill All the Fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val user: HashMap<String, String> = hashMapOf(
                    "type" to type,
                    "Name" to Name,
                    "Phone" to Phone,
                    "Blood_Group" to Blood_Group,
                    "Address" to Address,
                    "City" to City,
                    "State" to State,
                    "Country" to Country,
                    "BG_State" to BG_State,
                    "BG_City" to BG_City
                )
                val myRef = database.getReference("Blood Bank")

                var newRef = myRef.child(currentuser!!.uid)
                newRef.setValue(user).addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Value Added or Updated Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }

}


