package com.example.bloodbank2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.ticket.view.*
import java.io.Console
import java.lang.Math.log
import java.lang.StrictMath.log
import java.util.stream.DoubleStream.concat

class Search_Result : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var listPerson=ArrayList<Note>()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        val Blood_Group:String = intent.getStringExtra("Blood_group").toString().toUpperCase()

        val City:String = intent.getStringExtra("City").toString().toLowerCase().capitalize()
        val State:String = intent.getStringExtra("State").toString().toLowerCase().capitalize()
        val Country:String = intent.getStringExtra("Country").toString().toLowerCase().capitalize()
        val BG_Country=Blood_Group+"_"+Country
        val BG_State=Blood_Group+"_"+Country+"_"+State
        val BG_City=Blood_Group+"_"+Country+"_"+State+"_"+City
        val new_City=Country+"_"+State+"_"+City
        val new_State=Country+"_"+State

        if(Country!="")
        {
            if(State!="")
            {
                if(City!="")
                {

                        val myRef = database.reference
                       val myRslt=myRef.child("users").orderByChild("BG_City").equalTo(BG_City)
                    val myRslt2=myRef.child("Blood Bank").orderByChild("BG_City").equalTo(new_City)
                    readData(myRslt)
                    readData2(myRslt2,Blood_Group)
                }
                else
                {
                    val myRef = database.reference
                    val myRslt=myRef.child("users").orderByChild("BG_State").equalTo(BG_State)
                    val myRslt2=myRef.child("Blood Bank").orderByChild("BG_State").equalTo(new_State)
                    readData(myRslt)
                    readData2(myRslt2,Blood_Group)
                }
            }
            else
            {
                val myRef = database.reference
                val myRslt=myRef.child("users").orderByChild("BG_Country").equalTo(BG_Country)
                val myRslt2=myRef.child("Blood Bank").orderByChild("Country").equalTo(Country)
                readData(myRslt)
                readData2(myRslt2,Blood_Group)


            }

        }





    }
private   fun readData(Rslt:Query)
   {


       Rslt.addValueEventListener(object:ValueEventListener {
           override fun onCancelled(error: DatabaseError) {


           }

           override fun onDataChange(snapshot: DataSnapshot) {

               if (snapshot.exists()) {
                   listPerson.clear()

                   for (data in snapshot.children) {


                       val blood_group = data.child("Blood_Group").getValue().toString()
                       val city = data.child("City").getValue().toString()
                       val country = data.child("Country").getValue().toString()
                       val housenumber = data.child("House").getValue().toString()
                       val name = data.child("Name").getValue().toString()
                       val phn_number = data.child("Phone").getValue().toString()
                       val state = data.child("State").getValue().toString()
                       val street = data.child("Street").getValue().toString()
                       var address =
                           "$housenumber,$street,$city,$state,$country"


                       listPerson.add(Note(name, blood_group, phn_number, address))


                   }


               }
           }
       })


   }
    private fun readData2(Rslt: Query,B_G:String)
    {

        Rslt.addValueEventListener(object:ValueEventListener {
            override fun onCancelled(error: DatabaseError) {


            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists())
                {


                    for(data in snapshot.children)
                    {

                        val new_BG=","+B_G+","
                        val blood_group2 = data.child("Blood_Group").getValue().toString()
                        if(blood_group2.contains(new_BG)) {
                           val blood_group=blood_group2.replace(","," ")
                            val city = data.child("City").getValue().toString()
                            val country = data.child("Country").getValue().toString()

                            val name = data.child("Name").getValue().toString()
                            val phn_number = data.child("Phone").getValue().toString()
                            val state = data.child("State").getValue().toString()
                            val street = data.child("Address").getValue().toString()
                            val address =
                                "$street,$city,$state,$country"
                            Toast.makeText(this@Search_Result,name,Toast.LENGTH_SHORT).show()
                            listPerson.add(Note(name, blood_group, phn_number, address))

                        }
                        else
                        {
                            continue
                        }



                    }

                }
                val newListPerson =myListPersonAdaptor(listPerson)
                ListNew.adapter=newListPerson


            }
        })

    }
    inner class myListPersonAdaptor:BaseAdapter{
       private var ListPersonAdaptor=ArrayList<Note>()
        constructor(ListPersonAdaptor:ArrayList<Note>):super (){
            this.ListPersonAdaptor=ListPersonAdaptor
        }

        override fun getCount(): Int {
            return ListPersonAdaptor.size
        }

        override fun getItem(position: Int): Any {
            return ListPersonAdaptor[position]
        }

        override fun getItemId(position: Int): Long {
           return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
           val myView= layoutInflater.inflate(R.layout.ticket,null)
             val myNode=ListPersonAdaptor[position]
            myView.tvName.text=myNode.nodeName
            myView.tvAddress.text=myNode.address
            myView.tvB_G.text=myNode.blood_group
            myView.tvPhn_Number.text=myNode.phnNumber
            return myView
        }



    }

}









