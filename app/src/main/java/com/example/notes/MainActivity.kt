package com.example.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


const val EXTRA_MESSAGE = "com.example.notes.MESSAGE"

class MainActivity : AppCompatActivity() {
    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var files: File = filesDir
        var array = files.list()
        val arrayList: ArrayList<String> = ArrayList<String>()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        for(fileName in array)
        {
            println(fileName)
            val newFileName = fileName.replace(".txt", "")
            adapter.add(newFileName)

        }

        listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val item = listView.getItemAtPosition(position).toString()
                val intent = Intent(this, com.example.notes.Note::class.java)
                intent.putExtra(EXTRA_MESSAGE, item)
                startActivity(intent)
            }
    }

    fun onAdd(view: View)
    {
        var heading: String = findViewById<EditText>(R.id.headerField).text.toString()
        println(heading + "ajajaj")
        var content: String = findViewById<EditText>(R.id.contentField).text.toString()

        if(!heading.isEmpty()){
             if(!content.isEmpty()) {
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
                 adapter.add(heading)
                 listView.adapter = adapter
             }
            else
             {
                 findViewById<EditText>(R.id.contentField).setError("Content needs something!")
             }
        }
        else{
            findViewById<EditText>(R.id.headerField).setError("Heading needs to be filled!")
        }
    }
}