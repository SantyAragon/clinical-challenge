Vue.createApp({
    
    data() {
      return {
        passwordType: "password",
        loginEmail: "",
        loginContraseña: "",
        registroNombre: "",
        registroApellido: "",
        registroDni:"",
        registroEmail: "",
        registroContraseña: "",
        resetEmail:"",
        }

      },

    mounted(){


        // Loader
        $(document).ready(function preloaderSetup() {
            $(".st-perloader").fadeOut();
            $("st-perloader-in").delay(150).fadeOut("slow");
            })


        //Menu toggle-button for small screens
        $(document).ready(function (){
        $(".menu-icon").on("click", function() {
        $("nav ul").toggleClass("showing");
        });
        });
    },
  
    created(){

    },
  
    methods: {

      toggleType() {
        this.passwordType = this.passwordType === "password" ? "text" : "password";
      },
    
      registerForm(){
        let loginin = document.getElementById("login-in"),
        loginup = document.getElementById("login-up")
    
          loginin.classList.remove("block")
          loginup.classList.remove("none")
          loginin.classList.add("none")
          loginup.classList.add("block")
      },

      loginForm(){
        let loginin = document.getElementById("login-in"),
        loginup = document.getElementById("login-up")

          loginin.classList.remove("none")
          loginup.classList.remove("block")
          loginin.classList.add("block")
          loginup.classList.add("none")
      },

      forgotPassword(){
        let loginin = document.getElementById("login-in"),
        reset = document.getElementById("reset")
          loginin.classList.remove("block")
          reset.classList.remove("none")
          loginin.classList.add("none")
          reset.classList.add("block")
        },

        backToSignIn(){
          let loginin = document.getElementById("login-in"),
          reset = document.getElementById("reset")
            loginin.classList.remove("none")
            reset.classList.remove("block")
            loginin.classList.add("block")
            reset.classList.add("none") 
          },


      signIn() {},

      signUp(){},

      resetPassword(){},

    },

    computed: { 

    },

}).mount('#app')