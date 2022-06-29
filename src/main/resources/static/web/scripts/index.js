const app = Vue.createApp({
  data() {
    return {

    }
  },

  mounted() {

    //Scrolling Effect for nav
    $(window).on("scroll", function () {
      if ($(window).scrollTop()) {
        $('nav').addClass('black');
      } else {
        $('nav').removeClass('black');
      }
    });

    //Ripples effect -> cambiar efecto
    $('.header-container').ripples({
      resolution: 400,
      perturbance: 0.05,
    });

    // ===== Scroll to Top ==== 
    $(window).scroll(function () {
      if ($(this).scrollTop() >= 1080) {        // If page is scrolled more than 50px
        $('#return-to-top').fadeIn(500);    // Fade in the arrow
      } else {
        $('#return-to-top').fadeOut(500);   // Else fade out the arrow
      }
    });

    $('#return-to-top').click(function () {      // When arrow is clicked
      $('body,html').animate({
        scrollTop: 0                       // Scroll to top of body
      }, 500);
    });

    // toggle services
    $('.st-tabs.st-fade-tabs .st-tab-links a').on('click', function (e) {
      var currentAttrValue = $(this).attr('href');
      $('.st-tabs ' + currentAttrValue).fadeIn(400).siblings().hide();
      $(this).parents('li').addClass('active').siblings().removeClass('active');
      e.preventDefault();
    });

    const btns = document.querySelectorAll('button');
    btns.forEach((items) => {
      items.addEventListener('click', (evt) => {
        evt.target.classList.add('activeLoading');
      })
    });


    // Smooth Scrolling Using Navigation Menu
    // $(function() {
    //     $('a.page-scroll').bind('click', function(event) {
    //         var $anchor = $(this);
    //         $('html, body').stop().animate({
    //             scrollTop: $($anchor.attr('href')).offset().top
    //         }, 1500, 'easeInOutExpo');
    //         event.preventDefault();
    //     });
    // });

    // // Highlight the top nav as scrolling occurs
    // $('body').scrollspy({
    //     target: '.navbar-fixed-top'
    // })

    // // Closes the Responsive Menu on Menu Item Click
    // $('.navbar-collapse ul li a').click(function() {
    //     $('.navbar-toggle:visible').click();
    // });



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


  },

  created() {

  },

  methods: {

  },

  computed: {

  }

}).mount('#app')

