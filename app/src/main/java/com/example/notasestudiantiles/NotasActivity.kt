package com.example.notasestudiantiles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NotasActivity : AppCompatActivity() {

    private var contadorEstudiantes = 0
    private var contadorEstudiantesPerdieron = 0
    private lateinit var tvEstudianteActual: TextView
    private lateinit var etNombre: EditText
    private lateinit var etNota1: EditText
    private lateinit var etNota2: EditText
    private lateinit var etNota3: EditText
    private lateinit var etNota4: EditText

    // Porcentajes de las notas
    private val porcentajeNota1 = 0.25
    private val porcentajeNota2 = 0.25
    private val porcentajeNota3 = 0.20
    private val porcentajeNota4 = 0.30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        tvEstudianteActual = findViewById(R.id.tvEstudianteActual)
        etNombre = findViewById(R.id.etNombre)
        etNota1 = findViewById(R.id.etNota1)
        etNota2 = findViewById(R.id.etNota2)
        etNota3 = findViewById(R.id.etNota3)
        etNota4 = findViewById(R.id.etNota4)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnFinalizar = findViewById<Button>(R.id.btnFinalizar)

        actualizarContadorEstudiantes()

        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                calcularNotaDefinitiva()
                limpiarCampos()
                contadorEstudiantes++
                actualizarContadorEstudiantes()
            }
        }

        btnFinalizar.setOnClickListener {
            val intent = Intent(this, ResultadosActivity::class.java)
            intent.putExtra("totalEstudiantes", contadorEstudiantes)
            intent.putExtra("estudiantesPerdieron", contadorEstudiantesPerdieron)
            startActivity(intent)
            finish()
        }
    }

    private fun actualizarContadorEstudiantes() {
        tvEstudianteActual.text = "Estudiante #${contadorEstudiantes + 1}"
    }

    private fun validarCampos(): Boolean {
        if (etNombre.text.toString().trim().isEmpty()) {
            mostrarError("Debe ingresar el nombre del estudiante")
            return false
        }

        val campos = listOf(
            Pair(etNota1, "Nota 1"),
            Pair(etNota2, "Nota 2"),
            Pair(etNota3, "Nota 3"),
            Pair(etNota4, "Nota 4")
        )

        for ((campo, nombre) in campos) {
            val valor = campo.text.toString()
            if (valor.isEmpty()) {
                mostrarError("Debe ingresar la $nombre")
                return false
            }

            try {
                val nota = valor.toFloat()
                if (nota < 0 || nota > 5) {
                    mostrarError("La $nombre debe estar entre 0 y 5")
                    return false
                }
            } catch (e: NumberFormatException) {
                mostrarError("La $nombre debe ser un número válido")
                return false
            }
        }

        return true
    }

    private fun calcularNotaDefinitiva() {
        val nota1 = etNota1.text.toString().toFloat() * porcentajeNota1
        val nota2 = etNota2.text.toString().toFloat() * porcentajeNota2
        val nota3 = etNota3.text.toString().toFloat() * porcentajeNota3
        val nota4 = etNota4.text.toString().toFloat() * porcentajeNota4

        val notaDefinitiva = nota1 + nota2 + nota3 + nota4

        if (notaDefinitiva < 3.0) {
            contadorEstudiantesPerdieron++
        }

        val mensaje = "Estudiante: ${etNombre.text}\nNota definitiva: %.2f".format(notaDefinitiva)
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun limpiarCampos() {
        etNombre.text.clear()
        etNota1.text.clear()
        etNota2.text.clear()
        etNota3.text.clear()
        etNota4.text.clear()
        etNombre.requestFocus()
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}