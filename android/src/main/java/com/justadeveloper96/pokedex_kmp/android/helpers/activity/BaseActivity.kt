package com.justadeveloper96.pokedex_kmp.android.helpers.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    private var _binding: T? = null

    protected val binding get() = _binding!!

    abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layout)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
