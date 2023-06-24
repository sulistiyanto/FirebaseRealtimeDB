package com.kampus.merdeka.firebaserealtimedb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kampus.merdeka.firebaserealtimedb.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TireListAdapter(mutableListOf()) { tire ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(tire)
            findNavController().navigate(action)
        }
        binding.dataRecyclerView.adapter = adapter
        val firebaseDatabaseHelper = FirebaseDatabaseHelper()
        firebaseDatabaseHelper.readTires("tires") { tireList ->
            if (tireList.isNotEmpty()) {
                binding.illustrationImageView.visibility = View.GONE
                binding.emptyTextView.visibility = View.GONE
            }
            adapter.updateData(tireList)
        }
        binding.addFAB.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}