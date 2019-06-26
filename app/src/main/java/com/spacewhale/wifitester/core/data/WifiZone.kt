package com.spacewhale.wifitester.core.data

sealed class WifiZone(val levelSignal: Int, val signal: String) {
    class INDEFINITE : WifiZone(0, "INDEFINITE")
    class LOW : WifiZone(1, "LOW")
    class MODERATE : WifiZone(2, "MODERATE")
    class HIGH : WifiZone(3, "HIGH")
}