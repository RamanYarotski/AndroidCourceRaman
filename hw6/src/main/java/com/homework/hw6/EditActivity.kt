package com.homework.hw6

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_file)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
        val intent = intent
        val file = intent.getSerializableExtra("File") as File
        val fileName = intent.getStringExtra("FileName")
        val fileNameTextView = findViewById<TextView>(R.id.fileName)
        fileNameTextView.text = fileName
        val fileContent = findViewById<EditText>(R.id.fileContent)

        if (file.length() > 0) {
            val contentFromFile: String = getContent(file)
            fileContent.setText(contentFromFile)
        }

        val editFileButton = findViewById<Button>(R.id.editFileButton)
        editFileButton.setOnClickListener {
            val sharedPrefs = getPreferences(Context.MODE_PRIVATE)
            val isSharedPrefChecked = sharedPrefs.getBoolean("IS_SHARED_STORAGE", false)
            val resultIntent = Intent()
            file.delete()
            val editedFile = if (isSharedPrefChecked) {
                File(externalCacheDir, "$fileName.txt")
            } else {
                File(filesDir, "$fileName.txt")
            }
            FileOutputStream(file, true)
                    .bufferedWriter()
                    .use { out ->
                        out.append(fileContent.text)
                        out.newLine()
                        out.close()
                    }
            resultIntent.putExtra("File", editedFile)
            resultIntent.putExtra("FileName", fileName)
            setResult(1000, resultIntent)
            finish()
        }
    }

    private fun getContent(file: File): String {
        return FileInputStream(file)
                .bufferedReader()
                .use { out ->
                    out.readLine().toString()
                }
    }
}