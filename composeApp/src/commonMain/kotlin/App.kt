import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.ui.graphics.ColorFilter
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
import androidx.compose.ui.zIndex
import davidweb_kmp.composeapp.generated.resources.Res
import davidweb_kmp.composeapp.generated.resources.github_logo
import davidweb_kmp.composeapp.generated.resources.image_david
import davidweb_kmp.composeapp.generated.resources.medium_logo
import davidweb_kmp.composeapp.generated.resources.monitor_dashboard_ambient_v2
import davidweb_kmp.composeapp.generated.resources.office_pov_seated_lily58_premium_v8
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

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

private data class MonitorGeometry(
    val width: Dp,
    val height: Dp,
    val top: Dp,
    val shiftX: Dp
)

private data class OrbitPlacement(
    val section: PortfolioSection,
    val x: Dp,
    val y: Dp,
    val menuOnLeft: Boolean
)

@Composable
fun App(
    introProgress: Float = 1f,
    onReplayIntro: () -> Unit = {}
) {
    var activeSection by remember { mutableStateOf<PortfolioSection?>(null) }
    val menusVisible = introProgress >= 0.995f

    MaterialTheme {
        Surface(color = Color.Transparent, modifier = Modifier.fillMaxSize()) {
            PortfolioExperience(
                walkProgress = introProgress,
                menusVisible = menusVisible,
                activeSection = activeSection,
                onSectionSelected = { activeSection = it },
                onBack = { activeSection = null },
                onReplay = {
                    activeSection = null
                    onReplayIntro()
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
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
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
        modifier = modifier
    ) {
        FinalOfficeScene(modifier = Modifier.fillMaxSize())
        TopBar(
            compact = compact,
            onReplay = onReplay,
            modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth()
        )
        if (walkProgress < 0.12f) {
            IntroCopy(
                compact = compact,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = if (compact) 22.dp else 48.dp)
            )
        }
        if (walkProgress >= 0.995f) {
            TypingStatus(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(if (compact) 14.dp else 24.dp)
            )
        }
    }
}

@Composable
private fun TopBar(
    compact: Boolean,
    onReplay: () -> Unit,
    modifier: Modifier = Modifier
) {
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
private fun FinalOfficeScene(
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier.clipToBounds()) {
        PovFrame(
            resource = Res.drawable.office_pov_seated_lily58_premium_v8,
            contentDescription = "Vista en primera persona ante un Aurora Lily58 low-profile inalámbrico, un Mac Mini y un móvil Android",
            alpha = 1f,
            scale = 1f
        )
    }
}

private fun ultrawideMonitorGeometry(maxWidth: Dp, maxHeight: Dp): MonitorGeometry {
    val sourceWidth = 3344f
    val sourceHeight = 1882f
    val monitorLeft = 1136f
    val monitorTop = 498f
    val monitorWidth = 1074f
    val monitorHeight = 520f
    val cropScale = maxOf(maxWidth.value / sourceWidth, maxHeight.value / sourceHeight)
    val croppedLeft = (sourceWidth * cropScale - maxWidth.value) / 2f
    val croppedTop = (sourceHeight * cropScale - maxHeight.value) / 2f
    val monitorCenter = (monitorLeft + monitorWidth / 2f) * cropScale - croppedLeft

    return MonitorGeometry(
        width = (monitorWidth * cropScale).dp,
        height = (monitorHeight * cropScale).dp,
        top = (monitorTop * cropScale - croppedTop).dp,
        shiftX = (monitorCenter - maxWidth.value / 2f).dp
    )
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
    val painter = painterResource(resource)
    val density = LocalDensity.current
    Image(
        painter = painter,
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
        val geometry = ultrawideMonitorGeometry(maxWidth, maxHeight)
        val screenShape = RoundedCornerShape(if (compact) 10.dp else 16.dp)
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(260)) + scaleIn(tween(320), initialScale = 0.97f),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = geometry.shiftX, y = geometry.top)
                .width(geometry.width)
                .height(geometry.height)
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
                MonitorOrbitNavigation(
                    activeSection = activeSection,
                    viewportCompact = compact,
                    onSectionSelected = onSectionSelected,
                    onBack = onBack,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun MonitorOrbitNavigation(
    activeSection: PortfolioSection?,
    viewportCompact: Boolean,
    onSectionSelected: (PortfolioSection) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val dense = viewportCompact || maxWidth < 760.dp || maxHeight < 360.dp
        val edge = if (dense) 8.dp else 20.dp
        val nodeWidth = if (dense) {
            (maxWidth * 0.28f).coerceIn(138.dp, 180.dp)
        } else {
            (maxWidth * 0.25f).coerceIn(220.dp, 280.dp)
        }
        val nodeHeight = if (dense) 50.dp else 86.dp
        val hubSize = if (dense) {
            (minOf(maxWidth, maxHeight) * 0.30f).coerceIn(66.dp, 88.dp)
        } else {
            (minOf(maxWidth, maxHeight) * 0.25f).coerceIn(116.dp, 142.dp)
        }
        val hubX = (maxWidth - hubSize) / 2f
        val hubY = (maxHeight - hubSize) / 2f
        val horizontalGap = if (dense) 8.dp else (maxWidth * 0.035f).coerceIn(28.dp, 46.dp)
        val leftX = hubX - nodeWidth - horizontalGap
        val rightX = hubX + hubSize + horizontalGap
        val topY = edge
        val middleY = (maxHeight - nodeHeight) / 2f
        val bottomY = maxHeight - edge - nodeHeight
        val placements = listOf(
            OrbitPlacement(PortfolioSection.CV, leftX, topY, true),
            OrbitPlacement(PortfolioSection.PROJECTS, rightX, topY, false),
            OrbitPlacement(PortfolioSection.ABOUT, leftX, middleY, true),
            OrbitPlacement(PortfolioSection.EXPERIENCE, rightX, middleY, false),
            OrbitPlacement(PortfolioSection.CONTACT, leftX, bottomY, true),
            OrbitPlacement(PortfolioSection.SOCIAL, rightX, bottomY, false)
        )
        val transition = updateTransition(targetState = activeSection, label = "monitor-orbit-selection")
        var renderedSection by remember { mutableStateOf<PortfolioSection?>(activeSection) }
        LaunchedEffect(activeSection) {
            if (activeSection != null) {
                renderedSection = activeSection
            } else if (renderedSection != null) {
                delay(380)
                renderedSection = null
            }
        }
        val orbitAlpha by transition.animateFloat(
            transitionSpec = {
                if (targetState == null) tween(durationMillis = 140, delayMillis = 220) else tween(110)
            },
            label = "monitor-orbit-alpha"
        ) { selected -> if (selected == null) 1f else 0f }
        val detailProgress by transition.animateFloat(
            transitionSpec = { tween(360) },
            label = "monitor-orbit-detail-progress"
        ) { selected -> if (selected == null) 0f else 1f }
        val nodesEnabled = activeSection == null && renderedSection == null && !transition.isRunning
        val detailDense = maxWidth < 660.dp || maxHeight < 300.dp

        MonitorOrbitConnectors(
            placements = placements,
            nodeWidth = nodeWidth,
            nodeHeight = nodeHeight,
            hubX = hubX,
            hubY = hubY,
            hubSize = hubSize,
            modifier = Modifier.fillMaxSize().alpha(orbitAlpha)
        )
        MonitorOrbitHub(
            dense = dense,
            modifier = Modifier
                .offset(x = hubX, y = hubY)
                .size(hubSize)
                .graphicsLayer {
                    alpha = orbitAlpha
                    scaleX = 0.88f + orbitAlpha * 0.12f
                    scaleY = 0.88f + orbitAlpha * 0.12f
                }
        )

        placements.forEach { placement ->
            val nodeX by transition.animateDp(
                transitionSpec = { tween(340) },
                label = "orbit-node-x-${placement.section.name}"
            ) { selected ->
                when {
                    selected == null -> placement.x
                    selected == placement.section -> placement.x
                    else -> hubX + (hubSize - nodeWidth) / 2f
                }
            }
            val nodeY by transition.animateDp(
                transitionSpec = { tween(340) },
                label = "orbit-node-y-${placement.section.name}"
            ) { selected ->
                when {
                    selected == null -> placement.y
                    selected == placement.section -> placement.y
                    else -> hubY + (hubSize - nodeHeight) / 2f
                }
            }
            val nodeAlpha by transition.animateFloat(
                transitionSpec = {
                    if (targetState == null) tween(durationMillis = 180, delayMillis = 180) else tween(110)
                },
                label = "orbit-node-alpha-${placement.section.name}"
            ) { selected -> if (selected == null) 1f else 0f }
            val nodeScale by transition.animateFloat(
                transitionSpec = { tween(340) },
                label = "orbit-node-scale-${placement.section.name}"
            ) { selected ->
                when {
                    selected == null -> 1f
                    selected == placement.section -> 0.96f
                    else -> 0.68f
                }
            }

            Box(
                modifier = Modifier
                    .offset(x = nodeX, y = nodeY)
                    .width(nodeWidth)
                    .height(nodeHeight)
                    .zIndex(1f)
                    .graphicsLayer {
                        alpha = nodeAlpha
                        scaleX = nodeScale
                        scaleY = nodeScale
                    }
            ) {
                MonitorOrbitNode(
                    section = placement.section,
                    dense = dense,
                    menuOnLeft = placement.menuOnLeft,
                    enabled = nodesEnabled,
                    onClick = { onSectionSelected(placement.section) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        val displayedSection = activeSection ?: renderedSection
        displayedSection?.let { section ->
            val placement = placements.first { it.section == section }
            val panelWidth = maxWidth - edge * 2f
            val panelHeight = maxHeight - edge * 2f
            val expandedX = edge
            val expandedY = edge
            val pivotX = (
                (placement.x + nodeWidth / 2f - expandedX).value / panelWidth.value
            ).coerceIn(0f, 1f)
            val pivotY = (
                (placement.y + nodeHeight / 2f - expandedY).value / panelHeight.value
            ).coerceIn(0f, 1f)
            val startScaleX = (nodeWidth.value / panelWidth.value).coerceIn(0.08f, 1f)
            val startScaleY = (nodeHeight.value / panelHeight.value).coerceIn(0.08f, 1f)

            Box(
                modifier = Modifier
                    .offset(x = expandedX, y = expandedY)
                    .width(panelWidth)
                    .height(panelHeight)
                    .zIndex(if (detailProgress > 0.18f) 3f else 0f)
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(pivotX, pivotY)
                        scaleX = startScaleX + (1f - startScaleX) * detailProgress
                        scaleY = startScaleY + (1f - startScaleY) * detailProgress
                        alpha = detailProgress
                    }
            ) {
                MonitorSectionDetail(
                    section = section,
                    onBack = onBack,
                    dense = detailDense,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun MonitorSectionDetail(
    section: PortfolioSection,
    onBack: () -> Unit,
    dense: Boolean,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val ultraCompact = maxHeight < 180.dp
        val panelShape = RoundedCornerShape(if (dense) 10.dp else 16.dp)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .shadow(24.dp, panelShape, ambientColor = Color.Black, spotColor = accent.copy(alpha = 0.18f))
                .clip(panelShape)
                .background(premiumSurfaceBrush())
                .border(1.dp, premiumBorderBrush(monitorSectionAccent(section)), panelShape)
                .padding(
                    horizontal = if (dense) 10.dp else 18.dp,
                    vertical = if (ultraCompact) 5.dp else if (dense) 8.dp else 14.dp
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onBack)
                        .background(premiumInsetBrush())
                        .border(1.dp, accent.copy(alpha = 0.34f), RoundedCornerShape(8.dp))
                        .padding(
                            horizontal = if (dense) 7.dp else 10.dp,
                            vertical = if (ultraCompact) 2.dp else if (dense) 4.dp else 7.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text("←", color = accent, fontWeight = FontWeight.Black, fontSize = if (dense) 8.sp else 11.sp)
                    Text("MENÚ", color = primaryText, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Black, fontSize = if (dense) 6.sp else 8.sp)
                }
                Text(
                    if (ultraCompact) {
                        "${section.number} · ${section.label.uppercase()}"
                    } else {
                        "${section.number} / PORTFOLIO"
                    },
                    color = monitorSectionAccent(section),
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Black,
                    fontSize = if (dense) 6.sp else 8.sp,
                    letterSpacing = 0.5.sp
                )
            }
            if (!ultraCompact) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = if (dense) 5.dp else 9.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(if (dense) 24.dp else 34.dp)
                            .background(monitorSectionAccent(section).copy(alpha = 0.10f), CircleShape)
                            .border(1.dp, monitorSectionAccent(section).copy(alpha = 0.42f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        MenuSymbol(section, compact = true)
                    }
                    Column {
                        Text(
                            section.label,
                            color = primaryText,
                            fontWeight = FontWeight.Black,
                            fontSize = if (dense) 14.sp else 20.sp
                        )
                        Text(
                            section.subtitle.uppercase(),
                            color = secondaryText.copy(alpha = 0.72f),
                            fontFamily = FontFamily.Monospace,
                            fontSize = if (dense) 5.sp else 7.sp,
                            letterSpacing = 0.6.sp
                        )
                    }
                }
            }
            MonitorWideSectionContent(
                section = section,
                dense = dense,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = if (ultraCompact) 3.dp else if (dense) 6.dp else 10.dp)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun MonitorWideSectionContent(
    section: PortfolioSection,
    dense: Boolean,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    when (section) {
        PortfolioSection.CV -> Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(if (dense) 8.dp else 14.dp)
        ) {
            MonitorFeatureCard(
                title = "Currículum profesional",
                subtitle = "Experiencia, formación y stack técnico en un único documento.",
                section = PortfolioSection.CV,
                color = accent,
                dense = dense,
                modifier = Modifier.weight(0.46f).fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .weight(0.54f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .background(premiumInsetBrush())
                    .border(1.dp, premiumBorderBrush(), RoundedCornerShape(10.dp))
                    .padding(if (dense) 8.dp else 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(if (dense) 7.dp else 12.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Text("ANDROID DEVELOPER", color = accent, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Black, fontSize = if (dense) 6.sp else 8.sp)
                    Text("Kotlin · Compose · KMP", color = primaryText, fontWeight = FontWeight.Black, fontSize = if (dense) 10.sp else 14.sp, modifier = Modifier.padding(top = 3.dp))
                    if (!dense) {
                        Text("Clean Architecture · Testing · CI/CD", color = secondaryText, fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                }
                MonitorInlineAction("ABRIR CV") { uriHandler.openUri("/assets/cv/cv.pdf") }
            }
        }

        PortfolioSection.PROJECTS -> Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(if (dense) 7.dp else 12.dp)
        ) {
            MonitorProjectCard(
                "PermissionProtect",
                "Control y aprendizaje sobre los permisos de las aplicaciones Android.",
                "Kotlin · Jetpack Compose",
                accent,
                dense,
                Modifier.weight(1f).fillMaxSize()
            ) { uriHandler.openUri("https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es") }
            MonitorProjectCard(
                "Glassmorphism Compose",
                "Librería de efectos glassmorphism con RenderEffect y soporte NDK.",
                "Compose · Kotlin · C++",
                Color(0xFF9E8CFF),
                dense,
                Modifier.weight(1f).fillMaxSize()
            ) { uriHandler.openUri("https://github.com/Deiivid/Glassmorphism-Compose") }
            MonitorProjectCard(
                "Clean Architecture Compose",
                "Proyecto multimódulo con separación de dominio, datos y presentación.",
                "Clean Architecture · Koin · Detekt",
                Color(0xFF68DDC5),
                dense,
                Modifier.weight(1f).fillMaxSize()
            ) { uriHandler.openUri("https://github.com/Deiivid/Clean_Arquitecture_Compose") }
        }

        PortfolioSection.ABOUT -> MonitorAboutSection(
            dense = dense,
            modifier = modifier
        )

        PortfolioSection.EXPERIENCE -> Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(if (dense) 7.dp else 12.dp)
        ) {
            Column(Modifier.weight(1f).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(if (dense) 5.dp else 8.dp)) {
                MonitorExperienceCard("ACTUALMENTE", "Hiberus", "Android Developer · Jetpack Compose", dense, Modifier.weight(1f))
                MonitorExperienceCard("2024", "Especialización", "Clean Architecture y desarrollo móvil avanzado", dense, Modifier.weight(1f))
                MonitorExperienceCard("2021 — 2023", "Desarrollo móvil", "Primeros proyectos y liderazgo técnico", dense, Modifier.weight(1f))
            }
            Column(Modifier.weight(1f).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(if (dense) 5.dp else 8.dp)) {
                MonitorExperienceCard("2021", "Hiberus Héroes y Heroínas", "Formación intensiva en aplicaciones", dense, Modifier.weight(1f))
                MonitorExperienceCard("2019 — 2021", "DAM", "Desarrollo de Aplicaciones Multiplataforma", dense, Modifier.weight(1f))
            }
        }

        PortfolioSection.CONTACT -> Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(if (dense) 8.dp else 14.dp)
        ) {
            MonitorFeatureCard(
                title = "Hablemos",
                subtitle = "¿Tienes una idea, un proyecto o simplemente quieres saludar?",
                section = PortfolioSection.CONTACT,
                color = accent,
                dense = dense,
                modifier = Modifier.weight(0.44f).fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .weight(0.56f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .background(premiumInsetBrush())
                    .border(1.dp, premiumBorderBrush(), RoundedCornerShape(10.dp))
                    .padding(if (dense) 8.dp else 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(if (dense) 7.dp else 12.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Text("EMAIL DIRECTO", color = accent, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Black, fontSize = if (dense) 6.sp else 8.sp)
                    Text(contactEmail, color = primaryText, fontWeight = FontWeight.Black, fontSize = if (dense) 10.sp else 15.sp, modifier = Modifier.padding(top = 5.dp))
                    if (!dense) {
                        Text("Respuesta personal · Sin formularios", color = secondaryText, fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                }
                MonitorInlineAction("ENVIAR EMAIL") { uriHandler.openUri("mailto:$contactEmail?subject=Contacto%20desde%20tu%20portfolio") }
            }
        }

        PortfolioSection.SOCIAL -> Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(if (dense) 8.dp else 14.dp)
        ) {
            MonitorSocialCard(
                index = "01",
                name = "GitHub",
                handle = "@Deiivid",
                description = "Código, librerías y proyectos Android construidos en abierto.",
                eyebrow = "OPEN SOURCE",
                tags = listOf("ANDROID", "KOTLIN", "LIBRERÍAS"),
                icon = Res.drawable.github_logo,
                color = accent,
                dense = dense,
                modifier = Modifier.weight(1f).fillMaxSize()
            ) {
                uriHandler.openUri("https://github.com/Deiivid")
            }
            MonitorSocialCard(
                index = "02",
                name = "Medium",
                handle = "@davidnavarrom3",
                description = "Ideas y aprendizajes sobre Kotlin, Compose y arquitectura móvil.",
                eyebrow = "ARTÍCULOS TÉCNICOS",
                tags = listOf("KOTLIN", "COMPOSE", "ARQUITECTURA"),
                icon = Res.drawable.medium_logo,
                color = amber,
                dense = dense,
                modifier = Modifier.weight(1f).fillMaxSize()
            ) {
                uriHandler.openUri("https://medium.com/@davidnavarrom3")
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun MonitorAboutSection(
    dense: Boolean,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val compactStrip = maxHeight < 100.dp
        val verticalLayout = maxWidth < 430.dp && maxHeight >= 240.dp
        val gap = if (dense) 7.dp else 14.dp

        if (compactStrip) {
            MonitorAboutCompactStrip(Modifier.fillMaxSize())
        } else if (verticalLayout) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(gap)
            ) {
                MonitorAboutIdentityCard(
                    dense = true,
                    modifier = Modifier.fillMaxWidth().weight(1.04f)
                )
                MonitorAboutStackCard(
                    dense = true,
                    modifier = Modifier.fillMaxWidth().weight(0.96f)
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(gap)
            ) {
                MonitorAboutIdentityCard(
                    dense = dense,
                    modifier = Modifier.fillMaxSize().weight(if (dense) 0.46f else 0.50f)
                )
                MonitorAboutStackCard(
                    dense = dense,
                    modifier = Modifier.fillMaxSize().weight(if (dense) 0.54f else 0.50f)
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun MonitorAboutCompactStrip(modifier: Modifier = Modifier) {
    val stripShape = RoundedCornerShape(8.dp)
    Row(
        modifier = modifier
            .clip(stripShape)
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(mint), stripShape)
            .padding(horizontal = 7.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        val avatarShape = RoundedCornerShape(8.dp)
        Image(
            painter = painterResource(Res.drawable.image_david),
            contentDescription = "David Navarro",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(38.dp)
                .clip(avatarShape)
                .border(1.dp, premiumBorderBrush(mint), avatarShape)
        )
        Column(Modifier.weight(0.9f)) {
            Text(
                "David Navarro",
                color = primaryText,
                fontWeight = FontWeight.Black,
                fontSize = 9.sp,
                maxLines = 1
            )
            Text(
                "Android Developer · KMP",
                color = mint,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 6.sp,
                maxLines = 1,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        Box(
            Modifier
                .width(1.dp)
                .height(34.dp)
                .background(accent.copy(alpha = 0.24f))
        )
        Column(Modifier.weight(1.15f)) {
            Text(
                "STACK PRINCIPAL",
                color = accent,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                fontSize = 6.sp,
                letterSpacing = 0.3.sp
            )
            Text(
                "Kotlin · Compose · KMP",
                color = primaryText,
                fontWeight = FontWeight.Bold,
                fontSize = 7.sp,
                maxLines = 1,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun MonitorAboutIdentityCard(
    dense: Boolean,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(if (dense) 9.dp else 14.dp)
    Column(
        modifier = modifier
            .clip(cardShape)
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(mint), cardShape)
            .padding(if (dense) 9.dp else 14.dp),
        verticalArrangement = Arrangement.spacedBy(if (dense) 5.dp else 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (dense) 9.dp else 13.dp)
        ) {
            val avatarShape = RoundedCornerShape(if (dense) 10.dp else 16.dp)
            Image(
                painter = painterResource(Res.drawable.image_david),
                contentDescription = "David Navarro",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(if (dense) 44.dp else 76.dp)
                    .shadow(10.dp, avatarShape, ambientColor = Color.Black, spotColor = mint.copy(alpha = 0.20f))
                    .clip(avatarShape)
                    .border(1.dp, premiumBorderBrush(mint), avatarShape)
            )
            Column(Modifier.weight(1f)) {
                Text(
                    "ANDROID · KMP",
                    color = mint,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Black,
                    fontSize = if (dense) 7.sp else 9.sp,
                    letterSpacing = if (dense) 0.3.sp else 0.8.sp
                )
                Text(
                    "David Navarro",
                    color = primaryText,
                    fontWeight = FontWeight.Black,
                    fontSize = if (dense) 12.sp else 18.sp,
                    modifier = Modifier.padding(top = if (dense) 1.dp else 4.dp)
                )
                Text(
                    "Android Developer",
                    color = secondaryText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = if (dense) 8.sp else 11.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Text(
            if (dense) {
                "Apps móviles rápidas y mantenibles."
            } else {
                "Diseño y desarrollo productos móviles modernos, rápidos y fáciles de mantener."
            },
            color = primaryText.copy(alpha = 0.92f),
            fontSize = if (dense) 7.sp else 11.sp,
            lineHeight = if (dense) 9.sp else 15.sp,
            maxLines = 2,
            modifier = Modifier.padding(vertical = if (dense) 2.dp else 8.dp)
        )

        if (!dense) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                MonitorAboutFact(
                    label = "ESPECIALIDAD",
                    value = "Android · Compose",
                    modifier = Modifier.weight(1f)
                )
                MonitorAboutFact(
                    label = "ENFOQUE",
                    value = "KMP · Arquitectura",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun MonitorAboutFact(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val factShape = RoundedCornerShape(10.dp)
    Column(
        modifier = modifier
            .clip(factShape)
            .background(Color(0x55101C29))
            .border(1.dp, mint.copy(alpha = 0.18f), factShape)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(
            label,
            color = mint,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black,
            fontSize = 6.sp,
            letterSpacing = 0.5.sp
        )
        Text(
            value,
            color = primaryText,
            fontWeight = FontWeight.Bold,
            fontSize = 9.sp,
            maxLines = 1,
            modifier = Modifier.padding(top = 3.dp)
        )
    }
}

@Composable
private fun MonitorAboutStackCard(
    dense: Boolean,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(if (dense) 9.dp else 14.dp)
    val developmentTags = listOf("Kotlin", "Jetpack Compose", "KMP", "Coroutines", "Flow")
    val qualityTags = listOf("Clean Architecture", "Testing", "CI/CD")

    Column(
        modifier = modifier
            .clip(cardShape)
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(accent), cardShape)
            .padding(if (dense) 9.dp else 14.dp),
        verticalArrangement = Arrangement.spacedBy(if (dense) 6.dp else 8.dp)
    ) {
        Column {
            Text(
                "STACK PRINCIPAL",
                color = accent,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                fontSize = if (dense) 7.sp else 10.sp,
                letterSpacing = if (dense) 0.3.sp else 0.8.sp
            )
            Text(
                if (dense) {
                    "Stack para producto móvil real."
                } else {
                    "Tecnologías que uso para construir producto móvil real."
                },
                color = secondaryText,
                fontSize = if (dense) 7.sp else 11.sp,
                lineHeight = if (dense) 9.sp else 15.sp,
                maxLines = if (dense) 1 else 2,
                modifier = Modifier.padding(top = if (dense) 2.dp else 5.dp)
            )

            if (dense) {
                TagCloud(
                    tags = listOf("Kotlin", "Compose", "KMP"),
                    dense = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
                )
                Text(
                    "Coroutines · Flow · Clean Architecture · Testing",
                    color = primaryText.copy(alpha = 0.82f),
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 7.sp,
                    lineHeight = 9.sp,
                    maxLines = 2,
                    modifier = Modifier.padding(top = 5.dp)
                )
            } else {
                MonitorAboutSkillGroup(
                    label = "DESARROLLO",
                    tags = developmentTags,
                    modifier = Modifier.fillMaxWidth().padding(top = 9.dp)
                )
                MonitorAboutSkillGroup(
                    label = "CALIDAD",
                    tags = qualityTags,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }
        }

    }
}

@Composable
private fun MonitorAboutSkillGroup(
    label: String,
    tags: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            label,
            color = secondaryText.copy(alpha = 0.78f),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 6.sp,
            letterSpacing = 0.5.sp
        )
        TagCloud(
            tags = tags,
            dense = false,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
        )
    }
}

@Composable
private fun MonitorFeatureCard(
    title: String,
    subtitle: String,
    section: PortfolioSection,
    color: Color,
    dense: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(color), RoundedCornerShape(10.dp))
            .padding(if (dense) 10.dp else 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(if (dense) 10.dp else 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(if (dense) 54.dp else 78.dp)
                .background(color.copy(alpha = 0.10f), CircleShape)
                .border(1.dp, color.copy(alpha = 0.52f), CircleShape),
            contentAlignment = Alignment.Center
        ) { MenuSymbol(section, compact = false) }
        Column {
            Text(title, color = primaryText, fontWeight = FontWeight.Black, fontSize = if (dense) 9.sp else 16.sp, maxLines = 2)
            Text(
                subtitle,
                color = secondaryText,
                fontSize = if (dense) 5.sp else 10.sp,
                lineHeight = if (dense) 7.sp else 14.sp,
                maxLines = 2,
                modifier = Modifier.padding(top = if (dense) 2.dp else 4.dp)
            )
        }
    }
}

@Composable
private fun MonitorProjectCard(
    title: String,
    description: String,
    tags: String,
    color: Color,
    dense: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(color), RoundedCornerShape(10.dp))
            .padding(if (dense) 6.dp else 14.dp),
        verticalArrangement = if (dense) Arrangement.spacedBy(3.dp) else Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(if (dense) 21.dp else 44.dp)
                .background(color.copy(alpha = 0.10f), CircleShape)
                .border(1.dp, color.copy(alpha = 0.48f), CircleShape),
            contentAlignment = Alignment.Center
        ) { MenuSymbol(PortfolioSection.PROJECTS, compact = true) }
        Column {
            Text(title, color = primaryText, fontWeight = FontWeight.Black, fontSize = if (dense) 7.sp else 11.sp, maxLines = if (dense) 2 else 1)
            Text(description, color = secondaryText, fontSize = if (dense) 5.sp else 8.sp, lineHeight = if (dense) 7.sp else 11.sp, maxLines = if (dense) 1 else 2, modifier = Modifier.padding(top = if (dense) 1.dp else 3.dp))
            Text(tags, color = color, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = if (dense) 4.sp else 7.sp, maxLines = 1, modifier = Modifier.padding(top = if (dense) 2.dp else 5.dp))
        }
        if (!dense) {
            Text("ABRIR  ↗", color = color, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Black, fontSize = 7.sp)
        }
    }
}

@Composable
private fun MonitorInlineAction(text: String, onClick: () -> Unit) {
    Text(
        text,
        color = Color(0xFF06131A),
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Black,
        fontSize = 6.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .background(accent)
            .padding(horizontal = 9.dp, vertical = 5.dp)
    )
}

@Composable
private fun MonitorExperienceCard(period: String, title: String, description: String, dense: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(9.dp))
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(amber), RoundedCornerShape(9.dp))
            .padding(horizontal = if (dense) 8.dp else 12.dp, vertical = if (dense) 5.dp else 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(Modifier.size(if (dense) 7.dp else 9.dp).background(Color(0xFF17202A), CircleShape).border(2.dp, amber, CircleShape))
        Column {
            Text(period, color = amber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Black, fontSize = if (dense) 5.sp else 7.sp)
            Text(title, color = primaryText, fontWeight = FontWeight.Black, fontSize = if (dense) 8.sp else 11.sp, maxLines = 1)
            Text(description, color = secondaryText, fontSize = if (dense) 6.sp else 8.sp, maxLines = 1)
        }
    }
}

@Composable
private fun MonitorSocialCard(
    index: String,
    name: String,
    handle: String,
    description: String,
    eyebrow: String,
    tags: List<String>,
    icon: DrawableResource,
    color: Color,
    dense: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(if (dense) 9.dp else 14.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .clickable(onClick = onClick)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        color.copy(alpha = 0.13f),
                        Color(0xF20D1723),
                        Color(0xF709111C)
                    )
                )
            )
            .border(1.dp, premiumBorderBrush(color), shape)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = if (dense) 18.dp else 42.dp, y = if (dense) 18.dp else 48.dp)
                .size(if (dense) 86.dp else 180.dp)
                .border(1.dp, color.copy(alpha = 0.08f), CircleShape)
        )
        Text(
            name.take(1),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = if (dense) 2.dp else 10.dp, y = if (dense) 8.dp else 24.dp)
                .alpha(0.035f),
            color = color,
            fontWeight = FontWeight.Black,
            fontSize = if (dense) 54.sp else 116.sp
        )

        if (dense) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(color.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
                        .border(1.dp, color.copy(alpha = 0.56f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(17.dp),
                        colorFilter = ColorFilter.tint(color)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 2.dp, y = 2.dp)
                            .size(7.dp)
                            .background(Color(0xFF0D1723), CircleShape)
                            .border(1.dp, color.copy(alpha = 0.48f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(Modifier.size(2.5.dp).background(color, CircleShape))
                    }
                }
                Column(Modifier.weight(1f)) {
                    Text(
                        name,
                        color = primaryText,
                        fontWeight = FontWeight.Black,
                        fontSize = 8.sp,
                        maxLines = 1
                    )
                    Text(
                        handle,
                        color = color,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 5.sp,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 1.dp)
                    )
                    Text(
                        eyebrow,
                        color = secondaryText,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 3.5.sp,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        index,
                        color = color,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Black,
                        fontSize = 4.sp
                    )
                    Text("↗", color = color, fontWeight = FontWeight.Black, fontSize = 8.sp)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(11.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .background(color.copy(alpha = 0.11f), RoundedCornerShape(15.dp))
                                .border(1.dp, color.copy(alpha = 0.58f), RoundedCornerShape(15.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(color)
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .offset(x = 2.dp, y = 2.dp)
                                    .size(9.dp)
                                    .background(Color(0xFF0D1723), CircleShape)
                                    .border(1.dp, color.copy(alpha = 0.50f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(Modifier.size(3.dp).background(color, CircleShape))
                            }
                        }
                        Column {
                            Text(
                                name,
                                color = primaryText,
                                fontWeight = FontWeight.Black,
                                fontSize = 17.sp
                            )
                            Text(
                                handle,
                                color = color,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 8.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            "$index / PERFIL",
                            color = color,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black,
                            fontSize = 6.sp
                        )
                        Text(
                            eyebrow,
                            color = secondaryText,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 5.sp,
                            modifier = Modifier.padding(top = 3.dp)
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        Modifier
                            .padding(top = 4.dp)
                            .width(24.dp)
                            .height(2.dp)
                            .background(color, RoundedCornerShape(999.dp))
                    )
                    Text(
                        description,
                        color = secondaryText,
                        fontSize = 8.sp,
                        lineHeight = 11.sp,
                        maxLines = 2
                    )
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    tags.forEach { tag -> MonitorSocialTag(tag, color) }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.size(5.dp).background(color, CircleShape))
                        Text(
                            "PERFIL PÚBLICO",
                            color = secondaryText,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 5.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .background(color.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                            .border(1.dp, color.copy(alpha = 0.42f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "VISITAR",
                            color = color,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black,
                            fontSize = 6.sp
                        )
                        Text("↗", color = color, fontWeight = FontWeight.Black, fontSize = 9.sp)
                    }
                }
            }
            }
        }
    }

@Composable
private fun MonitorSocialTag(text: String, color: Color) {
    Text(
        text,
        color = color.copy(alpha = 0.90f),
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 6.sp,
        modifier = Modifier
            .background(color.copy(alpha = 0.08f), RoundedCornerShape(999.dp))
            .border(1.dp, color.copy(alpha = 0.22f), RoundedCornerShape(999.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
private fun MonitorOrbitConnectors(
    placements: List<OrbitPlacement>,
    nodeWidth: Dp,
    nodeHeight: Dp,
    hubX: Dp,
    hubY: Dp,
    hubSize: Dp,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val hubCenter = Offset(
            x = hubX.toPx() + hubSize.toPx() / 2f,
            y = hubY.toPx() + hubSize.toPx() / 2f
        )
        val orbitWidth = size.width * 0.58f
        val orbitHeight = size.height * 0.86f
        drawOval(
            color = accent.copy(alpha = 0.42f),
            topLeft = Offset((size.width - orbitWidth) / 2f, (size.height - orbitHeight) / 2f),
            size = Size(orbitWidth, orbitHeight),
            style = Stroke(width = 1.15.dp.toPx())
        )
        drawOval(
            color = accent.copy(alpha = 0.18f),
            topLeft = Offset((size.width - orbitWidth * 0.82f) / 2f, (size.height - orbitHeight * 0.82f) / 2f),
            size = Size(orbitWidth * 0.82f, orbitHeight * 0.82f),
            style = Stroke(width = 1.dp.toPx())
        )
        listOf(0.56f, 0.76f, 1f).forEachIndexed { index, fraction ->
            drawCircle(
                color = accent.copy(alpha = 0.10f + index * 0.05f),
                radius = hubSize.toPx() * fraction,
                center = hubCenter,
                style = Stroke(width = 1.dp.toPx())
            )
        }
        drawLine(
            color = accent.copy(alpha = 0.24f),
            start = Offset(size.width * 0.14f, hubCenter.y),
            end = Offset(size.width * 0.86f, hubCenter.y),
            strokeWidth = 1.dp.toPx()
        )
        listOf(
            Offset(hubCenter.x, (size.height - orbitHeight) / 2f),
            Offset(hubCenter.x + orbitWidth / 2f, hubCenter.y),
            Offset(hubCenter.x, (size.height + orbitHeight) / 2f),
            Offset(hubCenter.x - orbitWidth / 2f, hubCenter.y)
        ).forEachIndexed { index, marker ->
            drawCircle(
                color = if (index == 1) amber else accent,
                radius = 2.2.dp.toPx(),
                center = marker
            )
        }
    }
}

@Composable
private fun MonitorOrbitHub(dense: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.shadow(
            if (dense) 12.dp else 28.dp,
            CircleShape,
            ambientColor = Color.Black,
            spotColor = accent.copy(alpha = 0.55f)
        ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color(0xF20A121D), radius = size.minDimension * 0.48f)
            drawCircle(accent.copy(alpha = 0.10f), radius = size.minDimension * 0.45f)
            drawCircle(
                color = Color.White.copy(alpha = 0.76f),
                radius = size.minDimension * 0.47f,
                style = Stroke(width = if (dense) 1.2.dp.toPx() else 2.dp.toPx())
            )
            drawCircle(
                color = accent.copy(alpha = 0.72f),
                radius = size.minDimension * 0.40f,
                style = Stroke(width = if (dense) 1.dp.toPx() else 2.dp.toPx())
            )
        }
        Text(
            if (dense) "DN" else "DAVID\nNAVARRO",
            color = primaryText,
            fontWeight = FontWeight.Black,
            fontSize = if (dense) 9.sp else 17.sp,
            lineHeight = if (dense) 10.sp else 18.sp
        )
    }
}

@Composable
private fun MonitorOrbitNode(
    section: PortfolioSection,
    dense: Boolean,
    menuOnLeft: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val highlight = monitorSectionAccent(section)
    val shape = RoundedCornerShape(999.dp)
    val label = if (dense) {
        when (section) {
            PortfolioSection.CV -> "MI CV"
            PortfolioSection.PROJECTS -> "PROY."
            PortfolioSection.ABOUT -> "SOBRE"
            PortfolioSection.EXPERIENCE -> "EXP."
            PortfolioSection.CONTACT -> "EMAIL"
            PortfolioSection.SOCIAL -> "REDES"
        }
    } else {
        when (section) {
            PortfolioSection.CV -> "MI CV"
            PortfolioSection.PROJECTS -> "PROYECTOS"
            PortfolioSection.ABOUT -> "SOBRE MÍ"
            PortfolioSection.EXPERIENCE -> "EXPERIENCIA"
            PortfolioSection.CONTACT -> "EMAIL"
            PortfolioSection.SOCIAL -> "REDES"
        }
    }
    Box(
        modifier = modifier.clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (dense) 34.dp else 58.dp)
                .shadow(12.dp, shape, ambientColor = Color.Black, spotColor = highlight.copy(alpha = 0.18f))
                .clip(shape)
                .background(Brush.horizontalGradient(listOf(Color(0xE8152738), Color(0xF208111B))))
                .border(1.dp, highlight.copy(alpha = 0.34f), shape)
        )
        Text(
            label,
            color = primaryText,
            fontWeight = FontWeight.Black,
            fontSize = if (dense) 8.sp else 14.sp,
            maxLines = 1,
            modifier = Modifier
                .align(if (menuOnLeft) Alignment.CenterStart else Alignment.CenterEnd)
                .padding(
                    start = if (menuOnLeft) (if (dense) 12.dp else 22.dp) else 0.dp,
                    end = if (!menuOnLeft) (if (dense) 12.dp else 22.dp) else 0.dp
                )
        )
        MonitorOrbitSymbol(
            section = section,
            color = highlight,
            dense = dense,
            modifier = Modifier.align(if (menuOnLeft) Alignment.CenterEnd else Alignment.CenterStart)
        )
    }
}

@Composable
private fun MonitorOrbitNumber(number: String, color: Color, dense: Boolean) {
    Box(
        modifier = Modifier
            .size(if (dense) 18.dp else 24.dp)
            .background(color.copy(alpha = 0.08f), CircleShape)
            .border(1.dp, color.copy(alpha = 0.30f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            number,
            color = color,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black,
            fontSize = if (dense) 6.sp else 7.sp
        )
    }
}

@Composable
private fun MonitorOrbitSymbol(section: PortfolioSection, color: Color, dense: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shadow(
                if (dense) 14.dp else 26.dp,
                CircleShape,
                ambientColor = Color.Black,
                spotColor = color.copy(alpha = 0.72f)
            )
            .size(if (dense) 44.dp else 82.dp)
            .background(Brush.radialGradient(listOf(color.copy(alpha = 0.28f), Color(0xFF08121D))), CircleShape)
            .border(if (dense) 1.dp else 2.dp, Color.White.copy(alpha = 0.52f), CircleShape)
            .padding(if (dense) 4.dp else 7.dp)
            .border(if (dense) 1.dp else 2.dp, color.copy(alpha = 0.82f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        MenuSymbol(section = section, compact = dense)
    }
}

@Composable
private fun MonitorOrbitLabel(label: String, color: Color, dense: Boolean, modifier: Modifier = Modifier) {
    Text(
        label,
        color = primaryText,
        fontWeight = FontWeight.Black,
        fontSize = if (dense) 8.sp else 10.sp,
        maxLines = 1,
        modifier = modifier
    )
    Box(Modifier.size(if (dense) 3.dp else 4.dp).background(color.copy(alpha = 0.86f), CircleShape))
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
    monitorDense: Boolean = false,
    modifier: Modifier = Modifier
) {
    val panelShape = RoundedCornerShape(
        when {
            monitorDense -> 10.dp
            compact -> 24.dp
            else -> 22.dp
        }
    )
    Column(
        modifier = modifier
            .heightIn(max = maxHeight)
            .shadow(34.dp, panelShape, ambientColor = Color.Black, spotColor = accent.copy(alpha = 0.18f))
            .clip(panelShape)
            .background(premiumSurfaceBrush())
            .border(1.dp, premiumBorderBrush(), panelShape)
            .verticalScroll(rememberScrollState())
            .padding(
                when {
                    monitorDense -> 8.dp
                    compact -> 18.dp
                    else -> 22.dp
                }
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .shadow(
                        if (monitorDense) 5.dp else 10.dp,
                        RoundedCornerShape(if (monitorDense) 6.dp else 11.dp),
                        ambientColor = Color.Black,
                        spotColor = accent.copy(alpha = 0.22f)
                    )
                    .clip(RoundedCornerShape(if (monitorDense) 6.dp else 10.dp))
                    .clickable(onClick = onBack)
                    .background(premiumInsetBrush())
                    .border(
                        1.dp,
                        premiumBorderBrush(),
                        RoundedCornerShape(if (monitorDense) 6.dp else 10.dp)
                    )
                    .padding(
                        horizontal = if (monitorDense) 6.dp else 11.dp,
                        vertical = if (monitorDense) 4.dp else 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(if (monitorDense) 4.dp else 7.dp)
            ) {
                Text(
                    "←",
                    color = accent,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (monitorDense) 8.sp else 12.sp
                )
                Text(
                    "MENÚ",
                    color = primaryText,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (monitorDense) 6.sp else 9.sp
                )
            }
            Text(
                "${section.number} / PORTFOLIO",
                color = accent,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = if (monitorDense) 6.sp else 9.sp,
                letterSpacing = if (monitorDense) 0.3.sp else 0.8.sp
            )
        }
        Text(
            section.label,
            color = primaryText,
            fontWeight = FontWeight.Black,
            fontSize = when {
                monitorDense -> 16.sp
                compact -> 27.sp
                else -> 31.sp
            },
            modifier = Modifier.padding(
                top = if (monitorDense) 6.dp else 18.dp,
                bottom = if (monitorDense) 7.dp else 18.dp
            )
        )
        SectionContent(section, dense = monitorDense)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SectionContent(section: PortfolioSection, dense: Boolean = false) {
    val uriHandler = LocalUriHandler.current
    when (section) {
        PortfolioSection.CV -> {
            SectionLead("Mi formación, experiencia y tecnologías en un único documento.", dense = dense)
            PrimaryAction("ABRIR CV", dense = dense) { uriHandler.openUri("/assets/cv/cv.pdf") }
        }
        PortfolioSection.PROJECTS -> {
            SectionLead("Proyectos Android creados para resolver problemas reales y explorar nuevas ideas.", dense = dense)
            ProjectItem(
                title = "PermissionProtect",
                description = "Control y aprendizaje sobre los permisos de las aplicaciones Android.",
                tags = "Kotlin · Jetpack Compose",
                dense = dense,
                onClick = { uriHandler.openUri("https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es") }
            )
            ProjectItem(
                title = "Glassmorphism Compose",
                description = "Librería de efectos glassmorphism con RenderEffect y soporte NDK.",
                tags = "Compose · Kotlin · C++",
                dense = dense,
                onClick = { uriHandler.openUri("https://github.com/Deiivid/Glassmorphism-Compose") }
            )
            ProjectItem(
                title = "Clean Architecture Compose",
                description = "Proyecto multimódulo con separación de dominio, datos y presentación.",
                tags = "Clean Architecture · Koin · Detekt",
                dense = dense,
                onClick = { uriHandler.openUri("https://github.com/Deiivid/Clean_Arquitecture_Compose") }
            )
        }
        PortfolioSection.ABOUT -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(if (dense) 7.dp else 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val portraitShape = RoundedCornerShape(if (dense) 10.dp else 18.dp)
                Image(
                    painter = painterResource(Res.drawable.image_david),
                    contentDescription = "David Navarro",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(if (dense) 42.dp else 82.dp)
                        .shadow(
                            if (dense) 7.dp else 14.dp,
                            portraitShape,
                            ambientColor = Color.Black,
                            spotColor = accent.copy(alpha = 0.24f)
                        )
                        .clip(portraitShape)
                        .border(1.dp, premiumBorderBrush(), portraitShape)
                )
                Column {
                    Text(
                        "Android Developer",
                        color = accent,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (dense) 7.sp else 12.sp
                    )
                    Text(
                        "Kotlin · Compose · KMP",
                        color = primaryText,
                        fontWeight = FontWeight.Black,
                        fontSize = if (dense) 9.sp else 16.sp
                    )
                }
            }
            SectionLead(
                "Me apasiona crear aplicaciones modernas, mantenibles y rápidas, cuidando tanto la arquitectura como la experiencia de usuario.",
                modifier = Modifier.padding(top = if (dense) 7.dp else 16.dp),
                dense = dense
            )
            TagCloud(
                listOf("Kotlin", "Jetpack Compose", "KMP", "Coroutines", "Flow", "Clean Architecture", "Testing", "CI/CD"),
                dense = dense
            )
        }
        PortfolioSection.EXPERIENCE -> {
            ExperienceItem("ACTUALMENTE", "Hiberus", "Android Developer para cliente del sector bancario · Jetpack Compose", dense)
            ExperienceItem("2024", "Especialización", "Clean Architecture, proyectos personales y desarrollo móvil avanzado", dense)
            ExperienceItem("2021 — 2023", "Desarrollo móvil", "Primeros pasos profesionales, liderazgo de proyectos y creación de una librería", dense)
            ExperienceItem("2021", "Hiberus Héroes y Heroínas", "Formación intensiva en desarrollo de aplicaciones", dense)
            ExperienceItem("2019 — 2021", "DAM", "Técnico Superior en Desarrollo de Aplicaciones Multiplataforma", dense)
        }
        PortfolioSection.CONTACT -> {
            SectionLead("¿Tienes una idea, un proyecto o simplemente quieres saludar? Escríbeme.", dense = dense)
            Text(contactEmail, color = primaryText, fontWeight = FontWeight.Black, fontSize = if (dense) 8.sp else 15.sp)
            PrimaryAction(
                "ENVIAR EMAIL",
                modifier = Modifier.padding(top = if (dense) 7.dp else 16.dp),
                dense = dense
            ) {
                uriHandler.openUri("mailto:$contactEmail?subject=Contacto%20desde%20tu%20portfolio")
            }
        }
        PortfolioSection.SOCIAL -> {
            SectionLead("Código, artículos y todo lo que voy construyendo.", dense = dense)
            SocialAction("GitHub", "@Deiivid", accent, dense) { uriHandler.openUri("https://github.com/Deiivid") }
            SocialAction("Medium", "@davidnavarrom3", amber, dense) { uriHandler.openUri("https://medium.com/@davidnavarrom3") }
        }
    }
}

@Composable
private fun SectionLead(text: String, modifier: Modifier = Modifier, dense: Boolean = false) {
    Text(
        text,
        color = secondaryText,
        fontSize = if (dense) 8.sp else 13.sp,
        lineHeight = if (dense) 11.sp else 19.sp,
        modifier = modifier.padding(bottom = if (dense) 7.dp else 16.dp)
    )
}

@Composable
private fun PrimaryAction(text: String, modifier: Modifier = Modifier, dense: Boolean = false, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = accent),
        shape = RoundedCornerShape(if (dense) 7.dp else 12.dp),
        contentPadding = PaddingValues(
            horizontal = if (dense) 10.dp else 18.dp,
            vertical = if (dense) 6.dp else 12.dp
        )
    ) {
        Text(
            text,
            color = Color(0xFF06131A),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black,
            fontSize = if (dense) 7.sp else 10.sp
        )
    }
}

@Composable
private fun ProjectItem(title: String, description: String, tags: String, dense: Boolean = false, onClick: () -> Unit) {
    val itemShape = RoundedCornerShape(if (dense) 8.dp else 15.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (dense) 5.dp else 9.dp)
            .shadow(if (dense) 6.dp else 12.dp, itemShape, ambientColor = Color.Black, spotColor = accent.copy(alpha = 0.12f))
            .clip(itemShape)
            .clickable(onClick = onClick)
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(), itemShape)
            .padding(horizontal = if (dense) 8.dp else 14.dp, vertical = if (dense) 6.dp else 13.dp)
    ) {
        Text(title, color = primaryText, fontWeight = FontWeight.Bold, fontSize = if (dense) 9.sp else 14.sp)
        Text(
            description,
            color = secondaryText,
            fontSize = if (dense) 7.sp else 11.sp,
            lineHeight = if (dense) 9.sp else 16.sp,
            modifier = Modifier.padding(top = if (dense) 2.dp else 4.dp)
        )
        Text(
            tags,
            color = violet,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.SemiBold,
            fontSize = if (dense) 6.sp else 8.sp,
            modifier = Modifier.padding(top = if (dense) 4.dp else 8.dp)
        )
    }
}

@Composable
private fun ExperienceItem(period: String, title: String, description: String, dense: Boolean = false) {
    val itemShape = RoundedCornerShape(if (dense) 8.dp else 15.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (dense) 5.dp else 11.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = if (dense) 7.dp else 12.dp)
                .shadow(if (dense) 6.dp else 11.dp, itemShape, ambientColor = Color.Black, spotColor = warm.copy(alpha = 0.10f))
                .clip(itemShape)
                .background(premiumInsetBrush())
                .border(1.dp, premiumBorderBrush(warm), itemShape)
                .padding(
                    start = if (dense) 11.dp else 20.dp,
                    top = if (dense) 6.dp else 13.dp,
                    end = if (dense) 8.dp else 14.dp,
                    bottom = if (dense) 7.dp else 14.dp
                )
        ) {
            Text(
                period,
                color = warm,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = if (dense) 6.sp else 8.sp,
                letterSpacing = if (dense) 0.3.sp else 0.7.sp
            )
            Text(
                title,
                color = primaryText,
                fontWeight = FontWeight.Black,
                fontSize = if (dense) 9.sp else 15.sp,
                modifier = Modifier.padding(top = if (dense) 1.dp else 3.dp)
            )
            Text(
                description,
                color = secondaryText,
                fontSize = if (dense) 7.sp else 11.sp,
                lineHeight = if (dense) 9.sp else 16.sp,
                modifier = Modifier.padding(top = if (dense) 2.dp else 4.dp)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .shadow(if (dense) 5.dp else 9.dp, CircleShape, ambientColor = Color.Black, spotColor = warm.copy(alpha = 0.65f))
                .size(if (dense) 8.dp else 13.dp)
                .background(Color(0xFF18202A), CircleShape)
                .border(if (dense) 1.dp else 2.dp, warm, CircleShape)
        )
    }
}

@Composable
private fun TagCloud(
    tags: List<String>,
    dense: Boolean = false,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(if (dense) 4.dp else 5.dp),
        verticalArrangement = Arrangement.spacedBy(if (dense) 4.dp else 5.dp)
    ) {
        tags.forEach { tag ->
            val chipShape = RoundedCornerShape(999.dp)
            Text(
                tag,
                color = mint,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = if (dense) 7.sp else 8.sp,
                modifier = Modifier
                    .shadow(if (dense) 4.dp else 7.dp, chipShape, ambientColor = Color.Black, spotColor = mint.copy(alpha = 0.12f))
                    .background(premiumInsetBrush(), chipShape)
                    .border(1.dp, premiumBorderBrush(mint), chipShape)
                    .padding(horizontal = if (dense) 7.dp else 8.dp, vertical = if (dense) 3.dp else 4.dp)
            )
        }
    }
}

@Composable
private fun SocialAction(name: String, handle: String, color: Color, dense: Boolean = false, onClick: () -> Unit) {
    val itemShape = RoundedCornerShape(if (dense) 8.dp else 15.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (dense) 5.dp else 9.dp)
            .shadow(if (dense) 6.dp else 12.dp, itemShape, ambientColor = Color.Black, spotColor = color.copy(alpha = 0.12f))
            .clip(itemShape)
            .clickable(onClick = onClick)
            .background(premiumInsetBrush())
            .border(1.dp, premiumBorderBrush(color), itemShape)
            .padding(if (dense) 7.dp else 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(name, color = primaryText, fontWeight = FontWeight.Black, fontSize = if (dense) 9.sp else 14.sp)
            Text(handle, color = color, fontFamily = FontFamily.Monospace, fontSize = if (dense) 6.sp else 9.sp)
        }
        Text(
            "ABRIR  ↗",
            color = color,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black,
            fontSize = if (dense) 6.sp else 8.sp
        )
    }
}
