package com.example.gd.domain.repositories

import com.example.gd.domain.model.Category

object CategoriesRepository {

    fun getCategoriesData(): List<Category> {
        return listOf(
            Category(
                id = "1",
                name = "Burgers",
                image = "https://raw.githubusercontent.com/hitanshu-dhawan/McCompose/main/app/src/main/res/drawable-nodpi/" + "category_burgers.png"
            ),
            Category(
                id = "2",
                name = "Pizza",
                image = "https://raw.githubusercontent.com/hitanshu-dhawan/McCompose/main/app/src/main/res/drawable-nodpi/" + "category_fries.png"
            ),
            Category(
                id = "3",
                name = "Healthy",
                image = "https://raw.githubusercontent.com/hitanshu-dhawan/McCompose/main/app/src/main/res/drawable-nodpi/" + "category_beverages.png"
            ),
            Category(
                id = "4",
                name = "Combo Meals",
                image = "https://raw.githubusercontent.com/hitanshu-dhawan/McCompose/main/app/src/main/res/drawable-nodpi/" + "category_combo_meals.png"
            ),
            Category(
                id = "5",
                name = "Happy Meals",
                image = "https://raw.githubusercontent.com/hitanshu-dhawan/McCompose/main/app/src/main/res/drawable-nodpi/" + "category_happy_meals.png"
            ),
            Category(
                id = "6",
                name = "Desserts",
                image = "https://raw.githubusercontent.com/hitanshu-dhawan/McCompose/main/app/src/main/res/drawable-nodpi/" + "category_desserts.png"
            )
        )
    }
}