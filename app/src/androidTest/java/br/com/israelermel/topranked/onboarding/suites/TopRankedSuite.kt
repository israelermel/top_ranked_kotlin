package br.com.israelermel.topranked.onboarding.suites

import br.com.israelermel.topranked.onboarding.scenes.onboarding.OnBoardingAndroidTest
import br.com.israelermel.topranked.onboarding.scenes.onboarding.OnBoardingRobot
import br.com.israelermel.topranked.onboarding.scenes.topranked.TopRankedAndroidTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    OnBoardingAndroidTest::class,
    TopRankedAndroidTest::class
)
class TopRankedSuite