package com.man.hayujek.base

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.koin.androidx.scope.ScopeFragment
import java.lang.reflect.ParameterizedType

/**
 *
 * Created by Lukmanul Hakim on  13/07/22
 * devs.lukman@gmail.com
 */
//typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class BaseFragment<VM : BaseViewModel, T : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T
) : ScopeFragment() {

    private lateinit var _binding: T
    val binding get() = _binding
    abstract val viewModel : VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.initialBinding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProcess()
        observer()
    }

    open fun T.initialBinding() {}

    open fun initProcess() {}

    open fun observer() {}

    open fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    infix fun View.click(callFunc: () -> Unit) {
        setOnClickListener { callFunc() }
    }
}