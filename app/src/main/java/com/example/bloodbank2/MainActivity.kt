package com.example.bloodbank2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth?=null
    var database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
    }

    fun buIBD(view: View)
    {

        val CurrenntUser=mAuth!!.currentUser
        val mRef=database.reference
        if (CurrenntUser != null) {

            mRef.child("Blood Bank").child(CurrenntUser.uid).addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {


                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                        Toast.makeText(applicationContext,"User Data Exists As Blood Bank",Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        val it = Intent(this@MainActivity, Ibd::class.java)

                        startActivity(it)
                    }
                }


            })

        }
    }
    fun buSearch(view: View)
    {
        val uemail:String = intent.getStringExtra("email").toString()
        val it = Intent(this, search::class.java)
        it.putExtra("email",uemail)
        startActivity(it)
    }
    fun buRBB(view: View)
    {

        val CurrenntUser=mAuth!!.currentUser
        val mRef=database.reference
        if (CurrenntUser != null) {

            mRef.child("users").child(CurrenntUser.uid).addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {


                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                        Toast.makeText(applicationContext,"User Data Exists As Independent Donor",Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        val it = Intent(this@MainActivity, Rbb::class.java)

                        startActivity(it)
                    }
                }


            })

        }
    }
    fun signOut(view: View)
    {
        mAuth!!.signOut()
        val it = Intent(this, login::class.java)

        startActivity(it)
    }
    fun delData(view:View)
    {
        val CurrenntUser=mAuth!!.currentUser

        if (CurrenntUser != null) {
            val mRef= database.getReference("users")
            mRef.child(CurrenntUser.uid).setValue(null).addOnSuccessListener {

            }
            val mRef2= database.getReference("Blood Bank")
            mRef2.child(CurrenntUser.uid).setValue(null).addOnSuccessListener {
                Toast.makeText(applicationContext,"Data Deleted Successfully",Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun user(view: View)
    {
        var temp=0
        val CurrenntUser=mAuth!!.currentUser
        val mRef=database.reference
        if (CurrenntUser != null) {

            mRef.child("Blood Bank").child(CurrenntUser.uid).addValueEventListener(object:
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {


                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                                temp=1



                            val blood_group = snapshot.child("Blood_Group").getValue().toString()
                            val city = snapshot.child("City").getValue().toString()
                            val country = snapshot.child("Country").getValue().toString()

                            val name = snapshot.child("Name").getValue().toString()
                            val phn_number = snapshot.child("Phone").getValue().toString()
                            val state = snapshot.child("State").getValue().toString()
                            val street = snapshot.child("Address").getValue().toString()
                            val address =
                                "$street,$city,$state,$country"

                     var     it = Intent(this@MainActivity,userProfile::class.java)
                            it.putExtra("Blood_Group",blood_group)
                            it.putExtra("name",name)
                            it.putExtra("Address",address)
                            it.putExtra("phn_number",phn_number)
                            it.putExtra("type","Blood Bank")
                        startActivity(it)
                       return

                    }
                }


            })




            mRef.child("users").child(CurrenntUser?.uid.toString()).addValueEventListener(object:ValueEventListener {
                override fun onCancelled(error: DatabaseError) {


                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {



                                temp=1

                            val blood_group = snapshot.child("Blood_Group").getValue().toString()
                            val city = snapshot.child("City").getValue().toString()
                            val country = snapshot.child("Country").getValue().toString()
                            val housenumber = snapshot.child("House").getValue().toString()
                            val name = snapshot.child("Name").getValue().toString()
                            val phn_number = snapshot.child("Phone").getValue().toString()
                            val state = snapshot.child("State").getValue().toString()
                            val street = snapshot.child("Street").getValue().toString()
                            val address =
                                "$housenumber,$street,$city,$state,$country"

                        var    it = Intent(this@MainActivity,userProfile::class.java)
                            it.putExtra("Blood_Group",blood_group)
                            it.putExtra("name",name)
                            it.putExtra("Address",address)
                            it.putExtra("phn_number",phn_number)
                            it.putExtra("type","Individual Donor")
                            startActivity(it)



                    }
                    else {
                        if (temp==0)
                        {


                                Toast.makeText(applicationContext, "user data does not exist", Toast.LENGTH_SHORT)
                                    .show()

                        }
                    }

                }
            })


        }




    }
}

