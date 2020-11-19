package com.manickchand.pokecards.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manickchand.pokecards.model.PokemonModel
import com.manickchand.pokecards.repository.PokeCardsRemoteSource
import com.manickchand.pokecards.repository.RetrofitInit
import com.manickchand.pokecards.utils.getPokemonColor
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val mRetrofit: Retrofit = RetrofitInit.getClient()
    private val pokeCardsRemoteSource: PokeCardsRemoteSource = mRetrofit.create(
        PokeCardsRemoteSource::class.java)

    private val pokemonLiveData = MutableLiveData<List<PokemonModel>>()
    private val errorLiveData = MutableLiveData<Boolean>()
    private val allPokemonsList = ArrayList<PokemonModel>()

    fun getPokemonLiveData() = pokemonLiveData as LiveData<List<PokemonModel>>
    fun getErrorLiveData() = errorLiveData as LiveData<Boolean>

    fun fetchPokemons(context: Context) {

        viewModelScope.launch {
            try {
                var response = pokeCardsRemoteSource.getPokemons()
                setAllPokemonsList( context, response)
                pokemonLiveData.value = allPokemonsList
                errorLiveData.value = false
            }catch (e: Exception){
                errorLiveData.value = true
            }

        }
    }

    private fun setAllPokemonsList(context: Context, pokemons: List<PokemonModel>){
        allPokemonsList.clear()
        allPokemonsList.addAll(pokemons)
        setColorPokemons(context)
    }

    fun filterList(filterStr: String?){
        val pokemonsFiltered = filterStr?.run {
            allPokemonsList.filter { it.name.toLowerCase().contains(filterStr.toLowerCase()) }
        } ?: allPokemonsList
        pokemonLiveData.value = pokemonsFiltered
    }

    private fun setColorPokemons(context: Context){
        allPokemonsList.forEach { it.color = getPokemonColor(context, it.typeofpokemon.first()) }
    }
}