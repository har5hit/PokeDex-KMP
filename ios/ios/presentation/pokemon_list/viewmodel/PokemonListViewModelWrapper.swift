//
//  PokemonListViewModelWrapper.swift
//  ios
//
//  Created by Harshith Shetty on 03/05/22.
//  Copyright © 2022 Harshith Shetty. All rights reserved.
//

import Foundation
import shared
import SwiftUI


class PokemonListViewModelWrapper:ObservableObject{
    
    let viewmodel:IPokemonListViewModel
    @Published var state : IPokemonListViewModelUIState = IPokemonListViewModelUIState(loading: false, list: [], canLoadMore: false)
    
    init(viewmodel:IPokemonListViewModel){
        self.viewmodel = viewmodel
        CommonFlow<IPokemonListViewModelUIState>(origin: viewmodel.stateHolder).watch { [self] newState in
            self.state = newState!
        }
        viewmodel.add(action: IPokemonListViewModelAction.Fetch())
    }
    
    func add(action:IPokemonListViewModelAction){
        viewmodel.add(action: action)
    }
    
}
