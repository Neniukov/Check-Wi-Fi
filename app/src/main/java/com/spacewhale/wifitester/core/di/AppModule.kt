package com.spacewhale.wifitester.core.di

import com.spacewhale.wifitester.core.data.WifiSignal
import com.spacewhale.wifitester.feature.scannerWIFI.ScanWiFiViewModel
import com.spacewhale.wifitester.model.WiFiScanner
import com.spacewhale.wifitester.model.WiFiScannerManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ScanWiFiViewModel(WifiSignal(), get())}
    single<WiFiScanner> { WiFiScannerManager() }
}