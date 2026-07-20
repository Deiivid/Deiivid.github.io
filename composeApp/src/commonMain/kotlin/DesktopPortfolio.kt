import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import davidweb_kmp.composeapp.generated.resources.Res
import davidweb_kmp.composeapp.generated.resources.monitor_dashboard_ambient_v2
import davidweb_kmp.composeapp.generated.resources.portfolio_card_kmp
import davidweb_kmp.composeapp.generated.resources.portfolio_card_neverachef
import davidweb_kmp.composeapp.generated.resources.portfolio_card_permission
import davidweb_kmp.composeapp.generated.resources.portfolio_hero_target
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private val TargetNight = Color(0xFF000306)
private val TargetPanel = Color(0xF003080D)
private val TargetCyan = Color(0xFF62D5FF)
private val TargetAmber = Color(0xFFF2AD56)
private val TargetWhite = Color(0xFFF4F7FB)
private val TargetSlate = Color(0xFF95A5B8)
private val TargetGreen = Color(0xFF46E88B)

private const val TargetEmail = "davidnavarrom3@gmail.com"
private const val TargetCv = "/assets/cv/cv.pdf"
private const val TargetNeveraChef = "https://deiivid.github.io/NeveraChefAi-Web/"
private const val TargetPermissionProtect = "https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es"
private const val TargetPortfolio = "https://github.com/Deiivid/Glassmorphism-Compose"

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun TargetDesktopPortfolio() {
    val uriHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(TargetNight)
    ) {
        val scale = minOf(
            maxWidth.value / 1641f,
            maxHeight.value / 954f
        ).coerceAtMost(1.25f)
        val stageWidth = 1641.dp * scale
        val firstViewportHeight = 954.dp * scale

        Image(
            painter = painterResource(Res.drawable.monitor_dashboard_ambient_v2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5f,
            modifier = Modifier.fillMaxSize()
        )
        TargetAmbientCircuitOverlay(modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            TargetNight.copy(alpha = 0.5f),
                            Color(0xF2000205),
                            TargetNight.copy(alpha = 0.5f)
                        ),
                        start = Offset.Zero,
                        end = Offset(1800f, 1100f)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .width(stageWidth)
                    .height(firstViewportHeight)
            ) {
                Spacer(Modifier.height(20.dp * scale))

                TargetHeader(
                    scale = scale,
                    onContact = {
                        uriHandler.openUri("mailto:$TargetEmail?subject=Contacto%20desde%20tu%20portfolio")
                    }
                )

                TargetHero(
                    scale = scale,
                    onCv = { uriHandler.openUri(TargetCv) }
                )

                TargetProjectStrip(
                    scale = scale,
                    onNeveraChef = { uriHandler.openUri(TargetNeveraChef) },
                    onPermissionProtect = { uriHandler.openUri(TargetPermissionProtect) },
                    onPortfolio = { uriHandler.openUri(TargetPortfolio) }
                )

                Spacer(Modifier.height(20.dp * scale))
            }

            TargetExperienceSection(
                scale = scale,
                modifier = Modifier.width(stageWidth),
                onCv = { uriHandler.openUri(TargetCv) }
            )
            TargetAboutSection(
                scale = scale,
                modifier = Modifier.width(stageWidth),
                onContact = {
                    uriHandler.openUri("mailto:$TargetEmail?subject=Contacto%20desde%20tu%20portfolio")
                }
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun TargetMobilePortfolio() {
    val uriHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TargetNight)
    ) {
        Image(
            painter = painterResource(Res.drawable.monitor_dashboard_ambient_v2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.18f,
            modifier = Modifier.fillMaxSize()
        )
        TargetAmbientCircuitOverlay(modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(TargetNight.copy(alpha = 0.24f), TargetNight, Color(0xFF02050A))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            TargetMobileHeader(
                onContact = {
                    uriHandler.openUri("mailto:$TargetEmail?subject=Contacto%20desde%20tu%20portfolio")
                }
            )

            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = "Creo apps móviles\nque resuelven\nproblemas reales.",
                    color = TargetWhite,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 42.sp,
                    lineHeight = 46.sp,
                    letterSpacing = 0.sp
                )
                Text(
                    text = "Android, Kotlin Multiplatform e IA aplicada.\nArquitectura, producto y entrega de principio a fin.",
                    color = TargetSlate,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(224.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, TargetCyan.copy(alpha = 0.38f), RoundedCornerShape(24.dp))
            ) {
                Image(
                    painter = painterResource(Res.drawable.portfolio_hero_target),
                    contentDescription = "David Navarro y especialidades Android, KMP e IA",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            TargetActionButton(
                text = "Descargar CV",
                scale = 1f,
                modifier = Modifier.fillMaxWidth(),
                onClick = { uriHandler.openUri(TargetCv) }
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TargetAvailabilityPill(scale = 1f)
            }

            TargetMobileProjects(
                onNeveraChef = { uriHandler.openUri(TargetNeveraChef) },
                onPermissionProtect = { uriHandler.openUri(TargetPermissionProtect) },
                onPortfolio = { uriHandler.openUri(TargetPortfolio) }
            )

            TargetMobileDetailPanel(
                eyebrow = "EXPERIENCIA",
                title = "Desarrollador Android desde 2021",
                body = "Seguridad, IA, arquitectura y desarrollo Android y KMP en productos reales.",
                accent = TargetCyan,
                actionLabel = "Ver CV",
                onAction = { uriHandler.openUri(TargetCv) }
            )
            TargetMobileDetailPanel(
                eyebrow = "SOBRE MÍ",
                title = "Me importa el porqué, no solo el código",
                body = "Transformo necesidades complejas en soluciones simples, seguras y mantenibles.",
                accent = TargetAmber,
                actionLabel = "Hablemos",
                onAction = {
                    uriHandler.openUri("mailto:$TargetEmail?subject=Contacto%20desde%20tu%20portfolio")
                }
            )
        }
    }
}

@Composable
private fun TargetAmbientCircuitOverlay(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "ambient-circuit-motion")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ambient-circuit-progress"
    )

    Canvas(modifier = modifier) {
        fun drawFlowingWave(
            baseY: Float,
            amplitude: Float,
            wavelength: Float,
            phase: Float,
            alpha: Float,
            width: Float
        ) {
            val path = Path()
            val steps = 96
            repeat(steps + 1) { step ->
                val x = size.width * step / steps
                val y = baseY + sin((x / wavelength + progress + phase) * 2f * PI.toFloat()) * amplitude
                if (step == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }

            drawPath(
                path = path,
                color = TargetCyan.copy(alpha = alpha),
                style = Stroke(width = width)
            )
        }

        val waveWidth = 1.dp.toPx()
        val softWaveWidth = 22.dp.toPx()

        // Luz ambiental continua: se desliza como una onda por el fondo, sin tocar las tarjetas.
        drawFlowingWave(
            baseY = size.height * 0.19f,
            amplitude = size.height * 0.065f,
            wavelength = size.width * 0.72f,
            phase = 0.02f,
            alpha = 0.035f,
            width = softWaveWidth
        )
        drawFlowingWave(
            baseY = size.height * 0.19f,
            amplitude = size.height * 0.065f,
            wavelength = size.width * 0.72f,
            phase = 0.02f,
            alpha = 0.14f,
            width = waveWidth
        )
        drawFlowingWave(
            baseY = size.height * 0.76f,
            amplitude = size.height * 0.085f,
            wavelength = size.width * 0.58f,
            phase = 0.48f,
            alpha = 0.03f,
            width = softWaveWidth
        )
        drawFlowingWave(
            baseY = size.height * 0.76f,
            amplitude = size.height * 0.085f,
            wavelength = size.width * 0.58f,
            phase = 0.48f,
            alpha = 0.12f,
            width = waveWidth
        )

        // Dos pulsos exteriores para dar profundidad, manteniendo el centro limpio.
        listOf(
            Offset(size.width * 0.04f, size.height * 0.18f),
            Offset(size.width * 0.96f, size.height * 0.8f)
        ).forEachIndexed { index, center ->
            val wave = (progress + index * 0.5f) % 1f
            drawCircle(
                color = TargetCyan.copy(alpha = (1f - wave) * 0.12f),
                radius = size.height * (0.06f + wave * 0.15f),
                center = center,
                style = Stroke(width = waveWidth)
            )
        }

        // Puntos de luz que recorren las dos olas principales.
        repeat(5) { index ->
            val normalizedX = (progress + index * 0.21f) % 1f
            val x = normalizedX * size.width
            val y = size.height * 0.76f + sin((x / (size.width * 0.58f) + progress + 0.48f) * 2f * PI.toFloat()) * size.height * 0.085f
            drawCircle(TargetCyan.copy(alpha = 0.42f), radius = 2.dp.toPx(), center = Offset(x, y))
        }
    }
}

@Composable
private fun TargetMobileHeader(onContact: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(
                text = "DAVID NAVARRO",
                color = TargetWhite,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                letterSpacing = 0.7.sp
            )
            Text(
                text = "ANDROID · KMP · AI",
                color = TargetCyan,
                fontFamily = portfolioMono(),
                fontWeight = FontWeight.Bold,
                fontSize = 9.sp,
                letterSpacing = 1.1.sp
            )
        }
        TargetContactButton(scale = 0.72f, onClick = onContact)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TargetMobileProjects(
    onNeveraChef: () -> Unit,
    onPermissionProtect: () -> Unit,
    onPortfolio: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "PROYECTOS DESTACADOS",
            color = TargetCyan,
            fontFamily = portfolioMono(),
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            letterSpacing = 1.2.sp
        )
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val cardWidth = maxWidth
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TargetProjectImage(
                    resource = Res.drawable.portfolio_card_neverachef,
                    description = "Abrir NeveraChefAI",
                    width = cardWidth,
                    height = 174.dp,
                    scale = 0.8f,
                    onClick = onNeveraChef
                )
                TargetProjectImage(
                    resource = Res.drawable.portfolio_card_permission,
                    description = "Abrir Permission Protect",
                    width = cardWidth,
                    height = 174.dp,
                    scale = 0.8f,
                    onClick = onPermissionProtect
                )
                TargetProjectImage(
                    resource = Res.drawable.portfolio_card_kmp,
                    description = "Abrir Glassmorphism Compose",
                    width = cardWidth,
                    height = 174.dp,
                    scale = 0.8f,
                    titleOverride = "Glassmorphism Compose",
                    subtitleOverride = "Librería visual Compose",
                    onClick = onPortfolio
                )
            }
        }
    }
}

