package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.*


const val HEADER_MSG = "com.example.notes.MESSAGE"

class Note : AppCompatActivity() {
    var MESSAGE: String? = ""
    var FILE_NAME: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        println(intent.getStringExtra(EXTRA_MESSAGE))
        MESSAGE = intent.getStringExtra(EXTRA_MESSAGE)
        FILE_NAME = MESSAGE + ".txt"
        setNoteText()



    }

    fun setNoteText()
    {
        findViewById<TextView>(R.id.Heading).text = MESSAGE

        try{
            var fis = openFileInput(FILE_NAME)
            var reader = BufferedReader(InputStreamReader(fis))
            var line: String
            var whole = ""

            reader.forEachLine {
                whole = whole + it + "\n"
            }
            reader.close()

            findViewById<TextView>(R.id.content).text = whole

        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
    }

    /**
     * Sets the onlick action for the delete note.
     *
     * OnClick the method finds the note currently referenced, deletes it from
     * the dir, then returns to the main activity
     */
    fun delete_note(view: View)
    {
        var file = File(filesDir, FILE_NAME)
        file.delete()

        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun edit_note(view: View)
    {
        intent = Intent(this, edit_note::class.java)
        intent.putExtra(HEADER_MSG, MESSAGE)
        startActivity(intent)
    }

}