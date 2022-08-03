//
//  PokemonDatabaseProviderFactory.swift
//  ios
//
//  Created by Harshith Shetty on 03/05/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class PokemonDatabaseProviderFactory: IDatabaseDriverFactory {
    func createDriver() -> SqlDriver {
        return NativeSqliteDriver.init(schema: PokemonDatabaseCompanion.init().Schema, name: "pokemon.db", maxReaderConnections: 4)
    }

}
