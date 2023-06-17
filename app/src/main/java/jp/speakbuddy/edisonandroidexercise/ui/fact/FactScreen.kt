package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme

// The main UI component of the App, implemented using Compose. Derives state
// from FactViewModel.
@Composable
fun FactScreen(
    viewModel: FactViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Fact",
            style = MaterialTheme.typography.titleLarge
        )

        if (uiState.isLoading) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {

            if (uiState.fact.text.split(" ").any {
                    it.lowercase().replace("[.,?!;]".toRegex(), "") == "cats"
                    || it.lowercase() == "cats'"
            }) {
                Text(
                    text = "Multiple cats!!",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = uiState.fact.text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            if (uiState.fact.length != null && uiState.fact.length as Int > 100) {
                Text(
                    text = "Length: ${uiState.fact.length}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth().align(Alignment.End)
                )
            }

            val onClick = {
                viewModel.updateFact()
            }

            Button(onClick = onClick) {
                Text(text = "Update fact")
            }
        }
    }
}

@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(viewModel = FactViewModel())
    }
}
