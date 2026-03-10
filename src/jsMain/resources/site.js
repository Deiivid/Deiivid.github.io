(function () {
  function initTyping() {
    const el = document.getElementById('typingTitle');
    if (!el) return;

    const full = 'Android Developer';
    const typeDelay = 80;
    const holdDelay = 1200;
    const eraseDelay = 50;

    let i = 0;
    let dir = 1;

    function tick() {
      el.textContent = full.slice(0, i);
      if (dir === 1) {
        if (i < full.length) {
          i++;
          setTimeout(tick, typeDelay);
        } else {
          setTimeout(() => { dir = -1; tick(); }, holdDelay);
        }
      } else {
        if (i > 0) {
          i--;
          setTimeout(tick, eraseDelay);
        } else {
          dir = 1;
          setTimeout(tick, 300);
        }
      }
    }

    tick();
  }

  function initGame() {
    const openBtn = document.getElementById('openGameBtn');
    const modal = document.getElementById('gameModal');
    const closeBtn = document.getElementById('closeGameBtn');
    const canvas = document.getElementById('gameCanvas');

    if (!openBtn || !modal || !closeBtn || !canvas) return;

    const ctx = canvas.getContext('2d');
    const hudScore = document.getElementById('hudScore');
    const hudTime = document.getElementById('hudTime');
    const overlay = document.getElementById('overlay');
    const startBtn = document.getElementById('startBtn');
    const overlayTitle = document.getElementById('overlayTitle');
    const overlaySubtitle = document.getElementById('overlaySubtitle');

    let dpr = Math.max(1, window.devicePixelRatio || 1);
    let W = 0, H = 0, rafId = 0;
    let running = false, timeLeft = 30, score = 0, timerId = 0;

    let pointerInside = false;
    let mouseX = 0, mouseY = 0;
    let lastX = 0, lastY = 0;
    const splats = [];
    const popups = [];
    let shakeFrames = 0;

    const bugs = [];
    const BASE_BUGS = 6;
    const MAX_BUGS = 60;
    const SPEED_MIN = 40;
    const SPEED_MAX = 120;
    const RADIUS_CSS = 18;
    const HUD_TOP_CSS = 24;

    const GOLDEN_CHANCE = 0.06;
    const GREEN_CHANCE = 0.08;

    function resizeCanvas() {
      const rect = canvas.getBoundingClientRect();
      if (!rect.width || !rect.height) return;
      dpr = Math.max(1, window.devicePixelRatio || 1);
      W = Math.floor(rect.width * dpr);
      H = Math.floor(rect.height * dpr);
      canvas.width = W;
      canvas.height = H;
      ctx.setTransform(1, 0, 0, 1, 0, 0);
    }

    function onEnter() { pointerInside = true; canvas.style.cursor = 'none'; }
    function onLeave() { pointerInside = false; canvas.style.cursor = ''; }

    function onMove(e) {
      const r = canvas.getBoundingClientRect();
      const cx = (e.clientX - r.left) * dpr;
      const cy = (e.clientY - r.top) * dpr;
      lastX = mouseX; lastY = mouseY;
      mouseX = cx; mouseY = cy;
    }

    function fitPhone() {
      const box = document.getElementById('gameScaleBox');
      if (!box) return;
      const vw = Math.max(document.documentElement.clientWidth || 0, window.innerWidth || 0);
      const vv = window.visualViewport;
      const rawVh = vv ? vv.height : Math.max(document.documentElement.clientHeight || 0, window.innerHeight || 0);
      const safeW = vw * 0.92;
      const safeH = (rawVh - 80) * 0.92;
      const maxW = Math.min(safeW, 560);
      const aspect = 9 / 19.5;
      const widthFromHeight = safeH * aspect;
      const finalW = Math.floor(Math.max(280, Math.min(maxW, widthFromHeight)));
      box.style.width = finalW + 'px';
    }

    window.addEventListener('resize', () => {
      if (!modal.classList.contains('hidden')) {
        fitPhone();
        resizeCanvas();
      }
    }, { passive: true });

    const rnd = (min, max) => Math.random() * (max - min) + min;

    const chooseType = () => {
      const r = Math.random();
      if (r < GOLDEN_CHANCE) return 'golden';
      if (r < GOLDEN_CHANCE + GREEN_CHANCE) return 'green';
      return 'normal';
    };

    function spawnBug(type = chooseType()) {
      const r = RADIUS_CSS * dpr * (type === 'golden' ? 1.15 : type === 'green' ? 1.05 : 1);
      const vx = rnd(-1, 1) * rnd(SPEED_MIN, SPEED_MAX) * dpr / 60;
      const vy = rnd(-1, 1) * rnd(SPEED_MIN, SPEED_MAX) * dpr / 60;
      return {
        x: rnd(r, W - r),
        y: rnd(r + (HUD_TOP_CSS + 16) * dpr, H - r),
        vx, vy, r, type, alive: true
      };
    }

    function refillBase() {
      bugs.length = 0;
      for (let i = 0; i < BASE_BUGS; i++) bugs.push(spawnBug('normal'));
    }

    function resetGame() {
      score = 0;
      timeLeft = 30;
      hudScore.textContent = score;
      hudTime.textContent = timeLeft;
      refillBase();
    }

    function drawSwatter() {
      if (!pointerInside) return;
      const dx = mouseX - lastX, dy = mouseY - lastY;
      const ang = Math.atan2(dy, dx) + Math.PI * 0.15;
      const size = 42 * dpr;

      ctx.save();
      ctx.translate(mouseX, mouseY);
      ctx.rotate(ang);
      ctx.lineWidth = 6 * dpr;
      ctx.strokeStyle = 'rgba(255,255,255,0.25)';
      ctx.beginPath();
      ctx.moveTo(-size * 0.1, size * 0.6);
      ctx.lineTo(0, 0);
      ctx.stroke();

      const w = size * 0.6, h = size * 0.4;
      ctx.fillStyle = 'rgba(37,106,244,0.25)';
      ctx.strokeStyle = 'rgba(37,106,244,0.55)';
      ctx.lineWidth = 2 * dpr;
      ctx.beginPath();
      ctx.roundRect(0, -h, w, h, 6 * dpr);
      ctx.fill();
      ctx.stroke();

      ctx.strokeStyle = 'rgba(255,255,255,0.15)';
      ctx.lineWidth = 1 * dpr;
      for (let gx = 4 * dpr; gx < w; gx += 6 * dpr) {
        ctx.beginPath(); ctx.moveTo(gx, -h); ctx.lineTo(gx, 0); ctx.stroke();
      }
      for (let gy = -h + 4 * dpr; gy < 0; gy += 6 * dpr) {
        ctx.beginPath(); ctx.moveTo(0, gy); ctx.lineTo(w, gy); ctx.stroke();
      }
      ctx.restore();
    }

    function drawSplats(dt) {
      for (let i = splats.length - 1; i >= 0; i--) {
        const s = splats[i];
        s.t += dt * 0.0015;
        if (s.t >= 1) { splats.splice(i, 1); continue; }
        const r = (22 + 38 * s.t) * dpr;
        ctx.beginPath();
        ctx.arc(s.x, s.y, r, 0, Math.PI * 2);
        ctx.fillStyle = `rgba(225,29,72,${0.25 * (1 - s.t)})`;
        ctx.fill();
      }
    }

    function drawPopups(dt) {
      for (let i = popups.length - 1; i >= 0; i--) {
        const p = popups[i];
        p.t += dt * 0.002;
        if (p.t >= 1) { popups.splice(i, 1); continue; }
        const y = p.y - 26 * dpr * p.t * 1.2;
        ctx.globalAlpha = 1 - p.t;
        ctx.fillStyle = '#fff';
        ctx.font = `${14 * dpr}px system-ui, sans-serif`;
        ctx.textAlign = 'center';
        ctx.fillText(p.text, p.x, y);
        ctx.globalAlpha = 1;
      }
    }

    function drawBug(b) {
      ctx.beginPath();
      ctx.arc(b.x, b.y, b.r, 0, Math.PI * 2);
      if (b.type === 'golden') ctx.fillStyle = '#facc15';
      else if (b.type === 'green') ctx.fillStyle = '#22c55e';
      else ctx.fillStyle = '#e11d48';
      ctx.fill();

      ctx.beginPath();
      ctx.arc(b.x, b.y - b.r * 0.8, b.r * 0.55, 0, Math.PI * 2);
      ctx.fillStyle = (b.type === 'golden') ? '#b45309' : (b.type === 'green') ? '#065f46' : '#7f1d1d';
      ctx.fill();

      ctx.strokeStyle = '#0f0f0f';
      ctx.lineWidth = Math.max(1, 2 * dpr);
      for (let i = -1; i <= 1; i++) {
        ctx.beginPath();
        ctx.moveTo(b.x - b.r, b.y + i * 6 * dpr);
        ctx.lineTo(b.x - b.r - 8 * dpr, b.y + i * 10 * dpr);
        ctx.moveTo(b.x + b.r, b.y + i * 6 * dpr);
        ctx.lineTo(b.x + b.r + 8 * dpr, b.y + i * 10 * dpr);
        ctx.stroke();
      }

      if (b.type !== 'normal') {
        ctx.beginPath();
        ctx.arc(b.x, b.y, b.r * 1.35, 0, Math.PI * 2);
        ctx.strokeStyle = (b.type === 'golden') ? 'rgba(250,204,21,.45)' : 'rgba(34,197,94,.35)';
        ctx.lineWidth = 3 * dpr;
        ctx.stroke();
      }
    }

    function update(timestamp) {
      if (!update.prev) update.prev = timestamp || 0;
      const dt = Math.min(32, (timestamp || 0) - update.prev);
      update.prev = timestamp || 0;

      ctx.save();
      if (shakeFrames > 0) {
        const mag = 3 * dpr;
        ctx.translate((Math.random() - 0.5) * mag, (Math.random() - 0.5) * mag);
        shakeFrames--;
      }

      ctx.fillStyle = '#0b1626';
      ctx.fillRect(0, 0, W, H);

      drawSplats(dt);

      for (const b of bugs) {
        if (!b.alive) continue;
        b.x += b.vx;
        b.y += b.vy;
        if (b.x - b.r < 0 || b.x + b.r > W) b.vx *= -1;
        if (b.y - b.r < HUD_TOP_CSS * dpr || b.y + b.r > H) b.vy *= -1;
        drawBug(b);
      }

      drawPopups(dt);
      drawSwatter();
      ctx.restore();

      if (running) rafId = requestAnimationFrame(update);
    }

    function handleTap(x, y) {
      if (!running) return;
      for (let i = bugs.length - 1; i >= 0; i--) {
        const b = bugs[i];
        if (!b.alive) continue;
        const dx = x - b.x, dy = y - b.y;
        if (dx * dx + dy * dy <= b.r * b.r) {
          splats.push({ x, y, t: 0 });
          popups.push({ x, y: y - 10 * dpr, text: '+1', t: 0 });
          shakeFrames = 6;

          if (b.type === 'golden') {
            const aliveBefore = bugs.filter(bb => bb.alive).length;
            score += 5 + Math.max(0, aliveBefore - 1);
            hudScore.textContent = score;
            popups[popups.length - 1].text = `+${5 + Math.max(0, aliveBefore - 1)}`;
            refillBase();
          } else if (b.type === 'green') {
            score += 3;
            hudScore.textContent = score;
            popups[popups.length - 1].text = '+3';
            b.alive = false;
            const toAdd = Math.min(20, MAX_BUGS - bugs.filter(bb => bb.alive).length);
            for (let k = 0; k < toAdd; k++) bugs.push(spawnBug('normal'));
          } else {
            b.alive = false;
            score += 1;
            hudScore.textContent = score;
            setTimeout(() => {
              const idx = bugs.indexOf(b);
              if (idx >= 0) bugs[idx] = spawnBug();
            }, 200);
          }
          break;
        }
      }
    }

    function onCanvasClick(e) {
      const rect = canvas.getBoundingClientRect();
      handleTap((e.clientX - rect.left) * dpr, (e.clientY - rect.top) * dpr);
    }

    function onCanvasTouch(e) {
      const rect = canvas.getBoundingClientRect();
      const t = e.touches[0];
      handleTap((t.clientX - rect.left) * dpr, (t.clientY - rect.top) * dpr);
    }

    function startTimer() {
      clearInterval(timerId);
      timerId = setInterval(() => {
        timeLeft--;
        hudTime.textContent = timeLeft;
        if (timeLeft <= 0) endGame();
      }, 1000);
    }

    function startGame() {
      overlay.classList.add('hidden');
      running = true;
      resetGame();
      startTimer();
      cancelAnimationFrame(rafId);
      update();
    }

    function endGame() {
      running = false;
      clearInterval(timerId);
      overlayTitle.textContent = "Time's up!";
      overlaySubtitle.textContent = `Final score: ${score}`;
      startBtn.textContent = 'Play again';
      overlay.classList.remove('hidden');
    }

    function openModal() {
      modal.classList.remove('hidden');
      document.body.style.overflow = 'hidden';
      overlayTitle.textContent = 'Bug Smasher';
      overlaySubtitle.textContent = 'Tap the bugs before the time runs out.';
      startBtn.textContent = 'Start';
      overlay.classList.remove('hidden');
      fitPhone();
      requestAnimationFrame(() => {
        resizeCanvas();
        resetGame();
        canvas.style.cursor = 'none';
        ctx.fillStyle = '#0b1626';
        ctx.fillRect(0, 0, W, H);
        bugs.forEach(drawBug);
      });
    }

    function closeModal() {
      running = false;
      cancelAnimationFrame(rafId);
      clearInterval(timerId);
      canvas.style.cursor = '';
      modal.classList.add('hidden');
      document.body.style.overflow = '';
    }

    openBtn.addEventListener('click', openModal);
    closeBtn.addEventListener('click', closeModal);
    modal.addEventListener('click', (e) => { if (e.target === modal) closeModal(); });
    startBtn.addEventListener('click', startGame);

    canvas.addEventListener('mouseenter', onEnter, { passive: true });
    canvas.addEventListener('mouseleave', onLeave, { passive: true });
    canvas.addEventListener('mousemove', onMove, { passive: true });
    canvas.addEventListener('click', onCanvasClick, { passive: true });
    canvas.addEventListener('touchstart', onCanvasTouch, { passive: true });
  }

  function initContact() {
    const form = document.getElementById('contactForm');
    const statusEl = document.getElementById('formStatus');
    if (!form || !statusEl) return;

    function setStatus(text, ok = true) {
      statusEl.textContent = text;
      statusEl.classList.remove('hidden');
      statusEl.classList.toggle('text-green-400', ok);
      statusEl.classList.toggle('text-red-400', !ok);
    }

    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const data = new FormData(form);

      try {
        const res = await fetch(form.action, {
          method: 'POST',
          body: data,
          headers: { 'Accept': 'application/json' }
        });

        if (res.ok) {
          form.reset();
          setStatus('✅ Message sent! I will get back to you soon.', true);
        } else {
          const err = await res.json().catch(() => ({}));
          setStatus(err?.errors?.[0]?.message || '❌ Error sending the message. Please try again.', false);
        }
      } catch (err) {
        setStatus('❌ Network error. Please check your connection.', false);
      }
    });
  }

  window.addEventListener('load', () => {
    initTyping();
    initGame();
    initContact();
  });
})();
