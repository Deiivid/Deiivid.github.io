import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.zIndex
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import davidweb_kmp.composeapp.generated.resources.Res
import davidweb_kmp.composeapp.generated.resources.android_logo
import davidweb_kmp.composeapp.generated.resources.cleancode
import davidweb_kmp.composeapp.generated.resources.github_logo
import davidweb_kmp.composeapp.generated.resources.icon_code
import davidweb_kmp.composeapp.generated.resources.icon_deployed_code
import davidweb_kmp.composeapp.generated.resources.icon_hub
import davidweb_kmp.composeapp.generated.resources.icon_layers
import davidweb_kmp.composeapp.generated.resources.icon_science
import davidweb_kmp.composeapp.generated.resources.icon_sync
import davidweb_kmp.composeapp.generated.resources.icon_timeline_architecture
import davidweb_kmp.composeapp.generated.resources.icon_timeline_bank
import davidweb_kmp.composeapp.generated.resources.icon_timeline_phone
import davidweb_kmp.composeapp.generated.resources.icon_timeline_school
import davidweb_kmp.composeapp.generated.resources.icon_timeline_training
import davidweb_kmp.composeapp.generated.resources.image_david
import davidweb_kmp.composeapp.generated.resources.kotlin_logo
import davidweb_kmp.composeapp.generated.resources.medium_logo
import davidweb_kmp.composeapp.generated.resources.permission_protect
import davidweb_kmp.composeapp.generated.resources.icon_timer
import davidweb_kmp.composeapp.generated.resources.icon_bug
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.delay
import kotlin.math.sqrt
import kotlin.random.Random

private val pageBg = Color(0xFF0A1328)
private val cardBg = Color(0xFF131C34)
private val border = Color(0x22FFFFFF)
private val textPrimary = Color(0xFFF8FAFC)
private val textSecondary = Color(0xFF94A3B8)
private val accent = Color(0xFF256AF4)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val scroll = rememberScrollState()
    var showGame by remember { mutableStateOf(false) }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = pageBg) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 960.dp)
                        .fillMaxWidth()
                        .verticalScroll(scroll)
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    Header()
                    HeroSection()
                    SkillsSection()
                    TimelineSection()
                    ProjectsSection()
                    GameSection(onOpen = { showGame = true })
                    ContactSection()
                    Footer()
                }
                if (showGame) {
                    GameModal(
                        modifier = Modifier.zIndex(10f),
                        onClose = { showGame = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun Header() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xCC101622))
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                AnimatedHeaderIcon(
                    icon = Res.drawable.android_logo,
                    size = 28.dp,
                    bounce = true
                )
                AnimatedHeaderIcon(
                    icon = Res.drawable.kotlin_logo,
                    size = 22.dp,
                    bounce = false
                )
                Text("David Navarro", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                HeaderLabel("Home")
                HeaderLabel("Skills")
                HeaderLabel("Timeline")
                HeaderLabel("Projects")
                HeaderLabel("Game")
                HeaderLabel("Contact")
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(border))
    }
}

@Composable
private fun HeaderLabel(text: String) {
    Text(text, color = textSecondary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun HeroSection() {
    val uriHandler = LocalUriHandler.current

    SectionBlock {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val mobile = maxWidth < 864.dp
            val compact = maxWidth < 480.dp
            if (mobile) {
                Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    HeroText(uriHandler, compact)
                    HeroImage()
                }
            } else {
                    val imageWidth = 340.dp
                    val gap = 40.dp
                val textWidth = (maxWidth - imageWidth - gap).coerceAtLeast(320.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.width(textWidth)) {
                        HeroText(uriHandler, compact)
                    }
                    HeroImage()
                }
            }
        }
    }
}

