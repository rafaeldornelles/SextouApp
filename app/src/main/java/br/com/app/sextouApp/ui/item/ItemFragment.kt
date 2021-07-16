package br.com.app.sextouApp.ui.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.app.sextouApp.R
import br.com.app.sextouApp.adapter.ItemViewPagerAdapter
import br.com.app.sextouApp.databinding.FragmentItemBinding
import com.google.android.material.tabs.TabLayoutMediator

class ItemFragment : Fragment() {
    private lateinit var itemBinding: FragmentItemBinding
    val tabs = listOf("Items", "Members")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemBinding = FragmentItemBinding.inflate(inflater, container, false)
        return itemBinding.root
    }

    override fun onResume() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        super.onResume()
    }

    override fun onStop() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        itemBinding.viewPager.adapter = ItemViewPagerAdapter(this)
        TabLayoutMediator(itemBinding.tabLayout, itemBinding.viewPager){ tab, position ->
            tab.text = tabs[position]
        }.attach()
        super.onViewCreated(view, savedInstanceState)
    }

}