@Composable
private fun TargetMobileDetailPanel(
    eyebrow: String,
    title: String,
    body: String,
    accent: Color,
    actionLabel: String,
    onAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(TargetPanel)
            .border(1.dp, accent.copy(alpha = 0.32f), RoundedCornerShape(20.dp))
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = eyebrow,
            color = accent,
            fontFamily = portfolioMono(),
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            letterSpacing = 1.1.sp
        )
        Text(
            text = title,
            color = TargetWhite,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 23.sp,
            lineHeight = 28.sp
        )
        Text(
            text = body,
            color = TargetSlate,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
        Text(
            text = actionLabel,
            color = accent,
            fontFamily = portfolioMono(),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.clickable(onClick = onAction)
        )
    }
}

@Composable
private fun TargetHeader(
    scale: Float,
    onContact: () -> Unit
) {
    val shape = RoundedCornerShape(24.dp * scale)
    Row(
        modifier = Modifier
            .padding(horizontal = 26.dp * scale)
            .fillMaxWidth()
            .height(96.dp * scale)
            .clip(shape)
            .background(TargetPanel)
            .border(
                width = (1f * scale).coerceAtLeast(0.8f).dp,
                color = TargetCyan.copy(alpha = 0.18f),
                shape = shape
            )
            .padding(start = 38.dp * scale, end = 59.dp * scale),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp * scale)) {
            Text(
                text = "DAVID NAVARRO",
                color = TargetWhite,
                fontWeight = FontWeight.ExtraBold,
                fontSize = (24f * scale).sp,
                letterSpacing = (1.2f * scale).sp
            )
            Text(
                text = "SENIOR ANDROID DEVELOPER · KMP · AI ENGINEER",
                color = TargetCyan,
                fontFamily = portfolioMono(),
                fontWeight = FontWeight.SemiBold,
                fontSize = (14f * scale).sp,
                letterSpacing = (2f * scale).sp
            )
        }

        TargetContactButton(scale = scale, onClick = onContact)
    }
}

