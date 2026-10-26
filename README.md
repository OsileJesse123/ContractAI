# ContractAI

ContractAI is an Android app that helps you draft and review contracts with a generative model. You describe the agreement you need, answer a short set of questions, and the app drafts a contract. You can also upload a PDF, get a plain-language review, and keep asking questions about that document. Accounts, drafts, reviews, and chat history stay on the device after you sign in.

## Architecture

The app is a single `:app` module. Dependencies point inward: screens and view models call use cases and repositories, and repositories talk to local storage and remote services.

```
UI (Jetpack Compose + ViewModels)
        │
        ▼
Domain (use cases)
        │
        ▼
Data (repositories)
        │
        ├── Local: Room, SQLCipher, preferences
        └── Remote: Firebase Auth, Firebase Remote Config, Gemini
```

| Layer | What it owns |
| --- | --- |
| `ui` | Compose screens, view models, UI state, theme, and shared components. Each feature screen keeps its own state. |
| `navigation` | The Compose navigation graph: sign-in, sign-up, email verification, home, drafting, review, conversation, and history. |
| `domain` | Use cases for email, name, and password validation, password strength, sign-out, and combining contract results. |
| `data` | Repositories for contracts, authentication, PDF text extraction, and review chat. Room entities and DAOs live under `data/local`. Gemini calls live in `ContractNetworkDataSource`. Firebase Auth lives in `UserAuthenticationService`. |
| `di` | Hilt modules that bind repositories, data sources, use cases, dispatchers, the database, and remote config. |
| `core` | Remote-config contract and the model settings (model name, temperature, top-k, top-p, max output tokens, safety settings). |
| `work_manager` | App Startup wiring and the sign-out worker. |

Drafting flow: choose a contract type, describe it, answer generated questions, then generate (and optionally regenerate) the contract. Review flow: upload a PDF, extract the text, request a review, then continue in a conversation stored against that review. Generated contracts, reviewed contracts, and conversation items are stored in an encrypted Room database (`contract_ai_database`). A passphrase is created on device and protected with AndroidX Security.

Prompts that drive drafting, regeneration, and review are compiled into the app in `PromptUtil`. Firebase Remote Config currently supplies model and generation settings, not the prompt text.

## Libraries and packages

| Package | Version | Role |
| --- | --- | --- |
| Kotlin | 1.9.0 | Language |
| Android Gradle Plugin | 8.2.1 | Build |
| Gradle | 8.2 | Build |
| Jetpack Compose BOM | 2023.08.00 | UI (Compose compiler extension 1.5.1) |
| Material 3 | via Compose BOM | Components and theme |
| AndroidX Core KTX | 1.13.0 | Kotlin Android extensions |
| Lifecycle | 2.7.0 | ViewModel, runtime, and Compose state collection |
| Activity Compose | 1.9.0 | Compose host activity |
| ConstraintLayout Compose | 1.0.0 | Layout |
| Hilt | 2.51.1 | Dependency injection |
| Hilt Navigation Compose | 1.2.0 | ViewModels in the navigation graph |
| Jetpack Navigation Compose | transitive via Hilt Navigation | Screen graph |
| Firebase BOM | 33.1.2 | Auth, Analytics, Remote Config |
| Play Services Auth | 20.7.0 | Google sign-in |
| Google AI client (`generativeai`) | 0.9.0 | Gemini |
| Gson | 2.10.1 | JSON parsing |
| Room | 2.6.1 | Local database |
| SQLCipher | 4.5.0 | Database encryption |
| AndroidX Security Crypto | 1.0.0 | Passphrase storage |
| WorkManager | 2.9.0 | Background sign-out |
| Hilt Work | 1.2.0 | Inject the sign-out worker |
| iText 7 | 7.1.14 | Read text from uploaded PDFs |
| App Startup | 1.1.1 | WorkManager initializer |
| JUnit | 4.13.2 | Unit test runner (scaffold only) |
| Espresso / Compose UI Test | 3.5.1 / BOM | Instrumented UI tests (scaffold only) |

The app targets `minSdk` 27, `compileSdk` 34, and `targetSdk` 34. Java and Kotlin bytecode target 1.8.

## Setup

1. Install Android Studio with a JDK that can run Android Gradle Plugin 8.2.1.
2. Clone the repo and open the project root so Gradle syncs the `:app` module.
3. Create a Firebase project and download `google-services.json` into `app/`. Enable Email/Password and Google sign-in, plus Remote Config and Analytics.
4. Add a Gemini API key to the project-root `local.properties`. The build copies `API_KEY` straight into `BuildConfig`, so the value has to be a quoted Java string:

   ```properties
   API_KEY="\"YOUR_GEMINI_API_KEY\""
   ```

5. Add release signing values. The release `signingConfig` is created on every Gradle configuration, so these keys must be present in `local.properties` or in a root `keystore.properties` file before sync succeeds:

   ```properties
   storeFile=/absolute/path/to/keystore.jks
   storePassword=your-store-password
   keyAlias=your-alias
   keyPassword=your-key-password
   ```

6. In Firebase Remote Config, add a `generative_ai_config` JSON parameter with `model_name`, `generation_config` (`temperature`, `top_k`, `top_p`, `max_output_tokens`), and `safety_settings`. The app ships a default and refreshes it from Remote Config.
7. Run the `app` configuration on a device or emulator at API 27 or higher.

`local.properties` and `keystore.properties` hold secrets. Keep them out of version control.

## Things to come

- **Tests.** The repo only has the default unit and instrumented scaffolds. Add tests for validation use cases, repository mapping, prompt response parsing, and the draft and review flows.
- **Kotlin Multiplatform.** Move use cases, repositories, and the Gemini and persistence contracts into shared modules so the same drafting and review logic can run outside this Android app.
- **Package structure.** Split the single `:app` module into clearer feature and layer packages. UI models, domain rules, and data entities are still mixed, and screens, navigation, and Hilt bindings all live in one module.
- **Prompts in Remote Config.** `PromptUtil` hardcodes the draft, regenerate, and review prompts. Remote Config already controls the model name and generation settings. Move the prompt text there so wording can change without an app release.
- **Real database migrations.** The database is at version 1 and uses destructive migration. Replace that before any schema change ships to users.
