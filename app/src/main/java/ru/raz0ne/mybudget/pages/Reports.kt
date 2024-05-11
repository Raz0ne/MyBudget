package ru.raz0ne.mybudget.pages

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import ru.raz0ne.mybudget.components.ReportPage
import ru.raz0ne.mybudget.models.Recurrence
import ru.raz0ne.mybudget.ui.theme.TopAppBarBackground
import ru.raz0ne.mybudget.viewmodels.ReportsViewModel
import ru.raz0ne.mybudget.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun Reports(vm: ReportsViewModel = viewModel()) {
    val uiState = vm.uiState.collectAsState().value

    val recurrences = listOf(
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reports") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TopAppBarBackground
                ),
                actions = {
                    IconButton(onClick = vm::openRecurrenceMenu) {
                        Icon(
                            painterResource(id = R.drawable.today),
                            contentDescription = "Change recurrence"
                        )
                    }
                    DropdownMenu(
                        expanded = uiState.recurrenceMenuOpened,
                        onDismissRequest = vm::closeRecurrenceMenu
                    ) {
                        recurrences.forEach { recurrence ->
                            DropdownMenuItem(text = { Text(recurrence.name) }, onClick = {
                                vm.setRecurrence(recurrence)
                                vm.closeRecurrenceMenu()
                            })
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            val numOfPages = when (uiState.recurrence) {
                Recurrence.Weekly -> 53
                Recurrence.Monthly -> 12
                Recurrence.Yearly -> 1
                else -> 53
            }
            HorizontalPager(count = numOfPages, reverseLayout = true) { page ->
                ReportPage(innerPadding, page, uiState.recurrence)
            }
        }
    )
}