@Composable
private fun HeroText(uriHandler: androidx.compose.ui.platform.UriHandler, isCompact: Boolean) {
    val titleSize = if (isCompact) 36.sp else 48.sp
    val bodySize = if (isCompact) 16.sp else 18.sp

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        TypingTitle(text = "Android Developer", fontSize = titleSize)
        Text(
            "Passionate about building modern Android apps (Kotlin · Compose · KMP) with great UX and performance.",
            color = textSecondary,
            fontSize = bodySize,
            lineHeight = (bodySize.value + 6).sp
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = { uriHandler.openUri("/assets/cv/cv.pdf") },
                colors = ButtonDefaults.buttonColors(containerColor = accent),
                shape = RoundedCornerShape(10.dp)
            ) { Text("Download CV", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconLinkButton(icon = Res.drawable.github_logo, onClick = { uriHandler.openUri("https://github.com/Deiivid") })
                IconLinkButton(icon = Res.drawable.medium_logo, onClick = { uriHandler.openUri("https://medium.com/@davidnavarrom3") })
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun HeroImage() {
    val outerRadius = 22.dp
    Box(
        modifier = Modifier
            .width(340.dp)
            .aspectRatio(1f)
            .shadow(22.dp, RoundedCornerShape(outerRadius), clip = false)
            .border(1.dp, border, RoundedCornerShape(outerRadius))
            .clip(RoundedCornerShape(outerRadius))
            .background(Color(0x111B2A))
    ) {
        Image(
            painter = painterResource(Res.drawable.image_david),
            contentDescription = "David Navarro",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun SkillsSection() {
    SectionBlock {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SectionTitle("Key Skills")
            Text(
                "A selection of my preferred tools and technologies for building high-quality Android applications.",
                color = textSecondary,
                fontSize = 16.sp
            )
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val gap = 12.dp
                val columns = when {
                    maxWidth >= 860.dp -> 3
                    maxWidth >= 560.dp -> 2
                    else -> 1
                }
                val cardWidth = if (columns == 1) maxWidth else {
                    (maxWidth - gap * (columns - 1).toFloat()) / columns.toFloat()
                }

                Column(verticalArrangement = Arrangement.spacedBy(gap)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                        SkillCard(Res.drawable.icon_code, "Kotlin & Java", "Primary languages for Android development.", Modifier.width(cardWidth))
                        if (columns > 1) SkillCard(Res.drawable.icon_layers, "Jetpack Compose", "Modern declarative UI toolkit.", Modifier.width(cardWidth))
                        if (columns > 2) SkillCard(Res.drawable.icon_sync, "Coroutines & Flow", "Asynchronous and reactive programming.", Modifier.width(cardWidth))
                    }
                    if (columns == 1) {
                        SkillCard(Res.drawable.icon_layers, "Jetpack Compose", "Modern declarative UI toolkit.", Modifier.fillMaxWidth())
                        SkillCard(Res.drawable.icon_sync, "Coroutines & Flow", "Asynchronous and reactive programming.", Modifier.fillMaxWidth())
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                        SkillCard(Res.drawable.icon_hub, "Koin / Dagger-Hilt", "Dependency injection frameworks.", Modifier.width(cardWidth))
                        if (columns > 1) SkillCard(Res.drawable.icon_deployed_code, "CI/CD", "Automated build & delivery pipelines.", Modifier.width(cardWidth))
                        if (columns > 2) SkillCard(Res.drawable.icon_science, "Unit & UI Testing", "Quality, stability and coverage.", Modifier.width(cardWidth))
                    }
                    if (columns == 1) {
                        SkillCard(Res.drawable.icon_deployed_code, "CI/CD", "Automated build & delivery pipelines.", Modifier.fillMaxWidth())
                        SkillCard(Res.drawable.icon_science, "Unit & UI Testing", "Quality, stability and coverage.", Modifier.fillMaxWidth())
                    }
                }
            }

            val extras = listOf(
                "KMP",
                "Retrofit",
                "SQL",
                "Firebase",
                "Git",
                "GitHub Actions",
                "Detekt",
                "Koin",
                "Clean Architecture"
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text("Also comfortable with", color = accent, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            SkillsPills(extras)
        }
    }
}

@Composable
private fun SkillCard(icon: DrawableResource, title: String, subtitle: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(1.dp, border, RoundedCornerShape(14.dp))
            .background(Color(0xFF121A2E), RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(title, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(subtitle, color = textSecondary, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun SkillPill(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFF161D2E), RoundedCornerShape(999.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(text, color = textPrimary.copy(alpha = 0.9f), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SkillsPills(items: List<String>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.forEach { SkillPill(it) }
    }
}

@Composable
private fun TimelineSection() {
    SectionBlock {
        val entries = listOf(
            TimelineEntry(
                period = "Currently",
                title = "Hiberus – Android Developer supporting a banking sector client",
                subtitle = "Developing integrations using Jetpack Compose.",
                icon = Res.drawable.icon_timeline_bank,
                iconBg = Color(0xFF0F5D4A),
                iconTint = Color(0xFF8EF0C5),
                chipTone = Color(0xFF0F5D4A),
                chips = listOf("Android", "Jetpack Compose")
            ),
            TimelineEntry(
                period = "2024",
                title = "Specialization and Clean Architecture",
                subtitle = "Focused on Clean Architecture, personal projects, and advanced mobile development.",
                icon = Res.drawable.icon_timeline_architecture,
                iconBg = Color(0xFF203B7A),
                iconTint = Color(0xFF93C5FD),
                chipTone = Color(0xFF203B7A),
                chips = listOf("Clean Architecture", "MVVM", "Proyectos personales")
            ),
            TimelineEntry(
                period = "2021 - 2023",
                title = "Immersion in Mobile Development",
                subtitle = "First professional steps, project leadership, and creation of a personal library.",
                icon = Res.drawable.icon_timeline_phone,
                iconBg = Color(0xFF0F5D4A),
                iconTint = Color(0xFF8EF0C5),
                chipTone = Color(0xFF0F5D4A),
                chips = listOf("Java", "Kotlin", "Jetpack Compose", "Libreria")
            ),
            TimelineEntry(
                period = "2021",
                title = "Hiberus Heroes y Heroinas",
                subtitle = "Intensive training program specialized in Mobile application development with the MEAN Stack.",
                icon = Res.drawable.icon_timeline_training,
                iconBg = Color(0xFF203B7A),
                iconTint = Color(0xFF93C5FD),
                chipTone = Color(0xFF203B7A),
                chips = listOf("MEAN", "Javascript", "Training")
            ),
            TimelineEntry(
                period = "2019 - 2021",
                title = "Transition to DAM",
                subtitle = "From Systems Technician to Higher Degree in Multiplatform Application Development.",
                icon = Res.drawable.icon_timeline_school,
                iconBg = Color(0xFF0F5D4A),
                iconTint = Color(0xFF8EF0C5),
                chipTone = Color(0xFF0F5D4A),
                chips = listOf("Java", "Bases de datos", "Sistemas")
            )
        )

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            SectionTitleLarge("Timeline")
            TimelineList(entries)
        }
    }
}

@Composable
private fun TimelineList(entries: List<TimelineEntry>) {
    val lineX = 28.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val x = lineX.toPx()
                drawLine(
                    color = border,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            },
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        entries.forEach { TimelineItemRow(it) }
    }
}

private data class TimelineEntry(
    val period: String,
    val title: String,
    val subtitle: String,
    val icon: DrawableResource,
    val iconBg: Color,
    val iconTint: Color,
    val chipTone: Color,
    val chips: List<String>
)

@Composable
private fun TimelineItemRow(entry: TimelineEntry) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.width(56.dp),
            contentAlignment = Alignment.TopCenter
        ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(entry.iconBg, RoundedCornerShape(999.dp))
                .border(1.dp, border, RoundedCornerShape(999.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(entry.icon),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                colorFilter = ColorFilter.tint(entry.iconTint)
            )
        }
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(entry.period, color = textSecondary, fontSize = 13.sp)
            Text(entry.title, color = textPrimary, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Text(entry.subtitle, color = textSecondary, fontSize = 14.sp)
            ChipsRow(entry.chips, entry.chipTone)
        }
    }
}

@Composable
private fun ChipsRow(chips: List<String>, tone: Color) {
    val rows = chips.chunked(3)
    Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 6.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                row.forEach { Chip(it, tone) }
            }
        }
    }
}

@Composable
private fun Chip(text: String, tone: Color) {
    Box(
        modifier = Modifier
            .background(tone.copy(alpha = 0.22f), RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text, color = tone.copy(alpha = 0.9f), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ProjectsSection() {
    val uriHandler = LocalUriHandler.current
    SectionBlock {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            SectionTitle("My Projects")
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val stack = maxWidth < 860.dp
                if (stack) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        ProjectCardStack(
                            title = "PermissionProtect",
                            description = "Android app to review, control and learn about your app permissions. Built with Kotlin & Jetpack Compose.",
                            image = Res.drawable.permission_protect,
                            chips = listOf("Jetpack Compose", "Kotlin / Java"),
                            button1 = "Google Play" to "https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es",
                            button2 = "Web" to "https://deiivid.github.io/PermissionProtectWeb/",
                            onOpen = uriHandler::openUri
                        )
                        ProjectCardCustomStack(
                            title = "Jetpack Compose Glassmorphism library",
                            description = "For applying glassmorphism effects — blurring both background and content. Android 12+ uses RenderEffect, while Android 11 and below rely on a native C++/NDK.",
                            header = { GlassmorphismHeader() },
                            chips = listOf("Jetpack Compose", "Kotlin / Java", "C++ / NDK"),
                            button1 = "GitHub" to "https://github.com/Deiivid/Glassmorphism-Compose",
                            button2 = "README" to "https://github.com/Deiivid/Glassmorphism-Compose#readme",
                            onOpen = uriHandler::openUri
                        )
                        ProjectCardStack(
                            title = "Clean Architecture Compose",
                            description = "A multi-module Android project demonstrating a layered Clean Architecture pattern — separation of concerns between domain, data, and presentation layers using Jetpack Compose.",
                            image = Res.drawable.cleancode,
                            chips = listOf("Jetpack Compose", "Kotlin", "Clean Architecture", "Koin", "Detekt"),
                            button1 = "GitHub" to "https://github.com/Deiivid/Clean_Arquitecture_Compose",
                            button2 = "README" to "https://github.com/Deiivid/Clean_Arquitecture_Compose#readme",
                            onOpen = uriHandler::openUri
                        )
                    }
                } else {
                    val gap = 16.dp
                    val cardWidth = (maxWidth - gap) / 2f
                    Column(verticalArrangement = Arrangement.spacedBy(gap)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(gap),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ProjectCard(
                                title = "PermissionProtect",
                                description = "Android app to review, control and learn about your app permissions. Built with Kotlin & Jetpack Compose.",
                                image = Res.drawable.permission_protect,
                                chips = listOf("Jetpack Compose", "Kotlin / Java"),
                                button1 = "Google Play" to "https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es",
                                button2 = "Web" to "https://deiivid.github.io/PermissionProtectWeb/",
                                onOpen = uriHandler::openUri,
                                modifier = Modifier.width(cardWidth)
                            )
                            ProjectCardCustom(
                                title = "Jetpack Compose Glassmorphism library",
                                description = "For applying glassmorphism effects — blurring both background and content.",
                                header = { GlassmorphismHeader() },
                                chips = listOf("Jetpack Compose", "Kotlin / Java", "C++ / NDK"),
                                button1 = "GitHub" to "https://github.com/Deiivid/Glassmorphism-Compose",
                                button2 = "README" to "https://github.com/Deiivid/Glassmorphism-Compose#readme",
                                onOpen = uriHandler::openUri,
                                modifier = Modifier.width(cardWidth)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(gap),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ProjectCard(
                                title = "Clean Architecture Compose",
                                description = "A multi-module Android project demonstrating a layered Clean Architecture pattern — separation of concerns between domain, data, and presentation layers using Jetpack Compose.",
                                image = Res.drawable.cleancode,
                                chips = listOf("Jetpack Compose", "Kotlin", "Clean Architecture", "Koin", "Detekt"),
                                button1 = "GitHub" to "https://github.com/Deiivid/Clean_Arquitecture_Compose",
                                button2 = "README" to "https://github.com/Deiivid/Clean_Arquitecture_Compose#readme",
                                onOpen = uriHandler::openUri,
                                modifier = Modifier.width(cardWidth)
                            )
                            Spacer(modifier = Modifier.width(cardWidth))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ProjectCard(
    title: String,
    description: String,
    image: DrawableResource,
    chips: List<String> = emptyList(),
    button1: Pair<String, String>,
    button2: Pair<String, String>,
    onOpen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(16.dp)
    val imageShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Box(
        modifier = modifier
            .border(1.dp, border, cardShape)
            .background(cardBg, cardShape)
            .clip(cardShape)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(imageShape)
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier.fillMaxSize().border(1.dp, border, imageShape))
            }
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(title, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(description, color = textSecondary, fontSize = 14.sp, lineHeight = 20.sp)
                if (chips.isNotEmpty()) {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        chips.forEach { Chip(it, accent) }
                    }
                }
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val gap = 10.dp
                    val btnWidth = (maxWidth - gap) / 2f
                    Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                        Button(
                            modifier = Modifier.width(btnWidth),
                            onClick = { onOpen(button1.second) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF223252)),
                            shape = RoundedCornerShape(10.dp)
                        ) { Text(button1.first, color = Color.White, fontSize = 14.sp) }
                        Button(
                            modifier = Modifier.width(btnWidth),
                            onClick = { onOpen(button2.second) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF223252)),
                            shape = RoundedCornerShape(10.dp)
                        ) { Text(button2.first, color = Color.White, fontSize = 14.sp) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ProjectCardStack(
    title: String,
    description: String,
    image: DrawableResource,
    chips: List<String> = emptyList(),
    button1: Pair<String, String>,
    button2: Pair<String, String>,
    onOpen: (String) -> Unit
) {
    val cardShape = RoundedCornerShape(16.dp)
    val imageShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, border, cardShape)
            .background(cardBg, cardShape)
            .clip(cardShape)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(imageShape)
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier.fillMaxSize().border(1.dp, border, imageShape))
            }
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(title, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(description, color = textSecondary, fontSize = 14.sp, lineHeight = 20.sp)
                if (chips.isNotEmpty()) {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        chips.forEach { Chip(it, accent) }
                    }
                }
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val gap = 10.dp
                    val btnWidth = (maxWidth - gap) / 2f
                    Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                        Button(
                            modifier = Modifier.width(btnWidth),
                            onClick = { onOpen(button1.second) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF223252)),
                            shape = RoundedCornerShape(10.dp)
                        ) { Text(button1.first, color = Color.White, fontSize = 14.sp) }
                        Button(
                            modifier = Modifier.width(btnWidth),
                            onClick = { onOpen(button2.second) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF223252)),
                            shape = RoundedCornerShape(10.dp)
                        ) { Text(button2.first, color = Color.White, fontSize = 14.sp) }
                    }
                }
            }
        }
    }
}

