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
    
      toggleClass(){
        const signup = document.getElementById("sign-up"),
        signin = document.getElementById("sign-in"),
        loginin = document.getElementById("login-in"),
        loginup = document.getElementById("login-up")
    
          signup.addEventListener("click", () => {
          loginin.classList.remove("block")
          loginup.classList.remove("none")
          loginin.classList.add("none")
          loginup.classList.add("block")
          })
          signin.addEventListener("click", () => {
          loginin.classList.remove("none")
          loginup.classList.remove("block")
          loginin.classList.add("block")
          loginup.classList.add("none")
          })
      },

      signIn() {},

      signUp(){},

    },

    computed: { 

    },

}).mount('#app')