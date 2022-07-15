package com.man.hayujek.presentation.home

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.man.hayujek.R
import com.man.hayujek.base.BaseFragment
import com.man.hayujek.databinding.FragmentHomeBinding
import com.man.hayujek.domain.event.StateEventSubscriber
import com.man.hayujek.domain.model.UserModel
import com.man.hayujek.extentions.navigateTo
import com.man.hayujek.utils.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<HomeVM, FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    // region Initialize

    override val viewModel: HomeVM by viewModel()

    override fun FragmentHomeBinding.initialBinding() {
        btnLogOut click (::logOut)
    }

    // endregion

    // region Action

    private fun logOut() {
        viewModel.logout()
        lifecycleScope.launch {
            delay(2000)
            navigateTo(this@HomeFragment, R.id.action_navigationHome_to_navigationLogin)
        }
    }

    // endregion

    // region Subscription

    override fun initProcess() {
        super.initProcess()
        viewModel.getProfile()
    }

    override fun observer() {
        super.observer()
        viewModel.subscribeUser(subscribeUser())
    }

    private fun subscribeUser() = object : StateEventSubscriber<UserModel> {
        override fun onIdle() {
            log("On State : Idle")
        }

        override fun onLoading() {
            log("On State : Loading")
            Log.d(HomeFragment::class.java.simpleName, "On State : Loading")
        }

        override fun onFailure(throwable: Throwable) {
            log("On State : Failure")
            showMessage(throwable.message)
        }

        override fun onSuccess(data: UserModel) {
            binding.tvName.text = String.format("Wellcome %s", data.name)
        }
    }


    // endregion
}