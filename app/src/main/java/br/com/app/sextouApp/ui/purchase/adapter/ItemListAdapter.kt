package br.com.app.sextouApp.ui.purchase.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.ItemProductBinding
import br.com.app.sextouApp.model.Purchases

class ItemListAdapter(var purchases: List<Purchases>, private val listener: OnPurchaseItemClickListener) : RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {
    private lateinit var binding: ItemProductBinding
    var contextItemPosition: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bind(purchases[position])
        holder.itemView.setOnLongClickListener{
            contextItemPosition = position
            false
        }
    }

    override fun getItemCount(): Int {
        return purchases.size
    }
    inner class ItemListViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root),
        View.OnCreateContextMenuListener {
        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        fun bind(purchase: Purchases){
            binding.purcharse = purchase
            binding.hasInfo = !purchase.info.isNullOrBlank()
            binding.executePendingBindings()
            binding.root.setOnClickListener{ listener.onPurchaseItemClick(purchase) }
            binding.root.setOnLongClickListener { binding.root.showContextMenu() }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(
                Menu.NONE, R.id.menu_item_purchase,
                Menu.NONE, v?.context?.getString(R.string.option_purchased));
        }
    }


    interface OnPurchaseItemClickListener{
        fun onPurchaseItemClick(item: Purchases)
    }

}