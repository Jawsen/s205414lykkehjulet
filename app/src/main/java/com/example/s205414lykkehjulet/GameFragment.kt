package com.example.s205414lykkehjulet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.lang.Math
import kotlin.random.Random


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var hemmeligOrd = ""
    private var spillersGæt = ""
    // ord der bliver displayet i vistOrd
    private var dOrd = ""
    private val rigtigGæt = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val startKnap = view.findViewById<Button>(R.id.startSpil)
        val velkommenText = view.findViewById<TextView>(R.id.Velkommen)
        val drejKnap = view.findViewById<Button>(R.id.button3)
        val gætKnap = view.findViewById<Button>(R.id.gætKnap)
        val gætteOmråde = view.findViewById<EditText>(R.id.gætteOmråde)
        val vistOrd = view.findViewById<TextView>(R.id.visOrd)



        startKnap.setOnClickListener {
            startKnap.visibility = View.INVISIBLE
            drejKnap.visibility = View.VISIBLE
            gætKnap.visibility = View.VISIBLE
            vistOrd.visibility = View.VISIBLE
            gætteOmråde.visibility = View.VISIBLE


            startSpil()
            vistOrd.text = dOrd


        }

        drejKnap.setOnClickListener {

            Toast.makeText(activity, "DU HAR TRYKKET DREJ!!!!", Toast.LENGTH_SHORT).show()


        }
        gætKnap.setOnClickListener {
            spillersGæt = gætteOmråde.text.toString().toLowerCase()
            gætteOmråde.text = null



            Toast.makeText(activity, "Du har trykket på gæt", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    // Starter spillet..
    private fun startSpil() {
        val landearr = context?.resources?.getStringArray(R.array.lande_array)
        if (landearr != null) {
            hemmeligOrd = landearr.random()
        }
        dOrd = ""
        repeat(hemmeligOrd.length) {
            dOrd += "_"
        }


    }

    private fun tjekGæt() {
        if (spillersGæt.length == 1) {
            if (spillersGæt in hemmeligOrd.toLowerCase()) {

                hemmeligOrd.add(spillersGæt)


                Toast.makeText(activity, "Good guess!", Toast.LENGTH_SHORT).show()
                checkWin()
                return
            }


        }


    }


