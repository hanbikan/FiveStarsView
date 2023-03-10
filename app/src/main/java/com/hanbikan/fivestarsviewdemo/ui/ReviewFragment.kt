package com.hanbikan.fivestarsviewdemo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hanbikan.fivestarsviewdemo.databinding.FragmentReviewBinding

private const val TAG = "ReviewFragment"

class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this
            it.vm = viewModel
        }
        binding.fiveStarsView.addOnChangeStarRatingListener {
            Log.d(TAG, it.toString())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}