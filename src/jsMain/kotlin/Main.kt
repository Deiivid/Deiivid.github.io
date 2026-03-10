import kotlinx.browser.document

fun main() {
    val root = document.getElementById("root") ?: return
    root.innerHTML = """
<div class="relative flex min-h-screen w-full flex-col overflow-x-hidden">
  <div class="layout-container flex h-full grow flex-col">
    <div class="flex flex-1 justify-center py-5 md:px-10 lg:px-40">
      <div class="layout-content-container flex flex-col max-w-[960px] flex-1">
        <header class="sticky top-0 z-50 flex items-center justify-between whitespace-nowrap border-b border-white/10 px-4 sm:px-10 py-3 bg-background-dark/80 backdrop-blur-sm" id="home">
          <div class="flex items-center gap-4 text-white">
            <div class="size-5 text-primary">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
                   class="w-8 h-8 text-[#3DDC84] animate-bounce blink-icon" fill="currentColor" aria-hidden="true">
                <path d="M17.6 9.48l1.43-2.48a.5.5 0 0 0-.86-.5l-1.45 2.53a7.93 7.93 0 0 0-8.44 0L6.83 6.5a.5.5 0 0 0-.86.5l1.43 2.48A8.15 8.15 0 0 0 4 16a.5.5 0 0 0 .5.5h1.51v3.25a1.25 1.25 0 1 0 2.5 0V16h1.5v3.25a1.25 1.25 0 1 0 2.5 0V16h1.5v3.25a1.25 1.25 0 1 0 2.5 0V16H19.5a.5.5 0 0 0 .5-.5 8.15 8.15 0 0 0-2.4-6.02zM8.75 10.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5zm6.5 0a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5z"/>
              </svg>
            </div>
            <svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" class="w-6 h-6 blink-icon">
              <defs>
                <linearGradient id="kotlinGradientHeader" x1="0" y1="0" x2="1" y2="1">
                  <stop offset="0%" stop-color="#7F52FF"/>
                  <stop offset="50%" stop-color="#A97BFF"/>
                  <stop offset="100%" stop-color="#FF8A00"/>
                </linearGradient>
              </defs>
              <path fill="url(#kotlinGradientHeader)" d="M2 2h20L12 12l10 10H2z"/>
            </svg>
            <h2 class="text-white text-lg font-bold tracking-[-0.015em]">David Navarro</h2>
          </div>
          <div class="hidden md:flex flex-1 justify-end gap-8">
            <div class="flex items-center gap-9">
              <a class="text-white/80 hover:text-white text-sm font-medium transition-colors" href="#home">Home</a>
              <a class="text-white/80 hover:text-white text-sm font-medium transition-colors" href="#skills">Skills</a>
              <a class="text-white/80 hover:text-white text-sm font-medium transition-colors" href="#timeline">Timeline</a>
              <a class="text-white/80 hover:text-white text-sm font-medium transition-colors" href="#projects">Projects</a>
              <a class="text-white/80 hover:text-white text-sm font-medium transition-colors" href="#game">Game</a>
              <a class="text-white/80 hover:text-white text-sm font-medium transition-colors" href="#contact">Contact</a>
            </div>
          </div>
        </header>

        <main class="flex flex-col gap-16 md:gap-24">
          <section class="@container pt-16 md:pt-24">
            <div class="flex flex-col gap-6 px-4 py-10 @[480px]:gap-8 @[864px]:flex-row-reverse @[864px]:items-center">
              <div class="relative w-full flex-shrink-0 @[864px]:max-w-[320px]">
                <div aria-hidden class="absolute inset-0 rounded-[1rem] bg-gradient-to-br from-[#256af4]/10 via-transparent to-transparent blur-2xl"></div>
                <div aria-hidden class="pointer-events-none absolute -inset-8 -z-10 rounded-2xl bg-gradient-to-br from-primary/15 via-transparent to-transparent blur-3xl opacity-60"></div>
                <div class="relative overflow-hidden rounded-xl ring-1 ring-white/10 shadow-[0_10px_50px_rgba(0,0,0,0.6)] backdrop-blur-sm">
                  <img src="assets/img/imageDavid.jpeg" alt="David Navarro — Android Developer"
                       class="w-full h-full object-cover transition-all duration-700 ease-in-out hover:scale-[1.02] hover:brightness-105"/>
                  <div class="absolute inset-0 bg-gradient-to-t from-background-dark/80 via-transparent to-transparent mix-blend-overlay"></div>
                </div>
              </div>

              <div class="flex flex-col gap-6 @[480px]:min-w-[400px] @[480px]:gap-8 @[864px]:justify-center">
                <div class="flex flex-col gap-4 text-left">
                  <div class="flex items-center gap-3">
                    <h1 id="typingTitle" class="typing text-white text-4xl @[480px]:text-5xl font-black leading-tight">
                      Android Developer
                    </h1>
                  </div>

                  <h2 class="text-white/80 text-base @[480px]:text-lg">
                    Passionate about building modern Android apps (Kotlin · Compose · KMP) with great UX and performance.
                  </h2>
                </div>

                <div class="flex flex-col sm:flex-row gap-4 items-start">
                  <a id="downloadCvLink"
                     href="assets/cv/cv.pdf"
                     download="cv.pdf"
                     class="inline-flex min-w-[140px] items-center justify-center rounded-lg h-12 px-5 bg-primary text-white text-base font-bold tracking-[0.015em] hover:bg-primary/90 transition-colors">
                    Download CV
                  </a>

                  <div class="flex gap-2">
                    <a class="flex max-w-[480px] cursor-pointer items-center justify-center overflow-hidden rounded-lg h-12 bg-[#222f49] text-white/80 hover:text-white gap-2 text-sm font-bold leading-normal tracking-[0.015em] min-w-0 px-4 transition-colors"
                       href="https://github.com/Deiivid" target="_blank" rel="noopener" aria-label="GitHub">
                      <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
                        <path fill-rule="evenodd" clip-rule="evenodd"
                              d="M12 2C6.48 2 2 6.58 2 12.26c0 4.5 2.87 8.32 6.84 9.67.5.09.68-.22.68-.48v-1.68c-2.78.62-3.37-1.36-3.37-1.36-.46-1.18-1.11-1.49-1.11-1.49-.9-.63.07-.62.07-.62 1 .07 1.53 1.05 1.53 1.05.89 1.55 2.34 1.1 2.91.85.09-.67.35-1.1.63-1.36-2.22-.26-4.56-1.12-4.56-4.97 0-1.1.39-2 1.03-2.7-.1-.26-.45-1.27.1-2.64 0 0 .84-.28 2.75 1.05a9.38 9.38 0 0 1 5 0c1.9-1.33 2.75-1.05 2.75-1.05.55 1.37.2 2.38.1 2.64.64.7 1.03 1.6 1.03 2.7 0 3.86-2.34 4.71-4.57 4.97.36.31.68.93.68 1.88v2.78c0 .26.18.57.69.47A10.01 10.01 0 0 0 22 12.26C22 6.58 17.52 2 12 2Z"/>
                      </svg>
                    </a>

                    <a class="flex max-w-[480px] cursor-pointer items-center justify-center overflow-hidden rounded-lg h-12 bg-[#222f49] text-white/80 hover:text-white gap-2 text-sm font-bold leading-normal tracking-[0.015em] min-w-0 px-4 transition-colors"
                       href="https://medium.com/@davidnavarrom3" target="_blank" rel="noopener" aria-label="Medium">
                      <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 1043.63 592.71" aria-hidden="true">
                        <path d="M588.67 296.36c0 163.65-131.78 296.35-294.33 296.35S0 460 0 296.36 131.78 0 294.33 0s294.34 132.71 294.34 296.36zm304.55 0c0 154.13-65.89 279.06-147.2 279.06S598.82 450.49 598.82 296.36 664.71 17.3 745.99 17.3s147.23 125.07 147.23 279.06zm150.41 0c0 142.45-29.25 258.02-65.32 258.02s-65.32-115.57-65.32-258.02 29.25-258.02 65.32-258.02 65.32 115.57 65.32 258.02z"/>
                      </svg>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </section>

          <section id="skills" class="flex flex-col gap-6 px-4">
            <div class="flex flex-col gap-2">
              <h2 class="text-white text-[22px] font-bold tracking-[-0.015em] pt-5">Key Skills</h2>
              <p class="text-white/70 text-base">
                A selection of my preferred tools and technologies for building high-quality Android applications.
              </p>
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
              <article class="group rounded-xl border border-white/10 bg-[#131a2a] p-5 hover:border-primary/60 transition-colors"><div class="flex items-start gap-4"><span class="material-symbols-outlined text-3xl text-primary">code</span><div><h3 class="text-white font-bold">Kotlin &amp; Java</h3><p class="text-white/70 text-sm">Primary languages for Android development.</p></div></div></article>
              <article class="group rounded-xl border border-white/10 bg-[#131a2a] p-5 hover:border-primary/60 transition-colors"><div class="flex items-start gap-4"><span class="material-symbols-outlined text-3xl text-primary">layers</span><div><h3 class="text-white font-bold">Jetpack Compose</h3><p class="text-white/70 text-sm">Modern declarative UI toolkit.</p></div></div></article>
              <article class="group rounded-xl border border-white/10 bg-[#131a2a] p-5 hover:border-primary/60 transition-colors"><div class="flex items-start gap-4"><span class="material-symbols-outlined text-3xl text-primary">sync</span><div><h3 class="text-white font-bold">Coroutines &amp; Flow</h3><p class="text-white/70 text-sm">Asynchronous and reactive programming.</p></div></div></article>
              <article class="group rounded-xl border border-white/10 bg-[#131a2a] p-5 hover:border-primary/60 transition-colors"><div class="flex items-start gap-4"><span class="material-symbols-outlined text-3xl text-primary">hub</span><div><h3 class="text-white font-bold">Koin / Dagger-Hilt </h3><p class="text-white/70 text-sm">Dependency injection frameworks.</p></div></div></article>
              <article class="group rounded-xl border border-white/10 bg-[#131a2a] p-5 hover:border-primary/60 transition-colors"><div class="flex items-start gap-4"><span class="material-symbols-outlined text-3xl text-primary">deployed_code</span><div><h3 class="text-white font-bold">CI/CD</h3><p class="text-white/70 text-sm">Automated build &amp; delivery pipelines.</p></div></div></article>
              <article class="group rounded-xl border border-white/10 bg-[#131a2a] p-5 hover:border-primary/60 transition-colors"><div class="flex items-start gap-4"><span class="material-symbols-outlined text-3xl text-primary">science</span><div><h3 class="text-white font-bold">Unit &amp; UI Testing</h3><p class="text-white/70 text-sm">Quality, stability and coverage.</p></div></div></article>
            </div>

            <div class="mt-2">
              <h3 class="text-primary font-bold text-lg mb-3">Also comfortable with</h3>
              <div class="flex flex-wrap gap-3">
                <span class="flex h-9 items-center rounded-full bg-[#161d2e] px-4 text-sm font-medium text-white/90">KMP</span>
                <span class="flex h-9 items-center rounded-full bg-[#161d2e] px-4 text-sm font-medium text-white/90">Retrofit</span>
                <span class="flex h-9 items-center rounded-full bg-[#161d2e] px-4 text-sm font-medium text-white/90">SQL</span>
                <span class="flex h-9 items-center rounded-full bg-[#161d2e] px-4 text-sm font-medium text-white/90">Firebase</span>
                <span class="flex h-9 items-center rounded-full bg-[#161d2e] px-4 text-sm font-medium text-white/90">Git</span>
                <span class="flex h-9 items-center rounded-full bg-[#161d2e] px-4 text-sm font-medium text-white/90">GitHub Actions</span>
                <span class="flex h-9 items-center rounded-full bg-[#161d2e] px-4 text-sm font-medium text-white/90">Detekt</span>
                <span class="flex h-9 items-center rounded-full bg-[#161d2e] px-4 text-sm font-medium text-white/90">Koin</span>
                <span class="flex h-9 items-center rounded-full bg-[#161d2e] px-4 text-sm font-medium text-white/90">Clean Architecture</span>
              </div>
            </div>
          </section>

          <section id="timeline" class="px-4 md:px-0">
            <h2 class="text-text-primary-dark text-3xl md:text-[40px] font-bold tracking-[-0.02em] mb-6 md:mb-8">Timeline</h2>
            <div class="relative before:content-[''] before:absolute before:top-0 before:bottom-0 before:left-[28px] before:w-px before:bg-white/15">
              <ol class="space-y-10 md:space-y-14">
                <li class="grid grid-cols-[56px_1fr] gap-x-4"><div class="relative z-10 w-14 flex items-center justify-center"><div class="flex items-center justify-center w-11 h-11 rounded-full bg-[#134E4A] border border-white/15 shadow"><span class="material-symbols-outlined text-[#8EF0C5]">account_balance</span></div></div><div class="flex flex-col gap-2"><p class="text-text-secondary-dark text-sm">Currently</p><h3 class="text-text-primary-dark text-xl md:text-2xl font-semibold leading-snug">Hiberus – Android Developer supporting a banking sector client</h3><p class="text-text-secondary-dark">Developing integrations using Jetpack Compose.</p><div class="flex flex-wrap gap-2 mt-1"><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#052e2b] text-[#a7f3d0]">Android</span><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#052e2b] text-[#a7f3d0]">Jetpack Compose</span></div></div></li>
                <li class="grid grid-cols-[56px_1fr] gap-x-4"><div class="relative z-10 w-14 flex items-center justify-center"><div class="flex items-center justify-center w-11 h-11 rounded-full bg-[#1e3a8a] border border-white/15 shadow"><span class="material-symbols-outlined text-[#93c5fd]">architecture</span></div></div><div class="flex flex-col gap-2"><p class="text-text-secondary-dark text-sm">2024</p><h3 class="text-text-primary-dark text-xl md:text-2xl font-semibold leading-snug">Specialization and Clean Architecture</h3><p class="text-text-secondary-dark">Focused on Clean Architecture, personal projects, and advanced mobile development.</p><div class="flex flex-wrap gap-2 mt-1"><a href="https://github.com/Deiivid/Clean_Arquitecture_Compose" target="_blank" rel="noopener" data-tooltip="Open GitHub: Clean_Arquitecture_Compose" class="px-3 py-1 rounded-full text-xs font-medium bg-[#0b1220] text-[#9cc1ff] hover:bg-[#0f172a] hover:text-white transition-colors">Clean Architecture</a><a href="https://github.com/Deiivid" target="_blank" rel="noopener" data-tooltip="See MVVM work on GitHub" class="px-3 py-1 rounded-full text-xs font-medium bg-[#0b1220] text-[#9cc1ff] hover:bg-[#0f172a] hover:text-white transition-colors">MVVM</a><a href="https://github.com/Deiivid?tab=repositories" target="_blank" rel="noopener" data-tooltip="Browse personal projects" class="px-3 py-1 rounded-full text-xs font-medium bg-[#0b1220] text-[#9cc1ff] hover:bg-[#0f172a] hover:text-white transition-colors">Proyectos personales</a></div></div></li>
                <li class="grid grid-cols-[56px_1fr] gap-x-4"><div class="relative z-10 w-14 flex items-center justify-center"><div class="flex items-center justify-center w-11 h-11 rounded-full bg-[#134E4A] border border-white/15 shadow"><span class="material-symbols-outlined text-[#8EF0C5]">smartphone</span></div></div><div class="flex flex-col gap-2"><p class="text-text-secondary-dark text-sm">2021 - 2023</p><h3 class="text-text-primary-dark text-xl md:text-2xl font-semibold leading-snug">Immersion in Mobile Development</h3><p class="text-text-secondary-dark">First professional steps, project leadership, and creation of a personal library.</p><div class="flex flex-wrap gap-2 mt-1"><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#052e2b] text-[#a7f3d0]">Java</span><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#052e2b] text-[#a7f3d0]">Kotlin</span><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#052e2b] text-[#a7f3d0]">Jetpack Compose</span><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#052e2b] text-[#a7f3d0]">Librería</span></div></div></li>
                <li class="grid grid-cols-[56px_1fr] gap-x-4"><div class="relative z-10 w-14 flex items-center justify-center"><div class="flex items-center justify-center w-11 h-11 rounded-full bg-[#1e3a8a] border border-white/15 shadow"><span class="material-symbols-outlined text-[#93c5fd]">model_training</span></div></div><div class="flex flex-col gap-2"><p class="text-text-secondary-dark text-sm">2021</p><h3 class="text-text-primary-dark text-xl md:text-2xl font-semibold leading-snug">Hiberus Heroes y Heroinas</h3><p class="text-text-secondary-dark">Intensive training program specialized in Mobile application development with the MEAN Stack.</p><div class="flex flex-wrap gap-2 mt-1"><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#0b1220] text-[#9cc1ff]">MEAN</span><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#0b1220] text-[#9cc1ff]">Javascript</span><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#0b1220] text-[#9cc1ff]">Training</span></div></div></li>
                <li class="grid grid-cols-[56px_1fr] gap-x-4"><div class="relative z-10 w-14 flex items-center justify-center"><div class="flex items-center justify-center w-11 h-11 rounded-full bg-[#134E4A] border border-white/15 shadow"><span class="material-symbols-outlined text-[#8EF0C5]">school</span></div></div><div class="flex flex-col gap-2"><p class="text-text-secondary-dark text-sm">2019 - 2021</p><h3 class="text-text-primary-dark text-xl md:text-2xl font-semibold leading-snug">Transition to DAM</h3><p class="text-text-secondary-dark">From Systems Technician to Higher Degree in Multiplatform Application Development.</p><div class="flex flex-wrap gap-2 mt-1"><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#052e2b] text-[#a7f3d0]">Java</span><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#052e2b] text-[#a7f3d0]">Bases de datos</span><span class="px-3 py-1 rounded-full text-xs font-medium bg-[#052e2b] text-[#a7f3d0]">Sistemas</span></div></div></li>
              </ol>
            </div>
          </section>

          <section class="flex flex-col gap-8 px-4" id="projects">
            <h2 class="text-white text-[22px] font-bold tracking-[-0.015em] pb-3 pt-5">My Projects</h2>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
              <div class="flex flex-col gap-4 rounded-xl bg-[#161d2e] overflow-hidden group">
                <div class="relative w-full aspect-video overflow-hidden">
                  <img src="assets/img/permissionProtectImage.png"
                       alt="PermissionProtect — Android permissions helper"
                       class="absolute inset-0 w-full h-full object-cover transition-all duration-700 ease-in-out hover:scale-[1.02] hover:brightness-105"/>
                  <div class="absolute inset-0 ring-1 ring-white/10"></div>
                  <div class="absolute inset-0 bg-gradient-to-t from-background-dark/70 via-transparent to-transparent mix-blend-overlay"></div>
                </div>
                <div class="flex flex-col gap-6 p-6 pt-0">
                  <div class="flex flex-col gap-2">
                    <h3 class="text-white text-xl font-bold">PermissionProtect</h3>
                    <p class="text-white/70 text-sm">Android app to review, control and learn about your app permissions. Built with Kotlin &amp; Jetpack Compose.</p>
                  </div>
                  <div class="flex gap-2 flex-wrap">
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">Jetpack Compose</p></div>
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">Kotlin / Java</p></div>
                  </div>
                  <div class="flex gap-4">
                    <a class="flex-1 text-center py-2 px-4 rounded-lg bg-[#222f49] text-white text-sm font-bold hover:bg-[#2d3c5b] transition-colors" href="https://play.google.com/store/apps/details?id=es.permissionprotect&hl=es" target="_blank" rel="noopener">Google Play</a>
                    <a class="flex-1 text-center py-2 px-4 rounded-lg border border-[#222f49] text-white text-sm font-bold hover:bg-[#222f49] transition-colors" href="https://deiivid.github.io/PermissionProtectWeb/" target="_blank" rel="noopener">Web</a>
                  </div>
                </div>
              </div>

              <div class="flex flex-col gap-4 rounded-xl bg-[#161d2e] overflow-hidden group">
                <div class="relative w-full aspect-video overflow-hidden bg-gradient-to-br from-[#0b1220] via-[#111827] to-[#1f2937]">
                  <div class="absolute -left-16 -top-24 h-64 w-64 rounded-full bg-primary/25 blur-3xl"></div>
                  <div class="absolute right-[-40px] bottom-[-40px] h-72 w-72 rounded-full bg-[#3DDC84]/20 blur-3xl"></div>
                  <div class="absolute left-8 top-8 h-40 w-72 rounded-xl bg-white/10 border border-white/20 shadow-[0_8px_30px_rgba(0,0,0,0.4)] backdrop-blur-md"></div>
                  <div class="absolute right-10 bottom-10 h-24 w-44 rounded-xl bg-white/10 border border-white/20 backdrop-blur-md"></div>
                  <div class="absolute inset-0 ring-1 ring-white/10"></div>
                </div>

                <div class="flex flex-col gap-6 p-6 pt-0">
                  <div class="flex flex-col gap-2">
                    <h3 class="text-white text-xl font-bold"> Jetpack Compose Glassmorphism library </h3>
                    <p class="text-white/70 text-sm">For applying <em>glassmorphism</em> effects — blurring both background and content. Android 12+ uses <code>RenderEffect</code>, while Android 11 and below rely on a native <strong>C++/NDK</strong></p>
                  </div>
                  <div class="flex gap-2 flex-wrap">
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">Jetpack Compose</p></div>
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">Kotlin / Java</p></div>
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">C++ / NDK</p></div>
                  </div>

                  <div class="flex gap-4">
                    <a class="flex-1 text-center py-2 px-4 rounded-lg bg-[#222f49] text-white text-sm font-bold hover:bg-[#2d3c5b] transition-colors" href="https://github.com/Deiivid/Glassmorphism-Compose" target="_blank" rel="noopener">GitHub</a>
                    <a class="flex-1 text-center py-2 px-4 rounded-lg border border-[#222f49] text-white text-sm font-bold hover:bg-[#222f49] transition-colors" href="https://github.com/Deiivid/Glassmorphism-Compose#readme" target="_blank" rel="noopener">README</a>
                  </div>
                </div>
              </div>

              <div class="flex flex-col gap-4 rounded-xl bg-[#161d2e] overflow-hidden group">
                <div class="relative w-full aspect-video overflow-hidden">
                    <img src="assets/img/cleancode.png"
                         alt="Clean Architecture Compose — multi-module Android project"
                         loading="lazy"
                         class="absolute inset-0 w-full h-full object-cover transition-all duration-700 ease-in-out hover:scale-[1.02] hover:brightness-105"/>
                    <div class="absolute inset-0 ring-1 ring-white/10"></div>
                    <div class="absolute inset-0 bg-gradient-to-t from-background-dark/70 via-transparent to-transparent mix-blend-overlay"></div>
                </div>

                <div class="flex flex-col gap-6 p-6 pt-0">
                  <div class="flex flex-col gap-2">
                    <h3 class="text-white text-xl font-bold">Clean Architecture Compose</h3>
                    <p class="text-white/70 text-sm">A multi-module Android project demonstrating a layered <strong>Clean Architecture</strong> pattern — separation of concerns between <code>domain</code>, <code>data</code>, and <code>presentation</code> layers using <strong>Jetpack Compose</strong>. Includes custom <em>Detekt</em> rules, <em>use cases</em>, and dependency injection with <em>Koin</em>.</p>
                  </div>
                  <div class="flex gap-2 flex-wrap">
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">Jetpack Compose</p></div>
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">Kotlin</p></div>
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">Clean Architecture</p></div>
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">Koin</p></div>
                    <div class="flex h-7 items-center rounded-full bg-primary/20 px-3"><p class="text-primary text-xs font-medium">Detekt</p></div>
                  </div>

                  <div class="flex gap-4">
                    <a class="flex-1 text-center py-2 px-4 rounded-lg bg-[#222f49] text-white text-sm font-bold hover:bg-[#2d3c5b] transition-colors" href="https://github.com/Deiivid/Clean_Arquitecture_Compose" target="_blank" rel="noopener">GitHub</a>
                    <a class="flex-1 text-center py-2 px-4 rounded-lg border border-[#222f49] text-white text-sm font-bold hover:bg-[#222f49] transition-colors" href="https://github.com/Deiivid/Clean_Arquitecture_Compose#readme" target="_blank" rel="noopener">README</a>
                  </div>
                </div>
              </div>
            </div>
          </section>

          <section id="game" class="flex flex-col items-center gap-6 rounded-xl border border-white/10 bg-[#161a22] p-8 md:p-12 text-center">
            <h2 class="text-3xl md:text-4xl font-bold leading-tight tracking-[-0.015em] text-white">Ready for a Challenge?</h2>
            <p class="text-white/70 max-w-xl text-base md:text-lg">I built a tiny game to showcase problem-solving with a playful twist. Smash the bugs, beat the clock, brag later.</p>
            <div class="relative w-full max-w-xs h-auto select-none">
              <div class="relative w-full aspect-[9/19.5] bg-[#222] rounded-[2.2rem] border-[10px] border-black shadow-2xl overflow-hidden">
                <div class="absolute inset-[4px] bg-[#0f1626] rounded-[1.8rem] overflow-hidden">
                  <div class="w-full h-full flex items-center justify-center"><span class="text-white/60 text-sm">Tap “Play Now”</span></div>
                </div>
                <div class="absolute top-0 left-1/2 -translate-x-1/2 w-24 h-6 bg-black rounded-b-xl"></div>
                <div class="absolute right-[-3px] top-24 w-1 h-8 bg-black rounded-l-sm"></div>
                <div class="absolute right-[-3px] top-36 w-1 h-8 bg-black rounded-l-sm"></div>
                <div class="absolute left-[-3px] top-28 w-1 h-16 bg-black rounded-r-sm"></div>
              </div>
            </div>

            <button id="openGameBtn" class="flex min-w-[140px] items-center justify-center gap-2 rounded-lg h-12 px-6 bg-primary text-background-dark text-base font-bold tracking-[0.015em] hover:opacity-90 transition-opacity">
              <span class="material-symbols-outlined">gamepad</span>
              <span class="truncate">Play Now</span>
            </button>
          </section>

          <div id="gameModal" class="hidden fixed inset-0 z-[60] bg-black/70 backdrop-blur-sm">
            <div class="grid place-items-center h-[100svh] p-4 md:p-8">
            <div id="gameScaleBox" class="mx-auto relative" style="width:min(92vw,560px);">
              <div id="phoneAspect" class="relative aspect-[9/19.5]">
                <div class="absolute inset-0 bg-[#222] rounded-[2.4rem] border-[12px] border-black shadow-[0_20px_80px_rgba(0,0,0,0.8)] overflow-hidden">
                  <div class="absolute inset-[6px] bg-[#0b1220] rounded-[2rem] overflow-hidden">
                    <div class="absolute top-3 left-0 right-0 z-10 pointer-events-none flex items-center justify-center gap-3 select-none">
                      <div class="rounded-full bg-black/40 backdrop-blur px-4 py-2 text-white text-base md:text-lg font-bold shadow">⏱ <span id="hudTime">30</span>s</div>
                      <div class="rounded-full bg-black/40 backdrop-blur px-4 py-2 text-white text-base md:text-lg font-bold shadow">🪲 <span id="hudScore">0</span></div>
                    </div>
                    <canvas id="gameCanvas" class="absolute inset-0 w-full h-full z-0"></canvas>

                    <div id="overlay" class="absolute inset-0 flex flex-col items-center justify-center gap-3 bg-black/40 text-white">
                      <h3 id="overlayTitle" class="text-xl font-bold">Bug Smasher</h3>
                      <p id="overlaySubtitle" class="text-white/80 text-sm">Tap the bugs before the time runs out.</p>
                      <button id="startBtn" class="mt-1 rounded-lg px-5 h-11 bg-primary text-background-dark font-bold hover:opacity-90 transition-opacity">Start</button>
                    </div>
                  </div>

                  <div class="absolute top-0 left-1/2 -translate-x-1/2 w-28 h-7 bg-black rounded-b-xl"></div>
                  <div class="absolute right-[-4px] top-28 w-1 h-10 bg-black rounded-l-md"></div>
                  <div class="absolute right-[-4px] top-40 w-1 h-10 bg-black rounded-l-md"></div>
                  <div class="absolute left-[-4px] top-32 w-1 h-20 bg-black rounded-r-md"></div>
                </div>
              </div>
              <button id="closeGameBtn" class="absolute -top-4 -right-4 z-[70] bg-primary text-background-dark rounded-full p-3 shadow-lg hover:opacity-90 transition-opacity"><span class="material-symbols-outlined">close</span></button>
            </div>
            </div>
          </div>

          <section class="flex flex-col gap-8 px-4 py-10" id="contact">
            <div class="flex flex-col gap-2 text-center">
              <h2 class="text-white text-[22px] font-bold tracking-[-0.015em] pt-5">Get In Touch</h2>
              <p class="text-white/70 text-base">Have a project in mind or just want to say hi? Feel free to reach out!</p>
            </div>

            <form id="contactForm" action="https://formspree.io/f/xqagrndb" method="POST" class="flex flex-col gap-6 max-w-lg mx-auto w-full">
              <input type="text" name="_gotcha" class="hidden" tabindex="-1" autocomplete="off"/>

              <div class="flex flex-col gap-2">
                <label class="text-white/90 text-sm font-medium" for="name">Name</label>
                <input class="w-full bg-[#222f49] text-white border border-[#2d3c5b] rounded-lg h-12 px-4 focus:outline-none focus:ring-2 focus:ring-primary/50 transition-shadow" id="name" name="name" placeholder="Your Name" type="text" required/>
              </div>

              <div class="flex flex-col gap-2">
                <label class="text-white/90 text-sm font-medium" for="email">Email</label>
                <input class="w-full bg-[#222f49] text-white border border-[#2d3c5b] rounded-lg h-12 px-4 focus:outline-none focus:ring-2 focus:ring-primary/50 transition-shadow" id="email" name="email" placeholder="you@example.com" type="email" required/>
              </div>

              <div class="flex flex-col gap-2">
                <label class="text-white/90 text-sm font-medium" for="message">Message</label>
                <textarea class="w-full bg-[#222f49] text-white border border-[#2d3c5b] rounded-lg p-4 focus:outline-none focus:ring-2 focus:ring-primary/50 transition-shadow" id="message" name="message" placeholder="Your message here..." rows="5" required></textarea>
              </div>

              <input type="hidden" name="_subject" value="New message from davidnavarro.dev"/>
              <input type="hidden" name="_captcha" value="false"/>

              <button class="flex w-full cursor-pointer items-center justify-center rounded-lg h-12 px-5 bg-primary text-white text-base font-bold tracking-[0.015em] hover:bg-primary/90 transition-colors disabled:bg-primary/50 disabled:cursor-not-allowed" type="submit"><span class="truncate">Send Message</span></button>
              <p id="formStatus" class="text-sm text-white/80 hidden"></p>
            </form>
          </section>
        </main>

        <footer class="mt-16 md:mt-24 border-t border-white/10 px-4 sm:px-10 py-6">
          <div class="flex flex-col sm:flex-row items-center justify-between gap-4">
            <p class="text-white/60 text-sm">© 2024 David Navarro. All rights reserved.</p>
            <div class="flex gap-3">
              <a class="text-white/60 hover:text-white transition-colors" href="#"><span class="material-symbols-outlined text-2xl">code</span></a>
              <a class="text-white/60 hover:text-white transition-colors" href="#"><span class="material-symbols-outlined text-2xl">group</span></a>
            </div>
          </div>
        </footer>
      </div>
    </div>
  </div>
</div>
""".trimIndent()
}