@Composable
private fun GlassmorphismHeader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF0B1220), Color(0xFF111827), Color(0xFF1F2937))
                )
            )
            .border(1.dp, border, RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = (-48).dp, y = (-72).dp)
                .background(Color(0x40256AF4), RoundedCornerShape(999.dp))
        )
        Box(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = 40.dp)
                .background(Color(0x333DDC84), RoundedCornerShape(999.dp))
        )
        Box(
            modifier = Modifier
                .size(width = 220.dp, height = 120.dp)
                .offset(x = 28.dp, y = 24.dp)
                .background(Color(0x26FFFFFF), RoundedCornerShape(14.dp))
                .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(14.dp))
        )
        Box(
            modifier = Modifier
                .size(width = 140.dp, height = 80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-24).dp, y = (-24).dp)
                .background(Color(0x26FFFFFF), RoundedCornerShape(14.dp))
                .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(14.dp))
        )
    }
}

@Composable
private fun ProjectCardCustom(
    title: String,
    description: String,
    header: @Composable () -> Unit,
    chips: List<String>,
    button1: Pair<String, String>,
    button2: Pair<String, String>,
    onOpen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(16.dp)
    val imageShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Box(
        modifier = modifier
            .border(1.dp, border, cardShape)
            .background(cardBg, cardShape)
            .clip(cardShape)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(imageShape)
            ) {
                header()
                Box(modifier = Modifier.fillMaxSize().border(1.dp, border, imageShape))
            }
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(title, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(description, color = textSecondary, fontSize = 14.sp, lineHeight = 20.sp)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    chips.forEach { Chip(it, accent) }
                }
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val gap = 10.dp
                    val btnWidth = (maxWidth - gap) / 2f
                    Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                        Button(
                            modifier = Modifier.width(btnWidth),
                            onClick = { onOpen(button1.second) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF223252)),
                            shape = RoundedCornerShape(10.dp)
                        ) { Text(button1.first, color = Color.White, fontSize = 14.sp) }
                        Button(
                            modifier = Modifier.width(btnWidth),
                            onClick = { onOpen(button2.second) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF223252)),
                            shape = RoundedCornerShape(10.dp)
                        ) { Text(button2.first, color = Color.White, fontSize = 14.sp) }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectCardCustomStack(
    title: String,
    description: String,
    header: @Composable () -> Unit,
    chips: List<String>,
    button1: Pair<String, String>,
    button2: Pair<String, String>,
    onOpen: (String) -> Unit
) {
    val cardShape = RoundedCornerShape(16.dp)
    val imageShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, border, cardShape)
            .background(cardBg, cardShape)
            .clip(cardShape)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(imageShape)
            ) {
                header()
                Box(modifier = Modifier.fillMaxSize().border(1.dp, border, imageShape))
            }
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(title, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(description, color = textSecondary, fontSize = 14.sp, lineHeight = 20.sp)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    chips.forEach { Chip(it, accent) }
                }
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val gap = 10.dp
                    val btnWidth = (maxWidth - gap) / 2f
                    Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                        Button(
                            modifier = Modifier.width(btnWidth),
                            onClick = { onOpen(button1.second) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF223252)),
                            shape = RoundedCornerShape(10.dp)
                        ) { Text(button1.first, color = Color.White, fontSize = 14.sp) }
                        Button(
                            modifier = Modifier.width(btnWidth),
                            onClick = { onOpen(button2.second) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF223252)),
                            shape = RoundedCornerShape(10.dp)
                        ) { Text(button2.first, color = Color.White, fontSize = 14.sp) }
                    }
                }
            }
        }
    }
}

