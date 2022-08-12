//
//  PokemonListView.swift
//  ios
//
//  Created by Harshith Shetty on 05/05/22.
//  Copyright © 2022 Harshith Shetty. All rights reserved.
//

import SwiftUI
import iosUmbrellaModule
import SwiftUI

struct PokemonListView: View {

    @ObservedObject var state: IPokemonListViewModelUIState

    var callBack: (IPokemonListViewModelAction) -> Void

    var body: some View {
        if state.loading && state.list.isEmpty {
            ProgressView()
        } else {
            List {
                ForEach(state.list) { pokemonUiModel in
                    PokemonListCell(state: pokemonUiModel)
                }
                if state.canLoadMore {
                    ProgressView()
                        .onAppear {
                            callBack(IPokemonListViewModelAction.Fetch())
                        }
                }
            }.refreshable {
                callBack(IPokemonListViewModelAction.Refresh())
            }
        }
    }
}

struct PokemonListView_Previews: PreviewProvider {
    static var previews: some View {
        PokemonListView(state: IPokemonListViewModelUIState(loading: true, list: [PokemonUiModel(name: "Balbasur", url: "https://pokeapi.co/api/v2/pokemon/1/"), PokemonUiModel(name: "Balbasur 2", url: "https://pokeapi.co/api/v2/pokemon/2/")], canLoadMore: true), callBack: { action in
            print(action)
        })
    }
}

struct PokemonListCell: View {

    @SwiftUI.State var state: PokemonUiModel

    var body: some View {
        ZStack {
            Color(hexStringToUIColor(hex: state.color))

            HStack(spacing: 12) {
                Text(state.name)

                AsyncImage(url: URL(string: state.imagePng)) { image in
                    image.resizable()
                } placeholder: {
                    ProgressView()
                }
                .aspectRatio(contentMode: ContentMode.fit)
                .frame(width: 200, height: 200)

            }
        }

    }
}

extension PokemonUiModel: Identifiable {

}

extension IPokemonListViewModelUIState: ObservableObject {

}

func hexStringToUIColor (hex: String) -> UIColor {
    var cString: String = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()

    if cString.hasPrefix("#") {
        cString.remove(at: cString.startIndex)
    }

    if (cString.count) != 6 {
        return UIColor.gray
    }

    var rgbValue: UInt64 = 0
    Scanner(string: cString).scanHexInt64(&rgbValue)

    return UIColor(
        red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
        green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
        blue: CGFloat(rgbValue & 0x0000FF) / 255.0,
        alpha: CGFloat(1.0)
    )
}
