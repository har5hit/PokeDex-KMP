//
//  AppModule.swift
//  ios
//
//  Created by Harshith Shetty on 03/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class AppModule{
    static let dispatchers = CoroutineDependency.init().dispatchers
    
    static let networkGateway = IOSNetworkGateway(debug: false)
    
    static let pokemonApi = PokemonApi(gateway: networkGateway)
    
    static let database = PokemonDatabaseProvider(databaseDriver: PokemonDatabaseProviderFactory()).instance()
    
    static let pokemonDao = PokemonDao(queries: database.pokemonDaoModelQueries)
    
    
    static let pokemonRepository = PokemonRepository(pokemonApi: pokemonApi, pokemonDao: pokemonDao)


    static func viewmodel()-> PokemonListViewModel{
        return PokemonListViewModel(appCoroutineDispatchers: AppModule.dispatchers, repository: AppModule.pokemonRepository)
    }
}

let appDependencies = AppModule()
