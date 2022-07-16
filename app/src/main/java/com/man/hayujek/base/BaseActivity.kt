package com.man.hayujek.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import org.koin.androidx.scope.ScopeActivity
import java.lang.reflect.ParameterizedType

/**
 *
 * Created by Lukmanul Hakim on  13/07/22
 * devs.lukman@gmail.com
 */
abstract class BaseActivity<T : ViewBinding>(
    private val inflate: (LayoutInflater) -> T
) : ScopeActivity() {

    private lateinit var _binding: T
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(_binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.initialBinding()
    }

    open fun T.initialBinding() {}
}