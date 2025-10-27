package com.example.taskapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter
    private var taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTaskTitle = findViewById<EditText>(R.id.editTaskTitle)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)

        adapter = TaskAdapter(
            onTaskClicked = { task ->
                val index = taskList.indexOfFirst { it.id == task.id }
                if (index != -1) {
                    taskList[index] = taskList[index].copy(isCompleted = !taskList[index].isCompleted)
                    adapter.submitList(taskList.toList())
                }
            },
            onDeleteClicked = { task ->
                taskList.remove(task)
                adapter.submitList(taskList.toList())
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonAdd.setOnClickListener {
            val title = editTaskTitle.text.toString().trim()
            if (title.isNotEmpty()) {
                val newTask = Task(title = title)
                taskList.add(newTask)
                adapter.submitList(taskList.toList())
                editTaskTitle.text.clear()
            } else {
                Toast.makeText(this, "Digite um título", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
