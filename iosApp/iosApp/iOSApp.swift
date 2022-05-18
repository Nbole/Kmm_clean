import SwiftUI
import shared

@main
struct iOSApp: App {
    let sdk = JsonApi()
        var body: some Scene {
            WindowGroup {
                ContentView(viewModel: .init(sdk: sdk))
            }
        }
}
