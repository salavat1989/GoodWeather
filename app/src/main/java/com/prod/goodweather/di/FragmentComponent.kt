package com.prod.goodweather.di

import com.prod.goodweather.di.module.ViewModelModule
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.ui.fragment.HomeFragment
import com.prod.goodweather.ui.fragment.SearchFragment
import com.prod.goodweather.ui.fragment.SearchResultBottomSheet
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * Created by Kadyrov Salavat on 17.03.2022
 */
@Subcomponent(modules = [ViewModelModule::class])
interface FragmentComponent {

	fun inject(fragment: HomeFragment)
	fun inject(fragment: SearchFragment)
	fun inject(fragment: SearchResultBottomSheet)

	@Subcomponent.Factory
	interface Factory {
		fun create(
			@BindsInstance addressModel: AddressModel?,
		): FragmentComponent
	}
}