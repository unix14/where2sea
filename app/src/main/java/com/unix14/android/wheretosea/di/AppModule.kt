package com.monkeytech.playform.di

import com.unix14.android.wheretosea.features.add_report.add_report.AddReportViewModel
import com.unix14.android.wheretosea.features.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

//    single { UserRepository( get()) }
//
//    viewModel { SplashViewModel(get(),get()) }
//
//    //SignIn viewModels
//    viewModel { LoginViewModel(get()) }
//    viewModel { PhoneVerificationViewModel() }
//
//    //Signup viewModels
//    viewModel { SignUpViewModel(get()) }
//    viewModel { EnterNameViewModel() }
//    viewModel { AddProfilePhotoViewModel() }
//    viewModel { InsertDateViewModel() }
//    viewModel { SelectGenderViewModel() }
//    viewModel { InsertHeightViewModel() }
//    viewModel { SelectTeamViewModel() }
//    viewModel { SelectCityViewModel() }
//    viewModel { SelectDominantLegViewModel() }
//
//    //Main
    viewModel { MainViewModel(get()) }
//    viewModel { ProfileViewModel() }

    //Add report Activtiy
    viewModel { AddReportViewModel() }

//    viewModel { SettingsViewModel(get()) }
}