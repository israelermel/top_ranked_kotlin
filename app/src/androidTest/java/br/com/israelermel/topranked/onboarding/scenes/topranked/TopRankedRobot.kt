package br.com.israelermel.topranked.onboarding.scenes.topranked

import br.com.israelermel.feature_top_ranked.R
import br.com.israelermel.topranked.onboarding.robotcore.BaseTestRobot

fun topRanked(func: TopRankedRobot.() -> Unit) = TopRankedRobot()
    .apply(func)

open class TopRankedRobot : BaseTestRobot() {

    fun scrollRecyclerViewTo(position: Int) {
        baseScrollTo(R.id.repo_top_ranked_list, position)
    }

}