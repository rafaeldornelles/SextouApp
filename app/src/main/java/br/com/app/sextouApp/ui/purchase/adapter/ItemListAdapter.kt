package br.com.app.sextouApp.ui.purchase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app.sextouApp.databinding.ItemProductBinding
import br.com.app.sextouApp.model.Purchases

class ItemListAdapter(var purchases: List<Purchases>, private val listener: OnPurchaseItemClickListener) : RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {
    private lateinit var binding: ItemProductBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bind(purchases[position])
    }

    override fun getItemCount(): Int {
        return purchases.size
    }

    inner class ItemListViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(purchase: Purchases){
            binding.purcharse = purchase
            binding.executePendingBindings()
            binding.root.setOnClickListener{ listener.onPurchaseItemClick(purchase) }
        }
    }

    interface OnPurchaseItemClickListener{
        fun onPurchaseItemClick(item: Purchases)
    }
}