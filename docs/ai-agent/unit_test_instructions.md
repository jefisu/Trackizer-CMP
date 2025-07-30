# 🤖 AI Agent Unit Test Instructions (Kotlin Multiplatform)

Use this template when generating **unit tests** for Kotlin Multiplatform projects.

This includes usage of **Kotlin Coroutines**, **Flow**, **Turbine**, and **AssertK** for assertions. Follow the structure and best practices below.

---

## ✅ Structure Guidelines

- Use `kotlin.test` annotations: `@Test`, `@BeforeTest`, `@AfterTest`

- Test method names must follow **camelCase** and be descriptive.

  **Naming Pattern:**

  ```
  shouldDoExpectedBehavior_whenSpecificCondition
  ```

  **Example:**

  ```kotlin
  fun shouldReturnError_whenRepositoryFails() { }
  ```

- All tests must follow the **AAA (Arrange - Act - Assert)** pattern, with clear section comments:

  ```kotlin
  @Test
  fun shouldEmitLoadingState_whenFetchingData() = runTest {
      // Arrange

      // Act

      // Assert
  }
  ```

- Always create a **new instance** of the class under test inside the `@BeforeTest` block.

---

## 📊 Coroutine & Flow Testing

- Use `runTest` from `kotlinx.coroutines.test`
- Use `Turbine` to collect and test Flows: [Turbine GitHub](https://github.com/cashapp/turbine)

### Example:

```kotlin
@Test
fun shouldEmitLoadingTrueThenFalse_whenFetchStartsAndEnds() = runTest {
    // Arrange

    // Act
    viewModel.state.test {
        // Assert
        assertk.assertThat(awaitItem().isLoading).isTrue()
        assertk.assertThat(awaitItem().isLoading).isFalse()
        cancelAndIgnoreRemainingEvents()
    }
}
```

---

## 🔢 Assertions with AssertK

- Use [AssertK](https://github.com/willowtreeapps/assertk) for multiplatform-compatible assertions:

```kotlin
assertThat(result).isEqualTo(expected)
assertThat(error).isNotNull()
```

Avoid libraries with JVM dependencies like JUnit or Truth.

---

## 👩‍💻 Fake Dependencies

If the component under test depends on an interface:

- Create a `Fake` implementation
- Add a method `setShouldReturnError()` to simulate error behavior

### Example:

```kotlin
class FakeSubscriptionRepository : SubscriptionRepository {
    private var shouldReturnError = false

    fun setShouldReturnError() {
        shouldReturnError = true
    }

    override suspend fun getSubscriptions(): List<Subscription> {
        if (shouldReturnError) throw Exception("Forced error")
        return listOf()
    }
}
```

---

## 📂 Test Constants

- Repeated values across tests should be stored in a shared test utility file.
- File name should follow the pattern: `ModuleNameTestConstants.kt`

### Example file: `AddSubscriptionTestConstants.kt`

```kotlin
val subServices = listOf(
    SubscriptionService(name = "Netflix", imageData = ImageData.Server(""), id = "1", color = null),
    SubscriptionService(name = "Spotify", imageData = ImageData.Server(""), id = "2", color = null),
)
```

---

## ⚖️ Example Test Class Structure

```kotlin
class AddSubscriptionViewModelTest {

    private lateinit var viewModel: AddSubscriptionViewModel
    private lateinit var fakeSubServicesRepository: FakeSubServicesRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeSubServicesRepository = FakeSubServicesRepository()
        viewModel = AddSubscriptionViewModel(
            getSubServicesUseCase = GetSubServicesUseCase(fakeSubServicesRepository),
            addSubscriptionUseCase = AddSubscriptionUseCase()
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldAddSubscriptionSuccessfully_whenInputIsValid() = runTest {
        // Arrange

        // Act

        // Assert
    }
}
```

---

Keep your tests clean, reliable, and multiplatform-safe ✨

