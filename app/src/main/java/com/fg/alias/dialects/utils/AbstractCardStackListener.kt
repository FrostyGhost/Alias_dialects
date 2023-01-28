package com.fg.alias.dialects.utils

import android.view.View
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

open class AbstractCardStackListener : CardStackListener {
    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {

    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

}