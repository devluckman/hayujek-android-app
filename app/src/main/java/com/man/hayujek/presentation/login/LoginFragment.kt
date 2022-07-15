package com.man.hayujek.presentation.login

import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.man.hayujek.R
import com.man.hayujek.base.BaseFragment
import com.man.hayujek.databinding.FragmentLoginBinding
import com.man.hayujek.domain.event.StateEventSubscriber
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.extentions.gone
import com.man.hayujek.extentions.navigateTo
import com.man.hayujek.extentions.visible
import com.man.hayujek.utils.log
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginVM, FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    // region Initialize

    override val viewModel: LoginVM by viewModel()

    override fun FragmentLoginBinding.initialBinding() {

        edtUsername.addTextChangedListener {
            viewModel.setUserName(it.toString())
        }

        edtPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }

        btnSubmit click (::submitLogin)

        btnRegister click(::goToRegister)
    }

    // endregion

    // region Action

    private fun submitLogin() = with(binding){
        val request = RequestLogin(
            username = edtUsername.text.toString(),
            password = edtPassword.text.toString()
        )
        progressCircular.visible()
        viewModel.postLogin(request)
    }

    // endregion

    // region Subscription

    override fun observer() {
        super.observer()
        viewModel.subscribeLogin(subscribeLogin())

        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { values ->
                binding.btnSubmit.isEnabled = values
            }
        }
    }

    private fun subscribeLogin() = object : StateEventSubscriber<String> {
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

        override fun onSuccess(data: String) {
            log("State : On Success token => $data")
            binding.progressCircular.gone()
            if (data.isNotEmpty()) {
                viewModel.setToken(data)
                goToHome()
            }
        }
    }

    // endregion

    // region Route

    private fun goToHome() {
        navigateTo(this@LoginFragment, R.id.action_navigationLogin_to_navigationHome)
    }

    private fun goToRegister() {
        navigateTo(this@LoginFragment, R.id.action_navigationLogin_to_navigationRegister)
    }

    // endregion

}