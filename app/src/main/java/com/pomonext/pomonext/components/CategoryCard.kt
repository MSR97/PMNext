import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pomonext.pomonext.model.MCategory
import com.pomonext.pomonext.ui.theme.LightBorderC
import com.pomonext.pomonext.ui.theme.PomoSecC

@Preview(showBackground = true)
@Composable
private fun CategoryCard(title: String = "All") {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .width(112.dp)
            .height(28.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.0.dp, color = LightBorderC),
        elevation = 2.dp,
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp),
            color = PomoSecC
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesBox(
    categories: List<MCategory> =
        listOf(
            MCategory(title = "All"),
            MCategory(title = "Work"),
            MCategory(title = "Study"),
            MCategory(title = "Project")
        )
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(top = 12.dp, start = 1.dp, end = 1.dp),


    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//
            Row(modifier = Modifier.padding(start = 1.dp)) {

                LazyRow(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(categories) { category ->
                        CategoryCard(category.title.toString())

                    }
                }

            }
        }
    }
}