@Composable
private fun TargetContactButton(scale: Float, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .width(166.dp * scale)
            .height(52.dp * scale)
            .clip(RoundedCornerShape(14.dp * scale))
            .background(TargetCyan.copy(alpha = 0.07f))
            .border(
                width = (1f * scale).coerceAtLeast(0.8f).dp,
                color = TargetCyan.copy(alpha = 0.42f),
                shape = RoundedCornerShape(14.dp * scale)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 22.dp * scale),
        horizontalArrangement = Arrangement.spacedBy(12.dp * scale, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Hablemos",
            color = TargetWhite,
            fontWeight = FontWeight.Bold,
            fontSize = (18f * scale).sp
        )
        TargetArrowIcon(
            scale = scale,
            color = TargetCyan,
            modifier = Modifier.size(width = 22.dp * scale, height = 14.dp * scale)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TargetHero(
    scale: Float,
    onCv: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(564.dp * scale)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp * scale, top = 4.dp * scale)
                .width(890.dp * scale)
                .height(560.dp * scale)
        ) {
            Image(
                painter = painterResource(Res.drawable.portfolio_hero_target),
                contentDescription = "David Navarro y especialidades Android, KMP e IA",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colorStops = arrayOf(
                                0f to TargetNight,
                                0.06f to TargetNight.copy(alpha = 0.88f),
                                0.15f to TargetNight.copy(alpha = 0.34f),
                                0.25f to Color.Transparent,
                                0.68f to Color.Transparent,
                                0.9f to TargetNight.copy(alpha = 0.58f),
                                1f to TargetNight.copy(alpha = 0.94f)
                            )
                        )
                    )
            )
            HeroCircuitAnimation(modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier
                .padding(start = 64.dp * scale, top = 72.dp * scale)
                .width(670.dp * scale)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(letterSpacing = (1.9f * scale).sp)) {
                        append("Creo apps móviles")
                    }
                    append("\n")
                    withStyle(SpanStyle(letterSpacing = (2.5f * scale).sp)) {
                        append("que resuelven")
                    }
                    append("\n")
                    withStyle(SpanStyle(letterSpacing = (2.2f * scale).sp)) {
                        append("problemas reales")
                    }
                    withStyle(
                        SpanStyle(
                            color = TargetCyan,
                            letterSpacing = (2.2f * scale).sp
                        )
                    ) {
                        append(".")
                    }
                },
                color = TargetWhite,
                fontWeight = FontWeight.ExtraBold,
                fontSize = (67f * scale).sp,
                lineHeight = (75f * scale).sp,
                letterSpacing = 0.sp,
                modifier = Modifier.offset(x = (-2).dp * scale, y = (-7).dp * scale)
            )
            Spacer(Modifier.height(14.dp * scale))
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(letterSpacing = (0.9f * scale).sp)) {
                        append("Android, Kotlin Multiplatform e IA aplicada.")
                    }
                    append("\n")
                    withStyle(SpanStyle(letterSpacing = (0.65f * scale).sp)) {
                        append("Arquitectura, producto y entrega de principio a fin.")
                    }
                },
                color = TargetSlate,
                fontSize = (22f * scale).sp,
                lineHeight = (35f * scale).sp,
                modifier = Modifier.offset(y = (-3).dp * scale)
            )
            Spacer(Modifier.height(17.dp * scale))
            TargetActionButton(
                text = "Descargar CV",
                scale = scale,
                modifier = Modifier.width(270.dp * scale),
                onClick = onCv
            )
            Spacer(Modifier.height(25.dp * scale))
            TargetAvailabilityPill(scale)
        }
    }
}

