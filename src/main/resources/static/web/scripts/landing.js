Vue.createApp({
    data() {
      return {
        client: [],

      }
    },

    mounted(){

      //Menu toggle-button for small screens
      $(document).ready(function (){
      $(".menu-icon").on("click", function() {
      $("nav ul").toggleClass("showing");
      });
      });

      //Scrolling Effect for nav
      $(window).on("scroll", function() {
      if($(window).scrollTop()) {
      $('nav').addClass('black');
      } else {
      $('nav').removeClass('black');
      }
      });

    },
    
    created() {
  
      axios.get('/api/clients/current')
      .then (data =>{
        this.client = data.data;
      })
      .catch(error => console.warn(error.message));
    },
  
    methods: {

    },

    computed: {
  
    }
  
  }).mount('#app')
  
