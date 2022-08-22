package com.pomonext.pomonext.screens.home

import androidx.lifecycle.ViewModel
import com.pomonext.pomonext.repository.PomoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: PomoRepository) : ViewModel() {
}