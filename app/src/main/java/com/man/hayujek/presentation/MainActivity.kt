package com.man.hayujek.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.man.hayujek.R
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.scope.ScopeActivity

class MainActivity : ScopeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}