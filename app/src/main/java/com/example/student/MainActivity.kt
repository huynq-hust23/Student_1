package com.example.student

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Sample student list
        val students = mutableListOf(
            StudentModel("Nguyễn Văn An", "SV001"),
            StudentModel("Trần Thị Bảo", "SV002"),
            StudentModel("Lê Hoàng Cường", "SV003"),
            StudentModel("Phạm Thị Dung", "SV004"),
            StudentModel("Đỗ Minh Đức", "SV005"),
            StudentModel("Vũ Thị Hoa", "SV006"),
            StudentModel("Hoàng Văn Hải", "SV007"),
            StudentModel("Bùi Thị Hạnh", "SV008"),
            StudentModel("Đinh Văn Hùng", "SV009"),
            StudentModel("Nguyễn Thị Linh", "SV010")
        )

        // Initialize adapter and RecyclerView
        studentAdapter = StudentAdapter(students.toMutableList(), this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_students)
        recyclerView.adapter = studentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Handle Add new student button
        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
            dialog.setView(dialogView)

            val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
            val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)

            dialog.setPositiveButton("Add") { _, _ ->
                val name = editName.text.toString()
                val id = editId.text.toString()
                if (name.isNotEmpty() && id.isNotEmpty()) {
                    studentAdapter.addStudent(StudentModel(name, id))
                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.setNegativeButton("Cancel", null)
            dialog.show()
        }
    }
}
