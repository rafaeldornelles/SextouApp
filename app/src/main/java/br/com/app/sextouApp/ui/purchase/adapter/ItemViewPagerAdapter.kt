package br.com.app.sextouApp.ui.purchase.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.app.sextouApp.ui.purchase.PurchaseFragment
import br.com.app.sextouApp.ui.purchase.tabs.ItemTabFragment
import br.com.app.sextouApp.ui.purchase.tabs.MembersTabFragment

class ItemViewPagerAdapter(fragment: PurchaseFragment): FragmentStateAdapter(fragment) {
    private val tabCount = fragment.tabs.size

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ItemTabFragment()
            1 -> MembersTabFragment()
            else -> Fragment()
        }
    }
}