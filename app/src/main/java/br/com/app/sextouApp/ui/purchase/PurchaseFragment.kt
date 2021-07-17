package br.com.app.sextouApp.ui.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app.sextouApp.R
import br.com.app.sextouApp.ui.purchase.adapter.ItemViewPagerAdapter
import br.com.app.sextouApp.databinding.FragmentItemBinding
import com.google.android.material.tabs.TabLayoutMediator

class PurchaseFragment : Fragment() {
    private lateinit var itemBinding: FragmentItemBinding
    val tabs = listOf("Items", "Members")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemBinding = FragmentItemBinding.inflate(inflater, container, false)
        return itemBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        itemBinding.viewPager.adapter = ItemViewPagerAdapter(this)
        TabLayoutMediator(requireActivity().findViewById(R.id.tab_layout), itemBinding.viewPager){ tab, position ->
            tab.text = tabs[position]
        }.attach()
        super.onViewCreated(view, savedInstanceState)
    }

}