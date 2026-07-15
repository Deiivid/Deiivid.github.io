import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import davidweb_kmp.composeapp.generated.resources.Res
import davidweb_kmp.composeapp.generated.resources.image_david
import davidweb_kmp.composeapp.generated.resources.monitor_dashboard_ambient_v2
import davidweb_kmp.composeapp.generated.resources.office_background
import davidweb_kmp.composeapp.generated.resources.office_pov_approach_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_grab_chair_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_pull_chair_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_seated_centered_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_settle_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_sit_down_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_approach_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_grab_chair_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_pull_chair_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_seated_centered_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_settle_premium
import davidweb_kmp.composeapp.generated.resources.office_pov_sit_down_premium
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin

private val night = Color(0xFF070A10)
private val surface = Color(0xEE11161F)
private val accent = Color(0xFF62D5FF)
private val amber = Color(0xFFF2B879)
private val primaryText = Color(0xFFF4F7FB)
private val secondaryText = Color(0xFF9AA7B8)
private val violet = accent
private val mint = accent
private val warm = amber
private const val contactEmail = "davidnavarrom3@gmail.com"

private fun premiumSurfaceBrush() = Brush.linearGradient(
    colors = listOf(
        Color(0xFC111C29),
        Color(0xF70A121D),
        Color(0xFA101824)
    )
)

private fun premiumInsetBrush() = Brush.linearGradient(
    colors = listOf(
        Color(0xA5192A3B),
        Color(0xB80B131E),
        Color(0x9E111D2A)
    )
)

private fun premiumBorderBrush(highlight: Color = accent) = Brush.linearGradient(
    colors = listOf(
        highlight.copy(alpha = 0.72f),
        Color.White.copy(alpha = 0.18f),
        highlight.copy(alpha = 0.16f),
        Color.White.copy(alpha = 0.08f)
    )
)

private enum class PortfolioSection(
    val number: String,
    val label: String,
    val subtitle: String
) {
    CV("01", "Mi CV", "Trayectoria y stack"),
    PROJECTS("02", "Mis proyectos", "Apps y código"),
    ABOUT("03", "Sobre mí", "Perfil profesional"),
    EXPERIENCE("04", "Experiencia", "Mi recorrido"),
    CONTACT("05", "Email", "Hablemos"),
    SOCIAL("06", "Redes sociales", "Código y artículos")
}

private data class OrbitPlacement(
    val section: PortfolioSection,
    val x: Dp,
    val y: Dp,
    val menuOnLeft: Boolean
)

