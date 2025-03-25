package com.example.carservice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Находим контейнер с аватаркой, именем, почтой и стрелочкой
        val llUserBlock = view.findViewById<View>(R.id.llUserBlock)

        // При нажатии переключаем на фрагмент профиля
        llUserBlock.setOnClickListener {
            // Создаем экземпляр фрагмента настроек профиля
            val profileFragment = ProfileSettingsFragment()
            // Заменяем текущий фрагмент на ProfileSettingsFragment и добавляем в back stack
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, profileFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
