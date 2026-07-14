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
import androidx.compose.animation.slideInVertically
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import davidweb_kmp.composeapp.generated.resources.Res
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_01
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_02
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_03
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_04
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_05
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_06
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_07
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_08
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_09
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_10
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_11
import davidweb_kmp.composeapp.generated.resources.david_walk_premium_12
import davidweb_kmp.composeapp.generated.resources.image_david
import davidweb_kmp.composeapp.generated.resources.office_background
import davidweb_kmp.composeapp.generated.resources.office_david_integrated_v2
import davidweb_kmp.composeapp.generated.resources.office_david_typing_01
import davidweb_kmp.composeapp.generated.resources.office_david_typing_02
import kotlinx.coroutines.delay
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
        delay(360)
        walkProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(3900, easing = LinearEasing)
        )
        delay(720)
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

        if (menusVisible && activeSection == null) {
            HeadOrbitMenu(
                compact = compact,
                onSectionSelected = onSectionSelected,
                modifier = Modifier.fillMaxSize()
            )
        }

        activeSection?.let { section ->
            PortfolioDetailOverlay(
                section = section,
                compact = compact,
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
        if (walkProgress in 0.001f..0.985f) {
            Text(
                "ENTRANDO  ${((walkProgress * 100).toInt()).coerceIn(1, 99).toString().padStart(2, '0')}%",
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
    BoxWithConstraints(modifier) {
        var typingFrame by remember { mutableIntStateOf(0) }
        val seated = walkProgress >= 0.88f

        LaunchedEffect(seated) {
            typingFrame = 0
            while (seated) {
                delay(210)
                typingFrame = (typingFrame + 1) % 3
            }
        }

        Image(
            painter = painterResource(Res.drawable.office_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        )
        if (walkProgress < 0.995f) {
            WalkingCharacterLayer(
                walkProgress = walkProgress,
                compact = compact,
                sceneWidth = maxWidth,
                sceneHeight = maxHeight,
                modifier = Modifier.fillMaxSize()
            )
        }
        if (seated) {
            val typingPainters = listOf(
                painterResource(Res.drawable.office_david_integrated_v2),
                painterResource(Res.drawable.office_david_typing_01),
                painterResource(Res.drawable.office_david_typing_02)
            )
            Image(
                painter = typingPainters[typingFrame],
                contentDescription = "David Navarro tecleando en su ordenador",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                alpha = ((walkProgress - 0.88f) / 0.10f).coerceIn(0f, 1f),
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0f to Color.Black.copy(alpha = if (compact) 0.24f else 0.44f),
                            0.42f to Color.Black.copy(alpha = if (compact) 0.08f else 0.12f),
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
                        listOf(Color.Black.copy(alpha = 0.18f), Color.Transparent, Color.Black.copy(alpha = 0.22f))
                    )
                )
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun WalkingCharacterLayer(
    walkProgress: Float,
    compact: Boolean,
    sceneWidth: Dp,
    sceneHeight: Dp,
    modifier: Modifier = Modifier
) {
    val frames = listOf(
        painterResource(Res.drawable.david_walk_premium_01),
        painterResource(Res.drawable.david_walk_premium_02),
        painterResource(Res.drawable.david_walk_premium_03),
        painterResource(Res.drawable.david_walk_premium_04),
        painterResource(Res.drawable.david_walk_premium_05),
        painterResource(Res.drawable.david_walk_premium_06),
        painterResource(Res.drawable.david_walk_premium_07),
        painterResource(Res.drawable.david_walk_premium_08),
        painterResource(Res.drawable.david_walk_premium_09),
        painterResource(Res.drawable.david_walk_premium_10),
        painterResource(Res.drawable.david_walk_premium_11),
        painterResource(Res.drawable.david_walk_premium_12)
    )
    val baseCharacterHeight = if (compact) {
        (sceneHeight * 0.55f).coerceIn(380.dp, 460.dp)
    } else {
        (sceneHeight * 0.72f).coerceIn(500.dp, 600.dp)
    }
    val characterHeight = baseCharacterHeight * (1f + 0.055f * walkProgress)
    val characterWidth = characterHeight * (480f / 510f)
    val startX = -characterWidth * 0.72f
    val endX = sceneWidth * 0.50f - characterWidth * 0.50f
    val characterX = startX + (endX - startX) * walkProgress
    val travelled = (characterX - startX).value
    val fullStride = characterHeight.value * 0.74f
    val frameIndex = ((travelled / fullStride * frames.size).toInt().coerceAtLeast(0)) % frames.size
    val cyclePhase = frameIndex.toDouble() / frames.size
    val verticalBob = (-2.6f * abs(sin(cyclePhase * 2.0 * PI)).toFloat()).dp
    val floorPadding = if (compact) 18.dp else 26.dp
    val shadowWidth = characterHeight * 0.25f
    val shadowHeight = characterHeight * 0.026f
    val shadowX = characterX + characterWidth * 0.50f - shadowWidth * 0.50f
    val walkAlpha = ((1f - walkProgress) / 0.10f).coerceIn(0f, 1f)

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = shadowX, y = -floorPadding + 4.dp)
                .width(shadowWidth)
                .height(shadowHeight)
                .alpha(walkAlpha)
                .background(Color.Black.copy(alpha = 0.52f), CircleShape)
        )
        Image(
            painter = frames[frameIndex],
            contentDescription = "David Navarro caminando hacia su mesa",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = characterX, y = -floorPadding + verticalBob)
                .width(characterWidth)
                .height(characterHeight)
                .alpha(walkAlpha)
        )
    }
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
            "TECLEANDO · PORTFOLIO ONLINE",
            color = primaryText.copy(alpha = 0.78f),
            fontFamily = FontFamily.Monospace,
            fontSize = 8.sp,
            letterSpacing = 0.9.sp
        )
    }
}

