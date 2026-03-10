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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.offset
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import davidweb_kmp.composeapp.generated.resources.Res
import davidweb_kmp.composeapp.generated.resources.cleancode
import davidweb_kmp.composeapp.generated.resources.image_david
import davidweb_kmp.composeapp.generated.resources.permission_protect
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

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = pageBg) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 960.dp)
                        .fillMaxWidth()
                        .verticalScroll(scroll)
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(26.dp)
                ) {
                    Header()
                    HeroSection()
                    SkillsSection()
                    TimelineSection()
                    ProjectsSection()
                    GameSection()
                    ContactSection()
                    Footer()
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
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color(0xFF3DDC84), RoundedCornerShape(999.dp))
                )
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .graphicsLayer(rotationZ = 45f)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF7F52FF), Color(0xFFA97BFF), Color(0xFFFF8A00))
                            ),
                            RoundedCornerShape(3.dp)
                        )
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

    SectionCard {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val mobile = maxWidth < 940.dp
            val compact = maxWidth < 480.dp
            if (mobile) {
                Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    HeroText(uriHandler, compact)
                    HeroImage()
                }
            } else {
                val imageWidth = 320.dp
                val gap = 30.dp
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
        Text("Android Developer", color = textPrimary, fontWeight = FontWeight.Black, fontSize = titleSize)
        Text(
            "Passionate about building modern Android apps (Kotlin · Compose · KMP) with great UX and performance.",
            color = textSecondary,
            fontSize = bodySize,
            lineHeight = (bodySize.value + 6).sp
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { uriHandler.openUri("/assets/cv/cv.pdf") },
                colors = ButtonDefaults.buttonColors(containerColor = accent),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Download CV", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp) }
            Button(
                onClick = { uriHandler.openUri("https://github.com/Deiivid") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF233255)),
                shape = RoundedCornerShape(12.dp)
            ) { Text("GitHub", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp) }
            Button(
                onClick = { uriHandler.openUri("https://medium.com/@davidnavarrom3") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF233255)),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Medium", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp) }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun HeroImage() {
    Box(
        modifier = Modifier
            .width(320.dp)
            .aspectRatio(1f)
            .shadow(18.dp, RoundedCornerShape(16.dp))
            .border(1.dp, border, RoundedCornerShape(16.dp))
            .background(Color(0x111B2A), RoundedCornerShape(16.dp))
            .padding(6.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.image_david),
            contentDescription = "David Navarro",
            modifier = Modifier.fillMaxSize().background(Color.Black, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun SkillsSection() {
    SectionCard {
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
                        SkillCard("Kotlin & Java", "Primary languages for Android development.", Modifier.width(cardWidth))
                        if (columns > 1) SkillCard("Jetpack Compose", "Modern declarative UI toolkit.", Modifier.width(cardWidth))
                        if (columns > 2) SkillCard("Coroutines & Flow", "Asynchronous and reactive programming.", Modifier.width(cardWidth))
                    }
                    if (columns == 1) {
                        SkillCard("Jetpack Compose", "Modern declarative UI toolkit.", Modifier.fillMaxWidth())
                        SkillCard("Coroutines & Flow", "Asynchronous and reactive programming.", Modifier.fillMaxWidth())
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                        SkillCard("Koin / Dagger-Hilt", "Dependency injection frameworks.", Modifier.width(cardWidth))
                        if (columns > 1) SkillCard("CI/CD", "Automated build & delivery pipelines.", Modifier.width(cardWidth))
                        if (columns > 2) SkillCard("Unit & UI Testing", "Quality, stability and coverage.", Modifier.width(cardWidth))
                    }
                    if (columns == 1) {
                        SkillCard("CI/CD", "Automated build & delivery pipelines.", Modifier.fillMaxWidth())
                        SkillCard("Unit & UI Testing", "Quality, stability and coverage.", Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
private fun SkillCard(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(1.dp, border, RoundedCornerShape(14.dp))
            .background(Color(0xFF121A2E), RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, color = textSecondary, fontSize = 14.sp)
        }
    }
}

@Composable
private fun TimelineSection() {
    SectionCard {
        val entries = listOf(
            TimelineEntry(
                period = "Currently",
                title = "Hiberus – Android Developer supporting a banking sector client",
                subtitle = "Developing integrations using Jetpack Compose.",
                icon = "🏛",
                iconBg = Color(0xFF0F5D4A),
                chipTone = Color(0xFF0F5D4A),
                chips = listOf("Android", "Jetpack Compose")
            ),
            TimelineEntry(
                period = "2024",
                title = "Specialization and Clean Architecture",
                subtitle = "Focused on Clean Architecture, personal projects, and advanced mobile development.",
                icon = "🧭",
                iconBg = Color(0xFF203B7A),
                chipTone = Color(0xFF203B7A),
                chips = listOf("Clean Architecture", "MVVM", "Proyectos personales")
            ),
            TimelineEntry(
                period = "2021 - 2023",
                title = "Immersion in Mobile Development",
                subtitle = "First professional steps, project leadership, and creation of a personal library.",
                icon = "📱",
                iconBg = Color(0xFF0F5D4A),
                chipTone = Color(0xFF0F5D4A),
                chips = listOf("Java", "Kotlin", "Jetpack Compose", "Libreria")
            ),
            TimelineEntry(
                period = "2021",
                title = "Hiberus Heroes y Heroinas",
                subtitle = "Intensive training program specialized in Mobile application development with the MEAN Stack.",
                icon = "🧪",
                iconBg = Color(0xFF203B7A),
                chipTone = Color(0xFF203B7A),
                chips = listOf("MEAN", "Javascript", "Training")
            ),
            TimelineEntry(
                period = "2019 - 2021",
                title = "Transition to DAM",
                subtitle = "From Systems Technician to Higher Degree in Multiplatform Application Development.",
                icon = "🎓",
                iconBg = Color(0xFF0F5D4A),
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
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(border)
                .align(Alignment.TopStart)
                .offset(x = 24.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
            entries.forEach { TimelineItemRow(it) }
        }
    }
}

private data class TimelineEntry(
    val period: String,
    val title: String,
    val subtitle: String,
    val icon: String,
    val iconBg: Color,
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
            modifier = Modifier.width(48.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(entry.iconBg, RoundedCornerShape(999.dp))
                    .border(1.dp, border, RoundedCornerShape(999.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(entry.icon, fontSize = 14.sp, color = Color.White)
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
    SectionCard {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            SectionTitle("My Projects")
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val stack = maxWidth < 860.dp
                if (stack) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        ProjectCardStack(
                            title = "PermissionProtect",
                            description = "Android app to review, control and learn about app permissions.",
                            image = Res.drawable.permission_protect,
                            button1 = "Google Play" to "https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es",
                            button2 = "Web" to "https://deiivid.github.io/PermissionProtectWeb/",
                            onOpen = uriHandler::openUri
                        )
                        ProjectCardStack(
                            title = "Clean Architecture Compose",
                            description = "Multi-module Android project with layered architecture and Compose.",
                            image = Res.drawable.cleancode,
                            button1 = "GitHub" to "https://github.com/Deiivid/Clean_Arquitecture_Compose",
                            button2 = "README" to "https://github.com/Deiivid/Clean_Arquitecture_Compose#readme",
                            onOpen = uriHandler::openUri
                        )
                    }
                } else {
                    val gap = 16.dp
                    val cardWidth = (maxWidth - gap) / 2f
                    Row(horizontalArrangement = Arrangement.spacedBy(gap), modifier = Modifier.fillMaxWidth()) {
                        ProjectCard(
                            title = "PermissionProtect",
                            description = "Android app to review, control and learn about app permissions.",
                            image = Res.drawable.permission_protect,
                            button1 = "Google Play" to "https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es",
                            button2 = "Web" to "https://deiivid.github.io/PermissionProtectWeb/",
                            onOpen = uriHandler::openUri,
                            modifier = Modifier.width(cardWidth)
                        )
                        ProjectCard(
                            title = "Clean Architecture Compose",
                            description = "Multi-module Android project with layered architecture and Compose.",
                            image = Res.drawable.cleancode,
                            button1 = "GitHub" to "https://github.com/Deiivid/Clean_Arquitecture_Compose",
                            button2 = "README" to "https://github.com/Deiivid/Clean_Arquitecture_Compose#readme",
                            onOpen = uriHandler::openUri,
                            modifier = Modifier.width(cardWidth)
                        )
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
    button1: Pair<String, String>,
    button2: Pair<String, String>,
    onOpen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(1.dp, border, RoundedCornerShape(16.dp))
            .background(cardBg, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Image(
                painter = painterResource(image),
                contentDescription = title,
                modifier = Modifier.fillMaxWidth().height(260.dp).background(Color(0x20000000), RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(title, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(description, color = textSecondary, fontSize = 14.sp, lineHeight = 20.sp)
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

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ProjectCardStack(
    title: String,
    description: String,
    image: DrawableResource,
    button1: Pair<String, String>,
    button2: Pair<String, String>,
    onOpen: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, border, RoundedCornerShape(16.dp))
            .background(cardBg, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Image(
                painter = painterResource(image),
                contentDescription = title,
                modifier = Modifier.fillMaxWidth().height(240.dp).background(Color(0x20000000), RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(title, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(description, color = textSecondary, fontSize = 14.sp, lineHeight = 20.sp)
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

@Composable
private fun GameSection() {
    var showGame by remember { mutableStateOf(false) }

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
                onClick = { showGame = true },
                colors = ButtonDefaults.buttonColors(containerColor = accent),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Play Now", color = Color(0xFF061127), fontWeight = FontWeight.Bold, fontSize = 16.sp) }
        }
    }

    if (showGame) {
        GameModal(onClose = { showGame = false })
    }
}

private data class Bug(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val r: Float
)

@Composable
private fun GameModal(onClose: () -> Unit) {
    Box(
        modifier = Modifier
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
    var frameTick by remember { mutableIntStateOf(0) }
    val bugs = remember { mutableStateListOf<Bug>() }

    fun spawnBug(w: Float, h: Float): Bug {
        val r = 12f
        val vx = Random.nextFloat() * 1.6f - 0.8f
        val vy = Random.nextFloat() * 1.6f - 0.8f
        val x = r + Random.nextFloat() * (w - r * 2f)
        val y = r + 48f + Random.nextFloat() * (h - r * 2f - 48f)
        return Bug(x, y, vx, vy, r)
    }

    fun resetGame() {
        score = 0
        timeLeft = 30
        bugs.clear()
        val w = canvasSize.width.toFloat()
        val h = canvasSize.height.toFloat()
        if (w > 0 && h > 0) {
            repeat(6) { bugs.add(spawnBug(w, h)) }
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
            frameTick++
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
                .pointerInput(running, frameTick) {
                    detectTapGestures { pos ->
                        if (!running) return@detectTapGestures
                        for (i in bugs.indices.reversed()) {
                            val b = bugs[i]
                            val dx = pos.x - b.x
                            val dy = pos.y - b.y
                            if (sqrt(dx * dx + dy * dy) <= b.r) {
                                score += 1
                                val w = canvasSize.width.toFloat()
                                val h = canvasSize.height.toFloat()
                                bugs[i] = spawnBug(w, h)
                                break
                            }
                        }
                    }
                }
        ) {
            drawRect(Color(0xFF0B1626))
            for (b in bugs) {
                drawCircle(Color(0xFFE11D48), radius = b.r, center = Offset(b.x, b.y))
                drawCircle(Color(0xFF7F1D1D), radius = b.r * 0.55f, center = Offset(b.x, b.y - b.r * 0.8f))
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Pill("⏱ ${timeLeft}s")
            Pill("🐞 $score")
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
private fun Pill(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0x66000000), RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, color = textPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ContactSection() {
    SectionCard {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            SectionTitle("Get In Touch")
            Text("Have a project in mind or just want to say hi?", color = textSecondary, fontSize = 16.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ContactChip("Email: davidnavarrom3@gmail.com")
                ContactChip("GitHub: /Deiivid")
            }
        }
    }
}

@Composable
private fun ContactChip(text: String) {
    Box(
        modifier = Modifier
            .border(1.dp, border, RoundedCornerShape(999.dp))
            .background(Color(0xFF161F36), RoundedCornerShape(999.dp))
            .padding(horizontal = 14.dp, vertical = 9.dp)
    ) { Text(text, color = textPrimary, fontSize = 14.sp) }
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
private fun SectionTitle(text: String) {
    Text(text, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 22.sp)
}

@Composable
private fun SectionTitleLarge(text: String) {
    Text(text, color = textPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 32.sp)
}