@Composable
private fun HeroCircuitAnimation(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "hero-circuit")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5_600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "circuit-progress"
    )

    Canvas(modifier = modifier) {
        val scaleX = size.width / 890f
        val scaleY = size.height / 560f
        val sourceScale = minOf(scaleX, scaleY)

        fun sourceOffset(x: Float, y: Float) = Offset(x * scaleX, y * scaleY)

        fun drawNodeGlow(top: Float, arrival: Float) {
            val intensity = (1f - abs(progress - arrival) / 0.13f).coerceIn(0f, 1f)
            if (intensity <= 0f) return

            val topLeft = sourceOffset(580f, top)
            val nodeSize = Size(96f * scaleX, 96f * scaleY)
            val cornerRadius = CornerRadius(18f * sourceScale)
            drawRoundRect(
                color = TargetCyan.copy(alpha = 0.1f * intensity),
                topLeft = topLeft,
                size = nodeSize,
                cornerRadius = cornerRadius,
                style = Stroke(width = 12f * sourceScale)
            )
            drawRoundRect(
                color = TargetCyan.copy(alpha = 0.85f * intensity),
                topLeft = topLeft,
                size = nodeSize,
                cornerRadius = cornerRadius,
                style = Stroke(width = 2f * sourceScale)
            )
        }

        fun drawMovingPulse(
            sourcePoints: List<Offset>,
            startTime: Float,
            endTime: Float
        ) {
            if (progress < startTime || progress > endTime) return

            val fraction = ((progress - startTime) / (endTime - startTime)).coerceIn(0f, 1f)
            val points = sourcePoints.map { sourceOffset(it.x, it.y) }
            val lengths = points.zipWithNext { start, end ->
                val deltaX = end.x - start.x
                val deltaY = end.y - start.y
                sqrt(deltaX * deltaX + deltaY * deltaY)
            }
            val targetDistance = lengths.sum() * fraction
            var traversed = 0f
            var current = points.last()
            lengths.forEachIndexed { index, length ->
                if (targetDistance >= traversed && targetDistance <= traversed + length) {
                    val segmentFraction = ((targetDistance - traversed) / length).coerceIn(0f, 1f)
                    val start = points[index]
                    val end = points[index + 1]
                    current = Offset(
                        x = start.x + (end.x - start.x) * segmentFraction,
                        y = start.y + (end.y - start.y) * segmentFraction
                    )
                }
                traversed += length
            }
            drawCircle(
                color = TargetCyan.copy(alpha = 0.13f),
                radius = 10f * sourceScale,
                center = current
            )
            drawCircle(
                color = TargetCyan.copy(alpha = 0.32f),
                radius = 6f * sourceScale,
                center = current
            )
            drawCircle(
                color = TargetWhite,
                radius = 2.5f * sourceScale,
                center = current
            )
        }

        val ambientAngle = progress * PI.toFloat() * 2f
        val ambientPulse = 0.5f + 0.5f * sin(ambientAngle)
        drawCircle(
            color = TargetCyan.copy(alpha = 0.018f + ambientPulse * 0.02f),
            radius = (166f + 12f * sin(ambientAngle)) * sourceScale,
            center = sourceOffset(
                x = 628f + 24f * sin(ambientAngle),
                y = 280f + 15f * cos(ambientAngle)
            )
        )

        drawNodeGlow(top = 58f, arrival = 0.22f)
        drawNodeGlow(top = 200f, arrival = 0.5f)
        drawNodeGlow(top = 339f, arrival = 0.77f)

        drawMovingPulse(
            sourcePoints = listOf(
                Offset(454f, 219f),
                Offset(480f, 219f),
                Offset(505f, 207f),
                Offset(522f, 185f),
                Offset(522f, 151f),
                Offset(531f, 126f),
                Offset(548f, 111f),
                Offset(569f, 108f)
            ),
            startTime = 0.02f,
            endTime = 0.18f
        )
        drawMovingPulse(
            sourcePoints = listOf(Offset(627f, 154f), Offset(627f, 200f)),
            startTime = 0.31f,
            endTime = 0.43f
        )
        drawMovingPulse(
            sourcePoints = listOf(Offset(627f, 295f), Offset(627f, 339f)),
            startTime = 0.58f,
            endTime = 0.7f
        )
        drawMovingPulse(
            sourcePoints = listOf(Offset(676f, 387f), Offset(713f, 387f)),
            startTime = 0.85f,
            endTime = 0.97f
        )
    }
}

