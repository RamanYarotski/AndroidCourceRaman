package com.homework.hw6

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import kotlinx.android.synthetic.main.activity_main.newFileButton
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.settingsButton
import kotlinx.android.synthetic.main.create_file_dialog.view.fileNameEditText


private const val SETTINGS_CODE = 741

class MainActivity : AppCompatActivity() {

    private var filesMap = mutableMapOf<String, File>()
    private var ID: Int = 5

    interface ListItemActionListener {
        fun onItemClicked(name: String, button: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newFileButton.setOnClickListener {
            showDialog(ID)
        }
        settingsButton.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivityForResult(settingsIntent, SETTINGS_CODE)
        }
        recyclerView.apply {
            adapter = Adapter(filesMap, object : ListItemActionListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onItemClicked(name: String, button: String) {
                    if (button == "DELETE") {
                        filesMap.remove(name)
                        adapter?.notifyDataSetChanged()
                    } else if (button == "EDIT") {
                        val intent = Intent(this@MainActivity, EditActivity::class.java)
                        intent.putExtra("File", filesMap[name])
                        intent.putExtra("FileName", name)
                        startActivityForResult(intent, 2000)
                    }
                }
            })
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SETTINGS_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val sharedPref = getSharedPreferences(getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            if (data.getBooleanExtra(getString(R.string.preference_file_key), false)) {
                editor.putBoolean(getString(R.string.preference_file_key), true)
            } else {
                editor.putBoolean(getString(R.string.preference_file_key), false)
            }
            editor.apply()
        }
        if (requestCode == 2000 && resultCode == 1000 && data != null) {
            val fileName = data.getStringExtra("FileName").toString()
            val file = data.getSerializableExtra("File") as File
            filesMap.remove(fileName)
            filesMap[fileName] = file
        }
        this.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateDialog(id: Int): Dialog? {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view: View = inflater.inflate(R.layout.create_file_dialog, null)
        builder.setView(view)
                .setPositiveButton(R.string.create) { _, _ ->
                    val counter = 0
                    val fileName = view.fileNameEditText.text.toString()
                    val checkedFileName: String = validateFileName(fileName, counter)
                    filesMap[checkedFileName] = if (getPreferences(Context.MODE_PRIVATE)
                                    .getBoolean(getString(R.string.preference_file_key), false)) {
                        File(externalCacheDir, "$checkedFileName.txt")
                    } else {
                        File(filesDir, "$checkedFileName.txt")
                    }
                    this.recyclerView.adapter?.notifyDataSetChanged()
                }
                .setNegativeButton(R.string.cancel) { dialogInterface, _ -> dialogInterface.cancel() }
        return builder.create()
    }

    private fun validateFileName(fileName: String, counter: Int): String {
        var newCounter = counter
        var totalFileName = fileName;
        if (filesMap.isNotEmpty() && filesMap[fileName] != null) {
            newCounter += 1
            val newFilename = fileName + newCounter
            totalFileName = if (filesMap[newFilename] != null) {
                validateFileName(fileName, newCounter)
            } else {
                newFilename
            }
        }
        return totalFileName
    }

    class Adapter(
            private val map: Map<String, File>,
            private val listItemActionListener: ListItemActionListener?
    ) :
            RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ViewHolder(inflater, parent, listItemActionListener)
        }

        override fun getItemCount(): Int = map.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val filePair: Pair<String, File> = map.toList()[position]
            holder.bind(filePair)
        }

        class ViewHolder(
                inflater: LayoutInflater,
                parent: ViewGroup,
                private var listItemClickListener: ListItemActionListener?
        ) : RecyclerView.ViewHolder(
                inflater.inflate(
                        R.layout.file_item,
                        parent,
                        false
                )
        ) {
            private val fileName: TextView = itemView.findViewById(R.id.fileName)
            private val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteFile)

            fun bind(filePair: Pair<String, File>) {
                fileName.text = filePair.first
                deleteBtn.setOnClickListener {
                    listItemClickListener?.onItemClicked(filePair.first, "DELETE")
                }
                fileName.setOnClickListener {
                    listItemClickListener?.onItemClicked(filePair.first, "EDIT")
                }
            }
        }
    }
}