@Composable
private fun GameSection(onOpen: () -> Unit) {
    SectionCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SectionTitleLarge("Ready for a Challenge?")
            Text(
                "I built a tiny game to showcase problem-solving with a playful twist.",
                color = textSecondary,
                fontSize = 16.sp
            )
            Box(
                modifier = Modifier
                    .width(260.dp)
                    .height(520.dp)
                    .border(10.dp, Color.Black, RoundedCornerShape(36.dp))
                    .background(Color(0xFF0B162C), RoundedCornerShape(36.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Tap “Play Now”", color = textSecondary, fontSize = 14.sp)
            }
            Button(
                onClick = onOpen,
                colors = ButtonDefaults.buttonColors(containerColor = accent),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Play Now", color = Color(0xFF061127), fontWeight = FontWeight.Bold, fontSize = 16.sp) }
        }
    }
}

private enum class BugType { NORMAL, GOLDEN, GREEN }

private data class Bug(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val r: Float,
    val type: BugType
)

@Composable
private fun GameModal(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xB3000000))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClose() },
        contentAlignment = Alignment.Center
    ) {
        val phoneWidth = 320.dp
        val phoneHeight = phoneWidth * (19.5f / 9f)

        Box(
            modifier = Modifier
                .size(phoneWidth, phoneHeight)
                .background(Color.Black, RoundedCornerShape(40.dp))
                .padding(10.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0B162C), RoundedCornerShape(30.dp))
            ) {
                GameCanvas()
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(accent, RoundedCornerShape(999.dp))
                    .clickable { onClose() },
                contentAlignment = Alignment.Center
            ) {
                Text("X", color = Color(0xFF061127), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun GameCanvas() {
    var running by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableIntStateOf(30) }
    var score by remember { mutableIntStateOf(0) }
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var frameTime by remember { mutableStateOf(0L) }
    val bugs = remember { mutableListOf<Bug>() }
    val baseBugs = 6
    val maxBugs = 60
    val goldenChance = 0.06f
    val greenChance = 0.08f

    fun chooseType(): BugType {
        val r = Random.nextFloat()
        return when {
            r < goldenChance -> BugType.GOLDEN
            r < goldenChance + greenChance -> BugType.GREEN
            else -> BugType.NORMAL
        }
    }

    fun spawnBug(w: Float, h: Float, type: BugType = chooseType()): Bug {
        val baseR = 12f
        val r = when (type) {
            BugType.GOLDEN -> baseR * 1.15f
            BugType.GREEN -> baseR * 1.05f
            BugType.NORMAL -> baseR
        }
        val vx = Random.nextFloat() * 1.6f - 0.8f
        val vy = Random.nextFloat() * 1.6f - 0.8f
        val x = r + Random.nextFloat() * (w - r * 2f)
        val y = r + 48f + Random.nextFloat() * (h - r * 2f - 48f)
        return Bug(x, y, vx, vy, r, type)
    }

    fun refillBase(w: Float, h: Float) {
        bugs.clear()
        repeat(baseBugs) { bugs.add(spawnBug(w, h, BugType.NORMAL)) }
    }

    fun resetGame() {
        score = 0
        timeLeft = 30
        val w = canvasSize.width.toFloat()
        val h = canvasSize.height.toFloat()
        if (w > 0 && h > 0) {
            refillBase(w, h)
        }
    }

    LaunchedEffect(running, canvasSize) {
        if (!running || canvasSize == IntSize.Zero) return@LaunchedEffect
        var last = 0L
        while (running) {
            val now = withFrameNanos { it }
            val dt = if (last == 0L) 0f else (now - last) / 1_000_000_000f
            last = now

            val w = canvasSize.width.toFloat()
            val h = canvasSize.height.toFloat()
            for (b in bugs) {
                b.x += b.vx * 140f * dt
                b.y += b.vy * 140f * dt
                if (b.x - b.r < 0f || b.x + b.r > w) b.vx *= -1f
                if (b.y - b.r < 40f || b.y + b.r > h) b.vy *= -1f
            }
            frameTime = now
        }
    }

    LaunchedEffect(running) {
        if (!running) return@LaunchedEffect
        while (running && timeLeft > 0) {
            delay(1000)
            timeLeft -= 1
        }
        if (timeLeft <= 0) running = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { canvasSize = it }
                .pointerInput(running) {
                    detectTapGestures { pos ->
                        if (!running) return@detectTapGestures
                        val w = canvasSize.width.toFloat()
                        val h = canvasSize.height.toFloat()
                        for (i in bugs.indices.reversed()) {
                            val b = bugs[i]
                            val dx = pos.x - b.x
                            val dy = pos.y - b.y
                            if (sqrt(dx * dx + dy * dy) <= b.r) {
                                when (b.type) {
                                    BugType.GOLDEN -> {
                                        val aliveCount = bugs.size
                                        score += 5 + kotlin.math.max(0, aliveCount - 1)
                                        if (w > 0 && h > 0) refillBase(w, h)
                                    }
                                    BugType.GREEN -> {
                                        score += 3
                                        bugs.removeAt(i)
                                        val toAdd = kotlin.math.min(20, maxBugs - bugs.size)
                                        repeat(toAdd) { bugs.add(spawnBug(w, h, BugType.NORMAL)) }
                                    }
                                    BugType.NORMAL -> {
                                        score += 1
                                        bugs[i] = spawnBug(w, h)
                                    }
                                }
                                break
                            }
                        }
                    }
                }
        ) {
            frameTime // read to trigger redraws
            drawRect(Color(0xFF0B1626))
            for (b in bugs) {
                val bodyColor = when (b.type) {
                    BugType.GOLDEN -> Color(0xFFFACC15)
                    BugType.GREEN -> Color(0xFF22C55E)
                    BugType.NORMAL -> Color(0xFFE11D48)
                }
                val headColor = when (b.type) {
                    BugType.GOLDEN -> Color(0xFFB45309)
                    BugType.GREEN -> Color(0xFF065F46)
                    BugType.NORMAL -> Color(0xFF7F1D1D)
                }
                drawCircle(bodyColor, radius = b.r, center = Offset(b.x, b.y))
                drawCircle(headColor, radius = b.r * 0.55f, center = Offset(b.x, b.y - b.r * 0.8f))
                if (b.type != BugType.NORMAL) {
                    drawCircle(
                        color = if (b.type == BugType.GOLDEN) Color(0x66FACC15) else Color(0x6634D399),
                        radius = b.r * 1.35f,
                        center = Offset(b.x, b.y),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Pill(icon = Res.drawable.icon_timer, text = "${timeLeft}s")
            Pill(icon = Res.drawable.icon_bug, text = "$score")
        }

        if (!running) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x66000000)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        if (timeLeft <= 0) "Time's up!" else "Bug Smasher",
                        color = textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        if (timeLeft <= 0) "Final score: $score" else "Tap the bugs before the time runs out.",
                        color = textSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 10.dp)
                    )
                    Button(
                        onClick = {
                            resetGame()
                            running = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = accent),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(if (timeLeft <= 0) "Play again" else "Start", color = Color(0xFF061127), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalResourceApi::class)
private fun Pill(icon: DrawableResource, text: String) {
    Box(
        modifier = Modifier
            .background(Color(0x66000000), RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(text, color = textPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ContactSection() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    SectionBlock {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SectionTitle("Get In Touch")
            Text("Have a project in mind or just want to say hi? Feel free to reach out!", color = textSecondary, fontSize = 16.sp)
            Column(
                modifier = Modifier.widthIn(max = 520.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    placeholder = { Text("Your Name") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF222F49),
                        unfocusedContainerColor = Color(0xFF222F49),
                        focusedIndicatorColor = accent,
                        unfocusedIndicatorColor = Color(0xFF2D3C5B),
                        focusedLabelColor = textPrimary,
                        unfocusedLabelColor = textSecondary,
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary,
                        focusedPlaceholderColor = textSecondary,
                        unfocusedPlaceholderColor = textSecondary,
                        cursorColor = accent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    placeholder = { Text("you@example.com") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF222F49),
                        unfocusedContainerColor = Color(0xFF222F49),
                        focusedIndicatorColor = accent,
                        unfocusedIndicatorColor = Color(0xFF2D3C5B),
                        focusedLabelColor = textPrimary,
                        unfocusedLabelColor = textSecondary,
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary,
                        focusedPlaceholderColor = textSecondary,
                        unfocusedPlaceholderColor = textSecondary,
                        cursorColor = accent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Message") },
                    placeholder = { Text("Your message here...") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF222F49),
                        unfocusedContainerColor = Color(0xFF222F49),
                        focusedIndicatorColor = accent,
                        unfocusedIndicatorColor = Color(0xFF2D3C5B),
                        focusedLabelColor = textPrimary,
                        unfocusedLabelColor = textSecondary,
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary,
                        focusedPlaceholderColor = textSecondary,
                        unfocusedPlaceholderColor = textSecondary,
                        cursorColor = accent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(140.dp)
                )
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = accent),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send Message", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
private fun Footer() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("© 2024 David Navarro. All rights reserved.", color = textSecondary, fontSize = 12.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).background(accent, RoundedCornerShape(999.dp)))
            Text("KMP + Compose UI", color = textSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SectionCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, border, RoundedCornerShape(18.dp))
            .background(cardBg, RoundedCornerShape(18.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = content
    )
}

@Composable
private fun SectionBlock(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = content
    )
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 22.sp)
}

@Composable
private fun SectionTitleLarge(text: String) {
    Text(text, color = textPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 32.sp)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun AnimatedHeaderIcon(icon: DrawableResource, size: androidx.compose.ui.unit.Dp, bounce: Boolean) {
    val transition = rememberInfiniteTransition(label = "headerIcon")
    val alpha by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(animation = tween(1200), repeatMode = RepeatMode.Reverse),
        label = "iconAlpha"
    )
    val offsetY by transition.animateFloat(
        initialValue = 0f,
        targetValue = if (bounce) -6f else 0f,
        animationSpec = infiniteRepeatable(animation = tween(600), repeatMode = RepeatMode.Reverse),
        label = "iconBounce"
    )
    Image(
        painter = painterResource(icon),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .offset(y = offsetY.dp)
            .graphicsLayer(alpha = alpha)
    )
}

@Composable
private fun TypingTitle(text: String, fontSize: androidx.compose.ui.unit.TextUnit) {
    var content by remember { mutableStateOf("") }
    var index by remember { mutableIntStateOf(0) }
    var forward by remember { mutableStateOf(true) }
    val cursorAlpha by rememberInfiniteTransition(label = "cursorBlink").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(750), repeatMode = RepeatMode.Reverse),
        label = "cursorAlpha"
    )
    val density = LocalDensity.current
    val cursorHeight = with(density) { fontSize.toDp() + 6.dp }

    LaunchedEffect(text) {
        while (true) {
            content = text.take(index)
            if (forward) {
                if (index < text.length) {
                    index++
                    delay(80)
                } else {
                    delay(1200)
                    forward = false
                }
            } else {
                if (index > 0) {
                    index--
                    delay(50)
                } else {
                    forward = true
                    delay(300)
                }
            }
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(content, color = textPrimary, fontWeight = FontWeight.Black, fontSize = fontSize)
        Box(
            modifier = Modifier
                .padding(start = 4.dp)
                .width(2.dp)
                .height(cursorHeight)
                .background(accent)
                .graphicsLayer(alpha = cursorAlpha)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun IconLinkButton(icon: DrawableResource, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222F49)),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(48.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
    }
}
