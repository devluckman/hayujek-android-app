package com.man.hayujek.presentation.register

import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.man.hayujek.R
import com.man.hayujek.base.BaseFragment
import com.man.hayujek.databinding.FragmentRegisterBinding
import com.man.hayujek.domain.event.StateEventSubscriber
import com.man.hayujek.domain.request.RequestRegister
import com.man.hayujek.extentions.gone
import com.man.hayujek.extentions.navigateTo
import com.man.hayujek.extentions.visible
import com.man.hayujek.utils.log
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<RegisterVM, FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    // region Initialize

    override val viewModel: RegisterVM by viewModel()

    override fun FragmentRegisterBinding.initialBinding() {

        edtUsername.addTextChangedListener {
            viewModel.setUserName(it.toString())
        }

        edtPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }

        btnSubmit.setOnClickListener {
            val request = RequestRegister(
                username = edtUsername.text.toString(),
                password = edtPassword.text.toString()
            )
            progressCircular.visible()
            viewModel.postRegister(request)
        }

    }

    // endregion

    // region Subscription

    override fun observer() {
        super.observer()
        viewModel.subscribeRegister(subscribeRegister())

        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { values ->
                binding.btnSubmit.isEnabled = values
            }
        }
    }

    private fun subscribeRegister() = object : StateEventSubscriber<Boolean> {
        override fun onIdle() {
            log("State : On Idle")
        }

        override fun onLoading() {
            log("State : On Loading")
            binding.progressCircular.visible()
        }

        override fun onFailure(throwable: Throwable) {
            log("State : On Failure")
            throwable.printStackTrace()
            binding.progressCircular.gone()
            showMessage(throwable.localizedMessage)
        }

        override fun onSuccess(data: Boolean) {
            log("State : On Success")
            binding.progressCircular.gone()
            if (data) {
                showMessage("Registration Success")
                navigateTo(this@RegisterFragment, R.id.action_navigationRegister_to_navigationLogin)
            }
        }
    }

    // endregion

}