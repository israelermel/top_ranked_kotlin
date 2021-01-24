package br.com.israelermel.testing_core_uitest

import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import java.util.concurrent.TimeUnit

inline fun given(block: () -> Unit) = block()
inline fun whenever(block: () -> Unit) = block()
inline fun then(block: () -> Unit) = block()

open class BaseTestRobot {
    fun fillEditText(resId: Int, text: String) {
        writeTo(resId, text)
    }

    fun clickButton(resId: Int) {
        clickOn(resId)
    }

    fun clickContainer(resId: Int) {
        clickOn(resId)
    }

    fun sleep(millis: Long) {
        BaristaSleepInteractions.sleep(millis)
    }

    fun sleep(units: Long, timeunit: TimeUnit) {
        BaristaSleepInteractions.sleep(units, timeunit)
    }

//    fun validCalledIntent(context: Context, activity: KClass<*>) {
//        val createRelative =
//            ComponentName.createRelative(context, activity.java.name)
//
//        val component = Intents.getIntents()[Intents.getIntents().size - 1].component
//
//        component?.let {
//            if (createRelative.className.orEmpty() != it.className.orEmpty()) {
//                throw BaristaException(
//                    "Intent ${activity.java.name} not found",
//                    Throwable("Intent ${activity.java.name} not found")
//                )
//            }
//        }
//    }


//    fun clickStartOnBoarding() {
//        clickButton(R.id.btn_onboarding)
//    }

//    fun showConnectionError() {
//        BaristaVisibilityAssertions.assertDisplayed(
//            br.com.unicred.associacao.ui_components_library_android_core.R.id.ubr_error_bottom_sheet_txt_error_title,
//            "Sem conexão :("
//        )
//    }
//
//    fun existsValidationError() {
//        BaristaVisibilityAssertions.assertDisplayed(br.com.unicred.associacao.ui_components_library_android_core.R.id.txt_message_error_ob_compound_edittext)
//    }
//
//    fun noExistsValidationErrorShown() {
//        BaristaVisibilityAssertions.assertNotDisplayed(br.com.unicred.associacao.ui_components_library_android_core.R.id.txt_message_error_ob_compound_edittext)
//    }


}