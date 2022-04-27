import SwiftUI

struct ContentView: View {
    
    @StateObject var viewModel: PokemonListViewModelWrapper = PokemonListViewModelWrapper(viewmodel: AppModule.viewmodel())
    
    
    var body: some View {
        NavigationView {
            
            PokemonListView(state: viewModel.state,callBack: {action in
                viewModel.add(action: action)
            }).navigationTitle("PokeDex")
            
        }
    }
}
