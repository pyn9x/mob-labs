package com.creyc.test

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_regis.*

class RegisActivity : AppCompatActivity() {
    private fun isETEmpty(text: EditText) = text.text.toString().isEmpty()
    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private lateinit var shared: SharedPreferences

    private fun isLoginEmpty() = shared.getString("email", "no-login") != "no-login"
    private fun checkAutomaticLogin() = shared.getString("autoLogin", "no-auto") == "true"

    private fun changeActivity(activity: Class<*>){
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    var enterEmail = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

        shared = getSharedPreferences("data", MODE_PRIVATE)
        //shared.edit().putString("autoLogin", "false").apply()
        if (checkAutomaticLogin()){
            changeActivity(MainActivity::class.java)
        } else if (isLoginEmpty()) {
            //showToast(shared.getString("autoLogin", "no-auto").toString())
            changeActivity(LoginActivity::class.java)
        }

        btnReg.setOnClickListener {
            if (etPass.text.toString() != etRepeat.text.toString()) {
                showToast("Пароли не совпадают")
                return@setOnClickListener
            }
            if (isETEmpty(email)) {
                showToast("Поле почты пустое")
                return@setOnClickListener
            }
            if (isETEmpty(etPass)) {
                showToast("Поле пароля пустое")
                return@setOnClickListener
            }
            /*if (!email.text.toString().matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))) {
                showToast("Неверный формат почты")
                return@setOnClickListener
            }*/
                /*if (enterEmail) {
                if (!email.text.toString().matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))) {
                    showToast("Неверный формат почты")
                    return@setOnClickListener
                }
            }
            else {
                if (!email.text.toString().matches(Regex("^\\+?[1-9][0-9]{7,14}\$"))) {
                    showToast("Неверный формат телефона")
                    return@setOnClickListener
                }
            }*/

            shared.edit().putString("email", email.text.toString()).apply()
            shared.edit().putString("password", etPass.text.toString()).apply()

            changeActivity(MainActivity::class.java)

        }

        num.setOnClickListener {
            num.setTextColor(resources.getColor(R.color.purple_200))
            mail.setTextColor(resources.getColor(R.color.black))
            email.hint = "Введите номер"
            email.inputType =InputType.TYPE_CLASS_PHONE
            enterEmail = false
        }

        mail.setOnClickListener {
            mail.setTextColor(resources.getColor(R.color.purple_200))
            num.setTextColor(resources.getColor(R.color.black))
            email.hint = "Введите email"
            email.inputType =InputType.TYPE_NULL
            enterEmail = true
        }
    }
}