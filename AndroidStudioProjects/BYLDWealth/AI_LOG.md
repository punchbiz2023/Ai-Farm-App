# AI_LOG.md

## Tools Used
- Claude (Anthropic) — primary AI assistant throughout the project

## Significant Prompts

### Prompt 1
**Prompt:** "Give me the build.gradle.kts setup for an Android project with Hilt, Room, Navigation Compose"
**AI produced:** Complete gradle dependencies block
**Kept/Rejected:** Kept the structure, but had to fix plugin ordering — Hilt must come after Android plugin

### Prompt 2
**Prompt:** "Give me a Kotlin data class for a stock holding with symbol, quantity, avgCost, currentPrice"
**AI produced:** Holding.kt data class
**Kept:** Used as-is, clean and simple

### Prompt 3
**Prompt:** "Give me a HoldingsRepository interface and JSON implementation using Gson"
**AI produced:** Interface + JsonHoldingsRepository class
**Kept:** Used as-is, the Gson TypeToken pattern was exactly right

### Prompt 4
**Prompt:** "Give me a HoldingsViewModel with StateFlow loading/success/error states"
**AI produced:** ViewModel with MutableStateFlow and sealed state
**Kept:** Used the pattern, simplified the state class to a data class

### Prompt 5
**Prompt:** "Give me a Compose Canvas donut chart showing portfolio allocation percentage"
**AI produced:** Canvas drawArc implementation
**Kept:** Used as-is — clean and no extra library needed

## A Bug AI Introduced
AI initially imported both `androidx.compose.material` and `androidx.compose.material3`
in HoldingsListScreen.kt. This caused "Overload resolution ambiguity" errors because
both libraries have a `Text` composable. Fixed by removing material import and using
only material3 throughout.

## A Design Choice I Made Against AI Suggestion
AI suggested using a third-party charting library (MPAndroidChart) for the donut chart.
I chose to use Compose Canvas instead because:
1. I can explain every line of Canvas code
2. No extra dependency to justify
3. Lighter app size
4. The interviewer asked "if you added a library, know why" — Canvas was safer

## Time Split
- Writing code manually: 20%
- Prompting AI: 25%
- Reviewing AI output: 20%
- Debugging errors: 25%
- Testing + README: 10%