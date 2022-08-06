//
//  AppModule.swift
//  ios
//
//  Created by Harshith Shetty on 03/05/22.
//  Copyright © 2022 Harshith Shetty. All rights reserved.
//

import Foundation
import iosUmbrellaModule

class AppModule {
    static var dispatchers = { CoroutineDependency.init().dispatchers }()

    static var networkClientProvider = { IOSNetworkClientProvider(debug: false)}()

    static var networkExceptionMapper = { NetworkExceptionMapper() }()

    static var pokemonApi = { PokemonApi(networkClientProvider: networkClientProvider, networkExceptionMapping: networkExceptionMapper)}()

    static var database =  { PokemonDatabaseProvider(databaseDriver: PokemonDatabaseProviderFactory()).instance()}()

    static var pokemonDao = { PokemonDao(queries: database.pokemonDaoModelQueries) }()

    static var pokemonRepository = { PokemonRepository(pokemonApi: pokemonApi, pokemonDao: pokemonDao)}()

    static func viewmodel() -> PokemonListViewModel {
        return PokemonListViewModel(appCoroutineDispatchers: AppModule.dispatchers, repository: AppModule.pokemonRepository)
    }
}

let appDependencies = AppModule()
