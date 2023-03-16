package com.example.bodegapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bodegapp.databinding.FragmentPlanBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val STORAGE_ID = "storage_id"

/**
 * A simple [Fragment] subclass.
 * Use the [Plan.newInstance] factory method to
 * create an instance of this fragment.
 */
class Plan : Fragment() {
    // TODO: Rename and change types of parameters
    private var storageId: String? = null
    lateinit var planId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storageId = it.getString(STORAGE_ID)
        }
    }

    private var _binding: FragmentPlanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)


        val adapter = PlanAdapter(resources.getStringArray(R.array.plan_list))
        binding.rvPlans.adapter = adapter
        binding.rvPlans.layoutManager = LinearLayoutManager(context)

        binding.next.setOnClickListener {
            if (adapter.getPlanId() != "") {
                planId = adapter.getPlanId()
                val args = Bundle()
                args.putString("storage_id", storageId)
                args.putString("plan_id", planId)
                findNavController().navigate(R.id.action_plan_to_resume, args)
            } else {
                val alertDialog = context?.let { it1 -> AlertDialog.Builder(it1) }
                alertDialog?.setTitle("Información")
                alertDialog?.setMessage("Selecciona un plan para continuar")
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Plan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(storageId: String) =
            Plan().apply {
                arguments = Bundle().apply {
                    putString(STORAGE_ID, storageId)
                }
            }
    }
}