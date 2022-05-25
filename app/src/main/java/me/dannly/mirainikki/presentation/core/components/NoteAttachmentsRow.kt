package me.dannly.mirainikki.presentation.core.components

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.dannly.mirainikki.R

@Composable
fun NoteAttachmentsRow(
    modifier: Modifier = Modifier,
    imageSize: Dp,
    uris: List<Uri>,
    onRemove: ((Uri) -> Unit)? = null
) {
    val context = LocalContext.current
    LazyRow(horizontalArrangement = Arrangement.Center, modifier = modifier.height(imageSize)) {
        itemsIndexed(uris) { index, uri ->
                val sizeInPx = with(LocalDensity.current) { imageSize.roundToPx() }
                val bitmap by produceState<Bitmap?>(initialValue = null) {
                    withContext(Dispatchers.IO) {
                        val parcelFileDescriptor =
                            context.contentResolver.openFileDescriptor(uri, "r")
                        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
                        val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                        parcelFileDescriptor?.close()
                        value = ThumbnailUtils.extractThumbnail(
                            bitmap,
                            sizeInPx,
                            sizeInPx
                        )
                    }
                }
                bitmap?.let {
                    Box(modifier = Modifier.fillMaxHeight()) {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = stringResource(R.string.attachment_index, index),
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .align(Alignment.Center)
                            .clickable {
                                Intent(Intent.ACTION_VIEW)
                                    .apply {
                                        setDataAndType(
                                            uri,
                                            context.contentResolver.getType(uri)
                                        )
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        context.startActivity(this)
                                    }
                            }
                    )
                    if (onRemove != null)
                        Icon(
                            tint = Color.Red,
                            contentDescription = stringResource(R.string.remove),
                            imageVector = Icons.Default.Delete, modifier = Modifier
                                .align(Alignment.TopEnd)
                                .clickable {
                                    onRemove(uri)
                                }
                        )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}