@Composable
private fun TargetActionButton(
    text: String,
    scale: Float,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(10.dp * scale)
    Box(
        modifier = modifier
            .height(58.dp * scale)
            .clip(shape)
            .background(TargetNight.copy(alpha = 0.74f))
            .border(
                width = (1f * scale).coerceAtLeast(0.8f).dp,
                color = TargetWhite.copy(alpha = 0.22f),
                shape = shape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 0.dp)
    ) {
        TargetDownloadIcon(
            scale = scale,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 49.dp * scale)
        )
        Text(
            text = text,
            color = TargetAmber,
            fontWeight = FontWeight.Bold,
            fontSize = (20f * scale).sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 87.dp * scale)
        )
    }
}

@Composable
private fun TargetArrowIcon(
    scale: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(width = 24.dp * scale, height = 16.dp * scale)) {
        val stroke = (1.8f * scale).dp.toPx()
        val midY = size.height / 2f
        drawLine(color, Offset(0f, midY), Offset(size.width - 2.dp.toPx(), midY), stroke)
        drawLine(
            color,
            Offset(size.width - 10.dp.toPx(), midY - 7.dp.toPx()),
            Offset(size.width - 2.dp.toPx(), midY),
            stroke
        )
        drawLine(
            color,
            Offset(size.width - 10.dp.toPx(), midY + 7.dp.toPx()),
            Offset(size.width - 2.dp.toPx(), midY),
            stroke
        )
    }
}

