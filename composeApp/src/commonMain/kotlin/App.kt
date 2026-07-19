import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import davidweb_kmp.composeapp.generated.resources.Res
import davidweb_kmp.composeapp.generated.resources.dm_mono_medium
import davidweb_kmp.composeapp.generated.resources.dm_mono_regular
import davidweb_kmp.composeapp.generated.resources.image_david
import davidweb_kmp.composeapp.generated.resources.space_grotesk
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

private val Night = Color(0xFF070A10)
private val DeepBlue = Color(0xFF0A111B)
private val Panel = Color(0xF2111822)
private val PanelSoft = Color(0xD9141D29)
private val Cyan = Color(0xFF62D5FF)
private val Amber = Color(0xFFF2B879)
private val White = Color(0xFFF4F7FB)
private val Slate = Color(0xFF9AA7B8)
private val Green = Color(0xFF72E6AE)

@Composable
internal fun portfolioSans() = FontFamily(Font(Res.font.space_grotesk))

@Composable
internal fun portfolioMono() = FontFamily(
    Font(Res.font.dm_mono_regular, FontWeight.Normal),
    Font(Res.font.dm_mono_medium, FontWeight.Medium)
)

private const val ContactEmail = "davidnavarrom3@gmail.com"
private const val GitHubUrl = "https://github.com/Deiivid"
private const val MediumUrl = "https://medium.com/@davidnavarrom3"
private const val CvUrl = "/assets/cv/cv.pdf"

private enum class ProjectVisual {
    SECURITY,
    GLASS,
    ARCHITECTURE
}

private data class FeaturedProject(
    val eyebrow: String,
    val title: String,
    val description: String,
    val stack: String,
    val url: String,
    val accent: Color,
    val visual: ProjectVisual
)

private val featuredProjects = listOf(
    FeaturedProject(
        eyebrow = "SEGURIDAD ANDROID",
        title = "PermissionProtect",
        description = "Control y aprendizaje sobre los permisos que utilizan tus aplicaciones.",
        stack = "Kotlin · Jetpack Compose",
        url = "https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es",
        accent = Green,
        visual = ProjectVisual.SECURITY
    ),
    FeaturedProject(
        eyebrow = "UI AVANZADA",
        title = "Glassmorphism Compose",
        description = "Efectos de cristal para interfaces Compose con RenderEffect y soporte NDK.",
        stack = "Compose · Kotlin · C++",
        url = "https://github.com/Deiivid/Glassmorphism-Compose",
        accent = Cyan,
        visual = ProjectVisual.GLASS
    ),
    FeaturedProject(
        eyebrow = "ARQUITECTURA ANDROID",
        title = "Clean Architecture",
        description = "Proyecto multimódulo con dominio, datos y presentación bien separados.",
        stack = "Clean Architecture · Koin · Detekt",
        url = "https://github.com/Deiivid/Clean_Arquitecture_Compose",
        accent = Amber,
        visual = ProjectVisual.ARCHITECTURE
    )
)

@Composable
fun App() {
    val sans = portfolioSans()
    MaterialTheme {
        Surface(
            color = Night,
            modifier = Modifier.fillMaxSize()
        ) {
            ProvideTextStyle(TextStyle(fontFamily = sans)) {
                PortfolioDashboard()
            }
        }
    }
}

@Composable
private fun PortfolioDashboard() {
    val uriHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var projectsOffsetPx by remember { mutableIntStateOf(0) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Night, DeepBlue, Night),
                    start = Offset.Zero,
                    end = Offset(1500f, 1000f)
                )
            )
    ) {
        if (maxWidth >= 1100.dp && maxHeight >= 760.dp) {
            TargetDesktopPortfolio()
            return@BoxWithConstraints
        }

        val compact = maxWidth < 1100.dp
        val narrow = maxWidth < 520.dp
        val short = maxHeight < 780.dp
        val horizontalPadding = when {
            narrow -> 18.dp
            compact -> 28.dp
            else -> 42.dp
        }

        CircuitBackdrop(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = horizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 1440.dp)
            ) {
                PortfolioHeader(
                    compact = compact,
                    narrow = narrow,
                    onContact = { uriHandler.openUri("mailto:$ContactEmail?subject=Contacto%20desde%20tu%20portfolio") }
                )

                HeroSection(
                    compact = compact,
                    narrow = narrow,
                    short = short,
                    onProjects = {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(projectsOffsetPx)
                        }
                    },
                    onCv = { uriHandler.openUri(CvUrl) }
                )

                ExpertiseSection(compact = compact)

                ProjectsSection(
                    compact = compact,
                    onProject = { uriHandler.openUri(it.url) },
                    onGitHub = { uriHandler.openUri(GitHubUrl) },
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        projectsOffsetPx = coordinates.positionInParent().y.roundToInt()
                    }
                )

                PortfolioFooter(
                    compact = compact,
                    onEmail = { uriHandler.openUri("mailto:$ContactEmail?subject=Contacto%20desde%20tu%20portfolio") },
                    onGitHub = { uriHandler.openUri(GitHubUrl) },
                    onMedium = { uriHandler.openUri(MediumUrl) }
                )
            }
        }
    }
}

