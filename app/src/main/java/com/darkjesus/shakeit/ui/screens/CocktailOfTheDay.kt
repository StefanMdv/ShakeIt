package com.darkjesus.shakeit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.darkjesus.shakeit.R
import com.darkjesus.shakeit.data.model.Cocktail
import com.darkjesus.shakeit.ui.composables.CocktailCard
import com.darkjesus.shakeit.ui.viewmodel.CocktailUiState
import com.darkjesus.shakeit.ui.viewmodel.CocktailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CocktailOfTheDay(
    uiState: CocktailUiState,
    onCocktailClick: (Cocktail) -> Unit,
    onRefresh: () -> Unit,
    viewModel: CocktailViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher),
            contentDescription = "App Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(CircleShape)

        )

        Text(
            text = "Cocktail of the Day",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            uiState.selectedCocktail != null -> {
                CocktailCard(
                    cocktail = uiState.selectedCocktail,
                    onClick = { onCocktailClick(uiState.selectedCocktail) },
                    onFavoriteToggle = { viewModel.toggleFavorite(it) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                FilledTonalButton(
                    onClick = onRefresh,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("🎲 Get Another Random Cocktail")
                }
            }

            else -> {
                Text(
                    text = "No cocktail available",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}