@Composable
private fun TargetDownloadIcon(scale: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(24.dp * scale)) {
        val stroke = (1.8f * scale).dp.toPx()
        val centerX = size.width / 2f
        drawLine(TargetAmber, Offset(centerX, 2.dp.toPx()), Offset(centerX, 15.dp.toPx()), stroke)
        drawLine(TargetAmber, Offset(centerX, 15.dp.toPx()), Offset(centerX - 5.dp.toPx(), 10.dp.toPx()), stroke)
        drawLine(TargetAmber, Offset(centerX, 15.dp.toPx()), Offset(centerX + 5.dp.toPx(), 10.dp.toPx()), stroke)
        drawLine(TargetAmber, Offset(3.dp.toPx(), 19.dp.toPx()), Offset(3.dp.toPx(), 22.dp.toPx()), stroke)
        drawLine(TargetAmber, Offset(3.dp.toPx(), 22.dp.toPx()), Offset(size.width - 3.dp.toPx(), 22.dp.toPx()), stroke)
        drawLine(TargetAmber, Offset(size.width - 3.dp.toPx(), 22.dp.toPx()), Offset(size.width - 3.dp.toPx(), 19.dp.toPx()), stroke)
    }
}

@Composable
private fun TargetAvailabilityPill(scale: Float) {
    val transition = rememberInfiniteTransition(label = "availability-orbit")
    val orbitProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3_400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "availability-orbit-progress"
    )

    Box(
        modifier = Modifier
            .width(310.dp * scale)
            .height(42.dp * scale)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(11.dp * scale))
                .background(Color(0xB20B111A))
                .border(
                    width = (1f * scale).coerceAtLeast(0.8f).dp,
                    color = TargetGreen.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(11.dp * scale)
                )
                .padding(horizontal = 18.dp * scale),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp * scale)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp * scale)
                    .offset(y = 1.dp * scale)
                    .shadow(10.dp * scale, CircleShape, spotColor = TargetGreen)
                    .clip(CircleShape)
                    .background(TargetGreen)
            )
            Text(
                text = "Disponible para nuevos retos",
                color = TargetWhite.copy(alpha = 0.9f),
                fontSize = (14f * scale).sp,
                letterSpacing = (0.02f * scale).sp,
                maxLines = 1,
                softWrap = false,
                modifier = Modifier.offset(y = 1.dp * scale)
            )
        }
        AvailabilityOrbit(
            progress = orbitProgress,
            scale = scale,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun AvailabilityOrbit(
    progress: Float,
    scale: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val inset = 1.4.dp.toPx()
        val orbitWidth = size.width - inset * 2f
        val orbitHeight = size.height - inset * 2f
        val radius = (10.dp * scale).toPx().coerceAtMost(orbitHeight / 2f)
        val horizontal = orbitWidth - radius * 2f
        val vertical = orbitHeight - radius * 2f
        val quarterArc = PI.toFloat() * radius / 2f
        val perimeter = horizontal * 2f + vertical * 2f + quarterArc * 4f

        fun pointAt(rawFraction: Float): Offset {
            val normalized = ((rawFraction % 1f) + 1f) % 1f
            var distance = normalized * perimeter

            if (distance <= horizontal) {
                return Offset(inset + radius + distance, inset)
            }
            distance -= horizontal

            if (distance <= quarterArc) {
                val angle = -PI.toFloat() / 2f + distance / radius
                return Offset(
                    x = inset + orbitWidth - radius + cos(angle) * radius,
                    y = inset + radius + sin(angle) * radius
                )
            }
            distance -= quarterArc

            if (distance <= vertical) {
                return Offset(inset + orbitWidth, inset + radius + distance)
            }
            distance -= vertical

            if (distance <= quarterArc) {
                val angle = distance / radius
                return Offset(
                    x = inset + orbitWidth - radius + cos(angle) * radius,
                    y = inset + orbitHeight - radius + sin(angle) * radius
                )
            }
            distance -= quarterArc

            if (distance <= horizontal) {
                return Offset(inset + orbitWidth - radius - distance, inset + orbitHeight)
            }
            distance -= horizontal

            if (distance <= quarterArc) {
                val angle = PI.toFloat() / 2f + distance / radius
                return Offset(
                    x = inset + radius + cos(angle) * radius,
                    y = inset + orbitHeight - radius + sin(angle) * radius
                )
            }
            distance -= quarterArc

            if (distance <= vertical) {
                return Offset(inset, inset + orbitHeight - radius - distance)
            }
            distance -= vertical

            val angle = PI.toFloat() + distance / radius
            return Offset(
                x = inset + radius + cos(angle) * radius,
                y = inset + radius + sin(angle) * radius
            )
        }

        drawRoundRect(
            color = TargetGreen.copy(alpha = 0.13f),
            topLeft = Offset(inset, inset),
            size = Size(orbitWidth, orbitHeight),
            cornerRadius = CornerRadius(radius),
            style = Stroke(width = (1f * scale).dp.toPx())
        )

        listOf(
            Triple(0.045f, 0.12f, 2.2f),
            Triple(0.024f, 0.28f, 2.7f),
            Triple(0f, 0.95f, 3.2f)
        ).forEach { (delay, alpha, dotRadius) ->
            drawCircle(
                color = TargetGreen.copy(alpha = alpha),
                radius = dotRadius.dp.toPx() * scale,
                center = pointAt(progress - delay)
            )
        }

        drawCircle(
            color = TargetGreen.copy(alpha = 0.16f),
            radius = 7.dp.toPx() * scale,
            center = pointAt(progress)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TargetProjectStrip(
    scale: Float,
    modifier: Modifier = Modifier,
    onNeveraChef: () -> Unit,
    onPermissionProtect: () -> Unit,
    onPortfolio: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(254.dp * scale)
            .padding(horizontal = 64.dp * scale),
        horizontalArrangement = Arrangement.spacedBy(19.dp * scale),
        verticalAlignment = Alignment.Top
    ) {
        TargetProjectImage(
            resource = Res.drawable.portfolio_card_neverachef,
            description = "Abrir NeveraChefAI · IA local",
            width = 499.dp * scale,
            height = 254.dp * scale,
            scale = scale,
            onClick = onNeveraChef
        )
        TargetProjectImage(
            resource = Res.drawable.portfolio_card_permission,
            description = "Abrir Permission Protect · Seguridad Android",
            width = 467.dp * scale,
            height = 254.dp * scale,
            scale = scale,
            onClick = onPermissionProtect
        )
        TargetProjectImage(
            resource = Res.drawable.portfolio_card_kmp,
            description = "Abrir Glassmorphism Compose · Librería visual",
            width = 498.dp * scale,
            height = 254.dp * scale,
            scale = scale,
            titleOverride = "Glassmorphism Compose",
            subtitleOverride = "Librería visual Compose",
            onClick = onPortfolio
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TargetProjectImage(
    resource: org.jetbrains.compose.resources.DrawableResource,
    description: String,
    width: Dp,
    height: Dp,
    scale: Float,
    titleOverride: String? = null,
    subtitleOverride: String? = null,
    onClick: () -> Unit
) {
    val cardShape = RoundedCornerShape(16.dp * scale)
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .shadow(
                elevation = 10.dp * scale,
                shape = cardShape,
                spotColor = TargetCyan.copy(alpha = 0.2f)
            )
            .clip(cardShape)
            .border(
                width = (1.4f * scale).coerceAtLeast(1f).dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        TargetCyan.copy(alpha = 0.72f),
                        TargetAmber.copy(alpha = 0.48f),
                        TargetCyan.copy(alpha = 0.3f)
                    )
                ),
                shape = cardShape
            )
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(resource),
            contentDescription = description,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        if (titleOverride != null && subtitleOverride != null) {
            Box(
                modifier = Modifier
                    .offset(x = 12.dp * scale, y = 18.dp * scale)
                    .width(300.dp * scale)
                    .height(84.dp * scale)
                    .background(
                        Brush.horizontalGradient(
                            0f to Color(0xFF071019),
                            0.82f to Color(0xFF071019),
                            1f to Color.Transparent
                        )
                    )
            )
            Column(
                modifier = Modifier.offset(x = 24.dp * scale, y = 31.dp * scale),
                verticalArrangement = Arrangement.spacedBy(8.dp * scale)
            ) {
                Text(
                    text = titleOverride,
                    color = TargetWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = (20f * scale).sp
                )
                Text(
                    text = subtitleOverride,
                    color = TargetAmber,
                    fontSize = (16f * scale).sp
                )
            }
        }
    }
}

@Composable
private fun TargetExperienceSection(
    scale: Float,
    modifier: Modifier = Modifier,
    onCv: () -> Unit
) {
    TargetDetailPanel(
        eyebrow = "EXPERIENCIA",
        title = "Desarrollador Android desde 2021",
        body = "He trabajado en proyectos de sector público, media y televisión, banca, deporte y comunicación. Como referente técnico, he liderado proyectos y equipos Android e iOS.\n\nActualmente colaboro de forma transversal en mi empresa mejorando distintos repositorios: implemento funcionalidades de seguridad e IA, apoyo migraciones y realizo mejoras puntuales en Android y KMP. Mi experiencia incluye Java, Kotlin y Jetpack Compose, además de análisis funcionales, propuestas a cliente, auditorías, arquitectura y calidad de código.",
        accent = TargetCyan,
        scale = scale,
        modifier = modifier,
        actionLabel = "Ver experiencia completa en mi CV",
        onAction = onCv
    )
}

@Composable
private fun TargetAboutSection(
    scale: Float,
    modifier: Modifier = Modifier,
    onContact: () -> Unit
) {
    TargetDetailPanel(
        eyebrow = "SOBRE MÍ",
        title = "Me importa el porqué, no solo el código",
        body = "Me gusta entender el problema antes de construir la solución: analizar el impacto, tomar decisiones con criterio y convertir necesidades complejas en soluciones simples, seguras y mantenibles. Disfruto compartiendo conocimiento, ayudando al equipo y aprendiendo mediante proyectos reales. La curiosidad por Android, KMP e IA es lo que me impulsa a seguir creando y mejorando.",
        accent = TargetAmber,
        scale = scale,
        modifier = modifier
            .clickable(onClick = onContact)
            .padding(bottom = 80.dp * scale)
    )
}

@Composable
private fun TargetDetailPanel(
    eyebrow: String,
    title: String,
    body: String,
    accent: Color,
    scale: Float,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .padding(horizontal = 64.dp * scale, vertical = 28.dp * scale)
            .clip(RoundedCornerShape(24.dp * scale))
            .background(TargetPanel)
            .border(
                width = (1f * scale).coerceAtLeast(0.8f).dp,
                color = accent.copy(alpha = 0.28f),
                shape = RoundedCornerShape(24.dp * scale)
            )
            .padding(34.dp * scale),
        verticalArrangement = Arrangement.spacedBy(10.dp * scale)
    ) {
        Text(
            text = eyebrow,
            color = accent,
            fontFamily = portfolioMono(),
            fontWeight = FontWeight.Bold,
            fontSize = (11f * scale).sp,
            letterSpacing = (1.4f * scale).sp
        )
        Text(
            text = title,
            color = TargetWhite,
            fontWeight = FontWeight.Black,
            fontSize = (28f * scale).sp
        )
        Text(
            text = body,
            color = TargetSlate,
            fontSize = (16f * scale).sp,
            lineHeight = (24f * scale).sp
        )
        actionLabel?.let { label ->
            onAction?.let { action ->
                Row(
                    modifier = Modifier
                        .padding(top = 6.dp * scale)
                        .clip(RoundedCornerShape(10.dp * scale))
                        .background(accent.copy(alpha = 0.08f))
                        .clickable(onClick = action)
                        .padding(horizontal = 14.dp * scale, vertical = 10.dp * scale),
                    horizontalArrangement = Arrangement.spacedBy(8.dp * scale),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        color = accent,
                        fontFamily = portfolioMono(),
                        fontWeight = FontWeight.Bold,
                        fontSize = (12f * scale).sp
                    )
                    ExternalLinkIcon(
                        color = accent,
                        modifier = Modifier.size(14.dp * scale)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExternalLinkIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.6.dp.toPx()
        drawLine(color, Offset(size.width * 0.12f, size.height * 0.34f), Offset(size.width * 0.12f, size.height * 0.88f), stroke)
        drawLine(color, Offset(size.width * 0.12f, size.height * 0.88f), Offset(size.width * 0.66f, size.height * 0.88f), stroke)
        drawLine(color, Offset(size.width * 0.66f, size.height * 0.88f), Offset(size.width * 0.66f, size.height * 0.62f), stroke)
        drawLine(color, Offset(size.width * 0.38f, size.height * 0.62f), Offset(size.width * 0.88f, size.height * 0.12f), stroke)
        drawLine(color, Offset(size.width * 0.52f, size.height * 0.12f), Offset(size.width * 0.88f, size.height * 0.12f), stroke)
        drawLine(color, Offset(size.width * 0.88f, size.height * 0.12f), Offset(size.width * 0.88f, size.height * 0.48f), stroke)
    }
}
