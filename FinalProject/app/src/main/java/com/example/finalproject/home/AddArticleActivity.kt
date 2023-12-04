package com.example.finalproject.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.DBKey.Companion.DB_ARTICLES
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class AddArticleActivity: AppCompatActivity() {

    private var selectedUri: Uri? = null
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_article)


        findViewById<Button>(R.id.submitButton).setOnClickListener {
            val title = findViewById<EditText>(R.id.titleEditText).text.toString()
            val price = findViewById<EditText>(R.id.priceEditText).text.toString()
            val sell = findViewById<EditText>(R.id.SellOk).text.toString()
            val sellerId = auth.currentUser?.uid.orEmpty()

            if (title.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "제목 및 가격 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedUri != null) {
                val photoUri = selectedUri ?: return@setOnClickListener
            } else {
                uploadArticle(sellerId, title, price, sell)
            }

        }
    }

    private fun uploadArticle(sellerId: String, title: String, price: String, sell: String) {
        val model = ArticleModel(sellerId, title, System.currentTimeMillis(), price, sell)
        articleDB.push().setValue(model)
        Toast.makeText(this, "아이템이 등록되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

    }

}
