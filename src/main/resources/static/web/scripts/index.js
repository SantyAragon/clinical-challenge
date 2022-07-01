const app = Vue.createApp({
  data() {
    return {

      paciente: [],
    }
  },

  mounted() {

    //Menu toggle-button for small screens
    $(document).ready(function () {
      $(".menu-icon").on("click", function () {
        $("nav ul").toggleClass("showing");
      });
    });

    //Scrolling Effect for nav
    $(window).on("scroll", function () {
      if ($(window).scrollTop()) {
        $('nav').addClass('black');
      } else {
        $('nav').removeClass('black');
      }
    });

    //smooth scrolling
    $('.smoothscroll').on('click', function (e) {
      e.preventDefault();

      var target = this.hash,
        $target = $(target);

      $('html, body').stop().animate({
        'scrollTop': $target.offset().top
      }, 800, 'swing', function () {
        window.location.hash = target;
      });
    });

    //Ripples effect -> cambiar efecto
    $('.header-container').ripples({
      resolution: 800,
      perturbance: 0.05,
    });


    // toggle services
    $('.st-tabs.st-fade-tabs .st-tab-links a').on('click', function (e) {
      var currentAttrValue = $(this).attr('href');
      $('.st-tabs ' + currentAttrValue).fadeIn(400).siblings().hide();
      $(this).parents('li').addClass('active').siblings().removeClass('active');
      e.preventDefault();
    });


    // loading botton
    const btns = document.querySelectorAll('button');
    btns.forEach((items) => {
      items.addEventListener('click', (evt) => {
        evt.target.classList.add('activeLoading');
      })
    });

    // Loader
    $(document).ready(function preloaderSetup() {
      $(".st-perloader").fadeOut();
      $("st-perloader-in").delay(150).fadeOut("slow");
    });

    // toggle contacto
    $('#myTab a').on('click', function (e) {
      e.preventDefault()
      $(this).tab('show')
    })

    $(".button").click(function () {
      $(".active").removeClass("active");
      $(this).addClass("active");
    });

  },


  created() {

    axios.get('/api/pacientes/autenticado')
      .then(data => {
        this.paciente = data.data;
      })
      .catch(error => console.warn(error.message));

  },


  methods: {

  },

  computed: {

  }

}).mount('#app')