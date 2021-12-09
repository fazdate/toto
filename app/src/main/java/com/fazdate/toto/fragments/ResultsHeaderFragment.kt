package com.fazdate.toto.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.fazdate.toto.R
import com.fazdate.toto.databinding.FragmentHeaderBinding

class ResultsHeaderFragment : Fragment() {

    companion object {
        fun newInstance(): ResultsHeaderFragment {
            return ResultsHeaderFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHeaderBinding  = DataBindingUtil.inflate(inflater, R.layout.fragment_header, container, false)
        val view = binding.root
        binding.setHeaderText("Results")
        return view
    }
}