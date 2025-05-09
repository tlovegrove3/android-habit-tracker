package com.example.dailyhabittracker.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dailyhabittracker.data.model.Habit
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.util.*

@Composable
fun HabitHistoryCalendar(
    habit: Habit,
    dates: List<LocalDate>,
    modifier: Modifier = Modifier,
    // We'll pass these separately instead of using methods from Habit
    completedDates: List<LocalDate> = emptyList(),
    currentStreak: Int = 0,
    longestStreak: Int = 0,
    isDateCompleted: (LocalDate) -> Boolean = { false }
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Streak information
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StreakCard(
                title = "Current Streak",
                count = currentStreak,
                color = MaterialTheme.colorScheme.primary
            )
            StreakCard(
                title = "Longest Streak",
                count = longestStreak,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Calendar view
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(dates) { date ->
                val isCompleted = isDateCompleted(date) || completedDates.contains(date)
                val isToday = date.isEqual(LocalDate.now())

                DateItem(
                    date = date,
                    isCompleted = isCompleted,
                    isToday = isToday
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Legend
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Completed",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Not Completed",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun StreakCard(
    title: String,
    count: Int,
    color: Color
) {
    Card(
        modifier = Modifier.width(130.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "days",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun DateItem(
    date: LocalDate,
    isCompleted: Boolean,
    isToday: Boolean
) {
    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    val dayOfMonth = date.dayOfMonth.toString()

    val color by animateColorAsState(
        targetValue = if (isCompleted) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "date_color"
    )

    val textColor by animateColorAsState(
        targetValue = if (isCompleted) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurface,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "text_color"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayOfWeek,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(color)
                .border(
                    width = if (isToday) 2.dp else 1.dp,
                    color = if (isToday) MaterialTheme.colorScheme.primary
                    else if (isCompleted) Color.Transparent
                    else MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayOfMonth,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}