@Composable
private fun HeadOrbitMenu(
    compact: Boolean,
    onSectionSelected: (PortfolioSection) -> Unit,
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
        val menuWidth = if (compact) (maxWidth * 0.32f).coerceIn(116.dp, 140.dp) else 216.dp
        val menuHeight = if (compact) 48.dp else 54.dp
        val clearSpace = if (compact) 46.dp else 82.dp
        val rowStep = if (compact) 60.dp else 72.dp
        val edge = if (compact) 10.dp else 28.dp
        val leftX = (headX - clearSpace - menuWidth).coerceAtLeast(edge)
        val rightX = (headX + clearSpace).coerceAtMost(maxWidth - edge - menuWidth)
        val centerY = (headY - menuHeight / 2f).coerceIn(
            82.dp + rowStep,
            maxHeight - menuHeight - edge - rowStep
        )
        val rows = listOf(centerY - rowStep, centerY, centerY + rowStep)
        val leftSections = listOf(PortfolioSection.CV, PortfolioSection.ABOUT, PortfolioSection.CONTACT)
        val rightSections = listOf(PortfolioSection.PROJECTS, PortfolioSection.EXPERIENCE, PortfolioSection.SOCIAL)

        rows.forEachIndexed { row, y ->
            listOf(
                Triple(leftSections[row], leftX, true),
                Triple(rightSections[row], rightX, false)
            ).forEachIndexed { side, (section, x, menuOnLeft) ->
                val revealIndex = row * 2 + side
                Box(
                    modifier = Modifier
                        .offset(x = x, y = y)
                        .width(menuWidth)
                        .height(menuHeight)
                ) {
                    AnimatedVisibility(
                        visible = revealedCount > revealIndex,
                        enter = fadeIn(tween(260)) + scaleIn(tween(300), initialScale = 0.90f),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        HeadMenuButton(
                            section = section,
                            compact = compact,
                            menuOnLeft = menuOnLeft,
                            onClick = { onSectionSelected(section) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
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

    Box(
        modifier = modifier
            .shadow(22.dp, RoundedCornerShape(16.dp), ambientColor = Color.Black, spotColor = Color.Black)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(
                Brush.horizontalGradient(
                    if (menuOnLeft) {
                        listOf(Color(0xF50A111A), Color(0xF0122633))
                    } else {
                        listOf(Color(0xF0122633), Color(0xF50A111A))
                    }
                )
            )
            .border(1.dp, accent.copy(alpha = 0.24f), RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(1.dp)
                .background(Brush.horizontalGradient(listOf(Color.Transparent, Color.White.copy(alpha = 0.18f), Color.Transparent)))
        )
        Box(
            modifier = Modifier
                .align(if (menuOnLeft) Alignment.CenterEnd else Alignment.CenterStart)
                .width(2.dp)
                .height(if (compact) 25.dp else 30.dp)
                .background(accent, RoundedCornerShape(99.dp))
        )
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = if (compact) 9.dp else 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (compact) 6.dp else 9.dp)
        ) {
            if (!menuOnLeft) HamburgerIcon(compact)
            if (!compact) MenuNumber(section.number)
            Text(
                label,
                color = primaryText,
                fontWeight = FontWeight.Bold,
                fontSize = if (compact) 9.sp else 13.sp,
                letterSpacing = if (compact) 0.1.sp else 0.35.sp,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            if (menuOnLeft) HamburgerIcon(compact)
        }
    }
}

@Composable
private fun HamburgerIcon(compact: Boolean) {
    Box(
        modifier = Modifier
            .size(if (compact) 26.dp else 30.dp)
            .background(Color(0xFF0C202D), RoundedCornerShape(if (compact) 8.dp else 9.dp))
            .border(1.dp, accent.copy(alpha = 0.24f), RoundedCornerShape(if (compact) 8.dp else 9.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(if (compact) 3.dp else 4.dp)
        ) {
            Box(Modifier.width(if (compact) 10.dp else 12.dp).height(1.dp).background(accent, RoundedCornerShape(9.dp)))
            Box(Modifier.width(if (compact) 15.dp else 17.dp).height(1.dp).background(accent, RoundedCornerShape(9.dp)))
            Box(Modifier.width(if (compact) 8.dp else 10.dp).height(1.dp).background(accent, RoundedCornerShape(9.dp)))
        }
    }
}

@Composable
private fun MenuNumber(number: String) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(accent.copy(alpha = 0.10f), RoundedCornerShape(8.dp))
            .border(1.dp, accent.copy(alpha = 0.18f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            number,
            color = accent,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 8.sp
        )
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (compact) {
                        Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.30f), Color.Black.copy(alpha = 0.74f)))
                    } else {
                        Brush.horizontalGradient(listOf(Color.Black.copy(alpha = 0.66f), Color.Black.copy(alpha = 0.30f), Color.Transparent))
                    }
                )
        )
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(240)) + slideInVertically(tween(300), initialOffsetY = { if (compact) 36 else 12 }),
            modifier = if (compact) {
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
            } else {
                Modifier
                    .align(Alignment.CenterStart)
                    .width(430.dp)
                    .padding(start = 34.dp, top = 88.dp, bottom = 24.dp)
            }
        ) {
            PortfolioDetailPanel(
                section = section,
                onBack = onBack,
                compact = compact,
                maxHeight = if (compact) maxHeight * 0.62f else maxHeight - 112.dp,
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
    Column(
        modifier = modifier
            .heightIn(max = maxHeight)
            .shadow(30.dp, RoundedCornerShape(if (compact) 22.dp else 20.dp), ambientColor = Color.Black, spotColor = Color.Black)
            .clip(RoundedCornerShape(if (compact) 22.dp else 20.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFA0C141E), surface)))
            .border(1.dp, Color.White.copy(alpha = 0.14f), RoundedCornerShape(if (compact) 22.dp else 20.dp))
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
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onBack)
                    .background(Color.White.copy(alpha = 0.05f))
                    .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(10.dp))
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
                        .clip(RoundedCornerShape(18.dp))
                        .border(1.dp, accent.copy(alpha = 0.45f), RoundedCornerShape(18.dp))
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .background(Color.White.copy(alpha = 0.035f))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(14.dp))
            .padding(13.dp)
    ) {
        Text(title, color = primaryText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(description, color = secondaryText, fontSize = 11.sp, lineHeight = 16.sp, modifier = Modifier.padding(top = 4.dp))
        Text(tags, color = violet, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold, fontSize = 8.sp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
private fun ExperienceItem(period: String, title: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(8.dp)
                .background(warm, CircleShape)
        )
        Column {
            Text(period, color = warm, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 8.sp, letterSpacing = 0.7.sp)
            Text(title, color = primaryText, fontWeight = FontWeight.Black, fontSize = 15.sp, modifier = Modifier.padding(top = 3.dp))
            Text(description, color = secondaryText, fontSize = 11.sp, lineHeight = 16.sp, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
private fun TagCloud(tags: List<String>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(7.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
        tags.forEach { tag ->
            Text(
                tag,
                color = mint,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 9.sp,
                modifier = Modifier
                    .background(mint.copy(alpha = 0.08f), RoundedCornerShape(999.dp))
                    .border(1.dp, mint.copy(alpha = 0.20f), RoundedCornerShape(999.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun SocialAction(name: String, handle: String, color: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .background(Color.White.copy(alpha = 0.035f))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(14.dp))
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
