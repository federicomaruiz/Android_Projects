package com.fede.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    /*
       Declaro las variables que voy a utilizar
    * */
    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var currentWeight:Int = 70
    private var currentAge:Int = 30
    private var currentHeight: Int = 120

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubstractWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton
    private lateinit var btnSubstractAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var tvAge: TextView
    private lateinit var btnCalculate: Button

    /* Acceso desde todos lados (igual a un static en java)
    *
    * */
    companion object {
        const val IMC_KEY = "IMC_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initListeners()
        initUI()
    }

    /*
    * Funcion para iniciar componentes
    * */
    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale) // Accedo a la card mediante el id
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubstractWeight = findViewById(R.id.btnSubtractWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnSubstractAge = findViewById(R.id.btnSubtractAge)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate = findViewById(R.id.btnCalculate)

    }

    /*
    * Funcion para poner los botones , slider a la escucha preparo los eventos
    * */
    private fun initListeners() {
        viewMale.setOnClickListener {
            changeGender()
            setGenderColor()
        }  // cuando hagan click llamo a la funcion y le paso por parametro el boolean
        viewFemale.setOnClickListener {
            changeGender()
            setGenderColor()
        }

        rsHeight.addOnChangeListener { slider, value, fromUser -> // cada ves que se mueva la slider llama al metodo y me devuelve tres parametros

            val df =
                DecimalFormat("#.##") // Creo una variable que va tener 1 numero y dos decimales
            currentHeight =
                df.format(value).toInt() // creo otra variable para guardar el resultado formateado con la var df formateo el valor que me devuelve
            tvHeight.text = "$currentHeight cm" // solo me interesa el parametro value
        }

        btnSubstractWeight.setOnClickListener {  // resto uno al valor actual
            currentWeight -= 1
            setWeight()
        }
        btnPlusWeight.setOnClickListener {// sumo uno al valor actual
            currentWeight += 1
            setWeight()
        }

        btnSubstractAge.setOnClickListener{
            currentAge -= 1
            setAge()
        }

        btnPlusAge.setOnClickListener {
            currentAge += 1
            setAge()
        }

        btnCalculate.setOnClickListener {
            val result = calculateIMC()
            navigateToResult(result)
        }

    }

    /*
        Navegacion entre paginas mediante intent con extra se lleva el resultado
    * */
    private fun navigateToResult(result: Double) {
        val intent = Intent(this,resultIMC::class.java)
        intent.putExtra(IMC_KEY,result)
        startActivity(intent)
    }

    private fun calculateIMC():Double {
        val df= DecimalFormat("#.##")
        val imc = currentWeight/(currentHeight.toDouble() / 100 * currentHeight.toDouble()/100)
        return df.format(imc).toDouble()
    }

    private fun setAge(){
        tvAge.text = currentAge.toString()
    }

    private fun setWeight(){
        tvWeight.text = currentWeight.toString() // Cambio el valor del texto
    }

    /*
    * Funcion para cambiar el boolean
    * */

    private fun changeGender() {
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    /*
   * Funcion para cambiar el color del genero
   * */
    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    /*
    * Funcion que busca el color seleccionado y me devuelve un int
    * */

    private fun getBackgroundColor(isSelectComponet: Boolean): Int {

        val colorReference = if (isSelectComponet) {
            R.color.background_component_selected  // Refrencia al color
        } else {
            R.color.background_component
        }
        return ContextCompat.getColor(
            this,
            colorReference
        ) // Aqui guardo el color con su referencia

    }

    /*
    * Funcion para iniciar la interfaz llamo a la funcion para darle los colores
    * */

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }


}