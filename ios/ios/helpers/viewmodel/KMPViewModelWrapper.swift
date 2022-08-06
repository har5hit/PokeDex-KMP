//
//  KMPViewModelWrapper.swift
//  ios
//
//  Created by Harshith Shetty on 03/08/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
class KMPViewModelWrapper<S: AnyObject, E: AnyObject, A: AnyObject>: ObservableObject {

    let viewmodel: IFlowViewModel
    @Published var state: S!

    init(viewmodel: IFlowViewModel, events: @escaping ((E?) -> Void)) {
        self.viewmodel = viewmodel
        self.state = viewmodel.initialState as? S
        FlowAsCallback<S>(origin: viewmodel.stateHolder).watch { newState in
            self.state = newState!
        }
        FlowAsCallback<E>(origin: viewmodel.eventHolder).watch(block: events)
    }

    func add(action: A) {
        viewmodel.add(action: action)
    }

}
