package com.example.student

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(
    private val students: MutableList<StudentModel>, // Mutable for dynamic updates
    private val context: Context
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    // ViewHolder class to bind views
    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_student_item, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        // Bind student data to UI
        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId

        // Handle Edit Action
        holder.imageEdit.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_student, null)
            dialog.setView(dialogView)

            val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
            val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)

            editName.setText(student.studentName)
            editId.setText(student.studentId)

            dialog.setPositiveButton("Save") { _, _ ->
                val newName = editName.text.toString()
                val newId = editId.text.toString()
                if (newName.isNotEmpty() && newId.isNotEmpty()) {
                    student.studentName = newName
                    student.studentId = newId
                    notifyItemChanged(position)
                } else {
                    Toast.makeText(context, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.setNegativeButton("Cancel", null)
            dialog.show()
        }

        // Handle Delete Action
        holder.imageRemove.setOnClickListener {
            val removedStudent = students[position]

            AlertDialog.Builder(context)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete ${removedStudent.studentName}?")
                .setPositiveButton("Delete") { _, _ ->
                    students.removeAt(position)
                    notifyItemRemoved(position)

                    // Show Snackbar for Undo
                    Snackbar.make(holder.itemView, "${removedStudent.studentName} deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            students.add(position, removedStudent)
                            notifyItemInserted(position)
                        }.show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    // Add new student
    fun addStudent(student: StudentModel) {
        students.add(student)
        notifyItemInserted(students.size - 1)
    }
}
