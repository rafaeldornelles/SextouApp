package br.com.app.sextouApp.ui.purchase.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.FragmentItemTabBinding
import br.com.app.sextouApp.model.Purchases
import br.com.app.sextouApp.ui.purchase.PurchaseViewModel
import br.com.app.sextouApp.ui.purchase.adapter.ItemListAdapter

class ItemTabFragment : Fragment(), ItemListAdapter.OnPurchaseItemClickListener {
    private lateinit var binding: FragmentItemTabBinding
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(PurchaseViewModel::class.java)
    }
    private val adapter by lazy {
        ItemListAdapter(viewModel.purchases.value?: emptyList(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemTabBinding.inflate(inflater, container, false)
        binding.itemRecyclerView.adapter = adapter
        binding.itemRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.fabItem.setOnClickListener { findNavController().navigate(R.id.action_itemFragment_to_itemFormFragment) }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.purchases.observe(viewLifecycleOwner){
            adapter.purchases = it
            adapter.notifyDataSetChanged()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        val a = viewModel.purchases.value
        super.onResume()
    }

    override fun onPurchaseItemClick(item: Purchases) {
        viewModel.setItemFormValue(item)
        findNavController().navigate(R.id.action_itemFragment_to_itemFormFragment)
    }
}