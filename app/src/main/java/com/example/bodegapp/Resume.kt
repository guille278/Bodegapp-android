package com.example.bodegapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bodegapp.databinding.FragmentResumeBinding
import org.json.JSONObject
import java.text.NumberFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

private const val STORAGE_ID = "storage_id"
private const val PLAN_ID = "plan_id"


class Resume : Fragment() {
    private var storageId: String? = null
    private var planId: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storageId = it.getString(STORAGE_ID)
            planId = it.getString(PLAN_ID)
        }
    }

    private var _binding: FragmentResumeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResumeBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().baseContext.getSharedPreferences("auth", Context.MODE_PRIVATE)
        //Toast.makeText(context, arguments.toString(), Toast.LENGTH_SHORT).show()
        val token = sharedPreferences.getString("auth", "")

        val queque = Volley.newRequestQueue(context)

        binding.resumeCard.isChecked = true
        val request = object : JsonObjectRequest(
            "${getString(R.string.api_url)}/checkout/${arguments?.getString("storage_id")}/${
                arguments?.getString(
                    "plan_id"
                )
            }",
            {
                if (it.getBoolean("success")) {
                    binding.resumeStorageId.text =
                        "ID: #${it.getJSONObject("storage").getString("id")}"
                    binding.resumeTitle.text = it.getJSONObject("storage").getString("title")
                    binding.resumeDescription.text =
                        it.getJSONObject("storage").getString("description")

                    binding.resumePrice.text = "Pago mensual ${
                        NumberFormat.getCurrencyInstance(Locale.US)
                            .format(it.getJSONObject("storage").getDouble("price"))
                    }"
                    when (arguments?.getString("plan_id")) {
                        "0" -> binding.resumeCardPlanTitle.text = "1 Mes"
                        "1" -> binding.resumeCardPlanTitle.text = "3 Meses"
                        "2" -> binding.resumeCardPlanTitle.text = "6 Meses"
                        "3" -> binding.resumeCardPlanTitle.text = "12 Meses"
                    }

                    binding.resumeStartDate.text = "Inicio de plan: ${
                        DateTimeFormatter.ofPattern("dd/MM/yyyy").format(
                            OffsetDateTime.ofInstant(
                                Instant.parse(it.getString("start_date")),
                                ZoneId.systemDefault()
                            )
                        )
                    }"
                    binding.resumeEndDate.text = "Fin de plan: ${
                        DateTimeFormatter.ofPattern("dd/MM/yyyy").format(
                            OffsetDateTime.ofInstant(
                                Instant.parse(it.getString("end_date")),
                                ZoneId.systemDefault()
                            )
                        )
                    }"
                } else {
                    val alertDialog = AlertDialog.Builder(context)
                    alertDialog.setTitle("Error")
                    alertDialog.setMessage(it.getString("errors"))
                    alertDialog.setPositiveButton("Aceptar",
                        { dialogInterface, i -> this.activity?.finish() })
                    alertDialog.create().show()
                }
            }, {
                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        queque.add(request)

        binding.next.setOnClickListener {
            if (binding.resumeTerms.isChecked) {
                val data =  JSONObject()
                data.put("storage_id", arguments?.getString("storage_id"))
                data.put("plan_id", arguments?.getString("plan_id"))
                val request = object : JsonObjectRequest(
                    Method.POST,
                    "${getString(R.string.api_url)}/checkout",
                    data,
                    {
                        if (it.getBoolean("success")) {
                            findNavController().navigate(R.id.action_resume_to_confirmation)
                        } else {
                            findNavController().navigate(R.id.action_resume_to_error)
                        }
                    },
                    {
                        findNavController().navigate(R.id.action_resume_to_error)
                    }){
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "application/json"
                        headers["Authorization"] = "Bearer $token"
                        return headers
                    }
                }
                queque.add(request)
            }else{
                binding.resumeTerms.isErrorShown = true
                val alertDialog = context?.let { it1 -> androidx.appcompat.app.AlertDialog.Builder(it1) }
                alertDialog?.setTitle("Información")
                alertDialog?.setMessage("Debes aceptar los terminos y condiciones")
                alertDialog?.setPositiveButton("Aceptar", null)
                alertDialog?.create()?.show()
            }

        }

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(storageId: String, planId: String) =
            Resume().apply {
                arguments = Bundle().apply {
                    putString(STORAGE_ID, storageId)
                    putString(PLAN_ID, planId)
                }
            }
    }
}