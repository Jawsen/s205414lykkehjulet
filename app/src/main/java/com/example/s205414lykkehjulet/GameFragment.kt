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
import androidx.navigation.Navigation
import java.lang.Math
import java.lang.reflect.Constructor
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
    private var spilvundet: Boolean = false
    private var spiltabt: Boolean = false
    private var snurHjul = 0
    private var pointGivet = 0
    private var livTilbage = 5
    private var pointGemt = 0
    private var spillerPoint = 0
    private var tilfældigtFelt = ""


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
        val liv = view.findViewById<TextView>(R.id.liv)
        val displayAfhjul = view.findViewById<TextView>(R.id.displayAfhjul)
        val point = view.findViewById<TextView>(R.id.point)


        startKnap.setOnClickListener {
            startKnap.visibility = View.INVISIBLE
            drejKnap.visibility = View.VISIBLE
            vistOrd.visibility = View.VISIBLE
            displayAfhjul.visibility = View.VISIBLE
            velkommenText.visibility = View.INVISIBLE
            point.visibility = View.VISIBLE
            liv.visibility = View.VISIBLE


            startSpil()
            liv.setText(livTilbage.toString())
            vistOrd.text = dOrd
            Toast.makeText(activity, hemmeligOrd, Toast.LENGTH_SHORT).show()


        }

        drejKnap.setOnClickListener {
            gætKnap.visibility = View.VISIBLE
            drejKnap.visibility = View.INVISIBLE
            gætteOmråde.visibility = View.VISIBLE
            displayAfhjul.visibility = View.VISIBLE
            val hjulSnurrer = context?.resources?.getStringArray(R.array.point)
            tilfældigtFelt = hjulSnurrer?.random().toString()
            displayAfhjul.setText(tilfældigtFelt)
            Toast.makeText(activity, "DU HAR TRYKKET DREJ!!!!", Toast.LENGTH_SHORT).show()
            spinHjul()
            liv.setText(livTilbage.toString())
            point.setText(spillerPoint.toString())
            if (spiltabt) {
                Navigation.findNavController(view)
                    .navigate(R.id.action_gameFragment_to_loseFragment)
            }

        }
        gætKnap.setOnClickListener {
            gætKnap.visibility = View.INVISIBLE
            drejKnap.visibility = View.VISIBLE
            gætteOmråde.visibility = View.INVISIBLE
            displayAfhjul.visibility = View.INVISIBLE
            spillersGæt = gætteOmråde.text.toString().toLowerCase()
            gætteOmråde.text = null
            tjekGæt()
            tjekTabtSpil()
            liv.setText(livTilbage.toString())
            point.setText(spillerPoint.toString())
            vistOrd.text = dOrd
            println(spilvundet)
            println(hemmeligOrd)
            println(dOrd)
            if (spilvundet) {
                Navigation.findNavController(view).navigate(R.id.action_goToWin)
            }
            if (spiltabt) {
                Navigation.findNavController(view)
                    .navigate(R.id.action_gameFragment_to_loseFragment)
            }

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

    // funktion der tjekker om spillers gæt er rigtig
    private fun tjekGæt() {
        if (spillersGæt.length == 1) {
            if (spillersGæt in hemmeligOrd.toLowerCase() && spillersGæt !in dOrd) {

                rigtigGæt.add(spillersGæt)
                displayBogstavet()

                spillerPoint += pointGivet
                pointGivet = 0
                Toast.makeText(activity, "God gættet", Toast.LENGTH_SHORT).show()
                tjekVinder()


                return

            } else {
                pointGivet = 0
                livTilbage-=1
            }
        }

    }


    // funktion der displayer rigitg gættet bogstav
    private fun displayBogstavet() {
        val indexAfBogstav = hemmeligOrd.indexesOf(spillersGæt, true)

        if (indexAfBogstav.size == 1) {
            dOrd = dOrd.replaceRange(indexAfBogstav[0], indexAfBogstav[0] + 1, spillersGæt)

        } else if (indexAfBogstav.size == 2) {
            dOrd = dOrd.replaceRange(indexAfBogstav[0], indexAfBogstav[0] + 1, spillersGæt)
            dOrd = dOrd.replaceRange(indexAfBogstav[1], indexAfBogstav[1] + 1, spillersGæt)
        }


        println(dOrd)

    }

    fun tjekVinder() {
        if (hemmeligOrd.lowercase() == dOrd) {
            Toast.makeText(activity, "YAAAAY!!! du vandt", Toast.LENGTH_SHORT).show()
            spilvundet = true

        }
    }


    fun spinHjul() {
        if (tilfældigtFelt.equals("Du har får 100 point hvis du gætter rigtigt")) {
            pointGivet = 100
            //point.setText(pointGemt.toString())


        }
        if (tilfældigtFelt.equals(("Du har får 200 point hvis du gætter rigtigt"))) {
            pointGivet = 200
            //point.setText(pointGemt.toString())


        }
        if (tilfældigtFelt.equals(("Du har får 1000 point hvis du gætter rigtigt"))) {
            pointGivet = 1000
        }
        if (tilfældigtFelt.equals(("Du har får 2000 point hvis du gætter rigtigt"))) {
            pointGivet = 2000
        }
        if (tilfældigtFelt.equals(("Du har fået et ekstra liv"))){
            livTilbage +=1

        }

        if (tilfældigtFelt.equals(("Du har mistet et liv"))){
            livTilbage -= 1
            println(livTilbage)


        }

        if (tilfældigtFelt.equals(("Du er gået bankerot"))){
            spillerPoint = 0

        }


        // gør brug af .setText(point.toString())
    }
    fun tjekTabtSpil(){
            if(livTilbage == 0){
                spiltabt = true

            }
        }




    // fra internettet https://stackoverflow.com/questions/62189457/get-indexes-of-substrings-contained-in-a-string-in-kotlin-way?fbclid=IwAR0ADek0xyM8CCIagLT1C_7VdZuNZQp6utCf4FI1D_wg1yFjPPW0Bpbarzo

    fun ignoreCaseOpt(ignoreCase: Boolean) =
        if (ignoreCase) setOf(RegexOption.IGNORE_CASE) else emptySet()

    fun String?.indexesOf(pat: String, ignoreCase: Boolean = true): List<Int> =
        pat.toRegex(ignoreCaseOpt(ignoreCase)).findAll(this ?: "").map { it.range.first }.toList()


}







