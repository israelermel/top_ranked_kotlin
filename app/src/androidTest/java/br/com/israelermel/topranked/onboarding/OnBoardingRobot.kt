package br.com.israelermel.topranked.onboarding

import br.com.israelermel.feature_onboarding.R
import br.com.israelermel.testing_core_uitest.BaseTestRobot

fun onboarding(func: OnBoardingRobot.() -> Unit) = OnBoardingRobot()
    .apply(func)

class OnBoardingRobot : BaseTestRobot() {

    fun clickButtonOnBoarding() {
        clickButton(R.id.btn_onboarding)
    }

}