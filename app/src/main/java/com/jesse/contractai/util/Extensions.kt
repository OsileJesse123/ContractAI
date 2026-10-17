package com.jesse.contractai.util

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.navigation.NavHostController
import com.jesse.contractai.navigation.HomeDestination
import kotlin.math.sqrt


fun Path.customHexagon(radius: Float, size: Size) {
    val triangleHeight = (sqrt(3.0) * radius / 2)
    val centerX = size.width / 2
    val centerY = size.height / 2

    moveTo(centerX, centerY + radius)
    lineTo((centerX - triangleHeight).toFloat(), centerY + radius/2)
    lineTo((centerX - triangleHeight).toFloat(), centerY - radius/2)
    lineTo(centerX, centerY - radius)
    lineTo((centerX + triangleHeight).toFloat(), centerY - radius/2)
    lineTo((centerX + triangleHeight).toFloat(), centerY + radius/2)

    close()
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    popUpTo(HomeDestination.route){saveState = true}
    launchSingleTop = true
    restoreState = true
}