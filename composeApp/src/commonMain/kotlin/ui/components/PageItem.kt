package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ktmulproject.composeapp.generated.resources.Qt
import ktmulproject.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font
import ui.types.PageModel

@Composable
fun PageItem(d: PageModel) {
    val fontAwesome = FontFamily(Font(Res.font.Qt))
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val str = d.date.split('.')
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)) {
                        append(str.last())
                    }
                    append(" / ${str.first()}.${str[1]}")
                }
            )
            Text(d.week)
        }
        KamelImage(
            asyncPainterResource(d.imgUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(220.dp),
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopCenter
        )
        Text(
            d.content.split(',').first(),
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Start)
                .padding(horizontal = 8.dp, vertical = 18.dp),
            fontFamily = fontAwesome
        )
        Text(
            d.content.split(',').last(),
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.End)
                .padding(horizontal = 8.dp, vertical = 18.dp),
            fontFamily = fontAwesome
        )
        Text(d.author, modifier = Modifier.padding(horizontal = 8.dp).align(Alignment.End))
    }
}