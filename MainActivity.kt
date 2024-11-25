
package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

data class Student(var name: String, var studentId: String)

class MainActivity : AppCompatActivity() {

    private lateinit var studentList: ArrayList<Student>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listView: ListView
    private var selectedIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentList = arrayListOf(
            Student("Nguyen Van A", "12345"),
            Student("Tran Thi B", "67890")
        )

        listView = findViewById(R.id.list_view_students)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            studentList.map { "${it.name} (${it.studentId})" }
        )
        listView.adapter = adapter

        findViewById<View>(R.id.btn_add_new).setOnClickListener {
            openAddStudentActivity()
        }

        registerForContextMenu(listView)

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedIndex = position
            openEditStudentActivity(studentList[position])
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        selectedIndex = info.position

        when (item.itemId) {
            R.id.edit -> {
                openEditStudentActivity(studentList[selectedIndex])
                return true
            }
            R.id.remove -> {
                studentList.removeAt(selectedIndex)
                refreshList()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun openAddStudentActivity() {
        val intent = Intent(this, EditStudentActivity::class.java)
        startActivityForResult(intent, REQUEST_ADD)
    }

    private fun openEditStudentActivity(student: Student) {
        val intent = Intent(this, EditStudentActivity::class.java)
        intent.putExtra("name", student.name)
        intent.putExtra("studentId", student.studentId)
        startActivityForResult(intent, REQUEST_EDIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val name = data.getStringExtra("name") ?: return
            val studentId = data.getStringExtra("studentId") ?: return

            if (requestCode == REQUEST_ADD) {
                studentList.add(Student(name, studentId))
            } else if (requestCode == REQUEST_EDIT && selectedIndex >= 0) {
                val student = studentList[selectedIndex]
                student.name = name
                student.studentId = studentId
            }
            refreshList()
        }
    }

    private fun refreshList() {
        adapter.clear()
        adapter.addAll(studentList.map { "${it.name} (${it.studentId})" })
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
    }
}
