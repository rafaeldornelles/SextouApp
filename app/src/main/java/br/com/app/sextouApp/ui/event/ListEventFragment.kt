package br.com.app.sextouApp.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.FragmentListEventBinding
import br.com.app.sextouApp.databinding.FragmentLoginBinding
import br.com.app.sextouApp.ui.login.LoginViewModel

class ListEventFragment : Fragment() {

    private val viewModel: ListEventViewModel by lazy {
        ViewModelProvider(this).get(ListEventViewModel::class.java)
    }

    private lateinit var dataBinding : FragmentListEventBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentListEventBinding.inflate(inflater, container, false)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

}

