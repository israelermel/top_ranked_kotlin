package br.com.israelermel.feature_navigation

import android.content.Context
import android.content.Intent

object NavigationActions {
    fun openTopRankedIntent(context: Context) =
        internalIntent(context, "action.topranked.open")

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)

//    fun openDashboardIntent(userId: String) =
//        Intent(context, "action.dashboard.open")
//            .putExtra(EXTRA_USER, UserArgs(userId))
//
//    activity.startActivity(Actions.openDashboardIntent("userId"))
}