package com.man.hayujek.presentation.splash

import androidx.lifecycle.lifecycleScope
import com.man.hayujek.R
import com.man.hayujek.base.BaseFragment
import com.man.hayujek.databinding.FragmentSplashBinding
import com.man.hayujek.extentions.navigateTo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashVM, FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override val viewModel: SplashVM by viewModel()

    override fun observer() {
        super.observer()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isTokenExist.collect {
                delay(2000)
                if (it.isNotEmpty()) {
                    navigateTo(this@SplashFragment, R.id.action_navigationSplash_to_navigationHome)
                } else {
                    navigateTo(this@SplashFragment, R.id.action_navigationSplash_to_navigationLogin)
                }
            }
        }
    }


}