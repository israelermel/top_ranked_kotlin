package br.com.israelermel.feature_onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import br.com.israelermel.feature_navigation.NavigationActions
import br.com.israelermel.feature_onboarding.databinding.OnBoardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: OnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        with(binding) {
            btnOnboarding.setOnClickListener {
                startActivity(NavigationActions.openTopRankedIntent(baseContext))
            }
        }
    }
}