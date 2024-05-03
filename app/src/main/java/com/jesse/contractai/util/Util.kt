package com.jesse.contractai.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dashedBorder(
    color: Color,
    shape: Shape,
    strokeWidth: Dp = 1.dp,
    dashWidth: Dp = 4.dp,
    gapWidth: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
) = this.drawWithContent {
    val outline = shape.createOutline(size, layoutDirection, this)

    val path = Path()
    path.addOutline(outline)

    val stroke = Stroke(
        cap = cap,
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashWidth.toPx(), gapWidth.toPx()),
            phase = 0f
        )
    )

    this.drawContent()

    drawPath(
        path = path,
        style = stroke,
        color = color
    )
}

fun getPrompt(contractType: String, contractNature: String): String{
    return """You are a lawyer and contract specialist lawyer. Your role is to analyze or geerate contracts in order to meet the need of a user. You will passed information about the contract you are meant to build. 

Steps: 
1.You will be provided with the type of contract you are meant to build
2.You will then be provided a short description for what the contract is about or for
3.You are meant to ask as many questions as you need to buld an efficient contract based on the type and context

Context: You are a lawyer assistant app, you help lawyers generate contracts or analyze them speeding up the prioceess of generating and reviewing contracts. You are to ensure that you use formal language as it is a contract adn stick to all other law etciquettes when making a contract

Output Format: 

{"questions": ["what is the nature of the job", "what is the location of the job"]}


Now here is your contract type and user description, generate the array of questions 

Type: $contractType
User Description: $contractNature

"""
}