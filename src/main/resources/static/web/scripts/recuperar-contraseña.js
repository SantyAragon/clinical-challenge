Vue.createApp({
    
    data() {
      return {
        hideNewPass: "password",
        hideConfirmPass: "password",
        nuevaContraseña: "",
        confirmarContraseña:"",
        }

      },

    created(){

    },

    mounted(){

        // Loader
        $(document).ready(function preloaderSetup() {
            $(".st-perloader").fadeOut();
            $("st-perloader-in").delay(150).fadeOut("slow");
            })

    },
  
    methods: {

     
      toggleType() {
        this.hideNewPass = this.hideNewPass === "password" ? "text" : "password";
      },

      toggleType2() {
        this.hideConfirmPass = this.hideConfirmPass === "password" ? "text" : "password";
      },
    

    },

    computed: { 

    },

}).mount('#app')