@Composable
private fun PortfolioHeader(
    compact: Boolean,
    narrow: Boolean,
    onContact: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (compact) 68.dp else 84.dp)
            .padding(top = if (compact) 10.dp else 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(
                text = "DAVID NAVARRO",
                color = White,
                fontWeight = FontWeight.Black,
                fontSize = if (compact) 13.sp else 16.sp,
                letterSpacing = 0.5.sp
            )
            Text(
                text = if (narrow) {
                    "SENIOR ANDROID · KMP · AI"
                } else {
                    "SENIOR ANDROID DEVELOPER · KMP · AI ENGINEER"
                },
                color = Cyan,
                fontFamily = portfolioMono(),
                fontWeight = FontWeight.SemiBold,
                fontSize = if (compact) 7.sp else 9.sp,
                letterSpacing = if (narrow) 0.7.sp else 1.1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier
                    .height(if (compact) 40.dp else 44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Cyan.copy(alpha = 0.07f))
                    .border(1.dp, Cyan.copy(alpha = 0.42f), RoundedCornerShape(14.dp))
                    .clickable(onClick = onContact)
                    .padding(horizontal = if (compact) 14.dp else 18.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hablemos",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (compact) 11.sp else 12.sp
                )
                HeaderArrowIcon(compact = compact)
            }
        }
    }
}

@Composable
private fun HeaderArrowIcon(compact: Boolean) {
    Canvas(
        modifier = Modifier.size(
            width = if (compact) 16.dp else 18.dp,
            height = if (compact) 11.dp else 12.dp
        )
    ) {
        val stroke = 1.5.dp.toPx()
        val midY = size.height / 2f
        val endX = size.width - 1.dp.toPx()
        drawLine(Cyan, Offset(0f, midY), Offset(endX, midY), stroke)
        drawLine(
            Cyan,
            Offset(endX - 5.dp.toPx(), midY - 4.dp.toPx()),
            Offset(endX, midY),
            stroke
        )
        drawLine(
            Cyan,
            Offset(endX - 5.dp.toPx(), midY + 4.dp.toPx()),
            Offset(endX, midY),
            stroke
        )
    }
}

@Composable
private fun HeaderLink(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            color = White.copy(alpha = 0.76f),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun HeroSection(
    compact: Boolean,
    narrow: Boolean,
    short: Boolean,
    onProjects: () -> Unit,
    onCv: () -> Unit
) {
    val verticalPadding = if (short) 22.dp else if (compact) 30.dp else 42.dp

    if (compact) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalPadding),
            verticalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            HeroCopy(
                compact = true,
                narrow = narrow,
                onProjects = onProjects,
                onCv = onCv
            )
            IdentitySystemVisual(compact = true, narrow = narrow)
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalPadding),
            horizontalArrangement = Arrangement.spacedBy(44.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1.35f)) {
                HeroCopy(
                    compact = false,
                    narrow = false,
                    onProjects = onProjects,
                    onCv = onCv
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.85f)
                    .widthIn(max = 470.dp)
            ) {
                IdentitySystemVisual(compact = false, narrow = false)
            }
        }
    }
}

@Composable
private fun HeroCopy(
    compact: Boolean,
    narrow: Boolean,
    onProjects: () -> Unit,
    onCv: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(if (compact) 16.dp else 20.dp)
    ) {
        Text(
            text = "Creo apps móviles que resuelven problemas reales.",
            color = White,
            fontWeight = FontWeight.Black,
            fontSize = when {
                narrow -> 36.sp
                compact -> 44.sp
                else -> 58.sp
            },
            lineHeight = when {
                narrow -> 39.sp
                compact -> 47.sp
                else -> 61.sp
            },
            letterSpacing = (-1.1).sp,
            modifier = Modifier.widthIn(max = 780.dp)
        )

        Text(
            text = "Android, Kotlin Multiplatform e IA aplicada. Arquitectura, producto y entrega de principio a fin.",
            color = Slate,
            fontSize = if (compact) 15.sp else 18.sp,
            lineHeight = if (compact) 22.sp else 27.sp,
            modifier = Modifier.widthIn(max = 660.dp)
        )

        if (narrow) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                PrimaryAction("Ver proyectos", onProjects, Modifier.fillMaxWidth())
                SecondaryAction("Descargar CV", onCv, Modifier.fillMaxWidth())
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrimaryAction("Ver proyectos", onProjects)
                SecondaryAction("Descargar CV", onCv)
            }
        }
    }
}

