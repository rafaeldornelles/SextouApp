package br.com.app.sextouApp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.app.sextouApp.ui.item.ItemFragment
import br.com.app.sextouApp.ui.login.LoginFragment

class ItemViewPagerAdapter(fragment: ItemFragment): FragmentStateAdapter(fragment) {
    private val tabCount = fragment.tabs.size

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return LoginFragment()
    }
}