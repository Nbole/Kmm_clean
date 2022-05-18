import SwiftUI
import shared

struct RocketLaunchRow: View {
    var jsonMessage: JsonMessage

    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("Launch name: \(jsonMessage)")
            }
            Spacer()
        }
    }
}

struct ContentView: View {
    
  @ObservedObject private(set) var viewModel: ViewModel
    enum LoadableLaunches {
            case loading
            case result(String)
            case error(String)
        }
    
    var body: some View {
        NavigationView {
            listView()
        }
    }

    private func listView() -> AnyView {
        switch viewModel.launches {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))

        case .result(let launchet):
            return AnyView(
                Text(launchet)
            )

        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
    
    
    class ViewModel: ObservableObject {
        let sdk: JsonApi
        @Published var launches = LoadableLaunches.loading

        init(sdk: JsonApi) {
            self.sdk = sdk
            self.loadLaunches()
        }

        func loadLaunches() {
            self.launches = .loading
            sdk.getLatestMovies(completionHandler: { launch, error in
                if(launch != nil) {
                    self.launches = .result("\(launch)")
                } else {
                    self.launches = .error("go fuck yourself")
                    
                }
            }
            )
        }
    }
}
