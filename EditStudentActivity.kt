
package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_student)

        val nameInput = findViewById<EditText>(R.id.edit_student_name)
        val idInput = findViewById<EditText>(R.id.edit_student_id)
        val saveButton = findViewById<Button>(R.id.btn_save)

        val name = intent.getStringExtra("name")
        val studentId = intent.getStringExtra("studentId")
        nameInput.setText(name)
        idInput.setText(studentId)

        saveButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("name", nameInput.text.toString())
            resultIntent.putExtra("studentId", idInput.text.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
