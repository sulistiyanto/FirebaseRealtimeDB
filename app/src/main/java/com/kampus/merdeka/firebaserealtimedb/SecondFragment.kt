package com.kampus.merdeka.firebaserealtimedb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kampus.merdeka.firebaserealtimedb.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args : SecondFragmentArgs by navArgs()
    private var tire: TireModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tire = args.tire
        if (tire != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.nameEditText.setText(tire?.name)
            binding.descEditText.setText(tire?.description)
        }

        val firebaseDatabaseHelper = FirebaseDatabaseHelper()
        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text
            val desc = binding.descEditText.text
            if (tire == null) {
                val tireModel = TireModel(name = name.toString(), description = desc.toString())
                firebaseDatabaseHelper.createTire("tires", tireModel) { isSuccess ->
                }
            } else {
                Log.d("KESINI id", tire?.id.toString())
                val tireModel = TireModel(id = tire?.id, name = name.toString(), description = desc.toString())
                firebaseDatabaseHelper.updateTire("tires", tireModel) {}
            }
            findNavController().popBackStack()
        }

        binding.deleteButton.setOnClickListener {
            firebaseDatabaseHelper.deleteTire("tires", tire?.id!!) {}
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}