package com.example.bloodbank2
import android.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
class login : AppCompatActivity() {
    private var mAuth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

    }
    fun buLogin(view: View) {


        val userEmail = buEmail.text.toString()
        val userPassword = buPassword.text.toString()
        if (userEmail == "" || userPassword == "") {
            Toast.makeText(
                applicationContext,
                "Please enter both email and password  to login",
                Toast.LENGTH_LONG
            ).show()

        } else {
            mAuth?.signInWithEmailAndPassword(userEmail, userPassword)
                ?.addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Signin Successful", Toast.LENGTH_SHORT)
                            .show()
                        Loadmain()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Please provide valid email or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

        }

    }

    fun buSignUp(view: View) {
        try {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        } catch (e: Exception) {

        }
    }

    override fun onStart() {
        super.onStart()
        Loadmain()
    }
    fun Loadmain()
    {
        var CurrenntUser=mAuth!!.currentUser
        if(CurrenntUser!=null){
            val intent=Intent(this,MainActivity::class.java)
            intent.putExtra("Email",CurrenntUser.email)
            intent.putExtra("uid",CurrenntUser.uid)
            startActivity(intent)
        }
    }

}