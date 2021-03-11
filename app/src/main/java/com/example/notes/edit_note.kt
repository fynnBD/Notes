package com.example.notes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text
import java.io.*

class edit_note : AppCompatActivity() {
    var MESSAGE: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        MESSAGE = intent.getStringExtra(HEADER_MSG)
        setEditText()
    }

    private fun setEditText()
    {
        findViewById<TextView>(R.id.header_text).text = MESSAGE
        var FILE_NAME = MESSAGE + ".txt"
        try{
            var fis = openFileInput(FILE_NAME)
            var reader = BufferedReader(InputStreamReader(fis))
            var line: String
            var whole = ""

            reader.forEachLine {
                whole = whole + it + "\n"
            }
            reader.close()

            findViewById<EditText>(R.id.edit_content).setText(whole)

        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }


    }

    fun save(view: View)
    {
        var heading = MESSAGE
        var content = findViewById<EditText>(R.id.edit_content).text.toString()

        try {
            var fileOut: FileOutputStream = openFileOutput(
                heading + ".txt",
                Context.MODE_PRIVATE
            )
            fileOut.write(content.toByteArray())
            fileOut.close()
        }
        catch (e: FileNotFoundException)
        {
            e.printStackTrace()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }

        findViewById<TextView>(R.id.header_text).text = "Saved!"

    }

    fun back(view: View)
    {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}