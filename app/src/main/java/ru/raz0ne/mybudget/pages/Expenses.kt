package ru.raz0ne.mybudget.pages

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.raz0ne.mybudget.components.PickerTrigger
import ru.raz0ne.mybudget.components.expensesList.ExpensesList
import ru.raz0ne.mybudget.models.Recurrence
import ru.raz0ne.mybudget.ui.theme.MyBudgetTheme
import ru.raz0ne.mybudget.ui.theme.LabelSecondary
import ru.raz0ne.mybudget.ui.theme.TopAppBarBackground
import ru.raz0ne.mybudget.ui.theme.Typography
import ru.raz0ne.mybudget.viewmodels.ExpensesViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Expenses(
    navController: NavController,
    vm: ExpensesViewModel = viewModel()
) {
    val recurrences = listOf(
        Recurrence.Daily,
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )

    val state by vm.uiState.collectAsState()
    var recurrenceMenuOpened by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expenses") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TopAppBarBackground
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Total for:",
                        style = Typography.bodyMedium,
                    )
                    PickerTrigger(
                        state.recurrence.target ?: Recurrence.None.target,
                        onClick = { recurrenceMenuOpened = !recurrenceMenuOpened },
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    DropdownMenu(expanded = recurrenceMenuOpened,
                        onDismissRequest = { recurrenceMenuOpened = false }) {
                        recurrences.forEach { recurrence ->
                            DropdownMenuItem(text = { Text(recurrence.target) }, onClick = {
                                vm.setRecurrence(recurrence)
                                recurrenceMenuOpened = false
                            })
                        }
                    }
                }
                Row(modifier = Modifier.padding(vertical = 32.dp)) {
                    Text(
                        "₹",
                        style = Typography.bodyMedium,
                        color = LabelSecondary,
                        modifier = Modifier.padding(end = 4.dp, top = 4.dp)
                    )
                    Text(
                        DecimalFormat("0.#").format(state.sumTotal),
                        style = Typography.titleLarge
                    )
                }
                ExpensesList(
                    expenses = state.expenses,
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(
                            rememberScrollState()
                        )
                )
            }
        }
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExpensePreview() {
    MyBudgetTheme {
        Expenses(navController = rememberNavController())
    }
}