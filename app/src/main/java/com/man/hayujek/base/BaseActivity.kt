package com.man.hayujek.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 *
 * Created by Lukmanul Hakim on  13/07/22
 * devs.lukman@gmail.com
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    private var _binding: ViewBinding? = null
    protected val binding: T
        get() = _binding as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingClass = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java)
        val invokeLayout = inflateMethod.invoke(null, layoutInflater) as T
        setContentView(invokeLayout.root)

        invokeLayout.also {
            _binding = it
        }
    }

    override fun onStart() {
        super.onStart()
        binding.initialBinding()
    }

    open fun T.initialBinding() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}