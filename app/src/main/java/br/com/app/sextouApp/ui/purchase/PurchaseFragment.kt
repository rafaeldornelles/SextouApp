package br.com.app.sextouApp.ui.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app.sextouApp.MainActivity
import br.com.app.sextouApp.R
import br.com.app.sextouApp.ui.purchase.adapter.ItemViewPagerAdapter
import br.com.app.sextouApp.databinding.FragmentItemBinding
import com.google.android.material.tabs.TabLayoutMediator

class PurchaseFragment : Fragment() {
    private lateinit var itemBinding: FragmentItemBinding
    val tabs by lazy { listOf(getString(R.string.tab_items_title), getString(R.string.tab_members_title)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemBinding = FragmentItemBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).showTabLayout()
        return itemBinding.root
    }

    override fun onResume() {
        (requireActivity() as MainActivity).showTabLayout()
        return super.onResume()
    }

    override fun onStop() {
        (requireActivity() as MainActivity).hideTabLayout()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        itemBinding.viewPager.adapter = ItemViewPagerAdapter(this)
        TabLayoutMediator(requireActivity().findViewById(R.id.tab_layout), itemBinding.viewPager){ tab, position ->
            tab.text = tabs[position]
        }.attach()
        super.onViewCreated(view, savedInstanceState)
    }

}