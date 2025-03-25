package com.example.carservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class ProfileSettingsFragment : Fragment(R.layout.fragment_profile_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Находим контейнер «Выйти из профиля»
        val llLogout = view.findViewById<View>(R.id.llLogout)

        // При клике переходим на экран Auth2LoginActivity
        llLogout.setOnClickListener {
            // Открываем страницу логина
            startActivity(Intent(requireContext(), Auth2LoginActivity::class.java))
            // Завершаем текущую активность, чтобы не возвращаться назад
            requireActivity().finish()
        }
    }
}
