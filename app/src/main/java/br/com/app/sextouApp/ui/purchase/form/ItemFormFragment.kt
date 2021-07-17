package br.com.app.sextouApp.ui.purchase.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.FragmentItemFormBinding
import br.com.app.sextouApp.ui.purchase.PurchaseViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ItemFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemFormFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(PurchaseViewModel::class.java)
    }
    private lateinit var binding: FragmentItemFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemFormBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.itemFormButton.setOnClickListener { submitForm() }
        binding.lifecycleOwner = this
        return binding.root
    }

    fun submitForm(){
        if (viewModel.validateItemForm()){
            viewModel.submitItemForm()
            findNavController().navigateUp()
        }
        binding.itemFormName.error = getString(R.string.item_name_error)
    }
}