@Composable
private fun PrimaryAction(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Cyan,
            contentColor = Night
        ),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        Text(text, fontWeight = FontWeight.Black, fontSize = 13.sp)
    }
}

@Composable
private fun SecondaryAction(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Amber),
        contentPadding = PaddingValues(horizontal = 22.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun IdentitySystemVisual(compact: Boolean, narrow: Boolean) {
    val photoWidth = when {
        narrow -> 108.dp
        compact -> 126.dp
        else -> 174.dp
    }
    val photoHeight = when {
        narrow -> 132.dp
        compact -> 154.dp
        else -> 218.dp
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(if (compact) 24.dp else 30.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        Cyan.copy(alpha = 0.08f),
                        Panel,
                        Amber.copy(alpha = 0.07f)
                    )
                )
            )
            .border(
                1.dp,
                Brush.linearGradient(
                    listOf(Cyan.copy(alpha = 0.48f), Color.White.copy(alpha = 0.08f), Amber.copy(alpha = 0.28f))
                ),
                RoundedCornerShape(if (compact) 24.dp else 30.dp)
            )
            .padding(if (narrow) 14.dp else if (compact) 18.dp else 22.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(if (narrow) 12.dp else 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(photoWidth)
                    .height(photoHeight)
                    .shadow(24.dp, RoundedCornerShape(24.dp), spotColor = Cyan.copy(alpha = 0.22f))
                    .clip(RoundedCornerShape(24.dp))
                    .background(DeepBlue)
                    .border(1.dp, Cyan.copy(alpha = 0.42f), RoundedCornerShape(24.dp))
            ) {
                Image(
                    painter = painterResource(Res.drawable.image_david),
                    contentDescription = "David Navarro",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Night.copy(alpha = 0.9f))
                            )
                        )
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(if (narrow) 8.dp else 11.dp)
            ) {
                SystemNode("ANDROID", "Compose", Cyan, narrow)
                SystemConnector(Cyan)
                SystemNode("SHARED KMP", "Código común", White, narrow)
                SystemConnector(Amber)
                SystemNode("IA", "Producto asistido", Amber, narrow)
            }
        }
    }
}

