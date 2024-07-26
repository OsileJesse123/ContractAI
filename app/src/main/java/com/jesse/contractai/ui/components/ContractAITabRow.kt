package com.jesse.contractai.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jesse.contractai.navigation.Destinations

@Composable
fun ContractAITabRow(
    modifier: Modifier = Modifier,
    allScreens: List<Destinations>,
    onTabSelected: (Destinations) -> Unit,
    currentScreen: Destinations
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.extraLarge
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .selectableGroup()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            allScreens.forEach {
                    screen ->
                ContractAITab(
                    icon = screen.icon,
                    iconDescription = screen.iconDescription,
                    selected = currentScreen == screen,
                    onSelected = {
                        onTabSelected(screen)
                    }
                )
            }
        }
    }
}

@Composable
fun ContractAITab(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes iconDescription: Int,
    selected: Boolean,
    onSelected: () -> Unit
) {
    val iconDescString = stringResource(id = iconDescription)

    val surfaceColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        label = "tab surface color animation"
    )
    val iconColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        label = "tab surface color animation"
    )
    Surface(
        modifier = modifier
            .clip(CircleShape)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = iconDescString },
        color = surfaceColor
    ) {
        Image(
            modifier = Modifier
                .padding(10.dp)
                .size(24.dp),
            colorFilter = ColorFilter.tint(iconColor),
            painter = painterResource(id = icon),
            contentDescription = iconDescString
        )
    }
}