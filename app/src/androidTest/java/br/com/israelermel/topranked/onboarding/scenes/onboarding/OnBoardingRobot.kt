package br.com.israelermel.topranked.onboarding.scenes.onboarding

import br.com.israelermel.feature_onboarding.R
import br.com.israelermel.topranked.onboarding.robotcore.BaseTestRobot

fun onboarding(func: OnBoardingRobot.() -> Unit) = OnBoardingRobot()
    .apply(func)

open class OnBoardingRobot : BaseTestRobot() {

    fun clickButtonOnBoarding() {
        clickButton(R.id.btn_onboarding)
    }

}