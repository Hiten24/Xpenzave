package com.hcapps.xpenzave.presentation.compare.component.scroll_wheel_picker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hcapps.xpenzave.presentation.compare.component.scroll_wheel_picker.ScrollWheelStyle.Companion.defaultWheelPickerStyle
import com.hcapps.xpenzave.util.pixelsToDp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun ScrollWheelSelector(
    modifier: Modifier = Modifier,
    items: List<String>,
    startIndex: Int = 0,
    visibleItemsCount: Int = 5,
    selectedState: ScrollWheelState,
    style: ScrollWheelStyle = defaultWheelPickerStyle()
) {

    Card(modifier = modifier) {
        WheelPicker(
            modifier = Modifier.fillMaxWidth(),
            items = items,
            startIndex = startIndex,
            visibleItemsCount = visibleItemsCount,
            style = style,
            selectedState = selectedState
        )
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    startIndex: Int = 0,
    visibleItemsCount: Int = 5,
    selectedState: ScrollWheelState,
    style: ScrollWheelStyle = defaultWheelPickerStyle()
) {

    val lazyList = rememberLazyListState(startIndex)
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyList)

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    fun getItem(index: Int) = items[index]

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(key1 = lazyList) {
        snapshotFlow { lazyList.firstVisibleItemIndex }
            .map { index -> getItem(index) }
            .distinctUntilChanged()
            .collect { item ->  selectedState.selectedItem = item}
    }

    Box(modifier = modifier) {

        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            Spacer(modifier = Modifier.width(28.dp))
            Box(
                modifier = Modifier
                    .size(style.indicatorSize)
                    .background(style.indicatorColor, CircleShape)
            )
        }

        LazyColumn(
            state = lazyList,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient),
            flingBehavior = snapBehavior,
            contentPadding = PaddingValues(vertical = itemHeightDp * (visibleItemsCount - 1) / 2)
        ) {
            items(items) { items ->
                Text(
                    text = items,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = style.textStyle,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixels.value = size.height }
                        .padding(style.spaceBetweenItem)
                )
            }
        }
    }
}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
fun rememberScrollWheelSelectedState() = remember {
    ScrollWheelState()
}

class ScrollWheelState {
    var selectedItem by mutableStateOf("")
}
