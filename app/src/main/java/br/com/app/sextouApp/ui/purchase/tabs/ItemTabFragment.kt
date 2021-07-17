package br.com.app.sextouApp.ui.purchase.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.FragmentItemTabBinding
import br.com.app.sextouApp.model.Purchases
import br.com.app.sextouApp.repository.ListenerCrudFirebase
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
        registerForContextMenu(binding.itemRecyclerView);
        viewModel.purchases.observe(viewLifecycleOwner){
            adapter.purchases = it
            adapter.notifyDataSetChanged()
        }
        viewModel.listPurchases()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        val a = viewModel.purchases.value
        super.onResume()
    }

    override fun onStop() {
        viewModel.purchases.value = mutableListOf()
        super.onStop()
    }

    override fun onPurchaseItemClick(item: Purchases) {
        viewModel.setItemFormValue(item)
        findNavController().navigate(R.id.action_itemFragment_to_itemFormFragment)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_item_purchase -> viewModel.markPurchased(adapter.contextItemPosition, listenerCrudFirebase)
        }
        return super.onContextItemSelected(item)
    }

    val listenerCrudFirebase = object : ListenerCrudFirebase {
        override fun onSuccess() {
        }

        override fun onError() {
            Toast.makeText(requireContext(), getString(R.string.error_crud), Toast.LENGTH_LONG).show()
        }
    }
}