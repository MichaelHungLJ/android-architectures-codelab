package com.karlpark.architecturecodelab

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karlpark.architecturecodelab.data.CounterRepositoryImpl
import com.karlpark.architecturecodelab.di.ServiceLocator
import com.karlpark.architecturecodelab.domain.CounterRepository
import com.karlpark.architecturecodelab.domain.DecrementCounterUseCase
import com.karlpark.architecturecodelab.domain.GetCounterUseCase
import com.karlpark.architecturecodelab.domain.IncrementCounterUseCase
import com.karlpark.architecturecodelab.domain.UpdateCounterUseCase
import com.karlpark.architecturecodelab.presentation.mvc.CounterController
import com.karlpark.architecturecodelab.presentation.mvi.CounterMVIReducer
import com.karlpark.architecturecodelab.presentation.mvp.CounterMvpContract
import com.karlpark.architecturecodelab.presentation.mvp.CounterMvpPresenter
import com.karlpark.architecturecodelab.presentation.mvvm.CounterMVVMViewModel
import com.karlpark.architecturecodelab.presentation.viper.CounterViperInteractor

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.registerService(
            serviceType = CounterRepository::class.java,
            serviceInstance = CounterRepositoryImpl(),
        )

        ServiceLocator.registerService(
            serviceType = IncrementCounterUseCase::class.java,
            serviceInstance = IncrementCounterUseCase(ServiceLocator.getService(CounterRepository::class.java)),
        )

        ServiceLocator.registerService(
            serviceType = DecrementCounterUseCase::class.java,
            serviceInstance = DecrementCounterUseCase(ServiceLocator.getService(CounterRepository::class.java)),
        )

        ServiceLocator.registerService(
            serviceType = UpdateCounterUseCase::class.java,
            serviceInstance = UpdateCounterUseCase(ServiceLocator.getService(CounterRepository::class.java)),
        )

        ServiceLocator.registerService(
            serviceType = GetCounterUseCase::class.java,
            serviceInstance = GetCounterUseCase(ServiceLocator.getService(CounterRepository::class.java)),
        )

        ServiceLocator.registerService(
            serviceType = ViewModelProvider.Factory::class.java,
            serviceInstance = object : ViewModelProvider.Factory {
                private val updateCounterUseCase = ServiceLocator.getService(UpdateCounterUseCase::class.java)
                private val getCounterUseCase = ServiceLocator.getService(GetCounterUseCase::class.java)

                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return when {
                        modelClass.isAssignableFrom(CounterMVVMViewModel::class.java) -> {
                            CounterMVVMViewModel(
                                updateCounterUseCase = updateCounterUseCase,
                                getCounterUseCase = getCounterUseCase
                            ) as T
                        }

                        modelClass.isAssignableFrom(CounterMVIReducer::class.java) -> {
                            CounterMVIReducer(getCounterUseCase, updateCounterUseCase) as T
                        }
                        // MVC Controller and MVP/VIPER Presenter are not ViewModels, handled separately.
                        else -> throw IllegalArgumentException("Unknown ViewModel class")
                    }
                }
            }
        )

        ServiceLocator.registerService(
            serviceType = CounterController::class.java,
            serviceInstance = CounterController(
                ServiceLocator.getService(IncrementCounterUseCase::class.java),
                ServiceLocator.getService(DecrementCounterUseCase::class.java),
                ServiceLocator.getService(GetCounterUseCase::class.java),
            ),
        )

        ServiceLocator.registerService(
            serviceType = CounterMvpContract.Presenter::class.java,
            serviceInstance = CounterMvpPresenter(
                ServiceLocator.getService(GetCounterUseCase::class.java),
                ServiceLocator.getService(UpdateCounterUseCase::class.java)
            ),
        )

        ServiceLocator.registerService(
            serviceType = CounterViperInteractor::class.java,
            serviceInstance = CounterViperInteractor(
                ServiceLocator.getService(IncrementCounterUseCase::class.java),
                ServiceLocator.getService(DecrementCounterUseCase::class.java),
                ServiceLocator.getService(GetCounterUseCase::class.java),
            ),
        )
    }
}
