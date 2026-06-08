import SwiftUI
import iosUmbrellaModule

struct SharedComposePokemonListView: UIViewControllerRepresentable {
    let viewModel: IPokemonListViewModel

    func makeUIViewController(context: Context) -> UIViewController {
        return PokemonListComposeViewControllerKt.PokemonListComposeViewController(
            viewModel: viewModel,
            onMessage: { message in
                debugPrint(message ?? "")
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
