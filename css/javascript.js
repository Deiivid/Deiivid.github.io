$(".hover").mouseleave(
    function () {
        $(this).removeClass("hover");
    }
)

if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
    // dark mode
    let el = document.getElementById('body');
    el.className = 'theme-dark';


}
    function toggleDarkMode(){
        let el = document.getElementById('body');
        if (el.className === 'theme-light'){
            el.className = 'theme-dark';
        } else {
            el.className = 'theme-light';
        }
    }

    // bg img
    VanillaTilt.init(document.querySelector(".bg-profile-img"), {
        scale: 1.2
    });
    
    new Typed('#typed', {
        strings: ['<strong><i>Mobile Developer</i></strong>'],
        typeSpeed: 80,
        backSpeed: 30,
        cursorChar: '<strong>_</strong>',
        shuffle: true,
        smartBackspace: false,
        loop: true
    });

    