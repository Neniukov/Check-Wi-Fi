package com.spacewhale.wifitester.core.data

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class WifiSignal(val zone: Async<WifiZone> = Uninitialized) : MvRxState