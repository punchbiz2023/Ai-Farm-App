# BYLD Wealth — Android Intern Assignment

## About
A portfolio tracking app for HNI clients built with Kotlin + Jetpack Compose.
Variant B: Allocation Chart implemented.

## How to Run
1. Clone the repo: `git clone <your-repo-url>`
2. Open in Android Studio Hedgehog or newer
3. Wait for Gradle sync to complete
4. Connect an Android device (API 26+) with USB debugging ON
5. Click the green Run button ▶

## Run Tests
In Android Studio: Right click `com.Uma.byldwealth (test)` → Run Tests
Or use: `./gradlew test`

## Data Source
Bundled JSON fixtures in `src/main/assets/` folder.
Reason: Simplest approach, works offline, no API keys needed, always reliable.

## Architecture
- MVVM pattern
- Repository interface with JsonHoldingsRepository implementation
- Hilt for dependency injection
- Room ready for caching
- Jetpack Navigation Compose for screen navigation

## Variant B — Allocation Chart
Shows a donut chart on the detail screen displaying the holding's
weight in the total portfolio.
Formula: weight = (currentPrice × quantity) / totalPortfolioValue × 100
Implemented using Compose Canvas — no third party library needed.

## What I'd Do With 2 More Days
- Add pull-to-refresh with SwipeRefresh
- Add Room database caching for true offline support
- Add transaction history screen
- Add search and filter on holdings list
- Add price change animations
- Write more comprehensive unit and UI tests
- Add dark mode support
- Improve the UI with better colors and typography

## Time Spent
- Setup + Gradle configuration: 2 hrs
- Data layer (JSON + Repository): 1 hr
- UI Screens + Navigation: 2 hrs
- Variant B Chart: 1 hr
- Tests + README + AI_LOG: 1 hr
- Total: ~8 hrs