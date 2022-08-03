import SwiftUI
import shared

struct ContentView: View {

    @StateObject var viewModel = KMPViewModelWrapper
    <IPokemonListViewModelUIState,
     IPokemonListViewModelUIEvent,
     IPokemonListViewModelAction>(viewmodel: AppModule.viewmodel(), events: { event in
        // handle one off events
        debugPrint(event)
    })

    var body: some View {
        NavigationView {
            if viewModel.state==nil {
                ProgressView()
            } else {
            PokemonListView(state: viewModel.state!, callBack: {action in
                viewModel.add(action: action)
            }).navigationTitle("PokeDex")
            }
        }
    }
}