@Composable
fun App() {
    var introKey by remember { mutableIntStateOf(0) }
    var menusVisible by remember { mutableStateOf(false) }
    var activeSection by remember { mutableStateOf<PortfolioSection?>(null) }
    val walkProgress = remember(introKey) { Animatable(0f) }

    LaunchedEffect(introKey) {
        menusVisible = false
        activeSection = null
        walkProgress.snapTo(0f)
        delay(120)
        walkProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(4500, easing = LinearEasing)
        )
        delay(140)
        menusVisible = true
    }

    MaterialTheme {
        Surface(color = night, modifier = Modifier.fillMaxSize()) {
            PortfolioExperience(
                walkProgress = walkProgress.value,
                menusVisible = menusVisible,
                activeSection = activeSection,
                onSectionSelected = { activeSection = it },
                onBack = { activeSection = null },
                onReplay = {
                    activeSection = null
                    introKey++
                }
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun PortfolioExperience(
    walkProgress: Float,
    menusVisible: Boolean,
    activeSection: PortfolioSection?,
    onSectionSelected: (PortfolioSection) -> Unit,
    onBack: () -> Unit,
    onReplay: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(night)) {
        val compact = maxWidth < 700.dp || maxHeight < 560.dp

        CinematicStage(
            walkProgress = walkProgress,
            compact = compact,
            onReplay = onReplay,
            modifier = Modifier.fillMaxSize()
        )

        if (menusVisible) {
            MonitorPortfolioMenu(
                compact = compact,
                activeSection = activeSection,
                onSectionSelected = onSectionSelected,
                onBack = onBack,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun CinematicStage(
    walkProgress: Float,
    compact: Boolean,
    onReplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.background(night)
    ) {
        OfficeScene(walkProgress = walkProgress, compact = compact, modifier = Modifier.fillMaxSize())
        TopBar(
            compact = compact,
            onReplay = onReplay,
            modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth()
        )
        AnimatedVisibility(
            visible = walkProgress < 0.12f,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(280)),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            IntroCopy(compact = compact, modifier = Modifier.padding(start = if (compact) 22.dp else 48.dp))
        }
        if (walkProgress in 0.001f..0.90f) {
            val approachLabel = when {
                walkProgress < 0.40f -> "ACERCÁNDOME"
                walkProgress < 0.59f -> "COGIENDO LA SILLA"
                walkProgress < 0.70f -> "COLOCANDO LA SILLA"
                else -> "SENTÁNDOME"
            }
            Text(
                approachLabel,
                color = primaryText.copy(alpha = 0.72f),
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 9.sp,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(if (compact) 16.dp else 24.dp)
                    .background(Color.Black.copy(alpha = 0.42f), RoundedCornerShape(999.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(999.dp))
                    .padding(horizontal = 12.dp, vertical = 7.dp)
            )
        }
        if (walkProgress >= 0.90f) {
            TypingStatus(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(if (compact) 14.dp else 24.dp)
            )
        }
        if (!compact && walkProgress < 0.90f) {
            Text(
                "ANDROID · KOTLIN · KMP",
                color = secondaryText.copy(alpha = 0.58f),
                fontFamily = FontFamily.Monospace,
                fontSize = 9.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 27.dp)
            )
        }
    }
}

@Composable
private fun TopBar(compact: Boolean, onReplay: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = if (compact) 14.dp else 24.dp, vertical = if (compact) 13.dp else 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .size(if (compact) 34.dp else 38.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(Color(0xB20B111A))
                    .border(1.dp, accent.copy(alpha = 0.42f), RoundedCornerShape(11.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("DN", color = primaryText, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
            Column {
                Text("DAVID NAVARRO", color = primaryText, fontWeight = FontWeight.Bold, fontSize = if (compact) 12.sp else 14.sp)
                if (!compact) {
                    Text("MOBILE ENGINEER", color = accent, fontFamily = FontFamily.Monospace, fontSize = 8.sp, letterSpacing = 1.sp)
                }
            }
        }
        Text(
            if (compact) "REPETIR" else "REPLAY",
            color = primaryText.copy(alpha = 0.76f),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.SemiBold,
            fontSize = 9.sp,
            letterSpacing = 0.8.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .clickable(onClick = onReplay)
                .background(Color.Black.copy(alpha = 0.30f))
                .border(1.dp, Color.White.copy(alpha = 0.13f), RoundedCornerShape(999.dp))
                .padding(horizontal = if (compact) 11.dp else 14.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun IntroCopy(compact: Boolean, modifier: Modifier = Modifier) {
    Column(modifier = modifier.widthIn(max = if (compact) 285.dp else 460.dp)) {
        Text(
            "ANDROID ENGINEER / KMP",
            color = accent,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = if (compact) 9.sp else 10.sp,
            letterSpacing = 1.4.sp
        )
        Text(
            if (compact) "Productos móviles\nque se sienten bien." else "Diseño y construyo\nproductos móviles\nque duran.",
            color = primaryText,
            fontWeight = FontWeight.Black,
            fontSize = if (compact) 30.sp else 50.sp,
            lineHeight = if (compact) 34.sp else 52.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            "Kotlin, Compose y arquitectura pensada para crecer.",
            color = secondaryText,
            fontSize = if (compact) 12.sp else 14.sp,
            lineHeight = if (compact) 18.sp else 21.sp,
            modifier = Modifier.widthIn(max = 340.dp).padding(top = 13.dp)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun OfficeScene(walkProgress: Float, compact: Boolean, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier.clipToBounds()) {
        val portrait = maxHeight > maxWidth
        val walking = smoothStep(walkProgress / 0.38f)
        val stepStrength = smoothStep(walkProgress / 0.05f) *
            (1f - smoothStep((walkProgress - 0.34f) / 0.06f))
        val stepPhase = walking * 5.5f * PI.toFloat()
        val footfall = abs(sin(stepPhase.toDouble())).toFloat()
        val stepBob = -footfall * (if (portrait) 5f else 8f) * stepStrength
        val stepSway = sin(stepPhase.toDouble()).toFloat() *
            (if (portrait) 3.5f else 5.5f) * stepStrength
        val backgroundAlpha = 1f - smoothStep((walkProgress - 0.26f) / 0.08f)
        val approachAlpha = frameAlpha(walkProgress, 0.26f, 0.34f, 0.39f, 0.47f)
        val grabAlpha = frameAlpha(walkProgress, 0.39f, 0.47f, 0.50f, 0.58f)
        val pullAlpha = frameAlpha(walkProgress, 0.50f, 0.58f, 0.60f, 0.68f)
        val sitAlpha = frameAlpha(walkProgress, 0.60f, 0.68f, 0.72f, 0.80f)
        val settleAlpha = frameAlpha(walkProgress, 0.72f, 0.80f, 0.83f, 0.91f)
        val seatedAlpha = smoothStep((walkProgress - 0.83f) / 0.08f)
        val approach = smoothStep((walkProgress - 0.26f) / 0.21f)
        val grab = smoothStep((walkProgress - 0.39f) / 0.19f)
        val pull = smoothStep((walkProgress - 0.50f) / 0.18f)
        val sitting = smoothStep((walkProgress - 0.60f) / 0.20f)
        val settling = smoothStep((walkProgress - 0.72f) / 0.19f)
        val seated = smoothStep((walkProgress - 0.83f) / 0.17f)
        val seatCompression = sin(sitting * PI.toFloat()).toFloat()

        PovFrame(
            resource = Res.drawable.office_background,
            contentDescription = null,
            alpha = backgroundAlpha,
            scale = 1.005f + walking * 0.11f + footfall * 0.004f,
            translationX = maxWidth * (-0.025f * walking) + stepSway.dp,
            translationY = maxHeight * (-0.012f * walking) + stepBob.dp,
            rotation = stepSway * 0.045f,
            transformOrigin = TransformOrigin(0.58f, 0.56f)
        )
        PovFrame(
            resource = Res.drawable.office_pov_approach_premium,
            contentDescription = "Acercándose en primera persona a la silla",
            alpha = approachAlpha,
            scale = 1f + approach * 0.055f,
            translationX = stepSway.dp * 0.25f,
            translationY = stepBob.dp * 0.30f,
            rotation = stepSway * 0.018f,
            transformOrigin = TransformOrigin(0.50f, 0.56f)
        )
        PovFrame(
            resource = Res.drawable.office_pov_grab_chair_premium,
            contentDescription = "Cogiendo el respaldo de la silla en primera persona",
            alpha = grabAlpha,
            scale = 1f + grab * 0.012f,
            translationY = maxHeight * (0.004f * grab),
            rotation = grab * 0.08f
        )
        PovFrame(
            resource = Res.drawable.office_pov_pull_chair_premium,
            contentDescription = "Desplazando la silla hacia atrás en primera persona",
            alpha = pullAlpha,
            scale = 1f + pull * 0.018f,
            translationX = maxWidth * (0.008f * pull),
            translationY = maxHeight * (0.012f * pull),
            rotation = pull * 0.32f,
            transformOrigin = TransformOrigin(0.56f, 0.58f)
        )
        PovFrame(
            resource = Res.drawable.office_pov_sit_down_premium,
            contentDescription = "Sentándose y apoyando las manos en los reposabrazos",
            alpha = sitAlpha,
            scale = 1f + sitting * 0.012f,
            translationX = maxWidth * (-0.006f * seatCompression),
            translationY = maxHeight * (0.018f * seatCompression),
            rotation = -0.22f * seatCompression,
            transformOrigin = TransformOrigin(0.50f, 0.62f)
        )
        PovFrame(
            resource = Res.drawable.office_pov_settle_premium,
            contentDescription = "Acercando la silla y las manos al teclado",
            alpha = settleAlpha,
            scale = 1f + settling * 0.022f,
            translationY = maxHeight * (-0.008f * settling + 0.006f * seatCompression),
            transformOrigin = TransformOrigin(0.50f, 0.52f)
        )
        PovFrame(
            resource = Res.drawable.office_pov_seated_centered_premium,
            contentDescription = "Vista frontal sentado y centrado ante el monitor",
            alpha = seatedAlpha,
            scale = 1f + seated * 0.08f,
            translationY = maxHeight * (-0.004f * seated),
            transformOrigin = TransformOrigin(0.50f, 0.46f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0f to Color.Black.copy(alpha = if (compact) 0.22f else 0.36f),
                            0.42f to Color.Black.copy(alpha = if (compact) 0.06f else 0.10f),
                            0.72f to Color.Transparent,
                            1f to Color.Black.copy(alpha = 0.08f)
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Black.copy(alpha = 0.14f), Color.Transparent, Color.Black.copy(alpha = 0.24f))
                    )
                )
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun PovFrame(
    resource: DrawableResource,
    contentDescription: String?,
    alpha: Float,
    scale: Float,
    translationX: Dp = 0.dp,
    translationY: Dp = 0.dp,
    rotation: Float = 0f,
    transformOrigin: TransformOrigin = TransformOrigin(0.5f, 0.5f)
) {
    if (alpha <= 0f) return
    val density = LocalDensity.current
    Image(
        painter = painterResource(resource),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        alpha = alpha,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.translationX = with(density) { translationX.toPx() }
                this.translationY = with(density) { translationY.toPx() }
                rotationZ = rotation
                this.transformOrigin = transformOrigin
            }
    )
}

private fun frameAlpha(
    progress: Float,
    enterStart: Float,
    enterEnd: Float,
    exitStart: Float,
    exitEnd: Float
): Float {
    val entering = smoothStep((progress - enterStart) / (enterEnd - enterStart))
    val leaving = 1f - smoothStep((progress - exitStart) / (exitEnd - exitStart))
    return (entering * leaving).coerceIn(0f, 1f)
}

private fun smoothStep(value: Float): Float {
    val progress = value.coerceIn(0f, 1f)
    return progress * progress * (3f - 2f * progress)
}

@Composable
private fun TypingStatus(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "typing-status")
    val pulse by transition.animateFloat(
        initialValue = 0.42f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(520), RepeatMode.Reverse),
        label = "typing-pulse"
    )
    Row(
        modifier = modifier
            .background(Color(0xC90A1018), RoundedCornerShape(999.dp))
            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(999.dp))
            .padding(horizontal = 12.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(Modifier.size(6.dp).background(accent.copy(alpha = pulse), CircleShape))
        Text(
            "PORTFOLIO · ONLINE",
            color = primaryText.copy(alpha = 0.78f),
            fontFamily = FontFamily.Monospace,
            fontSize = 8.sp,
            letterSpacing = 0.9.sp
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun MonitorPortfolioMenu(
    compact: Boolean,
    activeSection: PortfolioSection?,
    onSectionSelected: (PortfolioSection) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val portrait = maxHeight > maxWidth
        val screenWidth = if (portrait) {
            maxWidth * 0.92f
        } else {
            (maxWidth * 0.35f).coerceIn(if (compact) 300.dp else 520.dp, 900.dp)
        }
        val screenHeight = if (portrait) {
            (screenWidth / 2.08f).coerceAtMost(maxHeight * 0.31f)
        } else {
            (screenWidth / 2.08f).coerceAtMost(maxHeight * 0.34f)
        }
        val screenTop = maxHeight * 0.245f
        val screenShiftX = 0.dp
        val screenShape = RoundedCornerShape(if (compact) 14.dp else 20.dp)
        val bentoLayout = screenWidth >= 660.dp && screenHeight >= 300.dp
        val tileGap = if (bentoLayout) 10.dp else 6.dp

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(260)) + scaleIn(tween(320), initialScale = 0.97f),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = screenShiftX, y = screenTop)
                .width(screenWidth)
                .height(screenHeight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(34.dp, screenShape, ambientColor = Color.Black, spotColor = accent.copy(alpha = 0.30f))
                    .clip(screenShape)
                    .background(Color(0xFF060D16))
                    .border(1.dp, premiumBorderBrush(), screenShape)
            ) {
                Image(
                    painter = painterResource(Res.drawable.monitor_dashboard_ambient_v2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().alpha(if (compact) 0.36f else 0.48f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xA607111C),
                                    Color(0xC808111B),
                                    Color(0xEE050A12)
                                )
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color.Transparent,
                                    accent.copy(alpha = 0.88f),
                                    Color(0xFF72E0C0).copy(alpha = 0.70f),
                                    amber.copy(alpha = 0.48f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                if (activeSection == null) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(if (bentoLayout) 16.dp else 10.dp),
                        verticalArrangement = Arrangement.spacedBy(tileGap)
                    ) {
                        MonitorDashboardHeader(compact = !bentoLayout)
                        if (bentoLayout) {
                            Row(
                                modifier = Modifier.fillMaxWidth().weight(1.18f),
                                horizontalArrangement = Arrangement.spacedBy(tileGap)
                            ) {
                                listOf(PortfolioSection.CV, PortfolioSection.PROJECTS).forEach { section ->
                                    MonitorMenuTile(
                                        section = section,
                                        featured = true,
                                        compact = false,
                                        onClick = { onSectionSelected(section) },
                                        modifier = Modifier.weight(1f).fillMaxSize()
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth().weight(0.86f),
                                horizontalArrangement = Arrangement.spacedBy(tileGap)
                            ) {
                                listOf(
                                    PortfolioSection.ABOUT,
                                    PortfolioSection.EXPERIENCE,
                                    PortfolioSection.CONTACT,
                                    PortfolioSection.SOCIAL
                                ).forEach { section ->
                                    MonitorMenuTile(
                                        section = section,
                                        featured = false,
                                        compact = false,
                                        onClick = { onSectionSelected(section) },
                                        modifier = Modifier.weight(1f).fillMaxSize()
                                    )
                                }
                            }
                        } else {
                            PortfolioSection.entries.chunked(2).forEachIndexed { rowIndex, rowSections ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().weight(1f),
                                    horizontalArrangement = Arrangement.spacedBy(tileGap)
                                ) {
                                    rowSections.forEach { section ->
                                        MonitorMenuTile(
                                            section = section,
                                            featured = rowIndex == 0,
                                            compact = true,
                                            onClick = { onSectionSelected(section) },
                                            modifier = Modifier.weight(1f).fillMaxSize()
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    PortfolioDetailPanel(
                        section = activeSection,
                        onBack = onBack,
                        compact = true,
                        maxHeight = screenHeight,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun MonitorDashboardHeader(compact: Boolean) {
    val transition = rememberInfiniteTransition(label = "monitor-online")
    val pulse by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(780), RepeatMode.Reverse),
        label = "monitor-online-pulse"
    )
    Row(
        modifier = Modifier.fillMaxWidth().height(if (compact) 30.dp else 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (compact) 7.dp else 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(if (compact) 28.dp else 36.dp)
                    .clip(RoundedCornerShape(if (compact) 8.dp else 11.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF153044), Color(0xFF0A1724))
                        )
                    )
                    .border(1.dp, accent.copy(alpha = 0.52f), RoundedCornerShape(if (compact) 8.dp else 11.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "DN",
                    color = primaryText,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Black,
                    fontSize = if (compact) 8.sp else 10.sp
                )
            }
            Column {
                Text(
                    "DAVID NAVARRO",
                    color = primaryText,
                    fontWeight = FontWeight.Black,
                    fontSize = if (compact) 8.sp else 11.sp,
                    letterSpacing = 0.3.sp
                )
                Text(
                    "ANDROID · KMP · COMPOSE",
                    color = accent.copy(alpha = 0.78f),
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = if (compact) 5.sp else 7.sp,
                    letterSpacing = 0.65.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(Color(0x9B0B1722))
                .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(999.dp))
                .padding(horizontal = if (compact) 7.dp else 10.dp, vertical = if (compact) 4.dp else 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                Modifier
                    .size(if (compact) 4.dp else 6.dp)
                    .background(Color(0xFF72E0C0).copy(alpha = pulse), CircleShape)
            )
            Text(
                if (compact) "ONLINE" else "PORTFOLIO · ONLINE",
                color = primaryText.copy(alpha = 0.80f),
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = if (compact) 5.sp else 7.sp,
                letterSpacing = 0.6.sp
            )
        }
    }
}

@Composable
private fun MonitorMenuTile(
    section: PortfolioSection,
    featured: Boolean,
    compact: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val highlight = monitorSectionAccent(section)
    val shape = RoundedCornerShape(if (compact) 10.dp else 15.dp)
    Box(
        modifier = modifier
            .shadow(
                if (featured) 18.dp else 10.dp,
                shape,
                ambientColor = Color.Black,
                spotColor = highlight.copy(alpha = 0.18f)
            )
            .clip(shape)
            .clickable(onClick = onClick)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xE9162736),
                        Color(0xE30B1622),
                        Color(0xF309111B)
                    )
                )
            )
            .border(1.dp, premiumBorderBrush(highlight), shape)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(if (featured) 3.dp else 2.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            highlight.copy(alpha = 0.92f),
                            highlight.copy(alpha = 0.28f),
                            Color.Transparent
                        )
                    )
                )
        )
        if (compact) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MonitorSectionIcon(section, highlight, compact = true)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        section.label.uppercase(),
                        color = primaryText,
                        fontWeight = FontWeight.Black,
                        fontSize = 9.sp,
                        maxLines = 1
                    )
                    Text(
                        section.subtitle.uppercase(),
                        color = secondaryText.copy(alpha = 0.72f),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 5.sp,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Text(
                    section.number,
                    color = highlight,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Black,
                    fontSize = 6.sp
                )
            }
        } else if (featured) {
            Column(
                modifier = Modifier.fillMaxSize().padding(if (compact) 9.dp else 13.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MonitorSectionIcon(section, highlight, compact = compact)
                    MonitorNumberBadge(section.number, highlight, compact = compact)
                }
                Column {
                    Text(
                        monitorSectionMeta(section),
                        color = highlight.copy(alpha = 0.86f),
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (compact) 5.sp else 7.sp,
                        letterSpacing = 0.75.sp
                    )
                    Text(
                        section.label,
                        color = primaryText,
                        fontWeight = FontWeight.Black,
                        fontSize = if (compact) 10.sp else 15.sp,
                        maxLines = 1,
                        modifier = Modifier.padding(top = if (compact) 2.dp else 4.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = if (compact) 2.dp else 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            section.subtitle.uppercase(),
                            color = secondaryText.copy(alpha = 0.76f),
                            fontFamily = FontFamily.Monospace,
                            fontSize = if (compact) 5.sp else 7.sp,
                            maxLines = 1
                        )
                        Text(
                            "ABRIR  ↗",
                            color = highlight,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black,
                            fontSize = if (compact) 5.sp else 7.sp
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MonitorSectionIcon(section, highlight, compact = true)
                    Text(
                        section.number,
                        color = highlight.copy(alpha = 0.72f),
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Black,
                        fontSize = 7.sp
                    )
                }
                Column {
                    Text(
                        section.label.uppercase(),
                        color = primaryText,
                        fontWeight = FontWeight.Black,
                        fontSize = 10.sp,
                        maxLines = 1
                    )
                    Text(
                        section.subtitle.uppercase(),
                        color = secondaryText.copy(alpha = 0.72f),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 6.sp,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MonitorSectionIcon(section: PortfolioSection, color: Color, compact: Boolean) {
    val iconSize = if (compact) 28.dp else 38.dp
    Box(
        modifier = Modifier
            .size(iconSize + 6.dp)
            .background(color.copy(alpha = 0.08f), CircleShape)
            .border(1.dp, color.copy(alpha = 0.16f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(iconSize)
                .background(
                    Brush.radialGradient(
                        listOf(color.copy(alpha = 0.18f), Color(0xFF0A131D))
                    ),
                    CircleShape
                )
                .border(1.dp, color.copy(alpha = 0.52f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            MenuSymbol(section = section, compact = compact)
        }
    }
}

@Composable
private fun MonitorNumberBadge(number: String, color: Color, compact: Boolean) {
    Text(
        number,
        color = color,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Black,
        fontSize = if (compact) 6.sp else 8.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(color.copy(alpha = 0.10f))
            .border(1.dp, color.copy(alpha = 0.28f), RoundedCornerShape(999.dp))
            .padding(horizontal = if (compact) 6.dp else 8.dp, vertical = if (compact) 3.dp else 5.dp)
    )
}

private fun monitorSectionMeta(section: PortfolioSection): String = when (section) {
    PortfolioSection.CV -> "DOCUMENTO · PERFIL"
    PortfolioSection.PROJECTS -> "APPS · CÓDIGO"
    PortfolioSection.ABOUT -> "IDENTIDAD"
    PortfolioSection.EXPERIENCE -> "TRAYECTORIA"
    PortfolioSection.CONTACT -> "CONTACTO"
    PortfolioSection.SOCIAL -> "COMUNIDAD"
}

private fun monitorSectionAccent(section: PortfolioSection): Color = when (section) {
    PortfolioSection.CV -> accent
    PortfolioSection.PROJECTS -> Color(0xFF8EC5FF)
    PortfolioSection.ABOUT -> Color(0xFF72E0C0)
    PortfolioSection.EXPERIENCE -> amber
    PortfolioSection.CONTACT -> Color(0xFF63B7FF)
    PortfolioSection.SOCIAL -> Color(0xFFC99BFF)
}

@Composable
private fun HeadOrbitMenu(
    compact: Boolean,
    activeSection: PortfolioSection?,
    onSectionSelected: (PortfolioSection) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var revealedCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        PortfolioSection.entries.indices.forEach { index ->
            delay(if (index == 0) 80 else 65)
            revealedCount = index + 1
        }
    }

    BoxWithConstraints(modifier) {
        val sourceAspect = 1672f / 941f
        val viewAspect = maxWidth.value / maxHeight.value
        val scaledHeight = if (viewAspect > sourceAspect) maxWidth / sourceAspect else maxHeight
        val headX = maxWidth * 0.50f
        val headY = scaledHeight * 0.36f - (scaledHeight - maxHeight) / 2f
        val narrow = maxWidth < 430.dp
        val menuWidth = when {
            narrow -> 136.dp
            compact -> (maxWidth * 0.31f).coerceIn(132.dp, 152.dp)
            else -> (maxWidth * 0.235f).coerceIn(268.dp, 304.dp)
        }
        val menuHeight = if (compact) 56.dp else 78.dp
        val clearSpace = when {
            narrow -> 64.dp
            compact -> 82.dp
            else -> (maxWidth * 0.12f).coerceIn(138.dp, 176.dp)
        }
        val rowStep = if (compact) 68.dp else (maxHeight * 0.19f).coerceIn(96.dp, 116.dp)
        val edge = if (compact) 8.dp else 24.dp
        val leftBase = (headX - clearSpace - menuWidth).coerceAtLeast(edge)
        val rightBase = (headX + clearSpace).coerceAtMost(maxWidth - edge - menuWidth)
        val centerY = (headY - menuHeight / 2f).coerceIn(
            72.dp + rowStep * 1.35f,
            maxHeight - menuHeight - edge - rowStep * 1.35f
        )
        val faceRadius = if (compact) 70.dp else 102.dp
        val horizontalScatter = if (compact) 12.dp else 42.dp
        val placements = listOf(
            OrbitPlacement(
                PortfolioSection.CV,
                (leftBase - horizontalScatter * 0.35f).coerceAtLeast(edge),
                centerY - rowStep * 1.42f,
                true
            ),
            OrbitPlacement(
                PortfolioSection.PROJECTS,
                (rightBase + horizontalScatter * 0.50f).coerceAtMost(maxWidth - edge - menuWidth),
                centerY - rowStep * 1.18f,
                false
            ),
            OrbitPlacement(
                PortfolioSection.ABOUT,
                (leftBase - horizontalScatter).coerceAtLeast(edge),
                centerY - rowStep * 0.18f,
                true
            ),
            OrbitPlacement(
                PortfolioSection.EXPERIENCE,
                (rightBase + horizontalScatter * 1.15f).coerceAtMost(maxWidth - edge - menuWidth),
                centerY + rowStep * 0.14f,
                false
            ),
            OrbitPlacement(
                PortfolioSection.CONTACT,
                (leftBase - horizontalScatter * 0.18f).coerceAtLeast(edge),
                centerY + rowStep * 1.16f,
                true
            ),
            OrbitPlacement(
                PortfolioSection.SOCIAL,
                (rightBase + horizontalScatter * 0.22f).coerceAtMost(maxWidth - edge - menuWidth),
                centerY + rowStep * 1.46f,
                false
            )
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            placements
                .filter { activeSection == null || it.section == activeSection }
                .forEach { placement ->
                val startX = if (placement.menuOnLeft) placement.x + menuWidth else placement.x
                val startY = placement.y + menuHeight / 2f
                val endX = headX + if (placement.menuOnLeft) -faceRadius else faceRadius
                val endY = headY + (startY - headY) * 0.32f
                val start = Offset(startX.toPx(), startY.toPx())
                val end = Offset(endX.toPx(), endY.toPx())
                val direction = end.x - start.x
                val connector = Path().apply {
                    moveTo(start.x, start.y)
                    cubicTo(
                        start.x + direction * 0.42f,
                        start.y,
                        end.x - direction * 0.20f,
                        end.y,
                        end.x,
                        end.y
                    )
                }
                drawPath(
                    path = connector,
                    color = accent.copy(alpha = 0.34f),
                    style = Stroke(width = 1.dp.toPx())
                )
                drawCircle(accent.copy(alpha = 0.16f), radius = 6.dp.toPx(), center = end)
                drawCircle(accent.copy(alpha = 0.92f), radius = 2.5.dp.toPx(), center = end)
            }
        }

        if (activeSection == null) {
            placements.forEachIndexed { revealIndex, placement ->
                Box(
                    modifier = Modifier
                        .offset(x = placement.x, y = placement.y)
                        .width(menuWidth)
                        .height(menuHeight)
                ) {
                    AnimatedVisibility(
                        visible = revealedCount > revealIndex,
                        enter = fadeIn(tween(220)) +
                            scaleIn(tween(280), initialScale = 0.95f) +
                            slideInHorizontally(tween(300)) { width ->
                                if (placement.menuOnLeft) width / 4 else -width / 4
                            },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        HeadMenuButton(
                            section = placement.section,
                            compact = compact,
                            menuOnLeft = placement.menuOnLeft,
                            onClick = { onSectionSelected(placement.section) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        } else {
            val selectedPlacement = placements.first { it.section == activeSection }
            val panelWidth = (maxWidth * 0.34f).coerceIn(360.dp, 470.dp)
            val panelMaxHeight = when (activeSection) {
                PortfolioSection.PROJECTS, PortfolioSection.EXPERIENCE ->
                    (maxHeight * 0.58f).coerceIn(340.dp, 510.dp)
                PortfolioSection.ABOUT -> (maxHeight * 0.50f).coerceIn(310.dp, 430.dp)
                else -> (maxHeight * 0.42f).coerceIn(250.dp, 360.dp)
            }
            val expandedX = if (selectedPlacement.menuOnLeft) {
                (selectedPlacement.x + menuWidth - panelWidth).coerceAtLeast(edge)
            } else {
                selectedPlacement.x.coerceAtMost(maxWidth - edge - panelWidth)
            }
            val expandedY = when (activeSection) {
                PortfolioSection.CV, PortfolioSection.PROJECTS -> selectedPlacement.y
                PortfolioSection.ABOUT, PortfolioSection.EXPERIENCE -> selectedPlacement.y - panelMaxHeight * 0.38f
                PortfolioSection.CONTACT, PortfolioSection.SOCIAL -> selectedPlacement.y + menuHeight - panelMaxHeight
            }.coerceIn(72.dp, maxHeight - panelMaxHeight - edge)

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(tween(180)) + scaleIn(tween(300), initialScale = 0.86f),
                modifier = Modifier
                    .offset(x = expandedX, y = expandedY)
                    .width(panelWidth)
            ) {
                PortfolioDetailPanel(
                    section = activeSection,
                    onBack = onBack,
                    compact = false,
                    maxHeight = panelMaxHeight,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun HeadMenuButton(
    section: PortfolioSection,
    compact: Boolean,
    menuOnLeft: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val label = when (section) {
        PortfolioSection.CV -> "MI CV"
        PortfolioSection.PROJECTS -> "PROYECTOS"
        PortfolioSection.ABOUT -> "SOBRE MÍ"
        PortfolioSection.EXPERIENCE -> "EXPERIENCIA"
        PortfolioSection.CONTACT -> "EMAIL"
        PortfolioSection.SOCIAL -> "REDES"
    }
    val descriptor = when (section) {
        PortfolioSection.CV -> "TRAYECTORIA"
        PortfolioSection.PROJECTS -> "APPS + CÓDIGO"
        PortfolioSection.ABOUT -> "PERFIL"
        PortfolioSection.EXPERIENCE -> "RECORRIDO"
        PortfolioSection.CONTACT -> "CONTACTO"
        PortfolioSection.SOCIAL -> "LINKS"
    }
    val nodeSize = if (compact) 44.dp else 58.dp
    val plateShape = if (menuOnLeft) {
        RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, topEnd = 9.dp, bottomEnd = 9.dp)
    } else {
        RoundedCornerShape(topStart = 9.dp, bottomStart = 9.dp, topEnd = 20.dp, bottomEnd = 20.dp)
    }

    BoxWithConstraints(
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        val plateWidth = maxWidth - nodeSize / 2f
        Box(
            modifier = Modifier
                .align(if (menuOnLeft) Alignment.CenterStart else Alignment.CenterEnd)
                .width(plateWidth)
                .fillMaxSize()
                .shadow(24.dp, plateShape, ambientColor = Color.Black, spotColor = Color.Black)
                .clip(plateShape)
                .background(
                    Brush.horizontalGradient(
                        if (menuOnLeft) {
                            listOf(Color(0xF20A1018), Color(0xF4111D28))
                        } else {
                            listOf(Color(0xF4111D28), Color(0xF20A1018))
                        }
                    )
                )
                .border(1.dp, Color.White.copy(alpha = 0.13f), plateShape)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, Color.White.copy(alpha = 0.20f), Color.Transparent)
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(if (menuOnLeft) Alignment.CenterEnd else Alignment.CenterStart)
                    .width(2.dp)
                    .height(if (compact) 20.dp else 28.dp)
                    .background(accent.copy(alpha = 0.74f), RoundedCornerShape(99.dp))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
                    .padding(
                        start = if (menuOnLeft) 14.dp else nodeSize / 2f + 13.dp,
                        end = if (menuOnLeft) nodeSize / 2f + 13.dp else 14.dp
                    )
            ) {
                Text(
                    label,
                    color = primaryText,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (compact) 10.sp else 15.sp,
                    letterSpacing = if (compact) 0.sp else 0.25.sp,
                    maxLines = 1
                )
                if (!compact) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            descriptor,
                            color = secondaryText.copy(alpha = 0.74f),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 8.sp,
                            letterSpacing = 0.7.sp,
                            maxLines = 1
                        )
                        Text(
                            section.number,
                            color = primaryText.copy(alpha = 0.30f),
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .align(if (menuOnLeft) Alignment.CenterStart else Alignment.CenterEnd)
                    .padding(horizontal = 8.dp)
                    .size(4.dp)
                    .background(amber.copy(alpha = 0.92f), CircleShape)
            )
        }
        Box(
            modifier = Modifier
                .align(if (menuOnLeft) Alignment.CenterEnd else Alignment.CenterStart)
                .size(nodeSize + 10.dp)
                .background(accent.copy(alpha = 0.06f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(if (menuOnLeft) Alignment.CenterEnd else Alignment.CenterStart)
                .size(nodeSize)
                .shadow(14.dp, CircleShape, ambientColor = Color.Black, spotColor = Color.Black)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFF183241), Color(0xFF0A1119))
                    )
                )
                .border(1.dp, Color.White.copy(alpha = 0.28f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(nodeSize - 7.dp)
                    .border(1.dp, accent.copy(alpha = 0.16f), CircleShape)
            )
            MenuSymbol(section = section, compact = compact)
        }
    }
}

@Composable
private fun MenuSymbol(section: PortfolioSection, compact: Boolean) {
    val symbolSize = if (compact) 19.dp else 24.dp
    Canvas(modifier = Modifier.size(symbolSize)) {
        val color = primaryText.copy(alpha = 0.92f)
        val stroke = Stroke(width = if (compact) 1.2.dp.toPx() else 1.35.dp.toPx())
        when (section) {
            PortfolioSection.CV -> {
                val left = size.width * 0.24f
                val top = size.height * 0.12f
                val width = size.width * 0.52f
                val height = size.height * 0.76f
                drawRect(color, Offset(left, top), Size(width, height), style = stroke)
                drawLine(color, Offset(size.width * 0.35f, size.height * 0.47f), Offset(size.width * 0.65f, size.height * 0.47f), stroke.width)
                drawLine(color, Offset(size.width * 0.35f, size.height * 0.64f), Offset(size.width * 0.61f, size.height * 0.64f), stroke.width)
            }
            PortfolioSection.PROJECTS -> {
                val cell = size.width * 0.27f
                val gap = size.width * 0.13f
                val start = size.width * 0.16f
                repeat(2) { row ->
                    repeat(2) { column ->
                        drawRect(
                            color,
                            Offset(start + column * (cell + gap), start + row * (cell + gap)),
                            Size(cell, cell),
                            style = stroke
                        )
                    }
                }
            }
            PortfolioSection.ABOUT -> {
                drawCircle(color, radius = size.width * 0.16f, center = Offset(size.width * 0.50f, size.height * 0.32f), style = stroke)
                drawArc(
                    color = color,
                    startAngle = 205f,
                    sweepAngle = 130f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.18f, size.height * 0.42f),
                    size = Size(size.width * 0.64f, size.height * 0.46f),
                    style = stroke
                )
            }
            PortfolioSection.EXPERIENCE -> {
                drawLine(color, Offset(size.width * 0.31f, size.height * 0.13f), Offset(size.width * 0.31f, size.height * 0.87f), stroke.width)
                listOf(0.24f, 0.50f, 0.76f).forEachIndexed { index, y ->
                    drawCircle(color, radius = size.width * 0.07f, center = Offset(size.width * 0.31f, size.height * y), style = stroke)
                    drawLine(
                        color,
                        Offset(size.width * 0.43f, size.height * y),
                        Offset(size.width * if (index == 1) 0.86f else 0.74f, size.height * y),
                        stroke.width
                    )
                }
            }
            PortfolioSection.CONTACT -> {
                val topLeft = Offset(size.width * 0.10f, size.height * 0.22f)
                val envelopeSize = Size(size.width * 0.80f, size.height * 0.58f)
                drawRect(color, topLeft, envelopeSize, style = stroke)
                drawLine(color, topLeft, Offset(size.width * 0.50f, size.height * 0.56f), stroke.width)
                drawLine(color, Offset(size.width * 0.90f, size.height * 0.22f), Offset(size.width * 0.50f, size.height * 0.56f), stroke.width)
            }
            PortfolioSection.SOCIAL -> {
                val nodes = listOf(
                    Offset(size.width * 0.50f, size.height * 0.18f),
                    Offset(size.width * 0.22f, size.height * 0.72f),
                    Offset(size.width * 0.78f, size.height * 0.72f)
                )
                drawLine(color, nodes[0], nodes[1], stroke.width)
                drawLine(color, nodes[0], nodes[2], stroke.width)
                drawLine(color, nodes[1], nodes[2], stroke.width)
                nodes.forEach { node ->
                    drawCircle(color, radius = size.width * 0.105f, center = node, style = stroke)
                }
            }
        }
    }
}

@Composable
private fun PortfolioDetailOverlay(
    section: PortfolioSection,
    compact: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val panelWidth = (maxWidth * 0.36f).coerceIn(420.dp, 520.dp)
        val panelGap = (maxWidth * 0.07f).coerceIn(92.dp, 138.dp)
        val leftPanelX = (maxWidth * 0.50f - panelGap - panelWidth).coerceAtLeast(24.dp)
        val rightPanelX = (maxWidth * 0.50f + panelGap).coerceAtMost(maxWidth - panelWidth - 24.dp)
        val desktopPanelModifier = when (section) {
            PortfolioSection.CV -> Modifier.align(Alignment.TopStart).offset(x = leftPanelX, y = 82.dp)
            PortfolioSection.PROJECTS -> Modifier.align(Alignment.TopStart).offset(x = rightPanelX, y = 82.dp)
            PortfolioSection.ABOUT -> Modifier.align(Alignment.CenterStart).offset(x = leftPanelX)
            PortfolioSection.EXPERIENCE -> Modifier.align(Alignment.CenterStart).offset(x = rightPanelX)
            PortfolioSection.CONTACT -> Modifier.align(Alignment.BottomStart).offset(x = leftPanelX, y = (-28).dp)
            PortfolioSection.SOCIAL -> Modifier.align(Alignment.BottomStart).offset(x = rightPanelX, y = (-28).dp)
        }
        val opensFromLeft = section == PortfolioSection.CV ||
            section == PortfolioSection.ABOUT ||
            section == PortfolioSection.CONTACT
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (compact) {
                        Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.30f), Color.Black.copy(alpha = 0.74f)))
                    } else {
                        Brush.horizontalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.42f),
                                Color.Black.copy(alpha = 0.12f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.12f),
                                Color.Black.copy(alpha = 0.42f)
                            )
                        )
                    }
                )
        )
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(240)) + scaleIn(tween(320), initialScale = 0.94f) +
                if (compact) {
                    slideInVertically(tween(300), initialOffsetY = { 36 })
                } else {
                    slideInHorizontally(tween(360), initialOffsetX = { width ->
                        if (opensFromLeft) -width / 5 else width / 5
                    })
                },
            modifier = if (compact) {
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
            } else {
                desktopPanelModifier.width(panelWidth)
            }
        ) {
            PortfolioDetailPanel(
                section = section,
                onBack = onBack,
                compact = compact,
                maxHeight = if (compact) {
                    maxHeight * 0.62f
                } else {
                    (maxHeight * 0.44f).coerceIn(270.dp, 430.dp)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PortfolioDetailPanel(
    section: PortfolioSection,
    onBack: () -> Unit,
    compact: Boolean,
    maxHeight: Dp,
    modifier: Modifier = Modifier
) {
    val panelShape = RoundedCornerShape(if (compact) 24.dp else 22.dp)
    Column(
        modifier = modifier
            .heightIn(max = maxHeight)
            .shadow(34.dp, panelShape, ambientColor = Color.Black, spotColor = accent.copy(alpha = 0.18f))
            .clip(panelShape)
            .background(premiumSurfaceBrush())
            .border(1.dp, premiumBorderBrush(), panelShape)
            .verticalScroll(rememberScrollState())
            .padding(if (compact) 18.dp else 22.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .shadow(10.dp, RoundedCornerShape(11.dp), ambientColor = Color.Black, spotColor = accent.copy(alpha = 0.22f))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onBack)
                    .background(premiumInsetBrush())
                    .border(1.dp, premiumBorderBrush(), RoundedCornerShape(10.dp))
                    .padding(horizontal = 11.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text("←", color = accent, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("MENÚ", color = primaryText, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 9.sp)
            }
            Text(
                "${section.number} / PORTFOLIO",
                color = accent,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 9.sp,
                letterSpacing = 0.8.sp
            )
        }
        Text(
            section.label,
            color = primaryText,
            fontWeight = FontWeight.Black,
            fontSize = if (compact) 27.sp else 31.sp,
            modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
        )
        SectionContent(section)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SectionContent(section: PortfolioSection) {
    val uriHandler = LocalUriHandler.current
    when (section) {
        PortfolioSection.CV -> {
            SectionLead("Mi formación, experiencia y tecnologías en un único documento.")
            PrimaryAction("ABRIR CV") { uriHandler.openUri("/assets/cv/cv.pdf") }
        }
        PortfolioSection.PROJECTS -> {
            SectionLead("Proyectos Android creados para resolver problemas reales y explorar nuevas ideas.")
            ProjectItem(
                title = "PermissionProtect",
                description = "Control y aprendizaje sobre los permisos de las aplicaciones Android.",
                tags = "Kotlin · Jetpack Compose",
                onClick = { uriHandler.openUri("https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es") }
            )
            ProjectItem(
                title = "Glassmorphism Compose",
                description = "Librería de efectos glassmorphism con RenderEffect y soporte NDK.",
                tags = "Compose · Kotlin · C++",
                onClick = { uriHandler.openUri("https://github.com/Deiivid/Glassmorphism-Compose") }
            )
            ProjectItem(
                title = "Clean Architecture Compose",
                description = "Proyecto multimódulo con separación de dominio, datos y presentación.",
                tags = "Clean Architecture · Koin · Detekt",
                onClick = { uriHandler.openUri("https://github.com/Deiivid/Clean_Arquitecture_Compose") }
            )
        }
        PortfolioSection.ABOUT -> {
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(Res.drawable.image_david),
                    contentDescription = "David Navarro",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(82.dp)
                        .shadow(14.dp, RoundedCornerShape(18.dp), ambientColor = Color.Black, spotColor = accent.copy(alpha = 0.24f))
                        .clip(RoundedCornerShape(18.dp))
                        .border(1.dp, premiumBorderBrush(), RoundedCornerShape(18.dp))
                )
                Column {
                    Text("Android Developer", color = accent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Kotlin · Compose · KMP", color = primaryText, fontWeight = FontWeight.Black, fontSize = 16.sp)
                }
            }
            SectionLead(
                "Me apasiona crear aplicaciones modernas, mantenibles y rápidas, cuidando tanto la arquitectura como la experiencia de usuario.",
                modifier = Modifier.padding(top = 16.dp)
            )
            TagCloud(listOf("Kotlin", "Jetpack Compose", "KMP", "Coroutines", "Flow", "Clean Architecture", "Testing", "CI/CD"))
        }
        PortfolioSection.EXPERIENCE -> {
            ExperienceItem("ACTUALMENTE", "Hiberus", "Android Developer para cliente del sector bancario · Jetpack Compose")
            ExperienceItem("2024", "Especialización", "Clean Architecture, proyectos personales y desarrollo móvil avanzado")
            ExperienceItem("2021 — 2023", "Desarrollo móvil", "Primeros pasos profesionales, liderazgo de proyectos y creación de una librería")
            ExperienceItem("2021", "Hiberus Héroes y Heroínas", "Formación intensiva en desarrollo de aplicaciones")
            ExperienceItem("2019 — 2021", "DAM", "Técnico Superior en Desarrollo de Aplicaciones Multiplataforma")
        }
        PortfolioSection.CONTACT -> {
            SectionLead("¿Tienes una idea, un proyecto o simplemente quieres saludar? Escríbeme.")
            Text(contactEmail, color = primaryText, fontWeight = FontWeight.Black, fontSize = 15.sp)
            PrimaryAction("ENVIAR EMAIL", modifier = Modifier.padding(top = 16.dp)) {
                uriHandler.openUri("mailto:$contactEmail?subject=Contacto%20desde%20tu%20portfolio")
            }
        }
        PortfolioSection.SOCIAL -> {
            SectionLead("Código, artículos y todo lo que voy construyendo.")
            SocialAction("GitHub", "@Deiivid", accent) { uriHandler.openUri("https://github.com/Deiivid") }
            SocialAction("Medium", "@davidnavarrom3", amber) { uriHandler.openUri("https://medium.com/@davidnavarrom3") }
        }
    }
}

@Composable
private fun SectionLead(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        color = secondaryText,
        fontSize = 13.sp,
        lineHeight = 19.sp,
        modifier = modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun PrimaryAction(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = accent),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Text(text, color = Color(0xFF06131A), fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Black, fontSize = 10.sp)
    }
}

@Composable
private fun ProjectItem(title: String, description: String, tags: String, onClick: () -> Unit) {
    val itemShape = RoundedCornerShape(15.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp)
            .shadow(12.dp, itemShape, ambientColor = Color.Black, spotColor = accent.copy(alpha = 0.12f))
            .clip(itemShape)
            .clickable(onClick = onClick)
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(), itemShape)
            .padding(horizontal = 14.dp, vertical = 13.dp)
    ) {
        Text(title, color = primaryText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(description, color = secondaryText, fontSize = 11.sp, lineHeight = 16.sp, modifier = Modifier.padding(top = 4.dp))
        Text(tags, color = violet, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold, fontSize = 8.sp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
private fun ExperienceItem(period: String, title: String, description: String) {
    val itemShape = RoundedCornerShape(15.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 11.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
                .shadow(11.dp, itemShape, ambientColor = Color.Black, spotColor = warm.copy(alpha = 0.10f))
                .clip(itemShape)
                .background(premiumInsetBrush())
                .border(1.dp, premiumBorderBrush(warm), itemShape)
                .padding(start = 20.dp, top = 13.dp, end = 14.dp, bottom = 14.dp)
        ) {
            Text(period, color = warm, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 8.sp, letterSpacing = 0.7.sp)
            Text(title, color = primaryText, fontWeight = FontWeight.Black, fontSize = 15.sp, modifier = Modifier.padding(top = 3.dp))
            Text(description, color = secondaryText, fontSize = 11.sp, lineHeight = 16.sp, modifier = Modifier.padding(top = 4.dp))
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .shadow(9.dp, CircleShape, ambientColor = Color.Black, spotColor = warm.copy(alpha = 0.65f))
                .size(13.dp)
                .background(Color(0xFF18202A), CircleShape)
                .border(2.dp, warm, CircleShape)
        )
    }
}

@Composable
private fun TagCloud(tags: List<String>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(7.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
        tags.forEach { tag ->
            val chipShape = RoundedCornerShape(999.dp)
            Text(
                tag,
                color = mint,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 9.sp,
                modifier = Modifier
                    .shadow(7.dp, chipShape, ambientColor = Color.Black, spotColor = mint.copy(alpha = 0.12f))
                    .background(premiumInsetBrush(), chipShape)
                    .border(1.dp, premiumBorderBrush(mint), chipShape)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun SocialAction(name: String, handle: String, color: Color, onClick: () -> Unit) {
    val itemShape = RoundedCornerShape(15.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp)
            .shadow(12.dp, itemShape, ambientColor = Color.Black, spotColor = color.copy(alpha = 0.12f))
            .clip(itemShape)
            .clickable(onClick = onClick)
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(color), itemShape)
            .padding(13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(name, color = primaryText, fontWeight = FontWeight.Black, fontSize = 14.sp)
            Text(handle, color = color, fontFamily = FontFamily.Monospace, fontSize = 9.sp)
        }
        Text("ABRIR  ↗", color = color, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Black, fontSize = 8.sp)
    }
}
