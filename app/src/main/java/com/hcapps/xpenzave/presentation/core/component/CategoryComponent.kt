package com.hcapps.xpenzave.presentation.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.add_expense.Category
import com.hcapps.xpenzave.ui.theme.BorderWidth

@Composable
fun CategoryComponent(
    modifier: Modifier = Modifier,
    category: Category,
    style: CategoryStyle = CategoryStyle.defaultCategoryStyle(),
    onSelect: (category: Category) -> Unit
) {

    val isSelected = category.isSelected

    val borderColor = if (isSelected) style.selectedBorderColor else style.borderColor
    val iconColor = if (isSelected) style.selectedIconColor else style.iconColor
    val textColor = if (isSelected) style.selectedColorText else style.textColor

    val onClickOfOutlinedCard: () -> Unit = {
        onSelect(category.copy(isSelected = !isSelected))
    }

    OutlinedCard(
        modifier = modifier
            .aspectRatio(1f)
            .clip(shape = Shapes().small)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClickOfOutlinedCard
            ),
        border = BorderStroke(style.borderWidth, borderColor),
        colors = CardDefaults.cardColors(containerColor = style.backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(style.iconSize),
                imageVector = category.icon,
                contentDescription = "Icon",
                tint = iconColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                style = style.textStyle,
                color = textColor
            )
        }
    }
}

data class CategoryStyle(
    val borderWidth: Dp,
    val borderColor: Color,
    val selectedBorderColor: Color,
    val iconColor: Color,
    val selectedIconColor: Color,
    val textColor: Color,
    val selectedColorText: Color,
    val backgroundColor: Color,
    val iconSize: Dp,
    val textStyle: TextStyle
) {
    companion object {
        private const val unFocusedBorderColorAlpha = 0.1f
        private const val unFocusedIconColorAlpha = 0.6f
        @Composable
        fun defaultCategoryStyle(
            borderWidth: Dp = BorderWidth,
            borderColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = unFocusedBorderColorAlpha),
            selectedBorderColor: Color = MaterialTheme.colorScheme.primary,
            iconColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = unFocusedIconColorAlpha),
            selectedIconColor: Color = MaterialTheme.colorScheme.primary,
            textColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = unFocusedIconColorAlpha),
            selectedColorText: Color = MaterialTheme.colorScheme.primary,
            backgroundColor: Color = MaterialTheme.colorScheme.background,
            textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
            iconSize: Dp = 32.dp
        ) = CategoryStyle(borderWidth, borderColor, selectedBorderColor, iconColor, selectedIconColor, textColor, selectedColorText, backgroundColor, iconSize, textStyle)
    }
}