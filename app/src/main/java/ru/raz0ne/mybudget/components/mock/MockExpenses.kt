package ru.raz0ne.mybudget.components.mock

import androidx.compose.ui.graphics.Color
import ru.raz0ne.mybudget.models.Category
import ru.raz0ne.mybudget.models.Expense
import ru.raz0ne.mybudget.models.Recurrence
import io.github.serpro69.kfaker.Faker
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

val faker = Faker()

val mockCategories = listOf(
    Category(
        "Bills",
        Color(
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255),
        )
    ),
    Category(
        "Fruits",
        Color(
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255),
        )
    ),
    Category(
        "Vegetables",
        Color(
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255),
        )
    ),
    Category(
        "Subscriptions",
        Color(
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255),
        ),
    )
)

val mockExpenses: List<Expense> = List(30) {
    Expense(
        amount = faker.random.nextInt(min = 100, max = 1000000).toDouble() + faker.random.nextDouble(),
        date = LocalDateTime.now().minus(
            faker.random.nextInt(min = 300, max = 345600).toLong(),
            ChronoUnit.SECONDS
        ),
        recurrence = faker.random.randomValue(
            listOf(
                Recurrence.None,
                Recurrence.Daily,
                Recurrence.Weekly,
                Recurrence.Monthly,
                Recurrence.Yearly
            )
        ),
        note = faker.backToTheFuture.characters(),
        category = faker.random.randomValue(mockCategories)
    )
}