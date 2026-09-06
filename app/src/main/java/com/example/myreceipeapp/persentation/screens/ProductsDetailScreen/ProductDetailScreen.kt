package com.example.myreceipeapp.persentation.screens.ProductsDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myreceipeapp.data.remote.dto.Products.Product
import com.example.myreceipeapp.data.remote.dto.Products.Review
import com.example.myreceipeapp.persentation.Components.ErrorMessage
import com.example.myreceipeapp.persentation.Components.LoadingIndicator
import com.example.myreceipeapp.persentation.Components.MyTopBar
import com.example.myreceipeapp.ui.theme.myOrange
import kotlin.math.roundToInt

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBack: () -> Unit,
    viewModel: ProductDetailScreenViewModel = viewModel()
) {
    val product = viewModel.product
    LaunchedEffect(productId) {
        viewModel.fetchProductsById(productId = productId)
    }
    Scaffold(
        topBar = {
            MyTopBar(
                title = "PRODUCT DETAILS",
                onBackClick = onBack,
                icon = Icons.AutoMirrored.Filled.ArrowBack
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = myOrange.copy(alpha = 0.05f))

        ) {
            when {
                viewModel.isLoading -> LoadingIndicator(1.dp)
                viewModel.errorMessage != null -> ErrorMessage(
                    viewModel = viewModel,
                    errorMessage = viewModel.errorMessage,
                    onRetry = { viewModel.fetchProductsById(productId) })

                viewModel.product != null -> {
                    ProductDetailContent(product)

                }
            }
        }
    }
}


@Composable
fun ProductDetailContent(
    product: Product?,
    modifier: Modifier = Modifier
) {
    // Guard once at the top instead of !! on every line below.
    if (product == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { HeaderSection(product) }
        item { PriceAndStockSection(product) }
        item { DescriptionSection(product) }
        item { TagsSection(product) }
        item { SpecsSection(product) }
        item {
            Text(
                text = "Reviews (${product.reviews.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        items(product.reviews) { review ->
            ReviewCard(review)
        }


    }

}

@Composable
private fun HeaderSection(product: Product) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = product.thumbnail,
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = Color(0xFFEFF5EF),
                ),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        product.brand?.let {
            Text(
                text = it.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = product.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFA000),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "%.2f".format(product.rating), fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "(${product.reviews.size} reviews)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PriceAndStockSection(product: Product) {
    val discountedPrice = product.price * (1 - product.discountPercentage / 100)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEFF5EF),
            contentColor = Color(0xFF1B5E20)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$${"%.2f".format(discountedPrice)}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                if (product.discountPercentage > 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$${"%.2f".format(product.price)}",
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.errorContainer
                    ) {
                        Text(
                            text = "-${product.discountPercentage.roundToInt()}%",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val (stockText, stockColor) = when {
                product.stock == 0 -> "Out of stock" to MaterialTheme.colorScheme.error
                product.stock in 1..10 -> "Only ${product.stock} left · ${product.availabilityStatus}" to Color(
                    0xFFF57C00
                )

                else -> "${product.stock} in stock · ${product.availabilityStatus}" to Color(
                    0xFF2E7D32
                )
            }
            Text(text = stockText, color = stockColor, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun DescriptionSection(product: Product) {
    Text(
        text = product.description,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun TagsSection(product: Product) {
    if (product.tags.isEmpty()) return
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        product.tags.forEach { tag ->
            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = tag,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
private fun SpecsSection(product: Product) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEFF5EF),
            contentColor = Color(0xFF1B5E20)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Details",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            SpecRow("Category", product.category.replaceFirstChar { it.uppercase() })
            SpecRow("SKU", product.sku)
            SpecRow("Weight", "${product.weight}g")
            SpecRow(
                "Dimensions",
                "${product.dimensions.width} x ${product.dimensions.height} x ${product.dimensions.depth} cm"
            )
            SpecRow("Warranty", product.warrantyInformation)
            SpecRow("Shipping", product.shippingInformation)
            SpecRow("Return policy", product.returnPolicy)
            SpecRow("Min. order qty", product.minimumOrderQuantity.toString())
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            text = value,
            fontWeight = FontWeight.Medium,
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}

@Composable
private fun ReviewCard(review: Review) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEFF5EF),
            contentColor = Color(0xFF1B5E20)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = review.reviewerName, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(8.dp))
                repeat(review.rating) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFA000),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.comment, style = MaterialTheme.typography.bodyMedium)
        }
    }
}