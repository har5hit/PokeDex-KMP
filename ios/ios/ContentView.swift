import SwiftUI
import iosUmbrellaModule

struct ContentView: View {
    @ObservedObject private var viewModel: KMPViewModelWrapper<
        IPokemonListViewModelUIState,
        IPokemonListViewModelUIEvent,
        IPokemonListViewModelAction
    > = KMPViewModelWrapper(
        viewmodel: DIModule.viewmodel(),
        events: { event in
            debugPrint(event)
        }
    )

    var body: some View {
        TabView {
            NavigationView {
                PokemonListView(state: viewModel.state, callBack: {action in
                    viewModel.add(action: action)
                })
                .navigationTitle("PokeDex")
            }
            .tabItem {
                Label("Native", systemImage: "iphone")
            }

            NavigationView {
                SharedComposePokemonListView(viewModel: DIModule.viewmodel())
                    .navigationTitle("PokeDex CMP")
            }
            .tabItem {
                Label("Shared CMP", systemImage: "square.stack.3d.up")
            }
        }
    }
}
