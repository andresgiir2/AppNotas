package com.example.notasestudiantiles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        val totalEstudiantes = intent.getIntExtra("totalEstudiantes", 0)
        val estudiantesPerdieron = intent.getIntExtra("estudiantesPerdieron", 0)

        val tvTotalEstudiantes = findViewById<TextView>(R.id.tvTotalEstudiantes)
        val tvEstudiantesPerdieron = findViewById<TextView>(R.id.tvEstudiantesPerdieron)
        val tvEstudiantesAprobaron = findViewById<TextView>(R.id.tvEstudiantesAprobaron)
        val tvPorcentajePerdieron = findViewById<TextView>(R.id.tvPorcentajePerdieron)

        val estudiantesAprobaron = totalEstudiantes - estudiantesPerdieron
        val porcentajePerdieron = if (totalEstudiantes > 0) {
            (estudiantesPerdieron.toFloat() / totalEstudiantes) * 100
        } else {
            0f
        }

        tvTotalEstudiantes.text = "Total de estudiantes: $totalEstudiantes"
        tvEstudiantesPerdieron.text = "Estudiantes que perdieron: $estudiantesPerdieron"
        tvEstudiantesAprobaron.text = "Estudiantes que aprobaron: $estudiantesAprobaron"
        tvPorcentajePerdieron.text = "Porcentaje de pérdida: %.2f%%".format(porcentajePerdieron)

        val btnNuevoRegistro = findViewById<Button>(R.id.btnNuevoRegistro)
        btnNuevoRegistro.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}