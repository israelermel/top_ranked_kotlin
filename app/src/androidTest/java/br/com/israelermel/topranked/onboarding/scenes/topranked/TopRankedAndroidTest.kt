package br.com.israelermel.topranked.onboarding.scenes.topranked

import android.content.Context
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import br.com.israelermel.feature_top_ranked.scenes.topranked.TopRankedKotlinRepositoriesActivity
import br.com.israelermel.topranked.App
import com.schibsted.spain.barista.rule.BaristaRule
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.Throws

@MediumTest
class TopRankedAndroidTest {
    @get:Rule
    var baristaRule = BaristaRule.create(TopRankedKotlinRepositoriesActivity::class.java)


    @get:Rule
    var clearDatabaseRule = ClearDatabaseRule()

    lateinit var device: UiDevice
    lateinit var context: Context

    @Before
    fun setup() {
        stopKoin()
        startKoin { }
        loadKoinModules(App().getModules())

        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        context = InstrumentationRegistry.getInstrumentation().context
        launchActivityAndPressHome()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    @Throws(Exception::class)
    @AllowFlaky(attempts = 1)
    fun startOnBoarding() {
        topRanked {
            sleep(5000)
            scrollRecyclerViewTo(30)
        }
    }

    fun launchActivityAndPressHome() {
        device.pressHome()
        launchActivity()
    }

    fun launchActivity() {
        baristaRule.launchActivity()
    }
}