@Composable
private fun SystemNode(label: String, detail: String, color: Color, narrow: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.Black.copy(alpha = 0.22f))
            .border(1.dp, color.copy(alpha = 0.34f), RoundedCornerShape(14.dp))
            .padding(horizontal = if (narrow) 10.dp else 13.dp, vertical = if (narrow) 8.dp else 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(if (narrow) 25.dp else 31.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(color.copy(alpha = 0.12f))
                .border(1.dp, color.copy(alpha = 0.56f), RoundedCornerShape(9.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (label == "IA") "AI" else "<> ",
                color = color,
                fontFamily = portfolioMono(),
                fontWeight = FontWeight.Black,
                fontSize = if (narrow) 7.sp else 8.sp
            )
        }
        Column {
            Text(
                text = label,
                color = White,
                fontWeight = FontWeight.Black,
                fontSize = if (narrow) 9.sp else 11.sp,
                maxLines = 1
            )
            if (!narrow) {
                Text(
                    text = detail,
                    color = Slate,
                    fontSize = 9.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun SystemConnector(color: Color) {
    Box(
        modifier = Modifier
            .padding(start = 25.dp)
            .width(1.dp)
            .height(5.dp)
            .background(color.copy(alpha = 0.4f))
    )
}

@Composable
private fun ExpertiseSection(compact: Boolean) {
    val items = listOf(
        Triple("01", "ANDROID", "Jetpack Compose · Coroutines · Flow"),
        Triple("02", "KMP", "Lógica compartida · UI multiplataforma"),
        Triple("03", "IA APLICADA", "Integración útil dentro del producto")
    )

    if (compact) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items.forEachIndexed { index, item ->
                ExpertiseCard(
                    number = item.first,
                    title = item.second,
                    description = item.third,
                    color = if (index == 2) Amber else Cyan,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items.forEachIndexed { index, item ->
                ExpertiseCard(
                    number = item.first,
                    title = item.second,
                    description = item.third,
                    color = if (index == 2) Amber else Cyan,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ExpertiseCard(
    number: String,
    title: String,
    description: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .heightIn(min = 86.dp)
            .clip(RoundedCornerShape(19.dp))
            .background(PanelSoft)
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(19.dp))
            .padding(horizontal = 18.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = number,
            color = color,
            fontFamily = portfolioMono(),
            fontWeight = FontWeight.Black,
            fontSize = 10.sp
        )
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = title,
                color = White,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                letterSpacing = 0.6.sp
            )
            Text(
                text = description,
                color = Slate,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun ProjectsSection(
    compact: Boolean,
    onProject: (FeaturedProject) -> Unit,
    onGitHub: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = if (compact) 42.dp else 50.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "PROYECTOS SELECCIONADOS",
                    color = Cyan,
                    fontFamily = portfolioMono(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 1.4.sp
                )
                Text(
                    text = "Código que demuestra el trabajo.",
                    color = White,
                    fontWeight = FontWeight.Black,
                    fontSize = if (compact) 24.sp else 30.sp
                )
            }
            if (!compact) {
                HeaderLink("Ver todo en GitHub", onGitHub)
            }
        }

        if (compact) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                featuredProjects.forEach { project ->
                    ProjectCard(
                        project = project,
                        onClick = { onProject(project) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.Top
            ) {
                featuredProjects.forEach { project ->
                    ProjectCard(
                        project = project,
                        onClick = { onProject(project) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProjectCard(
    project: FeaturedProject,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(24.dp)

    Column(
        modifier = modifier
            .heightIn(min = 286.dp)
            .shadow(22.dp, shape, spotColor = project.accent.copy(alpha = 0.1f))
            .clip(shape)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        project.accent.copy(alpha = 0.09f),
                        Panel,
                        Color(0xFF0B111A)
                    )
                )
            )
            .border(1.dp, project.accent.copy(alpha = 0.22f), shape)
            .clickable(onClick = onClick)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        ProjectArtwork(
            visual = project.visual,
            accent = project.accent,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )

        Text(
            text = project.eyebrow,
            color = project.accent,
            fontFamily = portfolioMono(),
            fontWeight = FontWeight.Bold,
            fontSize = 9.sp,
            letterSpacing = 1.sp
        )
        Text(
            text = project.title,
            color = White,
            fontWeight = FontWeight.Black,
            fontSize = 19.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = project.description,
            color = Slate,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = project.stack,
            color = White.copy(alpha = 0.74f),
            fontFamily = portfolioMono(),
            fontSize = 9.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "ABRIR PROYECTO",
            color = project.accent,
            fontWeight = FontWeight.Black,
            fontSize = 10.sp,
            letterSpacing = 0.6.sp
        )
    }
}

@Composable
private fun ProjectArtwork(
    visual: ProjectVisual,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(17.dp))
            .background(Color.Black.copy(alpha = 0.28f))
            .border(1.dp, Color.White.copy(alpha = 0.07f), RoundedCornerShape(17.dp)),
        contentAlignment = Alignment.Center
    ) {
        when (visual) {
            ProjectVisual.SECURITY -> SecurityArtwork(accent)
            ProjectVisual.GLASS -> GlassArtwork(accent)
            ProjectVisual.ARCHITECTURE -> ArchitectureArtwork(accent)
        }
    }
}

@Composable
private fun SecurityArtwork(accent: Color) {
    Canvas(modifier = Modifier.size(72.dp)) {
        val phoneWidth = size.width * 0.48f
        val phoneLeft = (size.width - phoneWidth) / 2f
        drawRoundRect(
            color = Color(0xFF111B26),
            topLeft = Offset(phoneLeft, 3.dp.toPx()),
            size = Size(phoneWidth, size.height - 6.dp.toPx()),
            cornerRadius = CornerRadius(9.dp.toPx()),
            style = Stroke(width = 1.5.dp.toPx())
        )
        drawCircle(
            color = accent.copy(alpha = 0.14f),
            radius = 22.dp.toPx(),
            center = center
        )
        drawCircle(
            color = accent,
            radius = 14.dp.toPx(),
            center = center,
            style = Stroke(width = 2.dp.toPx())
        )
        drawLine(
            color = accent,
            start = Offset(center.x, center.y - 8.dp.toPx()),
            end = Offset(center.x, center.y + 8.dp.toPx()),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            color = accent,
            start = Offset(center.x - 7.dp.toPx(), center.y),
            end = Offset(center.x + 7.dp.toPx(), center.y),
            strokeWidth = 2.dp.toPx()
        )
    }
}

@Composable
private fun GlassArtwork(accent: Color) {
    Box(modifier = Modifier.size(92.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(width = 62.dp, height = 54.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Cyan.copy(alpha = 0.13f))
                .border(1.dp, Cyan.copy(alpha = 0.46f), RoundedCornerShape(14.dp))
        )
        Box(
            modifier = Modifier
                .padding(start = 30.dp, top = 24.dp)
                .size(width = 54.dp, height = 46.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(Amber.copy(alpha = 0.12f))
                .border(1.dp, Amber.copy(alpha = 0.42f), RoundedCornerShape(13.dp))
        )
        Text(
            text = "GLASS",
            color = accent,
            fontFamily = portfolioMono(),
            fontWeight = FontWeight.Black,
            fontSize = 9.sp
        )
    }
}

@Composable
private fun ArchitectureArtwork(accent: Color) {
    Column(
        modifier = Modifier.width(150.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArchitectureLayer("PRESENTATION", accent, 1f)
        ArchitectureLayer("DOMAIN", accent, 0.82f)
        ArchitectureLayer("DATA", accent, 0.64f)
    }
}

@Composable
private fun ArchitectureLayer(label: String, accent: Color, widthFraction: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth(widthFraction)
            .height(22.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(accent.copy(alpha = 0.09f))
            .border(1.dp, accent.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = White.copy(alpha = 0.78f),
            fontFamily = portfolioMono(),
            fontWeight = FontWeight.Bold,
            fontSize = 7.sp
        )
    }
}

@Composable
private fun PortfolioFooter(
    compact: Boolean,
    onEmail: () -> Unit,
    onGitHub: () -> Unit,
    onMedium: () -> Unit
) {
    if (compact) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 34.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            FooterIdentity()
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                HeaderLink("Email", onEmail)
                HeaderLink("GitHub", onGitHub)
                HeaderLink("Medium", onMedium)
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 38.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FooterIdentity()
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                HeaderLink("Email", onEmail)
                HeaderLink("GitHub", onGitHub)
                HeaderLink("Medium", onMedium)
            }
        }
    }
}

@Composable
private fun FooterIdentity() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Green)
                .shadow(8.dp, CircleShape, spotColor = Green)
        )
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "Actualmente · Hiberus",
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
            Text(
                text = "Android Developer · Jetpack Compose",
                color = Slate,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun CircuitBackdrop(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = Cyan.copy(alpha = 0.045f),
            radius = size.minDimension * 0.42f,
            center = Offset(size.width * 0.92f, size.height * 0.12f)
        )
        drawCircle(
            color = Amber.copy(alpha = 0.035f),
            radius = size.minDimension * 0.32f,
            center = Offset(size.width * 0.58f, size.height * 0.5f)
        )

        val circuitColor = Cyan.copy(alpha = 0.13f)
        val stroke = 1.dp.toPx()
        val leftY = size.height * 0.18f
        drawLine(circuitColor, Offset(0f, leftY), Offset(size.width * 0.08f, leftY), stroke)
        drawLine(circuitColor, Offset(size.width * 0.08f, leftY), Offset(size.width * 0.12f, leftY + 36.dp.toPx()), stroke)
        drawLine(circuitColor, Offset(size.width * 0.12f, leftY + 36.dp.toPx()), Offset(size.width * 0.2f, leftY + 36.dp.toPx()), stroke)

        val rightY = size.height * 0.7f
        drawLine(circuitColor, Offset(size.width, rightY), Offset(size.width * 0.91f, rightY), stroke)
        drawLine(circuitColor, Offset(size.width * 0.91f, rightY), Offset(size.width * 0.87f, rightY - 42.dp.toPx()), stroke)
        drawLine(circuitColor, Offset(size.width * 0.87f, rightY - 42.dp.toPx()), Offset(size.width * 0.8f, rightY - 42.dp.toPx()), stroke)

        listOf(
            Offset(size.width * 0.08f, leftY),
            Offset(size.width * 0.2f, leftY + 36.dp.toPx()),
            Offset(size.width * 0.91f, rightY),
            Offset(size.width * 0.8f, rightY - 42.dp.toPx())
        ).forEach { point ->
            drawCircle(Cyan.copy(alpha = 0.5f), radius = 2.dp.toPx(), center = point)
        }
    }
}
