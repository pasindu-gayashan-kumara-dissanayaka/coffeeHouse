# coffeeHouse

A basic Android Coffee Shop app scaffold built with Kotlin and MVVM (Repository pattern), using Firebase Realtime Database for live updates.

## Included structure

- `activity/` - Main Activity setup
- `adapter/` - RecyclerView adapters for categories and coffee items
- `domain/` - Model classes (`Category`, `CoffeeItem`)
- `repository/` - Repository abstraction + Firebase implementation
- `viewmodel/` - `CoffeeViewModel` and UI state

## Features in scaffold

- Category browsing with RecyclerView
- Popular items and special offers sections
- Real-time Firebase listeners via Flow
- Material Design UI with ConstraintLayout and ViewBinding
- Glide integration for image loading
