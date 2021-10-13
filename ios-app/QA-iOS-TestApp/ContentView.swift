import SwiftUI

struct ContentView: View {
    @State private var age = ""
    @State private var textValue = "Insert the age and press the button"
    
    
    var body: some View {
        VStack(content: {
            Text(textValue)
                .padding()
            TextField("", text: $age)
                .keyboardType(.numberPad)
                .background(Color.gray)
                .padding(.horizontal, 100)
            Button("AM I OLD ENOUGH TO DRINK?") {
                textValue = ""
                guard let url = URL(string: "https://age-verifier.herokuapp.com/age/verifier") else { return }
                var urlRequest = URLRequest(url: url)
                urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
                urlRequest.httpMethod = "POST"
                let ageDictionary = ["age": age]
                urlRequest.httpBody = try? JSONSerialization.data(
                    withJSONObject: ageDictionary,
                    options: .prettyPrinted)
                
                URLSession.shared.dataTask(with: urlRequest) { data, response, error in
                    guard let data = data else { return }
                    guard let json = try? JSONSerialization.jsonObject(with: data, options: .allowFragments) as? [String: Bool?],
                          let isValid = json["isValid"] as? Bool else {
                        print("Not valid")
                        return
                    }
                    textValue = isValid == true ? "You can drink 🎉" : "You can't drink yet :("
                }
                .resume()
            }
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
