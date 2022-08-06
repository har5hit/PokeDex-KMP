//
//  AppModule.swift
//  ios
//
//  Created by Harshith Shetty on 03/05/22.
//  Copyright © 2022 Harshith Shetty. All rights reserved.
//

import Foundation
import iosUmbrellaModule

class DIModule {
    static var koin = { InitKoin().invoke() }()

    static func viewmodel() -> PokemonListViewModel {
        return DIAccessorKt.getPokemonViewModel(koin: DIModule.koin)
    }
}
