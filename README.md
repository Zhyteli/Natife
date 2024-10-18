## The basic logic
The basic logic is that the user can request different Gifs, go to each one in more detail by clicking on it, and also delete elements by holding down

![strem](https://github.com/user-attachments/assets/b7af68e7-f3b5-45b2-a603-7193addc1b71)


## Main Libraries Used

The project utilizes the following main libraries:

- **Navigation:**
  - `androidx.navigation.compose` - Compose Navigation for handling navigation in Jetpack Compose.

- **Room (Database):**
  - `androidx.room.compiler` - Room compiler for annotation processing (KAPT).
  - `androidx.room.runtime` - Runtime components of Room.
  - `androidx.room.ktx` - Kotlin extensions for Room.
  - `androidx.room.paging` - Paging integration for Room.

- **Dependency Injection (Hilt):**
  - `androidx.hilt.navigation.compose` - Hilt integration with Jetpack Compose.
  - `com.google.dagger:hilt-android` - Hilt for Android dependency injection.
  - `com.google.dagger:hilt-compiler` - Hilt annotation processor (KAPT).

- **UI & Compose:**
  - `androidx.compose.material` - Material Design components for Jetpack Compose.
  - `androidx.ui` - Core UI components.

- **Networking:**
  - `retrofit2:retrofit` - Retrofit for network requests.
  - `retrofit2:converter-gson` - Gson converter for Retrofit.

- **Logging:**
  - `com.squareup.okhttp3:logging-interceptor` - OkHttp logging interceptor for network logging.

- **Image Loading (Coil):**
  - `coil-compose` - Coil image loader for Jetpack Compose.
  - `coil-gif` - Support for GIF images in Coil.

- **Paging (Pagination):**
  - `androidx.paging.runtime` - Paging 3 runtime library for handling paginated data.
  - `androidx.paging.compose` - Paging support in Jetpack Compose.
  - `accompanist-pager` - Paging support with Accompanist Pager.
