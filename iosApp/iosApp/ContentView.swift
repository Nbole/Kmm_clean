import SwiftUI
import shared

struct RocketLaunchRow: View {

    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("Launch name:")
            }
            Spacer()
        }
    }
}

struct ContentView: View {

  @ObservedObject private(set) var viewModel: ViewModel
    enum LoadableLaunches {
            case loading
            case result([PreviewMovie])
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
            print(launchet)
            return AnyView(
                List {
                    ForEach(launchet, id: \.id) { movie in
                        HStack {
                            AsyncImage(
                                url: URL(
                                string: "https://image.tmdb.org/t/p/original" + (movie.posterPath ?? "")
                            ),
                            content: { image in
                            image.resizable()
                                 .aspectRatio(contentMode: .fit)
                                 .frame(width: 100, height: 100)
                            },
                            placeholder: {
                            ProgressView()
                            }
                            )
                            Text(movie.title ?? "")
                        }
                    }.navigationTitle("Ultimos estrenos")
                }
            )

        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
    
    
    class ViewModel: ObservableObject {
        var sdk: MovieApi
        @Published var launches = LoadableLaunches.loading

    
        init(sdk: MovieApi) {
            self.sdk = sdk
            print(sdk)
            self.loadLaunches()
        }

        func loadLaunches() {
            self.launches = .loading
    
            sdk.getLatestMovies(completionHandler: { launch, error in
                print(launch as Any)
                print(error as Any)
                if(launch != nil) {
                    self.launches = .result(launch ?? [])
                } else {
                    self.launches = .error("go fuck yourself")
                }
            }
            )
        }
    }
}
